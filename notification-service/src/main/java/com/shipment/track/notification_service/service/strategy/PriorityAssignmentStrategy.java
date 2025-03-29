package com.shipment.track.notification_service.service.strategy;


import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;

public interface PriorityAssignmentStrategy {
    NotificationMessage assignPriority(NotificationMessage notificationMessage);
}
