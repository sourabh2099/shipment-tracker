package com.shipment.track.location.service.repsitory;

import com.shipment.track.location.service.documents.LocationDocument;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends ReactiveCrudRepository<LocationDocument, ObjectId> {
}
