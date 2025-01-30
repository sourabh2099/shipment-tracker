package com.shipment.track.location.service.controller;

import com.shipment.track.location.service.service.OsmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class LocationServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(LocationServiceController.class);
    private final OsmService osmService;

    public LocationServiceController(OsmService osmService) {
        this.osmService = osmService;
    }

    private void addValueToMap(String key, String value, MultiValueMap<String, String> map) {
        if (!Objects.isNull(value)) {
            map.put(key, List.of(value));
        }
        LOG.info("New Map Value {}", map);
    }


    @GetMapping("/get-location-coordinates")
    public ResponseEntity<?> getLocationCoordinates(@RequestParam(name = "location") String location,
                                                    @RequestParam(name = "addressDetails", required = false) String addressNumber,
                                                    @RequestParam(name = "limit", required = false) String limit,
                                                    @RequestParam(name = "polygon_svg", required = false) String polygon_svg) {
        MultiValueMap<String, String> locationQueryParams = new LinkedMultiValueMap<>();
        addValueToMap("q", location, locationQueryParams);
        addValueToMap("format","json",locationQueryParams);
        addValueToMap("addressDetails", addressNumber, locationQueryParams);
        addValueToMap("limit", limit, locationQueryParams);
        addValueToMap("polygon_svg", polygon_svg, locationQueryParams);
        return ResponseEntity.ok(osmService.getLocationData(locationQueryParams));
    }
}
