package com.example.android_complete_authentication.api;

import com.example.android_complete_authentication.models.LoginRequest;
import com.example.android_complete_authentication.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("php_complete_authentication/user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
