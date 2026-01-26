package com.shipment.track.routing.init.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipment.track.routing.document.AdjacencyMatrixDocument;
import com.shipment.track.routing.document.OfficeLocationDocument;
import com.shipment.track.routing.init.RouteCreation;
import com.shipment.track.routing.repository.OfficeLocationRepository;
import com.shipment.track.shipment_tracker_pojo.pojo.dto.OfficeLocationContainerDto;
import com.shipment.track.shipment_tracker_pojo.pojo.utlis.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DijkstraRouteImpl implements RouteCreation {

    private final OfficeLocationRepository officeLocationRepository;
    private final ObjectMapper objectMapper;

    public DijkstraRouteImpl(OfficeLocationRepository officeLocationRepository,
                             ObjectMapper objectMapper) {
        this.officeLocationRepository = officeLocationRepository;
        this.objectMapper = objectMapper;
    }

    /**
     *
     */
    @Override
    public Mono<AdjacencyMatrixDocument> createRoutes() {
        return officeLocationRepository.findAll()
                .flatMap(this::findDistancesInBetweenOffices)
                .map(document -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(document.toJson());
                        OfficeLocationContainerDto officeLocationContainerDto = new OfficeLocationContainerDto();

                        officeLocationContainerDto.setFromOfficeName(
                                AppUtils.getNodeData(jsonNode,JsonNode::asText,"fromOffice"));

                        officeLocationContainerDto.setToOfficeName(
                                AppUtils.getNodeData(jsonNode,JsonNode::asText,"officeName"));

                        officeLocationContainerDto.setOfficeType(
                                AppUtils.getNodeData(jsonNode,JsonNode::asText,"officeType"));

                        officeLocationContainerDto.setCoordinates(
                                AppUtils.getListDataFromNode(jsonNode, Double.class,
                                        "officeCoordinates","coordinates"));

                        officeLocationContainerDto.setDistanceBetweenOffices(
                                AppUtils.getNodeData(jsonNode,JsonNode::asDouble,"distanceBetween"));

                        return officeLocationContainerDto;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).reduce(new AdjacencyMatrixDocument(),
                        (matrix,item) -> {
                            if(Double.compare(item.getDistanceBetweenOffices(),0.0) == 0){
                                return matrix;
                            }
                            Map<String, List<AdjacencyMatrixDocument.NodeDetails>> adjacencyMatrix = matrix.getAdjacencyMatrix();
                            if(adjacencyMatrix != null && adjacencyMatrix.containsKey(item.getFromOfficeName())){
                                List<AdjacencyMatrixDocument.NodeDetails> nodeDetails = adjacencyMatrix.get(item.getFromOfficeName());
                                nodeDetails.add(new AdjacencyMatrixDocument.NodeDetails(item.getToOfficeName(),item.getDistanceBetweenOffices()));
                                adjacencyMatrix.put(item.getFromOfficeName(),nodeDetails);
                            }else{
                                if(adjacencyMatrix == null){
                                    adjacencyMatrix = new ConcurrentHashMap<>();
                                }
                                List<AdjacencyMatrixDocument.NodeDetails> nodeList = new ArrayList<>();
                                nodeList.add(new AdjacencyMatrixDocument.NodeDetails(item.getToOfficeName(),item.getDistanceBetweenOffices()));
                                adjacencyMatrix.put(item.getFromOfficeName(),nodeList);
                            }
                            matrix.setAdjacencyMatrix(adjacencyMatrix);
                            return matrix;
                        });
    }


    private Flux<Document> findDistancesInBetweenOffices(OfficeLocationDocument officeLocationDocument) {
        Double longitude = officeLocationDocument.getOfficeCoordinates().getX();
        Double latitude = officeLocationDocument.getOfficeCoordinates().getY();
        return officeLocationRepository.getOfficePathDistances(longitude,latitude)
                .map(document -> {
                    document.append("fromOffice",officeLocationDocument.getOfficeName());
                    return document;
                });
    }

//    private AdjacencyMatrixDocument computeAndAddEdgesToMap(String toNodeDetailsString,
//                                                            OfficeLocationDocument officeLocationDocument) {
//        Map<String, List<AdjacencyMatrixDocument.NodeDetails>> map;
//        JsonNode toNodeDetails ;
//
//        try{
//            toNodeDetails = objectMapper.readTree(toNodeDetailsString);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        if(toNodeDetails.path("officeName").asText().equals(officeLocationDocument.getOfficeName())){
//            log.info("Office Node contains same name as the reaching node Skipping to add to adjacency list");
//            return adjacencyMatrixDocument;
//        }
//        if (Objects.isNull(adjacencyMatrixDocument.getAdjacencyMatrix())) {
//            map = new ConcurrentHashMap<>();
//        } else {
//            map = adjacencyMatrixDocument.getAdjacencyMatrix();
//        }
//
//        map.compute(officeLocationDocument.getOfficeName(), (key, value) -> {
//            if(Objects.isNull(value)){
//                value = new ArrayList<>();
//                value.add(new AdjacencyMatrixDocument.NodeDetails(toNodeDetails.path("officeName").asText(),
//                        toNodeDetails.path("distanceBetween").asDouble()));
//            }
//            value.add(new AdjacencyMatrixDocument.NodeDetails(toNodeDetails.path("officeName").asText(),
//                    toNodeDetails.path("distanceBetween").asDouble()));
//            return value;
//        });
//        adjacencyMatrixDocument.setAdjacencyMatrix(map);
//        return adjacencyMatrixDocument;
//    }


}
