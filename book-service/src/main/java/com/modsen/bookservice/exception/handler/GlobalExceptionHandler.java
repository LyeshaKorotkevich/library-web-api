package com.modsen.bookservice.exception.handler;

import com.modsen.bookservice.exception.BookNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that intercepts exceptions thrown by controllers
 * in the application and provides appropriate responses.
 *
 * This class is annotated with {@link ControllerAdvice}, allowing it to
 * handle exceptions globally across all controllers. It provides custom
 * responses for specific exceptions that may arise during the processing
 * of requests.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BookNotFoundException} exceptions.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error message and a 404 NOT FOUND status
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles validation exceptions that occur when method arguments
     * annotated with validation constraints are invalid.
     *
     * @param ex the exception containing details of the validation errors
     * @return a ResponseEntity containing a map of field names to error messages
     *         and a 400 BAD REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles {@link DataIntegrityViolationException} exceptions,
     * which occur due to violations of data integrity constraints.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing a conflict message and a 409 CONFLICT status
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: " + ex.getMessage());
    }
}
