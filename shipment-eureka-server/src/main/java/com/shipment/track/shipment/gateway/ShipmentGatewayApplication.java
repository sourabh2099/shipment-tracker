package com.shipment.track.shipment.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShipmentGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShipmentGatewayApplication.class, args);
    }
}
