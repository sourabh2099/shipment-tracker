package com.shipment.track.shipment_tracker_pojo.pojo.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record OfficeLocationDto(JsonNode locationData,OfficeDto officeDetailsInfo) {
}
