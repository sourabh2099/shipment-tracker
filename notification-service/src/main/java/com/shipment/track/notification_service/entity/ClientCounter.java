package com.shipment.track.notification_service.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("client_counter")
public class ClientCounter {
    @Id
    private String clientName;
    private Long startTimeInMillis;
    private Long count;
}
