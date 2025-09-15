package com.akshat.practice.app.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.akshat.practice.app.beans.response.StatusResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Already Exists
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<StatusResponse> handleAlreadyExistException(AlreadyExistException ex) {
        StatusResponse response = new StatusResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        StatusResponse response = new StatusResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StatusResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        StatusResponse response = new StatusResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        // Collect all field error messages
        String errors = ex.getBindingResult().getFieldErrors()
                          .stream()
                          .map(err -> err.getDefaultMessage())
                          .collect(Collectors.joining(", "));
        response.setMessage(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle Runtime Exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StatusResponse> handleRunTimeException(RuntimeException ex) {
        StatusResponse response = new StatusResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle Any Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusResponse> handleGenericException(Exception ex) {
        StatusResponse response = new StatusResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Something went wrong, please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
