package com.adbazarnet.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Activity.SubCategoriesActivity;
import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Adapter.SubCatProductsAdapter;
import com.adbazarnet.Adapter.SubCategoriesAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Interface.SubCategoryClick;
import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SubCategoryProductsInterface {
    private ImageView navIcon;
    private DrawerLayout drawerLayout;
    private List<ProductModel> adlist;
    private EditText searchEt;
    private Context context;
    private ImageView searchBtn;
    public static RecyclerView adsRecycler;
    private CategoryNamesAdapter categoryNamesAdapter;
    private TextView categoryTv,locationTv;
    private SubCatProductsAdapter adapter;
    private SubCategoriesAdapter adapter2;
    private ApiInterface apiInterface;
    public static TextView adCountTv;
    public static Dialog dialog;
    private SubCatProductsAdapter subProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        getAllAds();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = searchEt.getText().toString();
                if (newText== null || newText.equals("")){
                    getAllAds();
                }else{
                    adapter.getFilter().filter(newText);

                }
            }
        });

        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.select_category_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPop);
                TextView allCategoryTv = dialog.findViewById(R.id.allCategoryTv);
                RecyclerView categoriesRecycler = dialog.findViewById(R.id.categoriesRecycler);
                categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                getCategoriesList(categoriesRecycler);

                closeCatPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                allCategoryTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getAllAds();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }
        });

        return view;
    }

    private void getCategoriesList(RecyclerView categoriesRecycler) {
        Call<List<CategoriesModel>> call = apiInterface.getProductsCategories();
        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                if (response.isSuccessful()){
                    List<CategoriesModel> list = response.body();
                    categoryNamesAdapter = new CategoryNamesAdapter(list, context);
                    categoriesRecycler.setAdapter(categoryNamesAdapter);
                }
                categoryNamesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {

            }
        });
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
        searchEt = view.findViewById(R.id.searchEt);
        searchBtn = view.findViewById(R.id.searchBtn);
        categoryTv = view.findViewById(R.id.categoryTv);
        locationTv = view.findViewById(R.id.locationTv);
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

    @Override
    public void onClick(String slug) {

    }


}