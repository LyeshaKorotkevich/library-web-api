package com.modsen.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Book Service application.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
public class BookServiceApplication {

    /**
     * The main method that starts the Book Service application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }
}
