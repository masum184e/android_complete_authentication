package com.example.android_complete_authentication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android_complete_authentication.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

    private TextView signInLink;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signInLink=findViewById(R.id.signInLink);
        btnSignUp=findViewById(R.id.btnSignUp);
        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Registration.this, Login.class);
                startActivity(mainIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Registration.this, Profile.class);
                startActivity(mainIntent);
            }
        });
    }

}