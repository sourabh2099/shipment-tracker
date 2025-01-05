package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import com.shipment.track.shipment_tracker.service.SearchOperations;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SearchController {
    @Autowired
    private SearchOperations searchOperations;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("/getTrackingDetails/{trackingId}")
    public ResponseEntity<?> getShipmentDetailsByTrackingId(@PathVariable("trackingId") String trackingId) {
        LOG.info("Finding details for Shipment for TrackingId : {}", trackingId);
        return ResponseEntity.of(searchOperations.getTrackingDetails(Long.parseLong(trackingId)));

    }

    @GetMapping("/all-tracking-details")
    public ResponseEntity<?> getAlltrackingDetails() {
        LOG.info("Find all the available tracking details !!");
        return ResponseEntity.ok(searchOperations.getAllTrackingDetails());
    }

    @GetMapping("/get-shipments-for-user")
    public ResponseEntity<?> getShipmentsForUser(){
        List<TrackingDetails> shipmentsForUser = searchOperations.getShipmentsForUser();
        log.info("Shipments {}",shipmentsForUser);
        return ResponseEntity.ok(shipmentsForUser);
    }

}
