package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.User;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText nameEt, phnEt, emailEt, oldPassEt, newPassEt, confirmPassEt;
    private Button updateProfileBtn, updatePassBtn;
    private CircleImageView profileIv;
    private SharedPreferences sharedPreferences;
    private String name, email, phone, password, avatar, token;
    private Uri imageUri;
    private Dialog dialog;
    private int loggedIn;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView chipNavigationBar;
    private CircleImageView adPost;
    private ImageView navIcon;
    private String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        getLocale();
        init();

        getData();

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEt.getText().toString() == null) {
                    nameEt.setError("Please provide name!");
                } else if (emailEt.getText().toString() == null || !emailEt.getText().toString().contains("@") || !emailEt.getText().toString().contains(".")) {
                    emailEt.setError("Please provide valid email address");
                } else if (phnEt.getText().toString() == null || phnEt.getText().toString().length() < 11) {
                    phnEt.setError("Please provide valid phone no.");
                } else {
                    User user = new User(nameEt.getText().toString(), emailEt.getText().toString(), phnEt.getText().toString());
                    Call<User> call = ApiUtils.getUserService().updateProfile("Token " + token, user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", nameEt.getText().toString());
                                editor.putString("email", emailEt.getText().toString());
                                editor.putString("phone_number", phnEt.getText().toString());
                                editor.commit();
                                startActivity(getIntent());

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        updatePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassEt.getText().toString() == null || oldPassEt.getText().toString().length() < 5) {
                    oldPassEt.setError("Enter old password!");
                } else if (newPassEt.getText().toString() == null || newPassEt.getText().toString().length() < 5) {
                    newPassEt.setError("Enter new Password");
                } else if (confirmPassEt.getText().toString() == null || confirmPassEt.getText().toString().length() < 5) {
                    confirmPassEt.setError("Confirm new password");
                } else if (newPassEt.getText().toString() == confirmPassEt.getText().toString()) {
                    Toast.makeText(ProfileActivity.this, "New password doesn't matched", Toast.LENGTH_SHORT).show();
                } else {
                    UserDetailsModel model = new UserDetailsModel(oldPassEt.getText().toString(), newPassEt.getText().toString());
                    Call<UserDetailsModel> call = ApiUtils.getUserService().updatePassword("Token " + token, model);
                    call.enqueue(new Callback<UserDetailsModel>() {
                        @Override
                        public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                            if (response.code() == 200) {
                                Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                startActivity(getIntent());

                            } else if (response.code() == 400) {
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

        chipNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class).
                                putExtra("fragment", "home"));

                        break;
                    case R.id.favourite:
                        if (loggedIn == 0) {
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class).
                                    putExtra("fragment", "favourite"));

                        }
                        break;
                    case R.id.chat:
                        if (loggedIn == 0) {
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(ProfileActivity.this, MainActivity.class).
                                    putExtra("fragment", "chat"));

                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(ProfileActivity.this);
                            dialog.setContentView(R.layout.profile_option_xml);
                            Button close = dialog.findViewById(R.id.closeTv);
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

                                    startActivity(getIntent());
                                }
                            });

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this, MembershipActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this, MyAdsActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this, MainActivity.class).
                                            putExtra("fragment", "favourite"));
                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                                    dialog.dismiss();
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

                                    startActivity(getIntent());
                                }
                            });

                            dialog.setCancelable(false);
                            if (!isFinishing()) {
                                dialog.show();
                            }
                            break;
                        }
                }
                return false;
            }
        });

        adPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn == 0) {
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

                } else {
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
                                    PostAdActivity.class).putExtra("type", "sell"));

                            dialog.dismiss();
                        }
                    });
                    rentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "rent"));

                            dialog.dismiss();
                        }
                    });
                    auctionTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "bid"));

                            dialog.dismiss();
                        }
                    });
                    exchangeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "exchange"));

                            dialog.dismiss();
                        }
                    });
                    jobTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "job"));

                            dialog.dismiss();
                        }
                    });
                    lookforbuyTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforbuy"));

                            dialog.dismiss();
                        }
                    });
                    lookforRentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ProfileActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforrent"));

                            dialog.dismiss();
                        }
                    });

                    closeIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            chipNavigationBar.setSelectedItemId(R.id.home);
                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            chipNavigationBar.setSelectedItemId(R.id.home);
                        }
                    });
                    dialog.show();
                }
            }
        });

    }

    private void getLocale() {

        lang = sharedPreferences.getString("lang", "");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(getResources().getConfiguration());
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }

    private void getData() {
        name = sharedPreferences.getString("name", null);
        email = sharedPreferences.getString("email", null);
        phone = sharedPreferences.getString("phone_number", null);
        password = sharedPreferences.getString("password", null);
        avatar = sharedPreferences.getString("avatar", null);
        token = sharedPreferences.getString("token", null);

        nameEt.setText(name);
        phnEt.setText(phone);
        emailEt.setText(email);
        oldPassEt.setText(password);

        if (avatar != null) {
            try {
                Picasso.get()
                        .load(avatar)
                        .into(profileIv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
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

        loggedIn = sharedPreferences.getInt("loggedIn", 0);
        navIcon = findViewById(R.id.navIcon);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.home_navigation_drawer);
        navigationView.getMenu().removeItem(R.id.login);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        chipNavigationBar = findViewById(R.id.bottom_menu);
        chipNavigationBar.getMenu().clear();
        chipNavigationBar.inflateMenu(R.menu.bottom_drawer_menu);
        lang = sharedPreferences.getString("lang", "en");
        adPost = findViewById(R.id.adPost);
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

                Call<User> call = ApiUtils.getUserService().updateImage("Token " + token, user_photo);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
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
                Toast.makeText(ProfileActivity.this, "Failed" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

                break;
            case R.id.home:
                startActivity(new Intent(ProfileActivity.this, MainActivity.class)
                        .putExtra("fragment", "home"));

                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                startActivity(new Intent(ProfileActivity.this, MainActivity.class)
                        .putExtra("fragment", "home"));

                drawerLayout.closeDrawers();
                break;
            case R.id.contact:

                break;
            case R.id.language:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.langugae_question)
                        .setPositiveButton("English", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Locale locale = new Locale("en");
                                Locale.setDefault(locale);
                                Configuration configuration = new Configuration();
                                configuration.locale = locale;
                                getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
                                SharedPreferences.Editor editor = getSharedPreferences("MyRef", MODE_PRIVATE).edit();
                                editor.putString("lang", "en");
                                editor.apply();
                                startActivity(getIntent());

                            }
                        })
                        .setNegativeButton("বাংলা", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Locale locale2 = new Locale("bn");
                                Locale.setDefault(locale2);
                                Configuration configuration2 = new Configuration();
                                configuration2.locale = locale2;
                                getBaseContext().getResources().updateConfiguration(configuration2,
                                        getBaseContext().getResources().getDisplayMetrics());
                                SharedPreferences.Editor editor2 = getSharedPreferences("MyRef",
                                        MODE_PRIVATE).edit();
                                editor2.putString("lang", "bn");
                                editor2.apply();
                                startActivity(getIntent());

                            }
                        })
                        .show();
                break;

        }
        return false;
    }
}