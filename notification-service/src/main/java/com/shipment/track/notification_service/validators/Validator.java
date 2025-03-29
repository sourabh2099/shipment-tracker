package com.shipment.track.notification_service.validators;


import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;

public interface Validator<T extends NotificationMessage> {

    /**
     *
     * @param notificationMessage
     * @return
     */
    boolean isValid(T notificationMessage);
    /**
     *
     * @param notificationMessage
     */
    void validate(T notificationMessage);

    /**
     *
     * @param notificationMessage
     * @return
     */
    void passToNewValidator(T notificationMessage);
}
