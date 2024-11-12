package com.example.android_complete_authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Button selectImgBtn, uploadImgBtn;
    private ImageView userProfilePicture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String bearerToken = sharedPreferences.getString("authToken", null);

        if (bearerToken == null) {
            unauthorizedUser();
            return;
        }

        setContentView(R.layout.activity_edit_profile);

        selectImgBtn = findViewById(R.id.selectImgBtn);
        uploadImgBtn = findViewById(R.id.uploadImgBtn);
        userProfilePicture = findViewById(R.id.userProfilePicture);

        selectImgBtn.setOnClickListener(v -> selectImage());

        uploadImgBtn.setOnClickListener(v -> {
            if (imageUri != null) {
                uploadImage(bearerToken);
            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void unauthorizedUser() {
        Toast.makeText(this, "Unauthorized User", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    userProfilePicture.setImageURI(imageUri);
                }else{
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            });

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void uploadImage(String bearerToken) {
        Toast.makeText(this, "Uploading image..."+imageUri, Toast.LENGTH_LONG).show();
    }
}
