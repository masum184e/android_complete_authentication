package com.example.android_complete_authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        emailEditText = (TextInputEditText)getEmail.getEditText();
        passwordEditText = (TextInputEditText)getPassword.getEditText();

        // HANDLING SIGNUP BUTTON
        signupLink.setOnClickListener(view -> startActivity(new Intent(this, Registration.class)));

        // HANDLING SUBMIT BUTTON
        btnSignIn.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            getEmail.setError(email.isEmpty()?"Email is required":null);
            getPassword.setError(password.isEmpty()?"Password is required":null);

            if (!email.isEmpty() && !password.isEmpty()) loginUser(email, password);
        });
    }
    private void loginUser(String email, String password) {
        System.out.println(email+" "+password);
        startActivity(new Intent(this, Profile.class));
    }
}