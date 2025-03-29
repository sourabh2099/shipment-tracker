package com.shipment.track.notification_service.service;


import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;

public interface ReceiveNotification {
    void receive(NotificationMessage message);
}
