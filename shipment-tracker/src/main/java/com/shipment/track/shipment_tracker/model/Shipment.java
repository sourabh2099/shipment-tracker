package com.shipment.track.shipment_tracker.model;

import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "shipment_details")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_address")
    private Address originAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_address")
    private Address destinationAddress;

    @Column(name = "shipment_status")
    private ShipmentStatus shipmentStatus;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
