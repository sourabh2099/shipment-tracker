package com.shipment.track.shipment_tracker.enums;

public enum ShipmentStatus {
    CREATED("CREATED"),
    IN_TRANSIT("IN-TRANSIT"),
    PENDING("PENDING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    public final String label;

    ShipmentStatus(String label) {
        this.label = label;
    }

}
