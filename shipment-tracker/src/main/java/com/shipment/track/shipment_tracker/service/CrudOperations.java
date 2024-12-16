package com.shipment.track.shipment_tracker.service;

import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.pojo.AddressDto;
import com.shipment.track.shipment_tracker.pojo.CreateUserDto;

import java.util.List;

public interface CrudOperations {
    User createUser(CreateUserDto user);
    List<User> getAllUsers();
    void updateShipmentStatusOnDelay();
}
