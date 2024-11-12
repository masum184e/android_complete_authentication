package com.example.android_complete_authentication.models;

public class RegistrationRequest {
    private String email;
    private String password;
    private String name;

    public RegistrationRequest(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
