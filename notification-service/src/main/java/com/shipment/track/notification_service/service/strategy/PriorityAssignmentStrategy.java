package com.shipment.track.notification_service.service.strategy;

import com.shipment.track.notification_service.dto.NotificationMessage;

public interface PriorityAssignmentStrategy {
    NotificationMessage assignPriority(NotificationMessage notificationMessage);
}
