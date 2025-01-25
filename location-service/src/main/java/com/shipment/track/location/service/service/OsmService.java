package com.shipment.track.location.service.service;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface OsmService {
    Flux<?> getLocationData(MultiValueMap<String,String> locationQueryParams);
}
