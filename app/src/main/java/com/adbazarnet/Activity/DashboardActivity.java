package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.DashboardModel;
import com.adbazarnet.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView totalAdsTv,pendingAdsTv,liveAdsTv,expiredAdsTv,memberTv,expireTv,creditTv,remaingTv;
    private SharedPreferences sharedPreferences;
    private String token;

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
    }

    private void init() {
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        totalAdsTv = findViewById(R.id.totalAdsTv);
        pendingAdsTv = findViewById(R.id.pendingAdsTv);
        liveAdsTv = findViewById(R.id.liveAdsTv);
        expiredAdsTv = findViewById(R.id.expiredAdsTv);
        memberTv = findViewById(R.id.memberTv);
        expireTv = findViewById(R.id.expireTv);
        creditTv = findViewById(R.id.creditTv);
        remaingTv = findViewById(R.id.remaingTv);
    }
}