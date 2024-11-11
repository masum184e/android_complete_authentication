package com.example.android_complete_authentication.models;

public class LoginResponse {
    private String status;
    private String message;
    private String token;

    public String isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
