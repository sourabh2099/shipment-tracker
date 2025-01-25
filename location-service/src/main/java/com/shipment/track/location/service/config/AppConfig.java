package com.shipment.track.location.service.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;

@Configuration
public class AppConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public ReactiveMongoClientFactoryBean mongoConfig() {
        ReactiveMongoClientFactoryBean bean = new ReactiveMongoClientFactoryBean();
        bean.setConnectionString("mongodb://localhost:27017/location-service");
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
