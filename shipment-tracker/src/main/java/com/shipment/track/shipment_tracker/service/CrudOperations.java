package com.shipment.track.shipment_tracker.service;

import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker_pojo.pojo.AddressDto;
import com.shipment.track.shipment_tracker_pojo.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateAddressDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateShipmentDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.UpdateTrackingDto;

import java.util.List;

public interface CrudOperations {
    User createUser(CreateUserDto user);
    List<User> getAllUsers();
    void updateShipmentStatusOnDelay();

    void createShipment(CreateShipmentDto createShipmentDto);

    void addAddressForUser(CreateAddressDto createAddressDto);

    void updateShipmentStatus(List<UpdateTrackingDto> updateTrackingDto);
}
