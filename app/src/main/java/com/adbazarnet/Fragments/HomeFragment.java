package com.adbazarnet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ImageView navIcon;
    private DrawerLayout drawerLayout;
    private List<CategoriesModel> list;
    private RecyclerView categoriesRecycler;
    private CategoryNamesAdapter categoryNamesAdapter;
    private ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        getCategoriesName();

        return view;
    }

    private void getCategoriesName() {
        Call<List<CategoriesModel>> call = apiInterface.getProductsCategories();
        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {

                if (response.isSuccessful()){
                    list = response.body();
                    categoryNamesAdapter = new CategoryNamesAdapter(list,getContext());
                    categoriesRecycler.setAdapter(categoryNamesAdapter);
                    categoryNamesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {

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
        list = new ArrayList<>();
        categoriesRecycler = view.findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecycler.setLayoutManager(layoutManager);
    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

}