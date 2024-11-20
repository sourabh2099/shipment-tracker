package com.shipment.track.notification_service.service.impl;

import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.entity.ClientCounter;
import com.shipment.track.notification_service.queue.MessageQueue;
import com.shipment.track.notification_service.repository.ClientCounterRepository;
import com.shipment.track.notification_service.repository.UserCounterRepository;
import com.shipment.track.notification_service.service.OrchestratorService;
import com.shipment.track.notification_service.service.strategy.PriorityAssignmentStrategy;
import com.shipment.track.notification_service.validators.Validator;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
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

    public OrchestratorServiceImpl(PriorityAssignmentStrategy priorityAssignmentStrategy,
                                   Validator<NotificationMessage> validator) {
        this.priorityAssignmentStrategy = priorityAssignmentStrategy;
        this.validator = validator;
    }

    private Map<String, Integer> checkForClientLimits;

    @PostConstruct
    private void init() {
        checkForClientLimits = Map.of("new-shipment-tracker", 100, "", 100);
    }

    Predicate<ClientCounter> timeCheck = (clientCounter) ->{
        LOG.info(" {} ",Instant.now().toEpochMilli() - clientCounter.getStartTimeInMillis());
        return (Instant.now().toEpochMilli() - clientCounter.getStartTimeInMillis()) < 6000;
    };

    Predicate<ClientCounter> countCheck = clientCounter ->
            clientCounter.getCount() > checkForClientLimits.get(clientCounter.getClientName());

    @Override
    public Object processNotification(NotificationMessage notificationMessage) {
        validator.validate(notificationMessage);
        notificationMessage = priorityAssignmentStrategy.assignPriority(notificationMessage);
        // after the assignment of the priority we need a decision engine whether to send the request to queue or not
        boolean sent = checkAndSendNotification(notificationMessage);
        return null;
    }

    private boolean checkAndSendNotification(NotificationMessage notificationMessage) {
        LOG.info("checkAndSendNotification");
        ClientCounter clientCounter = (ClientCounter) Optional.empty()
                .orElse(ClientCounter.builder()
                        .clientName(notificationMessage.getClientName())
                        .startTimeInMillis(Instant.now().toEpochMilli())
                        .count(1L).build());
        boolean timecheckres = ! timeCheck.test(clientCounter);
        boolean clientCheckres = countCheck.test(clientCounter);
        LOG.info("{} , {}", timecheckres, clientCheckres);
        if (timecheckres || clientCheckres) {
            LOG.info("Rejecting Request as the limit is exhausted");
            return false;
        }
        if (timecheckres) {
            clientCounter.setCount(1L);
        }
        LOG.info("Processing to send message {} for client {}", notificationMessage.getMessage()
                , notificationMessage.getClientName());
        queue.add(notificationMessage);
        return true;
    }

}
