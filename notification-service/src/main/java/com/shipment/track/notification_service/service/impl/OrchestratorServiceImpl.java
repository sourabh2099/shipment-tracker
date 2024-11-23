package com.shipment.track.notification_service.service.impl;

import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.entity.ClientCounter;
import com.shipment.track.notification_service.queue.MessageQueue;
import com.shipment.track.notification_service.repository.ClientCounterRepository;
import com.shipment.track.notification_service.repository.UserCounterRepository;
import com.shipment.track.notification_service.service.EmailTemplateService;
import com.shipment.track.notification_service.service.OrchestratorService;
import com.shipment.track.notification_service.service.strategy.PriorityAssignmentStrategy;
import com.shipment.track.notification_service.validators.Validator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class OrchestratorServiceImpl implements OrchestratorService {
    private static final Logger LOG = LoggerFactory.getLogger(OrchestratorServiceImpl.class);
    private final PriorityAssignmentStrategy priorityAssignmentStrategy;
    private final Validator<NotificationMessage> validator;
    volatile Queue<NotificationMessage> queue = MessageQueue.getInstance().getNotificationQueue();
    @Autowired
    private ClientCounterRepository clientCounterRepository;
    @Autowired
    private UserCounterRepository userCounterRepository;
    @Autowired
    private EmailTemplateService emailTemplateService;

    public OrchestratorServiceImpl(PriorityAssignmentStrategy priorityAssignmentStrategy,
                                   Validator<NotificationMessage> validator) {
        this.priorityAssignmentStrategy = priorityAssignmentStrategy;
        this.validator = validator;
    }
    private Map<String,ClientCounter> clientDb = new HashMap<>();
    private Map<String, Long> checkForClientLimits;
    @PostConstruct
    private void init() {
        checkForClientLimits = Map.of("new-shipment-tracker", 100L, "", 100L);
    }
    // false if the time is less than 60 sec || true if diff greater than 60 sec
    Predicate<ClientCounter> timeCheck = (clientCounter) -> {
        LOG.info(" {} ", Instant.now().toEpochMilli() - clientCounter.getStartTimeInMillis());
        return (Instant.now().toEpochMilli() - clientCounter.getStartTimeInMillis()) > 6000;
    };
    //
    Predicate<ClientCounter> countCheck = clientCounter ->
            clientCounter.getCount() > checkForClientLimits.get(clientCounter.getClientName());

    Consumer<ClientCounter> incrementClientCounter = clientCounter -> {
        clientDb.computeIfPresent(clientCounter.getClientName(), (k, v) -> {
                    v.setCount(v.getCount() + 1);
                    v.setStartTimeInMillis(Instant.now().toEpochMilli());
                    return v;
                }
        );
        LOG.info("clientName : {} , hitsLimit: {}",clientCounter.getClientName(),clientCounter.getCount());
    };


    @Override
    public Object processNotification(NotificationMessage notificationMessage) {
        validator.validate(notificationMessage);
        notificationMessage = priorityAssignmentStrategy.assignPriority(notificationMessage);
        final String emailMessage = emailTemplateService.generateEmailTemplate(notificationMessage);
        // after the assignment of the priority we need a decision engine whether to send the request to queue or not
        boolean sent = checkAndSendNotification(notificationMessage);
        return null;
    }

    private boolean checkAndSendNotification(NotificationMessage notificationMessage) {
        LOG.info("checkAndSendNotification");
        ClientCounter clientCounter = getClientFromCache(notificationMessage);
        boolean timeCheckres = timeCheck.test(clientCounter);
        boolean clientCheckres = countCheck.test(clientCounter);
        LOG.info("{} , {}", timeCheckres, clientCheckres);
        if (!timeCheckres && clientCheckres) {
            LOG.info("Rejecting Request as the limit is exhausted");
            return false;
        }
        if (timeCheckres) {
            clientCounter.setCount(1L);
        }
        incrementClientCounter.accept(clientCounter);
        LOG.info("Processing to send message {} for client {}", notificationMessage.getMessage()
                , notificationMessage.getClientName());
        queue.add(notificationMessage);
        return true;
    }

    private  ClientCounter getClientFromCache(NotificationMessage notificationMessage) {
        ClientCounter build = ClientCounter.builder()
                .clientName(notificationMessage.getClientName())
                .startTimeInMillis(Instant.now().toEpochMilli())
                .count(0L).build();
        if(!clientDb.containsKey(notificationMessage.getClientName())){
            clientDb.put(notificationMessage.getClientName(),build);
            return clientDb.get(notificationMessage.getClientName());
        }
        return clientDb.get(notificationMessage.getClientName());
    }


}
