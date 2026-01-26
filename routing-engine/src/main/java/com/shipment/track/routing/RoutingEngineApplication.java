package com.shipment.track.routing;

import com.shipment.track.routing.init.RouteCreation;
import com.shipment.track.routing.repository.AdjacencyMatrixDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@EnableMongoAuditing
@ComponentScan("com.shipment.track")
public class RoutingEngineApplication implements CommandLineRunner {

    private final RouteCreation routeCreation;
    private final AdjacencyMatrixDocumentRepository adjacencyMatrixDocumentRepository;

    public RoutingEngineApplication(RouteCreation routeCreation,
                                    AdjacencyMatrixDocumentRepository adjacencyMatrixDocumentRepository) {
        this.routeCreation = routeCreation;
        this.adjacencyMatrixDocumentRepository = adjacencyMatrixDocumentRepository;
    }

    public static void main(String[] args) {
        log.info("Application Started At {}", LocalDateTime.now());
        SpringApplication.run(RoutingEngineApplication.class, args);
    }


    /**
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Creating routes {}", LocalDateTime.now());
        routeCreation.createRoutes()
                .flatMap(adjMatrix -> {
                    adjMatrix.setTimeStamp(LocalDateTime.now());
                    return adjacencyMatrixDocumentRepository.save(adjMatrix);
                })
                .doOnNext(d -> log.info("Saved item into the db {}", d))
                .onErrorStop()
                .subscribe();
    }
}
