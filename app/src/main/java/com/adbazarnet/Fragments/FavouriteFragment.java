package com.adbazarnet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.adbazarnet.Activity.LoginActivity;
import com.adbazarnet.Adapter.FavouriteAdsAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavouriteFragment extends Fragment {

    private RecyclerView favouriteRecycler;
    private List<FavouriteAds> adsList;
    private FavouriteAdsAdapter adapter;
    private String token;
    private SharedPreferences sharedPreferences;
    private ImageView fav_navIcon;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        init(view);

        fav_navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboardFrom(view.getContext());
            }
        });

        if (token.isEmpty()){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
        else{
            Call<List<FavouriteAds>> call= ApiUtils.getUserService().getFavouriteAds("Token "+token);
            call.enqueue(new Callback<List<FavouriteAds>>() {
                @Override
                public void onResponse(Call<List<FavouriteAds>> call, Response<List<FavouriteAds>> response) {
                    if (response.isSuccessful()){
                        adsList = response.body();
                        adapter = new FavouriteAdsAdapter(adsList,getContext());
                        favouriteRecycler.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<FavouriteAds>> call, Throwable t) {
                }
            });
        }

        return view;
    }

    private void init(View view) {
        favouriteRecycler = view.findViewById(R.id.favouriteRecycler);
        fav_navIcon = view.findViewById(R.id.fav_navIcon);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager1.setSmoothScrollbarEnabled(true);
        favouriteRecycler.setLayoutManager(layoutManager1);
        adsList = new ArrayList<>();
        sharedPreferences = getContext().getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);

    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }
}