package com.shipment.track.shipment_tracker.model;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class DataTime {
    @CreationTimestamp
    private LocalDateTime createdTime;
}
