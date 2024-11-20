package com.shipment.track.notification_service.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash
public class UserCounter {
    @Id
    private String userName;
    private String clientName;
    private Long count;
}
