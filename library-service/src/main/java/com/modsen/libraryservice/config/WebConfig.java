package com.modsen.libraryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up web-related beans in the application.
 *
 * This class is responsible for defining beans that are used for
 * making HTTP requests within the application, such as the RestTemplate.
 */
@Configuration
public class WebConfig {

    /**
     * Creates and configures a RestTemplate bean.
     *
     * The RestTemplate is used for making synchronous HTTP requests
     * to external services. By defining it as a bean, it can be
     * easily injected and reused throughout the application.
     *
     * @return a configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
