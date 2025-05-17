package com.shipment.track.location.service.repsitory;

import com.shipment.track.location.service.documents.OfficeLocationDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeLocationRepository extends ReactiveCrudRepository<OfficeLocationDocument,ObjectId > {
    @Query("{officeLocation: '?0'}")
    Optional<OfficeLocationDocument> findByOfficeLocation(String officeLocation);
}
