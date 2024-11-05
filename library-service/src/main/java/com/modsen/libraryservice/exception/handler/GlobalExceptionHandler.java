package com.modsen.libraryservice.exception.handler;

import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the library service.
 *
 * This class serves as a centralized handler for exceptions thrown within the
 * application, specifically for handling library-related exceptions. It uses
 * Spring's {@link ControllerAdvice} to intercept exceptions and return
 * appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles LibraryBookNotFoundException by returning a 404 NOT FOUND response.
     *
     * When a LibraryBookNotFoundException is thrown, this method is invoked to
     * provide a standardized error response with the message from the exception.
     *
     * @param ex the LibraryBookNotFoundException that was thrown
     * @return a ResponseEntity containing the error message and a 404 NOT FOUND status
     */
    @ExceptionHandler(LibraryBookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(LibraryBookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
