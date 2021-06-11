package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText nameEt,phoneEt,emailEt,passEt;
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
                    nameEt.setError("Please enter full name!");
                    nameEt.requestFocus();
                } else if (TextUtils.isEmpty(phone)){
                    phoneEt.setError("Date of Birth!");
                    phoneEt.requestFocus();
                }else if(TextUtils.isEmpty(pass)){
                    passEt.setError("Please enter password!");
                    passEt.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    emailEt.setError("please enter your address");
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
                if (response.code()==201){
                    List<Object> data = response.body().getUser_phone_numbers();
                    Toast.makeText(SignupActivity.this, "User Successfully Registered!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this, "Email or phone no matched with other user!", Toast.LENGTH_LONG).show();

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
        passEt = findViewById(R.id.pass_Et);
        signupBtn = findViewById(R.id.sign_upBtn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        finish();
    }
}