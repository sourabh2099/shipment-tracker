package com.shipment.track.notification_service.dto;

import com.shipment.track.notification_service.dto.enums.MessageType.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationMessage {
    private String message;
    private MessageType messageType;
    private String clientName;
    private String emailId;
    private String phoneNumber;
    private Integer priority;
    private String medium;

}
