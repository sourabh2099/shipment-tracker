package com.shipment.track.shipment_tracker.repository;

import com.shipment.track.shipment_tracker.model.TrackingDetails;
import jakarta.persistence.QueryHint;
import org.hibernate.boot.model.internal.QueryHintDefinition;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query(value = """
            select
            	td.*
            from
            	shipment_details sd
            left join tracking_details td on
            	sd.id = td.shipment_id
            where
            	sd.user_id = ?1;
            """, nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = AvailableHints.HINT_FETCH_SIZE, value = "20")})
    Stream<TrackingDetails> findAllTrackingDetailsByUser(Long userId);

    @Query(value = "select td.* from shipment_details sd left join tracking_details td on sd.id = td.shipment_id where sd.user_id = ?1 ", nativeQuery = true)
    List<TrackingDetails> findAllTrackingDetailsByUserList(Long userId);

    @Query(value = "select * from tracking_details td where shipment_id = ?1",nativeQuery = true)
    List<TrackingDetails> findTrackinngDetailsByShipmentId(Long shipmentId);


}
