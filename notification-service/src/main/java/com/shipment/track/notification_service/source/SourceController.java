package com.shipment.track.notification_service.source;

import com.shipment.track.notification_service.service.OrchestratorService;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class SourceController {
    private final OrchestratorService orchestratorService;
    private final Logger LOG = LoggerFactory.getLogger(SourceController.class);

    public SourceController(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }
    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/send-notification")
    public ResponseEntity<?> processSendNotificationRequest(@RequestBody NotificationMessage notificationMessage) {
        LOG.info("Got request to send Notification {}", notificationMessage);
        try {
            orchestratorService.processNotification(notificationMessage);
        } catch (Exception ex) {
            return respondWithError(notificationMessage, ex);
        }
        return ResponseEntity.ok("done");
    }

    private ResponseEntity<Exception> respondWithError(NotificationMessage notificationMessage, Exception ex) {
        // more conditional statements to be here for filtering response based on condition of the server.
        LOG.error("Found error while processing message ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
    }

    @GetMapping("/hello")
    public ResponseEntity<?> testController() {
        return ResponseEntity.ok("Got Request !!");
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail() throws MailException {
        LOG.info("sending email ");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("chowdhurysourabh58@gmail.com");
        message.setTo("sourabhchowdhury82@gmail.com");
        message.setSubject("Shipment status update");
        message.setText("hello world");
            javaMailSender.send(message);
        return ResponseEntity.accepted().body("Sent Mail");
    }

}
