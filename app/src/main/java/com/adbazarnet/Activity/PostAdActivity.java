package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Adapter.LocationAdapter;
import com.adbazarnet.Adapter.PostAdCategoryAdapter;
import com.adbazarnet.Adapter.PostAdLocationAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdActivity extends AppCompatActivity {

    public static TextView locationTv,categoryTv;
    public static String ad_Type;
    private EditText titleEt,otherInfoEt,priceEt,phnnoEt1,phnnoEt2,phnnoEt3,descriptionEt;
    private Switch negotiableBtn,hidePhnBtn;
    private Button addPhnBtn,addImgBtn;
    private ImageView img1,img2,img3,img4,img5;
    private Uri imageUri;
    public static Dialog dialog;
    private ApiInterface apiInterface;
    private PostAdCategoryAdapter categoryNamesAdapter;
    private PostAdLocationAdapter locationAdapter;
    private AutoCompleteTextView conditionSpinner;
    private String[] conditionArray = {"Used", "New", "Recondition"};
    private String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        init();

        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PostAdActivity.this);
                dialog.setContentView(R.layout.select_category_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPop);
                TextView allCategoryTv = dialog.findViewById(R.id.allCategoryTv);
                RecyclerView categoriesRecycler = dialog.findViewById(R.id.categoriesRecycler);
                categoriesRecycler.setLayoutManager(new LinearLayoutManager(PostAdActivity.this));


                Call<List<CategoriesModel>> call = apiInterface.getProductsCategories();
                call.enqueue(new Callback<List<CategoriesModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                        if (response.isSuccessful()){
                            List<CategoriesModel> list = response.body();
                            categoryNamesAdapter = new PostAdCategoryAdapter(list,PostAdActivity.this);
                            categoriesRecycler.setAdapter(categoryNamesAdapter);
                        }
                        categoryNamesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {

                    }
                });

                closeCatPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                allCategoryTv.setVisibility(View.GONE);

                dialog.setCancelable(false);
                dialog.show();
            }
        });

        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PostAdActivity.this);
                dialog.setContentView(R.layout.select_location_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPopL);
                TextView allLocationTv = dialog.findViewById(R.id.allLocationTv);
                RecyclerView locationRecycler = dialog.findViewById(R.id.locationRecycler);
                locationRecycler.setLayoutManager(new LinearLayoutManager(PostAdActivity.this));

                closeCatPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                allLocationTv.setVisibility(View.GONE);

                Call<List<LocationsModel>> call = apiInterface.getAllLocations();
                call.enqueue(new Callback<List<LocationsModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationsModel>> call, Response<List<LocationsModel>> response) {
                        if (response.isSuccessful()){
                            List<LocationsModel> list = response.body();
                            locationAdapter = new PostAdLocationAdapter(list,PostAdActivity.this);
                            locationRecycler.setAdapter(locationAdapter);
                        }
                        locationAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<LocationsModel>> call, Throwable t) {

                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }
        });

    }


    private void init() {
        locationTv = findViewById(R.id.locationTv);
        categoryTv = findViewById(R.id.categoryTv);
        titleEt = findViewById(R.id.titleEt);
        otherInfoEt = findViewById(R.id.otherInfoEt);
        priceEt = findViewById(R.id.priceEt);
        phnnoEt1 = findViewById(R.id.phnnoEt1);
        phnnoEt2 = findViewById(R.id.phnnoEt2);
        phnnoEt3 = findViewById(R.id.phnnoEt3);
        descriptionEt = findViewById(R.id.descriptionEt);
        addImgBtn = findViewById(R.id.addImgBtn);
        addPhnBtn = findViewById(R.id.addPhnBtn);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        ArrayAdapter<String> product_color = new ArrayAdapter<String>(this, R.layout.spinner_item_design, R.id.simpleSpinner, conditionArray);
        product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        conditionSpinner.setText(product_color.getItem(0),false);
        conditionSpinner.setAdapter(product_color);
        conditionSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionSpinner.showDropDown();
            }
        });
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        apiInterface = ApiUtils.getUserService();
    }

    public void setNegotiable(View view) {
    }
}