package com.shipment.track.shipment_tracker.pojo;

import com.shipment.track.shipment_tracker.enums.DeliveryStatus;
import com.shipment.track.shipment_tracker.model.Address;
import com.shipment.track.shipment_tracker.model.Shipment;

public class TrackingDetailsDto {
    private Shipment shipment;
    private Address address;
    private Long id;
    private String Location;

    private DeliveryStatus deliveryStatus;

}
