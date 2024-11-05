package com.modsen.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Authentication Service application.
 * This class bootstraps the application using Spring Boot.
 */
@SpringBootApplication
public class AuthServiceApplication {

    /**
     * Starts the Authentication Service application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
