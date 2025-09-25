package com.jasypt.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jasypt.security.dto.PasswordResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PasswordResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new PasswordResponse(e.getMessage()));
    }
    
    @ExceptionHandler(PasswordEncryptionException.class)
    public ResponseEntity<PasswordResponse> handleEncryptionException(PasswordEncryptionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new PasswordResponse(e.getMessage()));
    }
}
