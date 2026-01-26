package com.shipment.track.routing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;

@Configuration
public class DatabaseConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public ReactiveMongoClientFactoryBean mongoConfig() {
        ReactiveMongoClientFactoryBean bean = new ReactiveMongoClientFactoryBean();
        bean.setConnectionString("mongodb://localhost:27017/" + getDatabaseName());
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