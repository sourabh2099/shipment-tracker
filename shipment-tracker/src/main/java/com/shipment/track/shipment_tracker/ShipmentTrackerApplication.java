package com.shipment.track.shipment_tracker;

import com.shipment.track.shipment_tracker.service.CrudOperations;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan("com.shipment.track")
@Slf4j
public class ShipmentTrackerApplication {
    @Autowired
    CrudOperations crudOperations;
//	@PostConstruct
//	public void init(){
//		System.out.println("Calling crud operations for stream ops");
//		crudOperations.updateShipmentStatusOnDelay();
//	}

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String appName;

    @PostConstruct
    public void init() {
        log.info("Starting application with properties as appName {} , port {}",
                appName, port);
    }

    public static void main(String[] args) {

        SpringApplication.run(ShipmentTrackerApplication.class, args);

    }

}
