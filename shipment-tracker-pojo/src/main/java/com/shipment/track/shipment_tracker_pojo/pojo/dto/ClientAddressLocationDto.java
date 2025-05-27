package com.shipment.track.shipment_tracker_pojo.pojo.dto;

public record ClientAddressLocationDto(String addressLine1,
                                       Long longitude,
                                       Long latitude,
                                       String state,
                                       String location,
                                       String pincode) {
 // TODO to update more pincode and address.
 // TODO the latitude and longitude might not be present at first
 // TODO location is the locality name
}
