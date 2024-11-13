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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_complete_authentication.api.ApiService;
import com.example.android_complete_authentication.api.RetrofitClient;
import com.example.android_complete_authentication.models.CommonAPIResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Edit Profile");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
                } else {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            });

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void uploadImage(String bearerToken) {
        if (imageUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);

                RequestBody requestFile = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse(getContentResolver().getType(imageUri));
                    }

                    @Override
                    public void writeTo(okio.BufferedSink sink) throws java.io.IOException {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            sink.write(buffer, 0, bytesRead);
                        }
                    }
                };

                MultipartBody.Part body = MultipartBody.Part.createFormData("profilePicture", "image.jpg", requestFile);

                String token = "Bearer " + bearerToken;

                ApiService apiService = RetrofitClient.getApiService();
                Call<CommonAPIResponse> call = apiService.uploadProfilePicture(token, body);

                call.enqueue(new Callback<CommonAPIResponse>() {
                    @Override
                    public void onResponse(Call<CommonAPIResponse> call, Response<CommonAPIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonAPIResponse> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "File not found: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "File Path Selection Error", Toast.LENGTH_LONG).show();
        }
    }
}
