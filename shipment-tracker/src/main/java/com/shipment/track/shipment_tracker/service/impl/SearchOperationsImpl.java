package com.shipment.track.shipment_tracker.service.impl;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import com.shipment.track.shipment_tracker.model.User;
import com.shipment.track.shipment_tracker.repository.TrackingDetailsRepository;
import com.shipment.track.shipment_tracker.service.SearchOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class SearchOperationsImpl implements SearchOperations {
    @Autowired
    private TrackingDetailsRepository trackingDetailsRepository;
    @Override
    public Optional<TrackingDetails> getTrackingDetails(Long trackingId) {
        return trackingDetailsRepository.findById(trackingId);
    }

    @Override
    public List<TrackingDetails> getAllTrackingDetails() {
        return trackingDetailsRepository.findAll();
    }

    @Override
    @Transactional
    public void getShipmentsForUser() {
        User userData = getUserData();
        log.info("User trying to obtain information is {}",userData);
//        try(Stream<TrackingDetails> trackingDetailsStream = trackingDetailsRepository.findAllTrackingDetailsByUser(userData.getId())){
//            long count = trackingDetailsStream.peek(item -> log.info("Item {}",item)).count();
//            log.info("Found out count {}",count);
//        }
        List<TrackingDetails> allTrackingDetailsByUserList = trackingDetailsRepository.findAllTrackingDetailsByUserList(userData.getId());
        log.info("details : {}");

    }
    private User getUserData() {
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        return (User) authenticationContext.getPrincipal();
    }
}
