package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Adapter.ImageSliderAdapter;
import com.adbazarnet.Adapter.RelatedProductAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.AdImages;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.Models.RelatedAds;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdDetailsActivity extends AppCompatActivity {

    private int id,userId;
    private SliderView imageSlider;
    private ImageSliderAdapter sliderAdapter;
    private CircleImageView sellerProfileIv;
    private TextView productName,productPrice,uploadTime,categoryTv,conditionTv,
            warrantyTv,descriptionTv,sellerNameTv,locationTv,noDataTv,membershipTV,
            favouriteTv,callNowTV;
    private RecyclerView relatedProductRecycler;
    private RelatedProductAdapter relatedProductAdapter;
    private SharedPreferences sharedPreferences;
    private String token,userPhone;

    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        init();

        Call<AdDetails> call = ApiUtils.getUserService().getAdDetails(id);
        call.enqueue(new Callback<AdDetails>() {
            @Override
            public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                if (response.isSuccessful()){
                    List<AdImages> images = response.body().getAd_images();
                    List<RelatedAds> relatedAds = response.body().getRelated_ads();
                    productName.setText(response.body().getAd_title());
                    productPrice.setText("৳ "+response.body().getPrice());
                    uploadTime.setText(response.body().getCreated());
                    categoryTv.setText(""+response.body().getCategory().getCategory_name()+","
                            +response.body().getCategory().getName());
                    conditionTv.setText(response.body().getCondition());
                    descriptionTv.setText(response.body().getDescription());
                    sellerNameTv.setText(response.body().getUser().getName());
                    locationTv.setText(response.body().getLocation().getName()+", "+
                            response.body().getLocation().getLocation_name());
                    membershipTV.setText(response.body().getUser().getMembership_name()+" Member");
                    userPhone = response.body().getUser().getPhone_number();

                    if (response.body().getUser().getAvatar()!=null) {
                        try {
                            Picasso.get()
                                    .load(response.body().getUser().getAvatar())
                                    .into(sellerProfileIv);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        sellerProfileIv.setImageResource(R.drawable.ic_user);
                    }
                    if (response.body().getWarranty()==null) {
                        warrantyTv.setVisibility(View.GONE);
                    }else{
                        warrantyTv.setText(response.body().getWarranty());
                    }

                    getSliderImage(images);
                    getRelatedProduct(relatedAds);
                 }
            }

            @Override
            public void onFailure(Call<AdDetails> call, Throwable t) {

            }
        });

        favouriteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<FavouriteAds>> call1 = ApiUtils.getUserService().addToFav("Token "+token,userId,id);
                call1.enqueue(new Callback<List<FavouriteAds>>() {
                    @Override
                    public void onResponse(Call<List<FavouriteAds>> call, Response<List<FavouriteAds>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<FavouriteAds>> call, Throwable t) {

                    }
                });
                Dialog dialog2 = new Dialog(AdDetailsActivity.this);
                dialog2.setContentView(R.layout.success_popup);
                dialog2.setCancelable(false);
                dialog2.show();
                TextView textView = dialog2.findViewById(R.id.textview);
                Button okBtn = dialog2.findViewById(R.id.okBtn);
                textView.setText("Ad Saved Successfully.");

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();

                    }
                });
            }
        });

        callNowTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+userPhone));
                startActivity(callIntent);
            }
        });

    }

    private void getRelatedProduct(List<RelatedAds> relatedAds) {
        if (relatedAds.size()>0) {
            relatedProductAdapter = new RelatedProductAdapter(relatedAds, this);
            relatedProductRecycler.setAdapter(relatedProductAdapter);
            relatedProductAdapter.notifyDataSetChanged();
        }else{
            relatedProductRecycler.setVisibility(View.GONE);
            noDataTv.setVisibility(View.VISIBLE);
        }
    }

    private void getSliderImage(List<AdImages> images) {
        sliderAdapter=new ImageSliderAdapter(images);
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();
    }

    private void init() {
        imageSlider = findViewById(R.id.imageSlider);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        uploadTime = findViewById(R.id.uploadTime);
        categoryTv = findViewById(R.id.categoryTv);
        callNowTV = findViewById(R.id.callNowTV);
        conditionTv = findViewById(R.id.conditionTv);
        warrantyTv = findViewById(R.id.warrantyTv);
        descriptionTv = findViewById(R.id.descriptionTv);
        sellerProfileIv = findViewById(R.id.sellerProfileIv);
        locationTv = findViewById(R.id.LocationTv);
        noDataTv = findViewById(R.id.noDataTv);
        membershipTV = findViewById(R.id.membershipTV);
        favouriteTv = findViewById(R.id.favouriteTv);
        sellerNameTv = findViewById(R.id.sellerNameTv);
        relatedProductRecycler = findViewById(R.id.relatedProductRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedProductRecycler.setLayoutManager(layoutManager);
        sliderAdapter = new ImageSliderAdapter(this);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        userId = sharedPreferences.getInt("id",0);
        chipNavigationBar=findViewById(R.id.bottom_menu);
    }
}