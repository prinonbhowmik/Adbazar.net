package com.adbazarnet.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.User;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEt,phnEt,emailEt,oldPassEt,newPassEt,confirmPassEt;
    private Button updateProfileBtn,updatePassBtn;
    private CircleImageView profileIv;
    private SharedPreferences sharedPreferences;
    private String name,email,phone,password,avatar,token;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

        getData();

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEt.getText().toString()==null){
                    nameEt.setError("Please provide name!");
                }else if(emailEt.getText().toString()==null || !emailEt.getText().toString().contains("@") || !emailEt.getText().toString().contains(".")){
                    emailEt.setError("Please provide valid email address");
                }else if(phnEt.getText().toString()==null || phnEt.getText().toString().length()<11){
                    phnEt.setError("Please provide valid phone no.");
                }else{
                    User user = new User(nameEt.getText().toString(),emailEt.getText().toString(),phnEt.getText().toString());
                    Call<User> call = ApiUtils.getUserService().updateProfile("Token "+token,user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(ProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", nameEt.getText().toString());
                                editor.putString("email", emailEt.getText().toString());
                                editor.putString("phone_number", phnEt.getText().toString());
                                editor.commit();
                                startActivity(getIntent());
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        updatePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassEt.getText().toString()==null || oldPassEt.getText().toString().length()<5){
                    oldPassEt.setError("Enter old password!");
                }else if (newPassEt.getText().toString()==null || newPassEt.getText().toString().length()<5){
                    newPassEt.setError("Enter new Password");
                }else if(confirmPassEt.getText().toString()==null || confirmPassEt.getText().toString().length()<5){
                    confirmPassEt.setError("Confirm new password");
                }else if (newPassEt.getText().toString()==confirmPassEt.getText().toString()){
                    Toast.makeText(ProfileActivity.this, "New password doesn't matched", Toast.LENGTH_SHORT).show();
                }else{
                    UserDetailsModel model = new UserDetailsModel(oldPassEt.getText().toString(),newPassEt.getText().toString());
                    Call<UserDetailsModel> call = ApiUtils.getUserService().updatePassword("Token "+token,model);
                    call.enqueue(new Callback<UserDetailsModel>() {
                        @Override
                        public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                            if (response.code()==200) {
                                Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                startActivity(getIntent());
                                finish();
                            }else if(response.code()==400){
                                Toast.makeText(ProfileActivity.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailsModel> call, Throwable t) {

                        }
                    });

                }
            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(true)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(ProfileActivity.this);
            }
        });
    }

    private void getData() {
        name = sharedPreferences.getString("name",null);
        email = sharedPreferences.getString("email",null);
        phone = sharedPreferences.getString("phone_number",null);
        password = sharedPreferences.getString("password",null);
        avatar = sharedPreferences.getString("avatar",null);
        token = sharedPreferences.getString("token",null);

        nameEt.setText(name);
        phnEt.setText(phone);
        emailEt.setText(email);
        oldPassEt.setText(password);

        if (avatar!=null) {
            try {
                Picasso.get()
                        .load(avatar)
                        .into(profileIv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            profileIv.setImageResource(R.drawable.ic_user);
        }

    }

    private void init() {
        nameEt = findViewById(R.id.nameEt);
        phnEt = findViewById(R.id.phnEt);
        emailEt = findViewById(R.id.emailEt);
        oldPassEt = findViewById(R.id.oldPassEt);
        newPassEt = findViewById(R.id.newPassEt);
        confirmPassEt = findViewById(R.id.confirmPassEt);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updatePassBtn = findViewById(R.id.updatePassBtn);
        profileIv = findViewById(R.id.profileIv);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageUri = resultUri;
                File file = new File(imageUri.getPath());
                RequestBody userImage = RequestBody.create(MediaType.parse("image/*"), file);

                MultipartBody.Part user_photo = MultipartBody.Part.createFormData("avatar", file.getName(), userImage);

                Call<User> call = ApiUtils.getUserService().updateImage("Token "+token,user_photo);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code()==200){
                            profileIv.setImageURI(imageUri);
                            Toast.makeText(ProfileActivity.this, "Image Updated!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(ProfileActivity.this, "Failed"+error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}