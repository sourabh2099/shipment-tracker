package com.shipment.track.notification_service.repository;

import com.shipment.track.notification_service.entity.ClientCounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCounterRepository extends CrudRepository<ClientCounter,String> {
}
