package com.shipment.track.notification_service.config.configData;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.redis")
@Data
public class RedisConfigData {
    private Integer port;
    private String hostName;
}
