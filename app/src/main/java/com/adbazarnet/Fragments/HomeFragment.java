package com.adbazarnet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Adapter.SubCatProductsAdapter;
import com.adbazarnet.Adapter.SubCategoriesAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ImageView navIcon;
    private DrawerLayout drawerLayout;
    private List<ProductModel> adlist;
    private RecyclerView adsRecycler;
    private SubCatProductsAdapter adapter;
    private ApiInterface apiInterface;
    private TextView adCountTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        getAllAds();

        return view;
    }

    private void getAllAds() {
        Call<List<ProductModel>> call = apiInterface.getAllAds();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()){
                    adlist = response.body();
                    adCountTv.setText("("+adlist.size()+") Ads");
                    adapter = new SubCatProductsAdapter(adlist,getContext());
                    adsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

            }
        });
    }


    private void init(View view) {
        navIcon = view.findViewById(R.id.navIcon);
        drawerLayout=getActivity().findViewById(R.id.drawerLayout);
        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboardFrom(view.getContext());
            }
        });
        apiInterface = ApiUtils.getUserService();
        adlist = new ArrayList<>();
        adsRecycler = view.findViewById(R.id.adsRecycler);
        adCountTv = view.findViewById(R.id.adCountTv);
        adsRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

}