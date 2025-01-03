package com.shipment.track.shipment_tracker.model;

import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "shipment_details")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "origin_address")
    private Address originAddress;

    @ManyToOne()
    @JoinColumn(name = "destination_address")
    private Address destinationAddress;

    @Column(name = "shipment_status")
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shipment")
    private List<TrackingDetails> trackingDetails;

    @CreationTimestamp
    private Date created_At;

}
