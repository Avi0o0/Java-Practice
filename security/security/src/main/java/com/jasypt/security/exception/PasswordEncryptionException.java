package com.jasypt.security.exception;

public class PasswordEncryptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordEncryptionException(String message) {
        super(message);
    }

    public PasswordEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
