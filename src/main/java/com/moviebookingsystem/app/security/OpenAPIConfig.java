package com.moviebookingsystem.app.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Movie Booking System API")
                        .version("1.0")
                        .description("API documentation for the Movie Booking System")
                .license(new License().name("Apache Tomcat 2.0")));
    }
}
