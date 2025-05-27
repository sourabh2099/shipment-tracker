package com.shipment.track.location.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.shipment.track.location.service.documents.OfficeLocationDocument;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.ClientAddressLocationDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeLocationDto;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfficeLocationService {
    Flux<JsonNode> possibleOfficeLocations(OfficeDto officeDto);
    Mono<OfficeLocationDocument> registerOffice(OfficeLocationDto officeJsonNode);

    Flux<Document> findNearByOfficeLocation(ClientAddressLocationDto clientAddressLocationDto);
}
