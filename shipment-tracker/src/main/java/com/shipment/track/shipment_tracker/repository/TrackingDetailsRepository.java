package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingDetailsRepository extends JpaRepository<TrackingDetails, Long> {
}
