package com.shipment.track.shipment_tracker_pojo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShipmentDto {
    private Long originAddress;
    private Long destinationAddress;
    private List<PackageDto> packages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PackageDto {
        private String packageType;
        private Long value;
        private Long weight;
    }
}
