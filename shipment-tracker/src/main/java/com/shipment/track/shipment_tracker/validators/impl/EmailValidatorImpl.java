package com.shipment.track.shipment_tracker.validators.impl;

import com.shipment.track.shipment_tracker.validators.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(value).matches();
    }
}
