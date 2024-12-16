package com.shipment.track.shipment_tracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.track.shipment_tracker.enums.DeliveryStatus;
import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import com.shipment.track.shipment_tracker.model.Shipment;
import com.shipment.track.shipment_tracker.model.TrackingDetails;
import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.pojo.AddressDto;
import com.shipment.track.shipment_tracker.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker.repository.ShipmentRepository;
import com.shipment.track.shipment_tracker.repository.TrackingDetailsRepository;
import com.shipment.track.shipment_tracker.repository.UserRepository;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CrudOperationsImpl implements CrudOperations {
    private static final Logger LOG = LoggerFactory.getLogger(CrudOperationsImpl.class);
    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;
    private final TrackingDetailsRepository trackingDetailsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;
    private static List<TrackingDetails> trackingDetailsList = new ArrayList<>();

    public CrudOperationsImpl(UserRepository userRepository,
                              ShipmentRepository shipmentRepository,
                              TrackingDetailsRepository trackingDetailsRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.trackingDetailsRepository = trackingDetailsRepository;
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .email(createUserDto.getEmail())
                .name(createUserDto.getFullName())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .build();
        LOG.info("Saving user Into Db {}", user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void updateShipmentStatusOnDelay() {
        try (Stream<TrackingDetails> stream = trackingDetailsRepository.findAllTrackingDetails("PENDING")) {
            stream.peek(item -> {
                LOG.info("Data streamed {}", item);
                entityManager.detach(item);
                item.setDeliveryStatus(DeliveryStatus.DELAYED);
                trackingDetailsList.add(item);
            }).count();
        }
        List<TrackingDetails> trackingDetails = trackingDetailsRepository.saveAll(trackingDetailsList);
        LOG.info("Updated tracking details of size {}",trackingDetails.size());
    }


}
