package com.task.printpagesreducerapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Value("${openapi.config.title}")
    private String title;
    @Value("${openapi.config.version}")
    private String version;
    @Value("${openapi.config.description}")
    private String description;
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title(title)
                .version(version)
                .description(description);

        return new OpenAPI().info(info);
    }
}