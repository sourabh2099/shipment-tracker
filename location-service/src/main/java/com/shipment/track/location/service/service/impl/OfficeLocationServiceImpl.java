package com.shipment.track.location.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.track.location.service.documents.OfficeLocationDocument;
import com.shipment.track.location.service.repsitory.LocationRepository;
import com.shipment.track.location.service.repsitory.OfficeLocationRepository;
import com.shipment.track.location.service.service.OfficeLocationService;
import com.shipment.track.location.service.service.OsmService;
import com.shipment.track.location.service.utils.AppConstants;
import com.shipment.track.location.service.utils.enums.OsmQueryParams;
import com.shipment.track.shipment_tracker_pojo.pojo.config.LocationTrackerConfig;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.ClientAddressLocationDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeLocationDto;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Supplier;

@Service
public class OfficeLocationServiceImpl implements OfficeLocationService {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeLocationServiceImpl.class);

    private final LocationRepository locationRepository;
    private final OfficeLocationRepository officeLocationRepository;
    private final OsmService osmService;
    private final ObjectMapper objectMapper;

    public OfficeLocationServiceImpl(OsmService osmService,
                                     LocationRepository locationRepository,
                                     OfficeLocationRepository officeLocationRepository,
                                     ObjectMapper objectMapper) {
        this.osmService = osmService;
        this.locationRepository = locationRepository;
        this.officeLocationRepository = officeLocationRepository;
        this.objectMapper = objectMapper;
    }
    @Autowired
    private LocationTrackerConfig locationTrackerConfig;

    /**
     * @param officeDto
     * @return
     */
    @Override
    public Flux<JsonNode> possibleOfficeLocations(OfficeDto officeDto) {
        return findPossibleOfficeLocations(officeDto);
    }

    /**
     * @param officeLocationData
     * @return
     */
    @Override
    public Mono<OfficeLocationDocument> registerOffice(OfficeLocationDto officeLocationData) {
        Supplier<Mono<OfficeLocationDocument>> monoSupplier = checkIfOfficeExits(officeLocationData);
        return Mono.defer(monoSupplier);
    }

    /**
     * @param clientAddressLocationDto
     */
    @Override
    public Flux<Document> findNearByOfficeLocation(ClientAddressLocationDto clientAddressLocationDto) {
        return officeLocationRepository.findOfficeLocationByCoordinates(clientAddressLocationDto.longitude()
                , clientAddressLocationDto.latitude());

    }


    private Supplier<Mono<OfficeLocationDocument>> checkIfOfficeExits(OfficeLocationDto officeLocationData) {
        Supplier<Mono<OfficeLocationDocument>>[] supplierArr = new Supplier[1];
        supplierArr[0] = Mono::empty; // adding a default value so as to avoid NPE;
        officeLocationRepository.findOfficeLocationByCoordinatesAndType(
                        officeLocationData.locationData().get("lon").asLong(),
                        officeLocationData.locationData().get("lat").asLong(),
                        officeLocationData.officeDetailsInfo().officeType())
                .any(item -> Double.parseDouble(item.get("distanceBetween").toString()) < 10000)
                .subscribe(item -> {
                    LOG.info("{}",item);
                    if (Boolean.TRUE.equals(item)) {
                        supplierArr[0] = Mono::empty;
                    }else{
                        supplierArr[0] = () -> {
                            var officeLocationDocument = new OfficeLocationDocument();

                            officeLocationDocument.setOfficeCoordinates(
                                    new GeoJsonPoint(officeLocationData.locationData().get(AppConstants.LON).asDouble()
                                            , officeLocationData.locationData().get(AppConstants.LAT).asDouble()));
                            officeLocationDocument.setOfficeCountry(officeLocationData.officeDetailsInfo().officeCountry());
                            officeLocationDocument.setOfficeName(officeLocationData.officeDetailsInfo().officeName());
                            officeLocationDocument.setOfficeLocation(officeLocationData.officeDetailsInfo().officeLocation());
                            officeLocationDocument.setOfficeType(officeLocationData.officeDetailsInfo().officeType());
                            return officeLocationRepository.save(officeLocationDocument);
                        };
                    }
                }, throwable -> LOG.info("Found error while trying to fetch office records ", throwable));
        return supplierArr[0];
    }


    private Flux<JsonNode> findPossibleOfficeLocations(OfficeDto officeDto) {
        MultiValueMap<String, String> multiValueMap = OsmQueryParams.getValuesInMap();
        multiValueMap.put("q", List.of(officeDto.officeLocation()));
        return osmService.getLocationData(multiValueMap, "query")
                .map(item -> {
                    if (getLocationInfo(item, "display_name").equalsIgnoreCase(officeDto.officeCountry())
                            && getLocationInfo(item, "name").equalsIgnoreCase(officeDto.officeLocation())) {
                        throw new RuntimeException("Office Details already present ");
                    }
                    return item;
                }).onErrorStop();

    }

    private static String getLocationInfo(JsonNode item, String nodeKey) {
        String[] displayNames = item.path(nodeKey).asText("").split(",");
        return displayNames[displayNames.length - 1];
    }


}
