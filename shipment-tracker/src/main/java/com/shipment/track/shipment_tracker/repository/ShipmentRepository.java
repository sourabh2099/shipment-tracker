package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.Shipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment,Long> {
    @EntityGraph(attributePaths =  {"originAddress", "destinationAddress"})
    Optional<Shipment> findById(Long id);
}
