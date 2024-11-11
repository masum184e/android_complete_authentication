package com.example.android_complete_authentication.models;

public class ProfileModel{
    private int id;
    private String fullname;
    private String email;
    private String createdAt;
    private String profilePicture;

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}