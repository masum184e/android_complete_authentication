package com.example.android_complete_authentication.models;

public class ProfileResponse {
    private String status;
    private String message;
    private ProfileModel profile;

    public String isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ProfileModel getProfile() {
        return profile;
    }
}
