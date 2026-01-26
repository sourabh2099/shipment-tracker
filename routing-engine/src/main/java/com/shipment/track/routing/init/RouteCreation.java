package com.shipment.track.routing.init;

import com.shipment.track.routing.document.AdjacencyMatrixDocument;
import reactor.core.publisher.Mono;

public interface RouteCreation {
    Mono<AdjacencyMatrixDocument> createRoutes();
}
