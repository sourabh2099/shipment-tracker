package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import jakarta.persistence.QueryHint;
import org.hibernate.boot.model.internal.QueryHintDefinition;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TrackingDetailsRepository extends JpaRepository<TrackingDetails, Long> {
    @Query(value = """
                    select
                    	td.*
                    from
                    	tracking_details as td
                    join shipment_details as sd on
                    	td.shipment_id = sd.id
                    where sd.shipment_status = ?1
            """, nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = AvailableHints.HINT_FETCH_SIZE, value = "20")})
    Stream<TrackingDetails> findAllTrackingDetails(String shipmentStatus);

}
