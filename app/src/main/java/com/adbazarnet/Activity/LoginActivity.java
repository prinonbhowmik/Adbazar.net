package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText phoneEt,passEt;
    private TextInputLayout phoneLT,passLT;
    private Button loginBtn;
    private String phone,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneEt.getText().toString();
                password = passEt.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    hideKeyboardFrom(LoginActivity.this);
                    phoneLT.setError("Please Enter Phone Number");
                    phoneEt.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    hideKeyboardFrom(LoginActivity.this);
                    passLT.setError("Please Enter Password");
                    passLT.requestFocus();
                } else if (password.length() < 5) {
                    hideKeyboardFrom(LoginActivity.this);
                    passLT.setError("Minimum 6 digits password");
                    passEt.requestFocus();
                } else {
                    phoneLT.setErrorEnabled(false);
                    passLT.setErrorEnabled(false);
                    hideKeyboardFrom(LoginActivity.this);
                    userLogin(phone, password);
                }
            }
        });

    }

    private void userLogin(String phone, String password) {
        Call<UserDetailsModel> call = ApiUtils.getUserService().userLogin(phone,password);
        call.enqueue(new Callback<UserDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                if (response.code()==200){
                    String token = response.body().getToken();
                    if (token!=null){
                        int id = response.body().getUser().getId();
                        SharedPreferences sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putInt("loggedIn", 1);
                        editor.putInt("id", id);
                        editor.putString("name", response.body().getUser().getName());
                        editor.putString("avatar", response.body().getUser().getAvatar());
                        editor.putString("email", response.body().getUser().getEmail());
                        editor.putString("phone_number", response.body().getUser().getPhone_number());
                        editor.commit();
                        Log.d("ShowToken",token+","+id);
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class).
                                putExtra("fragment","home"));
                        finish();
                    }
                }else if(response.code()==400 || response.code()==404){
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsModel> call, Throwable t) {

            }
        });
    }

    private void init() {
        phoneEt = findViewById(R.id.phone_ET);
        phoneLT = findViewById(R.id.phone_LT);
        passEt = findViewById(R.id.password_ET);
        passLT = findViewById(R.id.password_LT);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("fragment","home"));
    }
}