package com.shipment.track.location.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.shipment.track.location.service.documents.LocationDocument;
import com.shipment.track.location.service.repsitory.LocationRepository;
import com.shipment.track.location.service.service.OsmService;
import com.shipment.track.location.service.utils.AppUtils;
import com.shipment.track.shipment_tracker_pojo.pojo.exceptions.NotSupportedOsmDataException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.shipment.track.location.service.utils.AppConstants.*;

@Service
public class OsmServiceImpl implements OsmService {
    private static final Logger LOG = LoggerFactory.getLogger(OsmServiceImpl.class);
    private WebClient webClient;

    @Autowired
    private LocationRepository locationRepository;


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
    }

    @Override
    public Flux<?> getLocationData(MultiValueMap<String, String> locationQueryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                            URI build = uriBuilder.path("/search")
                                    .queryParams(locationQueryParams).build();
                            LOG.info("URI : {}", build);
                            return build;
                        }
                )
                .exchangeToFlux(clientResponse -> {
                            LOG.info("Request made to {} and statusCode {}"
                                    , clientResponse.request().getURI(), clientResponse.statusCode());
                            return clientResponse.bodyToFlux(JsonNode.class);
                        }
                )
                .doOnNext(this::createLocationEntity)
                .doOnError(error -> LOG.error("Encountered error while processing", error.getCause()));

    }

    private void createLocationEntity(JsonNode locationData) {
        LOG.info("create Location entity called {}", locationData);
        if (!AppUtils.checkOsmNodeType.test(locationData)) {
            throw new NotSupportedOsmDataException("Server Currently Accepting only OSM types of "
                    + SUPPORTED_OSM_TYPE + " received of type" + locationData.get(OSM_TYPE));
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
