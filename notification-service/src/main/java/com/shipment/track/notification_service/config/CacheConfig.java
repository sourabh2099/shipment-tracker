package com.shipment.track.notification_service.config;

import com.shipment.track.notification_service.config.configData.RedisConfigData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CacheConfig {
    private final RedisConfigData redisConfigData;

    public CacheConfig(RedisConfigData redisConfigData) {
        this.redisConfigData = redisConfigData;
    }

    @Bean(name = "redis1ConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisConfigData.getHostName()
                ,redisConfigData.getPort());
        return new JedisConnectionFactory(config);
    }

    @Bean(name = "redis1StringRedisTemplate")
    public StringRedisTemplate userStringRedisTemplate(@Qualifier("redis1ConnectionFactory")
                                                           RedisConnectionFactory connectionFactory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;

    }

    @Bean(name = "redis1RedisTemplate")
    public RedisTemplate userRedisTemplate(@Qualifier("redis1ConnectionFactory")
                                              RedisConnectionFactory connectionFactory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }


}
