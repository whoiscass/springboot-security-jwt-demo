package com.example.demo.handler.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.user.dto.response.ErrorResponse;

/**
 * GlobalExceptionHandler handles exceptions thrown by controllers across the application.
 *
 * This class uses @RestControllerAdvice to apply global exception handling for REST APIs.
 * It provides custom responses for validation errors and other standard exceptions.
 */
@io.swagger.v3.oas.annotations.Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors triggered by @Valid on controller method arguments.
     *
     * It extracts the first validation error message from the exception and returns it
     * in a structured error response with HTTP status 400 (Bad Request).
     *
     * @param ex the MethodArgumentNotValidException thrown when validation fails
     * @return a ResponseEntity containing an ErrorResponse with the validation message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Invalid request");

        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }

    /**
     * Handles ResponseStatusException thrown from controller or service layers.
     *
     * It returns the reason and HTTP status code provided by the exception.
     *
     * @param ex the ResponseStatusException
     * @return a ResponseEntity containing an ErrorResponse with the exception reason
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getReason()));
    }
}
