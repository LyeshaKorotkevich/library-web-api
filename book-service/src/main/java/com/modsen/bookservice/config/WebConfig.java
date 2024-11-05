package com.modsen.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for web-related beans.
 *
 * This class is responsible for defining the beans that are used in the web layer of the application,
 * such as the {@link RestTemplate}. The {@link RestTemplate} is a synchronous client provided by Spring
 * to make HTTP requests to external services.
 */
@Configuration
public class WebConfig {

    /**
     * Creates a new instance of {@link RestTemplate}.
     *
     * @return a {@link RestTemplate} instance for making RESTful web service calls.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
