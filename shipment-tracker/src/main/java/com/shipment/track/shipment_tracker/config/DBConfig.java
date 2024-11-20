package com.shipment.track.shipment_tracker.config;

import com.shipment.track.shipment_tracker.config.data.DBConfigData;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    private final DBConfigData dbConfigData;

    public DBConfig(DBConfigData dbConfigData) {
        this.dbConfigData = dbConfigData;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dbConfigData.getDriverClassName());
        dataSourceBuilder.url(dbConfigData.getUrl());
        dataSourceBuilder.username(dbConfigData.getUserName());
        dataSourceBuilder.password(dbConfigData.getPassword());
        return dataSourceBuilder.build();
    }
}
