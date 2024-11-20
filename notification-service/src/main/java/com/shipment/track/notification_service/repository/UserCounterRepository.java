package com.shipment.track.notification_service.repository;

import com.shipment.track.notification_service.entity.UserCounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCounterRepository extends CrudRepository<UserCounter, String> {
}
