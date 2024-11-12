package com.example.android_complete_authentication.models;

public class RegistrationRequest {
    private String email;
    private String password;
    private String fullname;

    public RegistrationRequest(String fullname, String email, String password) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return fullname;
    }
}
