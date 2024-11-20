package com.shipment.track.shipment_tracker.config.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "database-details")
public class DBConfigData {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}
