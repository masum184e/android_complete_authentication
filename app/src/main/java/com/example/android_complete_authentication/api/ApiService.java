package com.example.android_complete_authentication.api;

import com.example.android_complete_authentication.models.CommonAPIResponse;
import com.example.android_complete_authentication.models.LoginRequest;
import com.example.android_complete_authentication.models.LoginResponse;
import com.example.android_complete_authentication.models.ProfileResponse;
import com.example.android_complete_authentication.models.RegistrationRequest;
import com.example.android_complete_authentication.models.RegistrationResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("php_complete_authentication/user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("php_complete_authentication/user/profile")
    Call<ProfileResponse> getUserProfile(@Header("Authorization") String bearerToken);

    @POST("php_complete_authentication/user/registration")
    Call<RegistrationResponse> registrationUser(@Body RegistrationRequest registrationRequest);

    @Multipart
    @POST("php_complete_authentication/user/upload-profile-picture")
    Call<CommonAPIResponse> uploadProfilePicture(
        @Header("Authorization") String bearerToken,
        @Part MultipartBody.Part file
    );

}
