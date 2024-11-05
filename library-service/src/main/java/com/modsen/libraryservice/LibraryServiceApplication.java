package com.modsen.libraryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Library Service application.
 *
 * This class serves as the bootstrap for the Spring Boot application, enabling
 * automatic configuration and component scanning. The application starts from this
 * main method.
 */
@SpringBootApplication
public class LibraryServiceApplication {

    /**
     * Main method that starts the Library Service application.
     *
     * This method uses SpringApplication.run() to launch the application context,
     * initializing the embedded server and loading the application components.
     *
     * @param args command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }
}
