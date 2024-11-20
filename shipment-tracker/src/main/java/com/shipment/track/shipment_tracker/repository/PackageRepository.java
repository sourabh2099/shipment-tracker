package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
}
