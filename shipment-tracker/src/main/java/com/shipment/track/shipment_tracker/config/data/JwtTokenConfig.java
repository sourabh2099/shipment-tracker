package com.shipment.track.shipment_tracker.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtTokenConfig {
    private String jwtSecretKey;
    private Long jwtExpirationTime;
}
