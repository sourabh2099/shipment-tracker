package com.shipment.track.api.gatway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
@Component
public class AuthenticationFilter implements GatewayFilter {

    private static final String AUTHORIZATION_ERROR_RESPONSE = "{\\\"error\\\": \\\"Missing Authorization header\\\"}";
    /**
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if(!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer data = exchange.getResponse().bufferFactory()
                    .wrap(AUTHORIZATION_ERROR_RESPONSE.getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Mono.just(data));
        }
        return Mono.empty();
    }
}
