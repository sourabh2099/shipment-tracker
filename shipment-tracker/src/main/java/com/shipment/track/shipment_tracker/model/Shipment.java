package com.shipment.track.shipment_tracker.model;

import com.shipment.track.shipment_tracker.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(Address originAddress) {
        this.originAddress = originAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TrackingDetails> getTrackingDetails() {
        return trackingDetails;
    }

    public void setTrackingDetails(List<TrackingDetails> trackingDetails) {
        this.trackingDetails = trackingDetails;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", originAddress=" + originAddress +
                ", destinationAddress=" + destinationAddress +
                ", shipmentStatus=" + shipmentStatus +
                ", user=" + user +
                ", trackingDetails=" + trackingDetails +
                ", created_At=" + created_At +
                '}';
    }
}
