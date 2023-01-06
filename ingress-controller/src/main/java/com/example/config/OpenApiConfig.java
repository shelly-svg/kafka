package com.example.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    protected OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Ingress controller")
                .description("This service is responsible for data obtaining")
                .version("v0.1-SNAPSHOT"))
            .externalDocs(new ExternalDocumentation()
                .description("Ingress controller wiki documentation")
                //TODO this dummy link should be replaced with real when it will be ready
                .url("https://dummy.com/wiki/ingress-controller"));
    }

}