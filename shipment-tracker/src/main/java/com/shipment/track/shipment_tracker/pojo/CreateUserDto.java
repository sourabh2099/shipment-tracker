package com.shipment.track.shipment_tracker.pojo;

import com.shipment.track.shipment_tracker.validators.EmailValidator;
import lombok.Data;

@Data
public class CreateUserDto {
    private String fullName;
    private String password;
    @EmailValidator(message = "Enter email in correct format !!")
    private String email;
    private String phoneNumber;
}
