package com.jasypt.security.service;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;

import com.jasypt.security.exception.PasswordEncryptionException;

@Service
public class PasswordService {

    private final BasicTextEncryptor textEncryptor;

    public PasswordService() {
        String jasyptPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        if (jasyptPassword == null || jasyptPassword.isEmpty()) {
            throw new PasswordEncryptionException("JASYPT_ENCRYPTOR_PASSWORD environment variable not set");
        }
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(jasyptPassword);
    }

    public String encrypt(String plainText) {
        try {
            return textEncryptor.encrypt(plainText);
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error encrypting password", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            return textEncryptor.decrypt(encryptedText);
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error decrypting password", e);
        }
    }
}
