package com.shipment.track.notification_service.service;

import com.shipment.track.notification_service.dto.NotificationMessage;

public interface OrchestratorService {
    Object processNotification(NotificationMessage notificationMessage);
}
