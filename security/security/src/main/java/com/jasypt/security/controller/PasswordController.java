package com.jasypt.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jasypt.security.dto.PasswordRequest;
import com.jasypt.security.dto.PasswordResponse;
import com.jasypt.security.service.PasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<PasswordResponse> encrypt(@Valid @RequestBody PasswordRequest request) {
        String encrypted = passwordService.encrypt(request.getPassword());
        // Wrap in ENC()
        String wrapped = "ENC(" + encrypted + ")";
        return ResponseEntity.ok(new PasswordResponse(wrapped));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<PasswordResponse> decrypt(@Valid @RequestBody PasswordRequest request) {
        String value = request.getPassword();
        // Remove ENC(...) wrapper if present
        if (value.startsWith("ENC(") && value.endsWith(")")) {
            value = value.substring(4, value.length() - 1);
        }
        String decrypted = passwordService.decrypt(value);
        return ResponseEntity.ok(new PasswordResponse(decrypted));
    }

}
