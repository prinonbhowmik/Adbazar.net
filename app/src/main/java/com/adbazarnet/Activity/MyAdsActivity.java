package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.adbazarnet.Adapter.MyAdsAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.FavouriteAdDetails;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdsActivity extends AppCompatActivity {
    private RecyclerView myAdsRecycler;
    private List<FavouriteAdDetails> list;
    private MyAdsAdapter adapter;
    private String token;
    private SharedPreferences sharedPreferences;

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
    }

    private void init() {
        myAdsRecycler= findViewById(R.id.myAdsRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager1.setSmoothScrollbarEnabled(true);
        myAdsRecycler.setLayoutManager(layoutManager1);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
    }
}