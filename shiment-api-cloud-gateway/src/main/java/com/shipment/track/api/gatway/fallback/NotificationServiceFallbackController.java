package com.shipment.track.api.gatway.fallback;

import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class NotificationServiceFallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceFallbackController.class);

    @GetMapping("/notification-service-fallback")
    public ResponseEntity<?> notificationServiceFallback(){
        LOG.info("Notification Service Fallback called ");
        return ResponseEntity.of(Optional.of("notification-service-fallback"));
    }
}
