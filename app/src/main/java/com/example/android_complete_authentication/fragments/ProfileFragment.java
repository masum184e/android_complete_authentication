package com.example.android_complete_authentication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_complete_authentication.Login;
import com.example.android_complete_authentication.R;
import com.example.android_complete_authentication.Registration;
import com.example.android_complete_authentication.api.ApiService;
import com.example.android_complete_authentication.api.RetrofitClient;
import com.example.android_complete_authentication.models.ProfileModel;
import com.example.android_complete_authentication.models.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView userName, userEmail;
    private ImageView userProfilePicture;
    private SharedPreferences sharedPreferences;
    private String BEARER_TOKEN;
    private Button logoutBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        sharedPreferences = requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);

        logoutBtn.setOnClickListener(btnView -> {
            startActivity(new Intent(getContext(), Login.class));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("authToken");
            editor.commit();
        });


        if(sharedPreferences.contains("authToken")){
            BEARER_TOKEN = sharedPreferences.getString("authToken", null);
        }

        if (BEARER_TOKEN != null) {
            fetchUserDetails(BEARER_TOKEN);
        } else {
            Toast.makeText(getContext(), "Unauthorized User", Toast.LENGTH_LONG).show();
        }

        return view;
    }
    private void fetchUserDetails(String BEARER_TOKEN) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ProfileResponse> call = apiService.getUserProfile("Bearer " + BEARER_TOKEN);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profileResponse = response.body();
                    Toast.makeText(getContext(), profileResponse.getMessage(), Toast.LENGTH_LONG).show();

                    if ("true".equals(profileResponse.isStatus())) {
                        ProfileModel profile = profileResponse.getProfile();

                        userName.setText(profile.getFullname());
                        userEmail.setText(profile.getEmail());

                        System.out.println("HELLO WORLD");
                        System.out.println(profile.getFullname());
                        System.out.println(profile.getEmail());

                    } else {
                        Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }


}