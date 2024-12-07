package com.shipment.track.shipment_tracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import com.shipment.track.shipment_tracker.model.Shipment;
import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.pojo.AddressDto;
import com.shipment.track.shipment_tracker.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker.repository.ShipmentRepository;
import com.shipment.track.shipment_tracker.repository.UserRepository;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrudOperationsImpl implements CrudOperations {
    private static final Logger LOG = LoggerFactory.getLogger(CrudOperationsImpl.class);
    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public CrudOperationsImpl(UserRepository userRepository,
                              ShipmentRepository shipmentRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .email(createUserDto.getEmail())
                .name(createUserDto.getFullName())
                .password(createUserDto.getPassword())
                .build();
        LOG.info("Saving user Into Db {}", user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateShipmentDetailsWithOrdinalData() {
//        ShipmentStatus[] shipmentStatuses = ShipmentStatus.values();
//
//        shipmentRepository.saveAll(shipmentList);
    }


}
