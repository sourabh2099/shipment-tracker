package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.events.CreateShipmentsForUserEvent;
import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.pojo.AddressDto;
import com.shipment.track.shipment_tracker.pojo.CreateUserDto;
import com.shipment.track.shipment_tracker.pojo.error.ErrorResponse;
import com.shipment.track.shipment_tracker.service.CrudOperations;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class DataInputController {
    @Autowired
    private CrudOperations crudOperations;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Logger LOG = LoggerFactory.getLogger(DataInputController.class);

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult) {
        LOG.info("Got Request as {}", createUserDto);
        if (bindingResult.hasErrors()) {
            return respondWithError(bindingResult, "/v1/create-user");
        }
        User user = crudOperations.createUser(createUserDto);
        //event triggered to create shipments for the given user
        applicationEventPublisher.publishEvent(new CreateShipmentsForUserEvent(this, user));

        return ResponseEntity.ok(user);
    }
    // todo to create shipment for user return the tracking id as a part of shipment addition and address Id
//    @PostMapping("/create-shipment-for-user/{userId}")
//    public ResponseEntity<?> createShipment(){
//
//    }


    @GetMapping("/get-all-users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(crudOperations.getAllUsers());
    }

    @PostMapping(path = "/add-address/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAddressForUser(@RequestBody List<AddressDto> addressDtoList,
                                               @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok("");
    }

    private ResponseEntity<?> respondWithError(BindingResult bindingResult, String path) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(path);
        errorResponse.setErrorList(bindingResult.getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}
