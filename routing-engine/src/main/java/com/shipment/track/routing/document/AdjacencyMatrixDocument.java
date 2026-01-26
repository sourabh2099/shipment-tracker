package com.shipment.track.routing.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("graph-edges")
public class AdjacencyMatrixDocument {
    @Id
    private ObjectId id;
    private Map<String, List<NodeDetails>> adjacencyMatrix;

    private LocalDateTime timeStamp;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NodeDetails{
        private String toNode;
        private Double edgeWeight;
    }

}
