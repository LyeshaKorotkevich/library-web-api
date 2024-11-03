package com.modsen.libraryservice.exception.handler;

import com.modsen.libraryservice.exception.LibraryBookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibraryBookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(LibraryBookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
