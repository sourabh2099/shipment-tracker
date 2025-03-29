package com.shipment.track.location.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Indexes;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.shipment.track.location.service.documents.LocationDocument;
import com.shipment.track.location.service.repsitory.LocationRepository;
import com.shipment.track.location.service.service.OsmService;
import com.shipment.track.location.service.utils.AppUtils;
import com.shipment.track.shipment_tracker_pojo.pojo.config.LocationTrackerConfig;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.ErrorResponseDto;
import com.shipment.track.shipment_tracker_pojo.pojo.exceptions.IndexAllReadyFouncException;
import com.shipment.track.shipment_tracker_pojo.pojo.exceptions.NotSupportedOsmDataException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.shipment.track.location.service.utils.AppConstants.*;

@Service
public class OsmServiceImpl implements OsmService {
    private static final Logger LOG = LoggerFactory.getLogger(OsmServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final LocationRepository locationRepository;
    private WebClient webClient;
    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private LocationTrackerConfig locationTrackerConfig;

    public OsmServiceImpl(ObjectMapper objectMapper, LocationRepository locationRepository) {
        this.objectMapper = objectMapper;
        this.locationRepository = locationRepository;

    }


    @PostConstruct
    void init() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                );
        webClient = WebClient.builder().baseUrl("https://nominatim.openstreetmap.org")
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        initDatabaseIndex();
    }

    private void initDatabaseIndex() {
        MongoDatabase database = mongoClient.getDatabase(locationTrackerConfig.getDatabase().getName());
        MongoCollection<Document> collection = database.getCollection(locationTrackerConfig.getDatabase().getCollectionName());
        Flux.from(collection.listIndexes())
                .doOnNext(indexDoc -> {
                    String indexName = getNodeData(indexDoc, "name");
                    if ("coordinates_2dsphere".equals(indexName)) {
                        throw new IndexAllReadyFouncException("Index already found to be created in the database");
                    }
                })
                .doOnComplete(() -> create2DSphereIndex(collection))
                .doOnTerminate(() -> LOG.info("Stream Terminated !! ")).subscribe();

    }

    private String getNodeData(Document document, String fieldName) {
        if (document.get(fieldName) instanceof String) {
            return document.get(fieldName).toString();
        }
        if (Objects.isNull(document.get(fieldName))) {
            return null;
        }
        // todo to implement a recurrsive implementation to find node details
        return null;

    }

    private void create2DSphereIndex(MongoCollection<Document> collection) {
        Mono.from(collection.createIndex(Indexes.geo2dsphere(locationTrackerConfig.getDatabase()
                        .getSpatialIndexFieldName())))
                .log().subscribe();
    }

    @Override
    public Flux<?> getLocationData(MultiValueMap<String, String> locationQueryParams) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/search")
                                .queryParams(locationQueryParams).build()
                )
                .exchangeToFlux(clientResponse -> {
                            LOG.info("Request made to {} and statusCode {}"
                                    , clientResponse.request().getURI(), clientResponse.statusCode());
                            return clientResponse.bodyToFlux(JsonNode.class);
                        }
                )
                .flatMap(item -> Mono.just(item)
                        .doOnNext(this::saveLocationEntity)
                        .onErrorResume(NotSupportedOsmDataException.class::isInstance
                                , throwable -> {
                                    LOG.info("Found error while trying to process request");
                                    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                                            .errorType(throwable.getClass().toGenericString())
                                            .errorMessage(throwable.getLocalizedMessage())
                                            .build();
                                    return Mono.just(objectMapper.valueToTree(errorResponse));
                                })
                        .onErrorStop()
                )
                .onErrorContinue((throwable, o) -> LOG.info("On Error Continue {}", o));

    }

    private void saveLocationEntity(JsonNode locationData) {
        if (!AppUtils.checkOsmNodeType.test(locationData)) {
            throw new NotSupportedOsmDataException("Server Currently Accepting only OSM types of "
                    + SUPPORTED_OSM_TYPE + " received of type " + locationData.get(OSM_TYPE).asText(""));
        }
        LocationDocument document = new LocationDocument();
        document.setName(locationData.get(PLACE_NAME).asText());
        document.setDisplayName(locationData.get(DISPLAY_NAME).asText());
        document.setGeoData(new GeoJsonPoint(locationData.get(LAT).asDouble(), locationData.get(LON).asDouble()));
        document.setCreatedDate(LocalDateTime.now());
        locationRepository.save(document).subscribe(savedDocument -> LOG.info("Document saved is {}", savedDocument),
                error -> LOG.error("Error while saving the document", error));
    }
}
