package com.shipment.track.shipment_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShipmentTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentTrackerApplication.class, args);
	}

}
