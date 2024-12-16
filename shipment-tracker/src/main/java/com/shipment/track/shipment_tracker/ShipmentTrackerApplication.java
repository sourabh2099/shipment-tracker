package com.shipment.track.shipment_tracker;

import com.shipment.track.shipment_tracker.service.CrudOperations;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShipmentTrackerApplication {
	@Autowired
	CrudOperations crudOperations;
//	@PostConstruct
//	public void init(){
//		System.out.println("Calling crud operations for stream ops");
//		crudOperations.updateShipmentStatusOnDelay();
//	}

	public static void main(String[] args) {
		SpringApplication.run(ShipmentTrackerApplication.class, args);

	}

}
