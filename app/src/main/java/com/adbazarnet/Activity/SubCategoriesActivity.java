package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.adbazarnet.Adapter.SubCatProductsAdapter;
import com.adbazarnet.Adapter.SubCategoriesAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoriesActivity extends AppCompatActivity implements SubCategoryProductsInterface {

    private ImageView backBtn;
    private TextView titleTxt;
    private String title,ad_type;
    private int id;
    private RecyclerView subCategoryRecyclerView,productRecycler;
    private SubCategoriesAdapter adapter;
    private SubCatProductsAdapter subProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        ad_type = intent.getStringExtra("ad_type");
        id = intent.getIntExtra("id",0);
        init();

        getSubCategories();
    }

    private void getSubCategories() {

    }

    private void init() {
        backBtn = findViewById(R.id.backBtn);
        titleTxt = findViewById(R.id.titleTxt);
        titleTxt.setText(title);
        subCategoryRecyclerView = findViewById(R.id.subCategoryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        subCategoryRecyclerView.setLayoutManager(layoutManager);
        productRecycler = findViewById(R.id.productRecycler);
        productRecycler.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void onClick(String slug) {

    }
}