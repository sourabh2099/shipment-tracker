package com.shipment.track.location.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;

@Configuration
public class AppConfig extends AbstractReactiveMongoConfiguration {

    @Value("${locationService.data.mongodb.uri}")
    private String mongoDbUri;

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ReactiveMongoClientFactoryBean mongoConfig() {
        LOG.info("Mongo DB URI {}", mongoDbUri);

        ReactiveMongoClientFactoryBean bean = new ReactiveMongoClientFactoryBean();
        bean.setConnectionString(mongoDbUri + getDatabaseName());
//        bean.setMongoClientSettings(MongoClientSettings.builder()
//                .addCommandListener()
//                        .credential(MongoCredential.createCredential())
//                .build());
        return bean;
    }
    @Override
    protected String getDatabaseName() {
        return "location-service";
    }
}
