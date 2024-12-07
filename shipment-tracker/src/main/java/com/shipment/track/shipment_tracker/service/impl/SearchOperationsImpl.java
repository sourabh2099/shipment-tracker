package com.shipment.track.shipment_tracker.service.impl;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import com.shipment.track.shipment_tracker.repository.TrackingDetailsRepository;
import com.shipment.track.shipment_tracker.service.SearchOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchOperationsImpl implements SearchOperations {
    @Autowired
    private TrackingDetailsRepository trackingDetailsRepository;
    @Override
    public Optional<TrackingDetails> getTrackingDetails(Long trackingId) {
        return trackingDetailsRepository.findById(trackingId);
    }

    @Override
    public List<TrackingDetails> getAllTrackingDetails() {
        return trackingDetailsRepository.findAll();
    }
}
