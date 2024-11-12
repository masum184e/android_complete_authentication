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

import com.bumptech.glide.Glide;
import com.example.android_complete_authentication.EditProfileActivity;
import com.example.android_complete_authentication.Login;
import com.example.android_complete_authentication.R;
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
    private String bearerToken;
    private Button logoutBtn, editBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // SELECTING ELEMENT
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userProfilePicture = view.findViewById(R.id.userProfilePicture);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        editBtn = view.findViewById(R.id.editBtn);
        sharedPreferences = requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);

        // HANDLING LOG OUT
        logoutBtn.setOnClickListener(v -> logout());

        // HANDLING EDIT BUTTON
        editBtn.setOnClickListener(v->startActivity(new Intent(getContext(), EditProfileActivity.class)));

        // CHECKING TOKEN
        bearerToken = sharedPreferences.getString("authToken", null);
        if ((bearerToken == null)) {
            unauthorizedUser();
        } else {
            fetchUserDetails();
        }

        return view;
    }

    private void logout() {
        sharedPreferences.edit().remove("authToken").apply();
        Toast.makeText(getContext(), "Logged Out Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), Login.class));
        requireActivity().finish();
    }

    private void unauthorizedUser() {
        Toast.makeText(getContext(), "Unauthorized User", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getContext(), Login.class));
        requireActivity().finish();
    }

    private void fetchUserDetails() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<ProfileResponse> call = apiService.getUserProfile("Bearer " + bearerToken);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && "true".equals(response.body().isStatus())) {
                    ProfileModel profile = response.body().getProfile();
                    userName.setText(profile.getFullname());
                    userEmail.setText(profile.getEmail());
                    String imageUrl="http://30.0.7.240:80/php_complete_authentication/uploads/"+profile.getProfilePicture();
                    Glide.with(getContext())
                            .load(imageUrl)
                            .error(R.drawable.avatar)
                            .into(userProfilePicture);
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
