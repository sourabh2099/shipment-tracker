package com.shipment.track.shipment_tracker_pojo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrackingDto {
    private Long id;
    private String location;
    private String shipmentStatus;
    private String message;
}
