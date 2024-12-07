package com.shipment.track.shipment_tracker.config;

import com.shipment.track.shipment_tracker.config.data.AsyncThreadPoolConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncThreadPoolConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncThreadPoolConfigData.class);

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("MyAysncThread-");
        executor.setRejectedExecutionHandler((runnable, threadPoolExecutor) ->
                LOG.info("Task Rejected as the Queue is full! current count: {} ", threadPoolExecutor.getActiveCount()));
        executor.initialize();
        return executor;
    }
}
