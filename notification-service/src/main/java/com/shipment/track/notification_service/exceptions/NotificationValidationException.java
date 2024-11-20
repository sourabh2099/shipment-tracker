package com.shipment.track.notification_service.exceptions;

public class NotificationValidationException extends RuntimeException {
    public NotificationValidationException() {
        super();
    }

    public NotificationValidationException(String message) {
        super(message);
    }

    public NotificationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
