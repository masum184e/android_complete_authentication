package com.example.android_complete_authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Registration extends AppCompatActivity {

    private TextView signInLink;
    private Button btnSignUp;
    SharedPreferences sharedPreferences;
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
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();

            getEmail.setError(email.isEmpty() ? "Email is required" : null);
            getPassword.setError(password.isEmpty() ? "Password is required" : null);
            getName.setError(name.isEmpty() ? "Name is required" : null);

            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) registerUser(email, password, name);
        });
    }
    private void registerUser(String name, String email, String password) {
        System.out.println(name+" "+email+" "+password);
        startActivity(new Intent(this, Profile.class));
    }
}