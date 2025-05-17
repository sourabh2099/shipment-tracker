package com.shipment.track.notification_service.queue;


import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class MessageQueue {
    // Making class structure as singleton as we want to use only one queue for filtering
    // and ordering of messages based on priority and client replenish rates.
    private final Queue<NotificationMessage> notificationQueue;

    private static class InstanceHolder {

        private static final MessageQueue INSTANCE = new MessageQueue();
    }

    private MessageQueue() {
        this.notificationQueue = new LinkedList<>();
    }

    public static MessageQueue getInstance(){
        return InstanceHolder.INSTANCE;
    }

}
