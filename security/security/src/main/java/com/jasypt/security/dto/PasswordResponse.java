package com.jasypt.security.dto;

public class PasswordResponse {
    private String result;

    public PasswordResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
