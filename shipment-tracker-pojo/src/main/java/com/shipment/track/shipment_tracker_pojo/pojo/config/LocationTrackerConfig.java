package com.shipment.track.shipment_tracker_pojo.pojo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "location-service.config")
public class LocationTrackerConfig {
    private String checkProperty;
    private DatabaseConfig database;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DatabaseConfig {
        private String name;
        private String collectionName;
        private String spatialIndexFieldName;
    }




}
