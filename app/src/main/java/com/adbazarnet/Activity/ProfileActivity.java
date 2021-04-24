package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEt,phnEt,emailEt,oldPassEt,newPassEt,confirmPassEt;
    private Button updateProfileBtn,updatePassBtn;
    private CircleImageView profileIv;
    private SharedPreferences sharedPreferences;
    private String name,email,phone,password,avatar,token;

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
                }else if(emailEt.getText().toString()==null || !emailEt.getText().toString().contains("@") || emailEt.getText().toString().contains(".")){
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
                            if (response.isSuccessful()){
                                Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                startActivity(getIntent());
                                finish();
                                /*if (response.body().getStatus().equals("success")){
                                    Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(getIntent());
                                    finish();
                                }else{
                                    Toast.makeText(ProfileActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailsModel> call, Throwable t) {

                        }
                    });

                }
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

        Log.d("avatar",avatar);

        nameEt.setText(name);
        phnEt.setText(phone);
        emailEt.setText(email);
        oldPassEt.setText(password);

        try {
            Picasso.get()
                    .load(avatar)
                    .into(profileIv);
        } catch (Exception e) {
            e.printStackTrace();
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
}