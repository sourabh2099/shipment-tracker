package com.shipment.track.notification_service.service.impl;

import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.queue.MessageQueue;
import com.shipment.track.notification_service.service.ReceiveNotification;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ReceiveNotificationServiceImpl implements ReceiveNotification {
    volatile Queue<NotificationMessage> pollingQueue = MessageQueue.getInstance().getNotificationQueue();
    private final static Logger LOG = LoggerFactory.getLogger(ReceiveNotificationServiceImpl.class);
    private final static ExecutorService es = Executors.newSingleThreadExecutor();
    @PostConstruct
    public void startPolling(){
        LOG.info("Starting to receive requests ");
        es.submit(run);
    }

    private Runnable run = () -> {
        while (true) {
            try {
                List<NotificationMessage> remove = removeMsgFromQueue();
                LOG.info("Retrieved Message of size {}", remove.size());
                sleep();
            } catch (RuntimeException e) {
                LOG.error("Found error while trying to poll the queue {}", e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("Found interrupted exception while trying to process !! {}", e.getMessage());
            }
        }
    };

    private List<NotificationMessage> removeMsgFromQueue() {
        List<NotificationMessage> messageList = new ArrayList<>();
        while(!pollingQueue.isEmpty()){
            messageList.add(pollingQueue.poll());
        }
        return messageList;
    }

    private static void sleep() throws InterruptedException {
        LOG.info("Putting thread to sleep at {}", LocalDateTime.now());
    }


    @Override
    public void receive(NotificationMessage message) {
        LOG.info("Starting to receive Requests ");
    }

}
