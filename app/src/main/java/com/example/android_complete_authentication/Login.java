package com.example.android_complete_authentication;

import android.content.Intent;
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
    private TextInputLayout getEmail, getPassword;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupLink = findViewById(R.id.signupLink);
        btnSignIn = findViewById(R.id.btnSignIn);
        getEmail = findViewById(R.id.getEmail);
        getPassword = findViewById(R.id.getPassword);

        emailEditText = (TextInputEditText)getEmail.getEditText();
        passwordEditText = (TextInputEditText)getPassword.getEditText();

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Login.this, Registration.class);
                startActivity(mainIntent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    getEmail.setError("Email is required");
                } else {
                    getEmail.setError(null);
                }

                if (password.isEmpty()) {
                    getPassword.setError("Password is required");
                } else {
                    getPassword.setError(null);
                }
            }
        });
    }
}
