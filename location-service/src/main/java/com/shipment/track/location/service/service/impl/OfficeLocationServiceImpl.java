package com.shipment.track.location.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.shipment.track.location.service.documents.OfficeLocationDocument;
import com.shipment.track.location.service.repsitory.LocationRepository;
import com.shipment.track.location.service.repsitory.OfficeLocationRepository;
import com.shipment.track.location.service.service.OfficeLocationService;
import com.shipment.track.location.service.service.OsmService;
import com.shipment.track.location.service.utils.AppConstants;
import com.shipment.track.location.service.utils.enums.OsmQueryParams;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeLocationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OfficeLocationServiceImpl implements OfficeLocationService {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeLocationServiceImpl.class);

    private final LocationRepository locationRepository;
    private final OfficeLocationRepository officeLocationRepository;
    private final OsmService osmService;

    public OfficeLocationServiceImpl(OsmService osmService,
                                     LocationRepository locationRepository,
                                     OfficeLocationRepository officeLocationRepository) {
        this.osmService = osmService;
        this.locationRepository = locationRepository;
        this.officeLocationRepository = officeLocationRepository;
    }

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
        var officeLocationDocument = new OfficeLocationDocument();
        officeLocationDocument.setOfficeCoordinates(
                new GeoJsonPoint(officeLocationData.locationData().get(AppConstants.LON).asDouble()
                ,officeLocationData.locationData().get(AppConstants.LAT).asDouble()));
        officeLocationDocument.setOfficeCountry(officeLocationData.officeDetailsInfo().officeCountry());
        officeLocationDocument.setOfficeName(officeLocationData.officeDetailsInfo().officeName());
        officeLocationDocument.setOfficeLocation(officeLocationData.officeDetailsInfo().officeLocation());

        return officeLocationRepository.save(officeLocationDocument);
    }



    private Flux<JsonNode> findPossibleOfficeLocations(OfficeDto officeDto) {
        MultiValueMap<String, String> multiValueMap = OsmQueryParams.getValuesInMap();
        multiValueMap.put("q", List.of(officeDto.officeLocation()));
        return osmService.getLocationData(multiValueMap, "query")
                .map(item -> {
                    if(getLocationInfo(item,"display_name").equalsIgnoreCase(officeDto.officeCountry())
                        && getLocationInfo(item,"name").equalsIgnoreCase(officeDto.officeLocation())){
                        throw new RuntimeException("Office Details already present ");
                    }
                    return item;
                }).onErrorStop();

    }

    private static String getLocationInfo(JsonNode item,String nodeKey) {
        String[] displayNames = item.path(nodeKey).asText("").split(",");
        return displayNames[displayNames.length - 1];
    }


}
