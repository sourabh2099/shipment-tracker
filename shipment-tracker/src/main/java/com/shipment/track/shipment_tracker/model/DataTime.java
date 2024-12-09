package com.shipment.track.shipment_tracker.model;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class DataTime {
    @CreationTimestamp
    @Column(updatable = false,name = "created_at")
    private LocalDateTime createdTime;
}
