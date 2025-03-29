package com.shipment.track.notification_service.validators.impl;


import com.shipment.track.notification_service.exceptions.NotificationValidationException;
import com.shipment.track.notification_service.validators.Validator;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("EmailValidator")
public class EmailValidator implements Validator<NotificationMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(EmailValidator.class);
    private final Validator<NotificationMessage> notificationValidator;


    public EmailValidator(Validator<NotificationMessage> notificationValidator) {
        this.notificationValidator = null;
    }

    // checks if eligible for this validation or not !!
    @Override
    public boolean isValid(NotificationMessage notificationMessage) {
        return !Objects.isNull(notificationMessage.getEmailId());
    }

    @Override
    public void validate(NotificationMessage notificationMessage) {
        if (isValid(notificationMessage)) {
            LOG.info("notification is validated !!");
        }
        else{
            throw new NotificationValidationException("Found Exception while validating the email");
        }
        // not forwarding to any further validators as this is the last validator !!
    }

    @Override
    public void passToNewValidator(NotificationMessage notificationMessage) {

    }
}
