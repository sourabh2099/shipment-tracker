package com.shipment.track.routing.repository;

import com.shipment.track.routing.document.AdjacencyMatrixDocument;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjacencyMatrixDocumentRepository extends ReactiveCrudRepository<AdjacencyMatrixDocument, ObjectId> {

}
