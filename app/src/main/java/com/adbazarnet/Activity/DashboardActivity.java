package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.DashboardModel;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView totalAdsTv,pendingAdsTv,liveAdsTv,expiredAdsTv,memberTv,expireTv,creditTv,remaingTv;
    private SharedPreferences sharedPreferences;
    private String token;
    private BottomNavigationView chipNavigationBar;
    private CircleImageView adPost;
    private Dialog dialog;
    private int loggedIn;
    private ImageView navIcon;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();

        Call<DashboardModel> call = ApiUtils.getUserService().dashboardData("Token "+token);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                if (response.isSuccessful()){
                    totalAdsTv.setText(""+response.body().getTotal_ads());
                    pendingAdsTv.setText(""+response.body().getPending_ads());
                    liveAdsTv.setText(""+response.body().getLive_ads());
                    expiredAdsTv.setText(""+response.body().getExpired_ads());
                    memberTv.setText(response.body().getMembership()+" Member");

                    if (response.body().getExpires_at()==null){
                        expireTv.setText("Never");
                    }else{
                        expireTv.setText(""+response.body().getExpires_at());
                    }
                    creditTv.setText(""+response.body().getTotal_credit());
                    remaingTv.setText(""+response.body().getFree_remaining());
                }
            }

            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {

            }
        });

        chipNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(DashboardActivity.this,MainActivity.class).
                                putExtra("fragment","home"));

                        break;
                    case R.id.favourite:
                        if (loggedIn == 0) {
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(DashboardActivity.this,MainActivity.class).
                                    putExtra("fragment","favourite"));

                        }
                        break;
                    case R.id.chat:
                        if (loggedIn == 0) {
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(DashboardActivity.this,MainActivity.class).
                                    putExtra("fragment","chat"));

                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(DashboardActivity.this);
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
                                    startActivity(new Intent(DashboardActivity.this, MembershipActivity.class));

                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this, MyAdsActivity.class));

                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*chipNavigationBar.setSelectedItemId(R.id.favourite, true);*/
                                    startActivity(new Intent(DashboardActivity.this,MainActivity.class).
                                            putExtra("fragment","favourite"));

                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));

                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));

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

                                    startActivity(new Intent(DashboardActivity.this,MainActivity.class)
                                            .putExtra("fragment","home"));

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
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                }
                else {
                    dialog = new Dialog(DashboardActivity.this);
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
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "sell"));

                            dialog.dismiss();
                        }
                    });
                    rentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "rent"));

                            dialog.dismiss();
                        }
                    });
                    auctionTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "bid"));

                            dialog.dismiss();
                        }
                    });
                    exchangeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "exchange"));

                            dialog.dismiss();
                        }
                    });
                    jobTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "job"));

                            dialog.dismiss();
                        }
                    });
                    lookforbuyTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforbuy"));

                            dialog.dismiss();
                        }
                    });
                    lookforRentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforrent"));

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
            }
        });

    }

    private void init() {
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        totalAdsTv = findViewById(R.id.totalAdsTv);
        pendingAdsTv = findViewById(R.id.pendingAdsTv);
        liveAdsTv = findViewById(R.id.liveAdsTv);
        expiredAdsTv = findViewById(R.id.expiredAdsTv);
        memberTv = findViewById(R.id.memberTv);
        expireTv = findViewById(R.id.expireTv);
        creditTv = findViewById(R.id.creditTv);
        remaingTv = findViewById(R.id.remaingTv);

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
        lang = sharedPreferences.getString("lang","en");
        adPost = findViewById(R.id.adPost);


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
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                break;
            case R.id.home:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));

                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));

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