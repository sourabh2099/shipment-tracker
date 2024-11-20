package com.shipment.track.shipment_tracker.enums;

public enum DeliveryStatus {

    IN_TRANSIT("IN TRANSIT"),
    OUT_FOR_DELIVERY("OUT FOR DELIVERY"),
    READY_TO_BE_SHIPPED("READY TO BE SHIPPED"),
    DELIVERED("DELIVERED");
    private String name;

    DeliveryStatus(String name) {
        this.name = name;
    }
}
