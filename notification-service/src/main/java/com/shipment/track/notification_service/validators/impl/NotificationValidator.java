package com.shipment.track.notification_service.validators.impl;

import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.dto.enums.MessageType.MessageType;
import com.shipment.track.notification_service.exceptions.NotificationValidationException;
import com.shipment.track.notification_service.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Primary
@Service("NotificationValidator")
public class NotificationValidator implements Validator<NotificationMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationValidator.class);
    private final Validator<NotificationMessage> nextValidator;

    public NotificationValidator(@Lazy
                                 @Qualifier("EmailValidator") Validator<NotificationMessage> nextValidator) {
        this.nextValidator = nextValidator;
    }

    // establish chain of responsibility here
    @Override
    public boolean isValid(NotificationMessage notificationMessage) {
        return !Objects.isNull(notificationMessage.getMessage())
                && MessageType.getMessageTypeList().containsValue(notificationMessage.getMessageType());
    }

    @Override
    public void validate(NotificationMessage notificationMessage) {
        // validate whether to send the notification or not
        LOG.info("validate notification ");
        if (isValid(notificationMessage) && notificationMessage.getMessageType().equals(MessageType.TRANSACTIONAL)) {
            LOG.info("Validated notification message as with messsage {} for user {}"
                    , notificationMessage.getMessage()
                    , notificationMessage.getEmailId());
            passToNewValidator(notificationMessage);
        } else {
            throw new NotificationValidationException("Found Exception while validating with NotificationValidator");
        }

    }

    @Override
    public void passToNewValidator(NotificationMessage notificationMessage) {
        LOG.info("passing onto next validator");
        nextValidator.validate(notificationMessage);
    }
}
