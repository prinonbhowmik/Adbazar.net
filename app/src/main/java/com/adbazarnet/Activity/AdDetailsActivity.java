package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adbazarnet.Adapter.ImageSliderAdapter;
import com.adbazarnet.Adapter.RelatedProductAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.AdImages;
import com.adbazarnet.Models.RelatedAds;
import com.adbazarnet.R;
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

    private int id;
    private SliderView imageSlider;
    private ImageSliderAdapter sliderAdapter;
    private CircleImageView sellerProfileIv;
    private TextView productName,productPrice,uploadTime,categoryTv,conditionTv,
            warrantyTv,descriptionTv,sellerNameTv,locationTv,noDataTv,membershipTV;
    private RecyclerView relatedProductRecycler;
    private RelatedProductAdapter relatedProductAdapter;

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
        conditionTv = findViewById(R.id.conditionTv);
        warrantyTv = findViewById(R.id.warrantyTv);
        descriptionTv = findViewById(R.id.descriptionTv);
        sellerProfileIv = findViewById(R.id.sellerProfileIv);
        locationTv = findViewById(R.id.LocationTv);
        noDataTv = findViewById(R.id.noDataTv);
        membershipTV = findViewById(R.id.membershipTV);
        sellerNameTv = findViewById(R.id.sellerNameTv);
        relatedProductRecycler = findViewById(R.id.relatedProductRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedProductRecycler.setLayoutManager(layoutManager);
        sliderAdapter = new ImageSliderAdapter(this);

    }
}