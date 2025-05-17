package com.shipment.track.location.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface OsmService {
    Flux<JsonNode> getLocationData(MultiValueMap<String,String> locationQueryParams, String operation);

}
