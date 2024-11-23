package com.shipment.track.notification_service.config;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;

@Configuration
public class JteConfig {
    @Bean
    public TemplateEngine templateEngine(){
        String path = new File(".").getAbsolutePath();
        path = path.substring(0,path.length() - 1);
        CodeResolver codeResolver = new DirectoryCodeResolver(Path.of(path + "notification-service\\src\\main\\jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}
