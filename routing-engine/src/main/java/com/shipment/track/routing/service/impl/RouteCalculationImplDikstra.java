package com.shipment.track.routing.service.impl;

import com.shipment.track.routing.repository.AdjacencyMatrixDocumentRepository;
import com.shipment.track.routing.repository.OfficeLocationRepository;
import com.shipment.track.routing.service.RouteCalculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RouteCalculationImplDikstra implements RouteCalculation {

    private AdjacencyMatrixDocumentRepository adjacencyMatrixDocumentRepository;
    private OfficeLocationRepository officeLocationRepository;


    // todo also call the location service to find the location of the requested place;
    /**
     * @param source
     * @param destination
     */
    @Override
    public void computePathSourceToDestination(String source, String destination) {

    }
}
