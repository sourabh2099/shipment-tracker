package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
