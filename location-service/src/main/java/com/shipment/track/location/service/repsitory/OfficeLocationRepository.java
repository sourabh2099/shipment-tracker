package com.shipment.track.location.service.repsitory;

import com.shipment.track.location.service.documents.OfficeLocationDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface OfficeLocationRepository extends ReactiveCrudRepository<OfficeLocationDocument, ObjectId> {

    @Query("{officeLocation: '?0'}")
    Optional<OfficeLocationDocument> findByOfficeLocation(String officeLocation);

    @Aggregation("""
            {
            $geoNear: { 
                 near: {
                   type: \"Point\",
                   coordinates: [?0, ?1]
                 },
                 distanceField: \"distanceBetween\",
                 maxDistance: 100000,
                 query: {officeType : ?2 },
                 spherical: true
               }
             }
            """)
    Flux<Document> findOfficeLocationByCoordinatesAndType(Long longitude,
                                                          Long latitude,
                                                          String officeType);

    @Aggregation("""
            {
            $geoNear: { 
                 near: {
                   type: \"Point\",
                   coordinates: [?0, ?1]
                 },
                 distanceField: \"distanceBetween\",
                 maxDistance: 100000,
                 spherical: true
               }
             }
            """)
    Flux<Document> findOfficeLocationByCoordinates(Long longitude,
                                                          Long latitude);
}
