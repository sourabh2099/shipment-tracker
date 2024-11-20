package com.shipment.track.shipment_tracker.validators;

import com.shipment.track.shipment_tracker.validators.impl.EmailValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidatorImpl.class)
public @interface EmailValidator {
    String message() default "Please enter email Correctly";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
