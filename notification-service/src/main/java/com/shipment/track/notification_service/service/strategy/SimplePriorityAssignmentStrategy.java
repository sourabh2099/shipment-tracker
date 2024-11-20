package com.shipment.track.notification_service.service.strategy;

import com.shipment.track.notification_service.dto.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimplePriorityAssignmentStrategy implements PriorityAssignmentStrategy {
    private final Logger LOG = LoggerFactory.getLogger(SimplePriorityAssignmentStrategy.class);

    /**
     * <ol>
     *     <li>Transactional -> Priority 1</li>
     *     <li>Informational -> Priority 2</li>
     *     <li>Promotional -> Priority 3</li>
     * </ol>
     *
     * @param notificationMessage
     * @return
     */
    @Override
    public NotificationMessage assignPriority(NotificationMessage notificationMessage) {
        switch (notificationMessage.getMessageType()) {
            case INFORMATION -> notificationMessage.setPriority(1);
            case PROMOTIONAL -> notificationMessage.setPriority(2);
            default -> notificationMessage.setPriority(3);
        }
        return notificationMessage;
    }
}
