package com.shipment.track.shipment_tracker_pojo.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull
    private String addressLine1;
    private String addressLine2;
    @NotNull
    private String pincode;
    @NotNull
    private String state;
    private String addressType;

}
