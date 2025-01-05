package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.service.CrudOperations;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateAddressDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.CreateShipmentDto;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.UpdateTrackingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class DataInputController {

    @Autowired
    private CrudOperations crudOperations;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Logger LOG = LoggerFactory.getLogger(DataInputController.class);


    @PostMapping("/create-shipment")
    public ResponseEntity<?> createShipmentForUser(@RequestBody CreateShipmentDto createShipmentDto)
            throws URISyntaxException {
        LOG.info("Got Request to create shipment for user {}",createShipmentDto);
        crudOperations.createShipment(createShipmentDto);
        return ResponseEntity.created(new URI("/create-shipment")).body("");
    }

    @PostMapping("/add-address-for-user")
    public ResponseEntity<?> addAddressForUser(@RequestBody CreateAddressDto createAddressDto){
        crudOperations.addAddressForUser(createAddressDto);
        return ResponseEntity.ok("");
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(crudOperations.getAllUsers());
    }


    @PutMapping(path = "/update-tracking-details",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTrackingDetails(List<UpdateTrackingDto> updateTrackingDto){
        crudOperations.updateShipmentStatus(updateTrackingDto);
        return ResponseEntity.ok("");
    }






}
