package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.adbazarnet.Adapter.MyAdsAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.FavouriteAdDetails;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView myAdsRecycler;
    private List<FavouriteAdDetails> list;
    private MyAdsAdapter adapter;
    private String token;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView chipNavigationBar;
    private CircleImageView adPost;
    private Spinner spinner;
    String[] languageArray = {"English","বাংলা"};
    private String language;
    private Dialog dialog;
    private int loggedIn;
    private ImageView navIcon;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        init();

        Call<List<FavouriteAdDetails>> call = ApiUtils.getUserService().getMyAds("Token "+token);
        call.enqueue(new Callback<List<FavouriteAdDetails>>() {
            @Override
            public void onResponse(Call<List<FavouriteAdDetails>> call, Response<List<FavouriteAdDetails>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    adapter = new MyAdsAdapter(list,MyAdsActivity.this);
                    myAdsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<FavouriteAdDetails>> call, Throwable t) {

            }
        });

        chipNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                        home.replace(R.id.fragment_container, new HomeFragment());
                        home.commit();
                        break;
                    case R.id.favourite:
                        if (loggedIn == 0) {
                            startActivity(new Intent(MyAdsActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            FragmentTransaction favourite = getSupportFragmentManager().beginTransaction();
                            favourite.replace(R.id.fragment_container, new FavouriteFragment());
                            favourite.commit();
                        }
                        break;
                    case R.id.chat:
                        if (loggedIn == 0) {
                            startActivity(new Intent(MyAdsActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            FragmentTransaction chat = getSupportFragmentManager().beginTransaction();
                            chat.replace(R.id.fragment_container, new ChatFragment());
                            chat.commit();
                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(MyAdsActivity.this, LoginActivity.class));
                            finish();
                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(MyAdsActivity.this);
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
                                    finish();
                                    startActivity(getIntent());
                                }
                            });

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MyAdsActivity.this, MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MyAdsActivity.this, MyAdsActivity.class));
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*chipNavigationBar.setSelectedItemId(R.id.favourite, true);*/
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment()).commit();
                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MyAdsActivity.this, DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MyAdsActivity.this, ProfileActivity.class));
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
                    startActivity(new Intent(MyAdsActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    dialog = new Dialog(MyAdsActivity.this);
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
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "sell"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    rentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "rent"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    auctionTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "bid"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    exchangeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "exchange"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    jobTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "job"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    lookforbuyTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforbuy"));
                            finish();
                            dialog.dismiss();
                        }
                    });
                    lookforRentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforrent"));
                            finish();
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

    private void init() {
        myAdsRecycler= findViewById(R.id.myAdsRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager1.setSmoothScrollbarEnabled(true);
        myAdsRecycler.setLayoutManager(layoutManager1);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        chipNavigationBar = findViewById(R.id.bottom_menu);

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
        lang = sharedPreferences.getString("lang","en");
        adPost = findViewById(R.id.adPost);
        spinner = (Spinner) navigationView.getMenu().findItem(R.id.language).getActionView();
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.
                simple_spinner_dropdown_item,languageArray));
        spinner.setSelection(0);
        if (lang.equals("en")){
            spinner.setSelection(0);
        }else{
            spinner.setSelection(1);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MyAdsActivity.this,MainActivity.class).putExtra("fragment","home"));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MyAdsActivity.this, LoginActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(MyAdsActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));
                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                startActivity(new Intent(MyAdsActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));
                drawerLayout.closeDrawers();
                break;
            case R.id.contact:

                break;
            case R.id.language:
                language = spinner.getSelectedItem().toString();

                if (language.equals("বাংলা")) {
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
                }else{
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
                break;

        }

        return false;
    }
}