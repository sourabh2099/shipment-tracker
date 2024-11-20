package com.shipment.track.shipment_tracker.service;

import com.shipment.track.shipment_tracker.model.TrackingDetails;

import java.util.Optional;

public interface SearchOperations {
    Optional<TrackingDetails> getTrackingDetails(Long trackingId);
}
