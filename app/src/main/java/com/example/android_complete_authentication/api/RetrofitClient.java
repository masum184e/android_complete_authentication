package com.example.android_complete_authentication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                // LOCALHOST
                // .baseUrl("http://10.0.2.2:80/")
                // SALAM HALL
                .baseUrl("http://30.0.7.240:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit.create(ApiService.class);
    }
}