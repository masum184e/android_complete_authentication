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
import com.example.android_complete_authentication.models.RegistrationRequest;
import com.example.android_complete_authentication.models.RegistrationResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    private TextView signInLink;
    private Button btnSignUp;
    private SharedPreferences sharedPreferences;
    private TextInputLayout getName, getEmail, getPassword;
    private TextInputEditText nameEditText, emailEditText, passwordEditText;

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
        setContentView(R.layout.activity_registration);

        // SELECTING ELEMENT
        signInLink=findViewById(R.id.signInLink);
        btnSignUp=findViewById(R.id.btnSignUp);
        getEmail = findViewById(R.id.getEmail);
        getPassword = findViewById(R.id.getPassword);
        getName = findViewById(R.id.getName);

        // GETTING ELEMENT VALUE
        emailEditText = (TextInputEditText) getEmail.getEditText();
        passwordEditText = (TextInputEditText) getPassword.getEditText();
        nameEditText = (TextInputEditText) getName.getEditText();

        // HANDLING SIGNUP BUTTON
        signInLink.setOnClickListener(view -> startActivity(new Intent(this, Login.class)));

        // HANDLING SUBMIT BUTTON
        btnSignUp.setOnClickListener(view -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            getName.setError(name.isEmpty() ? "Name is required" : null);
            getEmail.setError(email.isEmpty() ? "Email is required" : null);
            getPassword.setError(password.isEmpty() ? "Password is required" : null);

            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) registerUser(name, email, password);
        });
    }
    private void registerUser(String name, String email, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<RegistrationResponse> call = apiService.registrationUser(new RegistrationRequest(name, email, password));

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegistrationResponse registrationResponse = response.body();
                    Toast.makeText(Registration.this, registrationResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if ("true".equals(registrationResponse.isStatus())) {
                        getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE).edit().putString("authToken", registrationResponse.getToken()).apply();
                        startActivity(new Intent(Registration.this, Profile.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Registration.this, "Failed to Registration", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(Registration.this, "Network error occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}