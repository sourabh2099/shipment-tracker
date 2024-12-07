package com.shipment.track.shipment_tracker.sources;

import com.shipment.track.shipment_tracker.service.SearchOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/v1")
public class SearchController {
    private static Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    SearchOperations searchOperations;

    @GetMapping("/getTrackingDetails/{trackingId}")
    public ResponseEntity<?> getShipmentDetailsByTrackingId(@PathVariable("trackingId") String trackingId) {
        LOG.info("Finding details for Shipment for TrackingId : {}", trackingId);
        return ResponseEntity.of(searchOperations.getTrackingDetails(Long.parseLong(trackingId)));

    }
    @GetMapping("/all-tracking-details")
    public ResponseEntity<?> getAlltrackingDetails(){
        LOG.info("Find all the available tracking details !!");
        return ResponseEntity.ok(searchOperations.getAllTrackingDetails());
    }

}
