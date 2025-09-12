package com.akshat.practice.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.akshat.practice.app.beans.response.StatusResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private StatusResponse response;

	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<StatusResponse> handleAlreadyExistException(AlreadyExistException ex) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
	
    // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    	response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle Bad Request (like invalid inputs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StatusResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    	response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    // Handle Bad Request (like invalid inputs)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StatusResponse> handleRunTimeException(RuntimeException ex) {
    	response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle Any Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusResponse> handleGenericException(Exception ex) {
    	response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
