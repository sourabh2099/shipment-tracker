package com.shipment.track.shipment_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "pincode")
    private Long pincode;
    @Column(name = "state")
    private String state;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long id() {
        return id;
    }

    public Address setId(Long id) {
        this.id = id;
        return this;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public Address setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String addressLine2() {
        return addressLine2;
    }

    public Address setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public Long pincode() {
        return pincode;
    }

    public Address setPincode(Long pincode) {
        this.pincode = pincode;
        return this;
    }

    public String state() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }
}
