package com.shipment.track.shipment_tracker.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.track.shipment_tracker.enums.DeliveryStatus;
import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import com.shipment.track.shipment_tracker.model.Package;
import com.shipment.track.shipment_tracker.model.*;
import com.shipment.track.shipment_tracker.repository.*;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import com.shipment.track.shipment_tracker_pojo.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateAddressDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateShipmentDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.UpdateTrackingDto;
import com.shipment.track.shipment_tracker_pojo.pojo.exceptions.RecordNotFoundException;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
    private AddressRepository addressRepository;
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private EntityManager entityManager;


    private final Function<Long, Address> getAddressWithId = id -> addressRepository.findById(id).orElseThrow();

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
                item.setDeliveryStatus(DeliveryStatus.DELAYED);
                trackingDetailsList.add(item);
            }).count();
        }
        List<TrackingDetails> trackingDetails = trackingDetailsRepository.saveAll(trackingDetailsList);
        LOG.info("Updated tracking details of size {}", trackingDetails.size());
    }

    @Override
    @Transactional
    public void createShipment(CreateShipmentDto createShipmentDto) {
        User userData = getUserData();
        Shipment shipmentEntity = getShipmentEntity(createShipmentDto, userData);
        List<Package> packageList = createShipmentDto.getPackages().stream().map(packageItem -> {
                    Package aPackage = objectMapper.convertValue(packageItem, Package.class);
                    aPackage.setShipment(shipmentEntity);
                    return aPackage;
                }
        ).toList();
        packageRepository.saveAll(packageList);
        TrackingDetails trackingInfo = createTrackingInfo(shipmentEntity, createShipmentDto);
        trackingDetailsRepository.save(trackingInfo);

    }

    @Override
    public void addAddressForUser(CreateAddressDto createAddressDto) {
        User userData = getUserData();
        Address savedAddress = addressRepository.save(Address.builder()
                .user(userData)
                .addressLine1(createAddressDto.getAddressLine1())
                .addressLine2(createAddressDto.getAddressLine2())
                .pincode(createAddressDto.getPincode())
                .state(createAddressDto.getState())
                .build());
        LOG.info("Saved address with details as follows {}", savedAddress);

    }

    @Override
    public void updateShipmentStatus(List<UpdateTrackingDto> updateTrackingDto) {
        // updates the shipment with statuses as a workflow of (PENDING -> CREATED -> IN-TRANSIT -> DELIVERED)
        // if cancel comes to this method throw CANCEL requested as it will be served from other endpoint
        User userData = getUserData();
        updateTrackingDto = updateTrackingDto.stream()
                .filter(item -> shipmentRepository.findById(item.getId())
                        .orElseThrow(
                                () -> new RecordNotFoundException("Record not found with the given record"))
                        .getUser().getEmail().equals(userData.getEmail()))
                .filter(trackingDto -> ShipmentStatus.CANCELLED.equals(ShipmentStatus.valueOf(
                        trackingDto.getShipmentStatus().trim().toUpperCase())))
                .collect(Collectors.toList());
        for (UpdateTrackingDto trackingDto : updateTrackingDto) {
            Optional<Shipment> optionalShipment = shipmentRepository.findById(trackingDto.getId());
            if (optionalShipment.isPresent()) {
                Shipment shipment = optionalShipment.get();
                List<TrackingDetails> trackinngDetailsByShipmentId =
                        trackingDetailsRepository.findTrackinngDetailsByShipmentId(shipment.getId());
                TrackingDetails trackingDetails = trackinngDetailsByShipmentId.stream()
                        .max((a, b) -> Math.toIntExact(a.getId() - b.getId())).orElseThrow();
                // detaching em to avoid duplicate lines of code
                entityManager.detach(trackingDetails);
                trackingDetails.setMessage(trackingDto.getMessage());
                trackingDetails.setId(null);
                TrackingDetails newTrackingDetails = trackingDetailsRepository.save(trackingDetails);
                LOG.info("updated tracking details for user {} with {}", userData.getName(), newTrackingDetails);
            } else {
                LOG.debug("Shipment not found with id {}", trackingDto.getId());
            }
        }
    }

    private TrackingDetails createTrackingInfo(Shipment shipmentEntity, CreateShipmentDto createShipmentDto) {
        TrackingDetails trackingDetails = new TrackingDetails();
        trackingDetails.setLocation(shipmentEntity.getOriginAddress().getState()); // setting the initial origin state
        trackingDetails.setDeliveryStatus(DeliveryStatus.READY_TO_BE_SHIPPED);
        trackingDetails.setShipment(shipmentEntity);
        return trackingDetails;
    }

    private Shipment getShipmentEntity(CreateShipmentDto createShipmentDto, User userData) {
        Shipment shipment = new Shipment();
        shipment.setDestinationAddress(getAddressWithId.apply(createShipmentDto.getDestinationAddress()));
        shipment.setOriginAddress(getAddressWithId.apply(createShipmentDto.getOriginAddress()));
        shipment.setUser(userData);
        shipmentRepository.save(shipment);
        return shipment;
    }

    private User getUserData() {
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        return (User) authenticationContext.getPrincipal();
    }


}
