package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Models.DashboardModel;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView totalAdsTv,pendingAdsTv,liveAdsTv,expiredAdsTv,memberTv,expireTv,creditTv,remaingTv;
    private SharedPreferences sharedPreferences;
    private String token;
    private ChipNavigationBar chipNavigationBar;
    private Dialog dialog;
    private int loggedIn;

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

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        startActivity(new Intent(DashboardActivity.this,MainActivity.class)
                                .putExtra("fragment","home"));
                        finish();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(DashboardActivity.this,MainActivity.class)
                                .putExtra("fragment","favourite"));
                        finish();
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                            finish();
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
                                            PostAdActivity.class).putExtra("type","sell"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            rentTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this,
                                            PostAdActivity.class).putExtra("type","rent"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            auctionTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this,
                                            PostAdActivity.class).putExtra("type","bid"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            exchangeTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this,
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
                        startActivity(new Intent(DashboardActivity.this,MainActivity.class)
                                .putExtra("fragment","chat"));
                        finish();
                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }
                        else{
                            //pop-up will be shown
                            dialog = new Dialog(DashboardActivity.this);
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
                                    startActivity(new Intent(DashboardActivity.this,MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this,MyAdsActivity.class));
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
                                    startActivity(new Intent(DashboardActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(DashboardActivity.this,ProfileActivity.class));
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
                                    startActivity(new Intent(DashboardActivity.this,MainActivity.class)
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

    private void init() {
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        totalAdsTv = findViewById(R.id.totalAdsTv);
        pendingAdsTv = findViewById(R.id.pendingAdsTv);
        liveAdsTv = findViewById(R.id.liveAdsTv);
        expiredAdsTv = findViewById(R.id.expiredAdsTv);
        chipNavigationBar = findViewById(R.id.bottom_menu);
        memberTv = findViewById(R.id.memberTv);
        expireTv = findViewById(R.id.expireTv);
        creditTv = findViewById(R.id.creditTv);
        remaingTv = findViewById(R.id.remaingTv);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DashboardActivity.this,MainActivity.class).putExtra("fragment","home"));
    }
}