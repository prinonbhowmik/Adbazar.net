package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.BidsFragment;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private ChipNavigationBar chipNavigationBar;
    private int id,loggedIn;
    private Dialog dialog;
    private String loadFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Intent intent = getIntent();
        loadFragment = intent.getStringExtra("fragment");
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        if(id!=0){
            navigationView.getMenu().removeItem(R.id.login);
        }

        if (loadFragment.equals("home")) {
            chipNavigationBar.setItemSelected(R.id.home, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }else if(loadFragment.equals("favourite")){
            chipNavigationBar.setItemSelected(R.id.favourite, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment()).commit();
        }else if(loadFragment.equals("chat")){
            chipNavigationBar.setItemSelected(R.id.chat, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
        }

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                        home.replace(R.id.fragment_container, new HomeFragment());
                        home.commit();
                        break;
                    case R.id.favourite:
                        if (loggedIn==0){
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }else {
                            FragmentTransaction favourite = getSupportFragmentManager().beginTransaction();
                            favourite.replace(R.id.fragment_container, new FavouriteFragment());
                            favourite.commit();
                        }
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }else {
                            dialog = new Dialog(MainActivity.this);
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
                                    startActivity(new Intent(MainActivity.this, PostAdActivity.class));
                                    finish();
                                    dialog.dismiss();
                                }
                            });

                            closeIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    chipNavigationBar.setItemSelected(R.id.home, true);
                                }
                            });

                            closeBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    chipNavigationBar.setItemSelected(R.id.home, true);
                                }
                            });
                            dialog.show();
                        }
                        break;
                    case R.id.chat:
                        if (loggedIn==0){
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }else {
                            FragmentTransaction chat = getSupportFragmentManager().beginTransaction();
                            chat.replace(R.id.fragment_container, new ChatFragment());
                            chat.commit();
                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }else{
                            //pop-up will be shown
                            dialog = new Dialog(MainActivity.this);
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
                                    startActivity(new Intent(MainActivity.this,MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MainActivity.this,MyAdsActivity.class));
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
                                    startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
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
                            dialog.show();

                            break;
                        }
                }

            }
        });

    }

    private void init() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        chipNavigationBar=findViewById(R.id.bottom_menu);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
            case R.id.home:
                FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                home.replace(R.id.fragment_container, new HomeFragment());
                home.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                FragmentTransaction bids = getSupportFragmentManager().beginTransaction();
                bids.replace(R.id.fragment_container, new BidsFragment());
                bids.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.contact:
                Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
                break;

        }
        return false;
    }
}