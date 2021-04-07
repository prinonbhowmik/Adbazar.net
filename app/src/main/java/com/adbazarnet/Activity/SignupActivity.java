package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.User;
import com.adbazarnet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText nameEt,phoneEt,emailEt,passEt;
    private TextInputLayout nameLT,phoneLT,emailLT,passLT;
    private Button signupBtn;
    private String  name, phone,  pass, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
                phone = phoneEt.getText().toString();
                pass = passEt.getText().toString();
                email = emailEt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    nameLT.setError("Please enter full name!");
                    nameEt.requestFocus();
                } else if (TextUtils.isEmpty(phone)){
                    phoneLT.setError("Date of Birth!");
                    phoneEt.requestFocus();
                }else if(TextUtils.isEmpty(pass)){
                    passLT.setError("Please enter password!");
                    passEt.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    emailLT.setError("please enter your address");
                    emailEt.requestFocus();
                }else{
                    userRegister(name,phone,email,pass);
                }
            }
        });
    }

    private void userRegister(String name, String phone, String email, String pass) {
        Call<User> call = ApiUtils.getUserService().userRegister(name,phone,email,pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    List<Object> data = response.body().getUser_phone_numbers();
                    Toast.makeText(SignupActivity.this, "User Successfully Registered!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void init() {
        nameEt = findViewById(R.id.name_Et);
        phoneEt = findViewById(R.id.phone_Et);
        emailEt = findViewById(R.id.email_Et);
        passEt = findViewById(R.id.password_Et);
        nameLT = findViewById(R.id.name_LT);
        phoneLT = findViewById(R.id.phone_LT);
        emailLT = findViewById(R.id.email_LT);
        passLT = findViewById(R.id.password_Lt);
        signupBtn = findViewById(R.id.sign_upBtn);
    }
}