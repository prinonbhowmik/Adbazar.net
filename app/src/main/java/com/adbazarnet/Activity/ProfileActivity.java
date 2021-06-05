package com.adbazarnet.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Models.User;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
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
    private ChipNavigationBar chipNavigationBar;
    private Dialog dialog;
    private int loggedIn;

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
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(ProfileActivity.this);
            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        startActivity(new Intent(ProfileActivity.this,MainActivity.class)
                                .putExtra("fragment","home"));
                        finish();
                        dialog.dismiss();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(ProfileActivity.this,MainActivity.class)
                                .putExtra("fragment","favourite"));
                        finish();
                        dialog.dismiss();
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            dialog = new Dialog(ProfileActivity.this);
                            dialog.setContentView(R.layout.post_ad_popup);
                            ImageView closeIv = dialog.findViewById(R.id.closeIv);
                            TextView sellItemTv = dialog.findViewById(R.id.sellItemTv);
                            TextView rentTv = dialog.findViewById(R.id.rentTv);
                            TextView auctionTv = dialog.findViewById(R.id.auctionTv);
                            TextView exchangeTv = dialog.findViewById(R.id.exchangeTv);
                            TextView jobTv = dialog.findViewById(R.id.jobTv);
                            TextView brideTv = dialog.findViewById(R.id.brideTv);
                            TextView lookforbuyTv = dialog.findViewById(R.id.lookforbuyTv);
                            TextView lookforRentTv = dialog.findViewById(R.id.lookforRentTv);
                            Button closeBtn = dialog.findViewById(R.id.closeBtn);

                            sellItemTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,
                                            PostAdActivity.class).putExtra("type","sell"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            rentTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,
                                            PostAdActivity.class).putExtra("type","rent"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            auctionTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,
                                            PostAdActivity.class).putExtra("type","bid"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            exchangeTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,
                                            PostAdActivity.class).putExtra("type","exchange"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });

                            closeIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            closeBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        break;
                    case R.id.chat:
                        startActivity(new Intent(ProfileActivity.this,MainActivity.class)
                                .putExtra("fragment","chat"));
                        finish();                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }else{
                            //pop-up will be shown
                            dialog = new Dialog(ProfileActivity.this);
                            dialog.setContentView(R.layout.profile_option_xml);
                            CardView close  = dialog.findViewById(R.id.closeTv);
                            TextView dashboard = dialog.findViewById(R.id.dashboardTv);
                            TextView myAds = dialog.findViewById(R.id.myAdsTv);
                            TextView favouriteTv = dialog.findViewById(R.id.favouriteTv);
                            TextView membership = dialog.findViewById(R.id.membershipTv);
                            TextView profile = dialog.findViewById(R.id.profileTv);
                            TextView logout = dialog.findViewById(R.id.logoutTv);

                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    finish();
                                    startActivity(getIntent());
                                }
                            });

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,MyAdsActivity.class));
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    chipNavigationBar.setItemSelected(R.id.favourite, true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment()).commit();
                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                                    finish();
                                }
                            });

                            logout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<UserDetailsModel> call = ApiUtils.getUserService().logoutUser();
                                    call.enqueue(new Callback<UserDetailsModel>() {
                                        @Override
                                        public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<UserDetailsModel> call, Throwable t) {

                                        }
                                    });
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", "");
                                    editor.putInt("loggedIn", 0);
                                    editor.putInt("id", 0);
                                    editor.commit();
                                    finish();
                                    startActivity(new Intent(ProfileActivity.this,MainActivity.class)
                                            .putExtra("fragment","home"));
                                }
                            });

                            dialog.setCancelable(false);
                            dialog.show();

                            break;
                        }
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
        chipNavigationBar = findViewById(R.id.bottom_menu);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class).putExtra("fragment","home"));
    }
}