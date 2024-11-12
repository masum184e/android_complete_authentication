package com.example.android_complete_authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android_complete_authentication.fragments.ProfileFragment;
import com.example.android_complete_authentication.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // IF USER IS NOT LOGIN REDIRECT TO LOGIN ACTIVITY
        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String bearerToken = sharedPreferences.getString("authToken", null);

        if (bearerToken == null) {
            unauthorizedUser();
            return;
        }

        // SETUP ACTIVITY LAYOUT
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            loadFragment(item.getItemId() == R.id.navigation_profile ? new ProfileFragment() : new SettingFragment());
            return true;
        });

        bottomNavigation.setSelectedItemId(R.id.navigation_profile);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_layout_container, fragment).commit();
    }

    private void unauthorizedUser() {
        Toast.makeText(this, "Unauthorized User", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}