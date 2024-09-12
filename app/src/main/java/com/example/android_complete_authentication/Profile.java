package com.example.android_complete_authentication;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android_complete_authentication.fragments.ProfileFragment;
import com.example.android_complete_authentication.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigation=findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int currentItemId=menuItem.getItemId();

                if(currentItemId==R.id.navigation_profile)load_fragment(new ProfileFragment());
                else load_fragment(new SettingFragment());

                return true;
            }
        });
        bottomNavigation.setSelectedItemId(R.id.navigation_profile);
    }
    public void load_fragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.profile_layout_container, fragment).commit();
    }

}