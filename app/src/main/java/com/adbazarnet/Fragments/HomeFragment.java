package com.adbazarnet.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Adapter.LocationAdapter;
import com.adbazarnet.Adapter.SubCatProductsAdapter;
import com.adbazarnet.Adapter.SubCategoriesAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements SubCategoryProductsInterface {
    private ImageView navIcon;
    private DrawerLayout drawerLayout;
    private List<ProductModel> adlist;
    private EditText searchEt;
    private Context context;
    private ImageView searchBtn;
    public static RecyclerView adsRecycler;
    private CategoryNamesAdapter categoryNamesAdapter;
    public int homeCount = 0;
    private TextView categoryTv,locationTv;
    private SubCatProductsAdapter adapter;
    private SubCategoriesAdapter adapter2;
    private LocationAdapter locationAdapter;
    private ApiInterface apiInterface;
    public static TextView adCountTv;
    public static Dialog dialog;
    private SubCatProductsAdapter subProductAdapter;
    public static String lang;
    private SharedPreferences sharedPreferences;
    private ScrollView scrollView;
    private RelativeLayout layout1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        getLocale();

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

        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.select_location_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPopL);
                TextView allLocationTv = dialog.findViewById(R.id.allLocationTv);
                RecyclerView locationRecycler = dialog.findViewById(R.id.locationRecycler);
                locationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

                closeCatPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                allLocationTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getAllAds();
                    }
                });

                getAllLocations(locationRecycler);

                dialog.setCancelable(false);
                dialog.show();
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                float y = 0;
                if (scrollView.getScrollY() < y) {
                    layout1.setGravity(View.GONE);
                } else {
                    layout1.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void getLocale() {
        sharedPreferences = getContext().getSharedPreferences("MyRef", MODE_PRIVATE);
        lang = sharedPreferences.getString("lang","");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

    }

    private void getAllLocations(RecyclerView locationRecycler) {
        Call<List<LocationsModel>> call = apiInterface.getAllLocations(lang);
        call.enqueue(new Callback<List<LocationsModel>>() {
            @Override
            public void onResponse(Call<List<LocationsModel>> call, Response<List<LocationsModel>> response) {
                if (response.isSuccessful()){
                    List<LocationsModel> list = response.body();
                    locationAdapter = new LocationAdapter(list,getContext());
                    locationRecycler.setAdapter(locationAdapter);
                }
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LocationsModel>> call, Throwable t) {

            }
        });
    }

    private void getCategoriesList(RecyclerView categoriesRecycler) {
        Call<List<CategoriesModel>> call = apiInterface.getProductsCategories(lang);
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
        Call<List<ProductModel>> call = apiInterface.getAllAds(lang);
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
        layout1 = view.findViewById(R.id.layout1);
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
        scrollView = view.findViewById(R.id.scrollView);
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