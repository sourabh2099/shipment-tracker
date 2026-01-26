package com.shipment.track.shipment_tracker_pojo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeLocationContainerDto {
    private String toOfficeName;
    private String fromOfficeName;
    private String officeType;
    private Double distanceBetweenOffices;
    private List<Double> coordinates;
}
