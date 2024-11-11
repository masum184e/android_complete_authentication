package com.example.android_complete_authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_complete_authentication.api.ApiService;
import com.example.android_complete_authentication.api.RetrofitClient;
import com.example.android_complete_authentication.models.LoginRequest;
import com.example.android_complete_authentication.models.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextView signupLink;
    private Button btnSignIn;
    SharedPreferences sharedPreferences;
    private TextInputLayout getEmail, getPassword;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // IF USER IS LOG IN REDIRECT TO PROFILE ACTIVITY
        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("authToken")) {
            startActivity(new Intent(this, Profile.class));
            finish();
            return;
        }

        // SETUP ACTIVITY LAYOUT
        setContentView(R.layout.activity_login);

        // SELECTING ELEMENT
        signupLink = findViewById(R.id.signupLink);
        btnSignIn = findViewById(R.id.btnSignIn);
        getEmail = findViewById(R.id.getEmail);
        getPassword = findViewById(R.id.getPassword);

        // GETTING ELEMENT VALUE
        emailEditText = (TextInputEditText) getEmail.getEditText();
        passwordEditText = (TextInputEditText) getPassword.getEditText();

        // HANDLING SIGNUP BUTTON
        signupLink.setOnClickListener(view -> startActivity(new Intent(this, Registration.class)));

        // HANDLING SUBMIT BUTTON
        btnSignIn.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            getEmail.setError(email.isEmpty() ? "Email is required" : null);
            getPassword.setError(password.isEmpty() ? "Password is required" : null);

            if (!email.isEmpty() && !password.isEmpty()) loginUser(email, password);
        });
    }

    private void loginUser(String email, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<LoginResponse> call = apiService.loginUser(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if ("true".equals(loginResponse.isStatus())) {
                        getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE).edit().putString("authToken", loginResponse.getToken()).apply();
                        startActivity(new Intent(Login.this, Profile.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Login.this, "Failed to log in", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Network error occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}