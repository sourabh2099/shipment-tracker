package com.shipment.track.shipment_tracker_pojo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto {
    private String addressLine1;
    private String addressLine2;
    private Long pincode;
    private String state;
}
