package com.shipment.track.api.gatway.config;

import com.shipment.track.api.gatway.filters.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouteConfig {
    private final AuthenticationFilter authenticationFilter;

    public RouteConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route.path("/shipment-tracker/++")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.addRequestHeader("hello", "world"))
                        .uri("lb://shipment-tracker"))
                .route(route -> route.path("/notifcation-service")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authenticationFilter)
                                .circuitBreaker(circuitBreakerConsumer -> circuitBreakerConsumer
                                        .setFallbackUri("forward://fallback")))
                        .uri("lb://notification-service"))
                .build();
    }
}
