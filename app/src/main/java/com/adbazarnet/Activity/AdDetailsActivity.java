package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Adapter.BidsShowAdapter;
import com.adbazarnet.Adapter.ChatMsgAdapter;
import com.adbazarnet.Adapter.ImageSliderAdapter;
import com.adbazarnet.Adapter.RelatedProductAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Interface.GetBiderIdInterface;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.AdImages;
import com.adbazarnet.Models.BidModel;
import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.Models.ChatModel2;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.Models.RelatedAds;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GetBiderIdInterface {

    private int id,userId,sellerId;
    private SliderView imageSlider;
    private ImageSliderAdapter sliderAdapter;
    private CircleImageView sellerProfileIv;
    private TextView productName,productPrice,uploadTime,categoryTv,conditionTv,
            warrantyTv,descriptionTv,sellerNameTv,locationTv,noDataTv,membershipTV,
            favouriteTv,callNowTV,noBidTv,bidBtn,chatTV;
    private RecyclerView relatedProductRecycler,bidRecycler;
    private RelatedProductAdapter relatedProductAdapter;
    private BidsShowAdapter bidAdapter;
    private SharedPreferences sharedPreferences;
    private String token,userPhone,location,prductimg,category;
    private Dialog dialog;
    private ChipNavigationBar chipNavigationBar;
    private int loggedIn,receiver;
    private RelativeLayout relatedLayout,bidLayout;
    private boolean is_bid;
    private EditText bidEt;
    private FrameLayout contactLayout;

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
                    category = response.body().getCategory().getCategory_name();
                    conditionTv.setText(response.body().getCondition());
                    descriptionTv.setText(response.body().getDescription());
                    sellerNameTv.setText(response.body().getUser().getName());
                    locationTv.setText(response.body().getLocation().getName()+", "+
                            response.body().getLocation().getLocation_name());
                    location = response.body().getLocation().getName();
                    membershipTV.setText(response.body().getUser().getMembership_name()+" Member");
                    sellerId = response.body().getUser().getId();
                    userPhone = response.body().getUser().getPhone_number();
                    is_bid = response.body().isIs_bid();
                    receiver = response.body().getUser().getId();

                    if (is_bid==true){
                        relatedLayout.setVisibility(View.GONE);
                        bidLayout.setVisibility(View.VISIBLE);
                        contactLayout.setVisibility(View.GONE);

                        showBids(id);
                    }

                    if (response.body().getUser().getAvatar()!=null) {
                        try {
                            Picasso.get()
                                    .load(response.body().getUser().getAvatar())
                                    .into(sellerProfileIv);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        prductimg = response.body().getUser().getAvatar();
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

        chatTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog2 = new Dialog(AdDetailsActivity.this);
                Call<ChatModel> call = ApiUtils.getUserService().createChannel("Token "+token,id,receiver);
                call.enqueue(new Callback<ChatModel>() {
                    @Override
                    public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                        if (response.isSuccessful()){
                            int channelID = response.body().getId();
                            dialog2.setContentView(R.layout.ad_chat_dialog);
                            dialog2.show();
                            ImageView productIv = dialog2.findViewById(R.id.productIv);
                            TextView headerTv = dialog2.findViewById(R.id.header);
                            TextView productNameTv = dialog2.findViewById(R.id.productNameTv);
                            TextView locationNameTv = dialog2.findViewById(R.id.locationNameTv);
                            TextView priceTv = dialog2.findViewById(R.id.priceTv);
                            RecyclerView msgRecycler = dialog2.findViewById(R.id.msgRecycler);
                            msgRecycler.setLayoutManager(new LinearLayoutManager(AdDetailsActivity.this));
                            EditText msgEt = dialog2.findViewById(R.id.msgEt);
                            ImageView msgBtn = dialog2.findViewById(R.id.msgBtn);

                            headerTv.setText("Chat With "+sellerNameTv.getText().toString());
                            try {
                                Picasso.get()
                                        .load(prductimg)
                                        .into(productIv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            productNameTv.setText(productName.getText().toString());
                            locationNameTv.setText(location+","+category);
                            priceTv.setText(productPrice.getText().toString());

                            msgBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String msg = msgEt.getText().toString();

                                    Call<ChatChannelModel> chatcall = ApiUtils.getUserService().sendMsg
                                            ("Token "+token,channelID,msg);
                                    chatcall.enqueue(new Callback<ChatChannelModel>() {
                                        @Override
                                        public void onResponse(Call<ChatChannelModel> call, Response<ChatChannelModel> response) {
                                            if (response.isSuccessful()){
                                                msgEt.setText("");
                                                List<ChatModel2> list = new ArrayList<>();
                                                ChatModel2 model2 = new ChatModel2(msg);
                                                list.add(model2);
                                                ChatMsgAdapter adapter = new ChatMsgAdapter(list);
                                                msgRecycler.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ChatChannelModel> call, Throwable t) {

                                        }
                                    });
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<ChatModel> call, Throwable t) {

                    }
                });

            }
        });

        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bidMsg = bidEt.getText().toString();
                if (bidMsg.equals("")){
                    bidEt.setError("Please Enter bid amount");
                }else{
                    if (loggedIn == 0){
                        startActivity(new Intent(AdDetailsActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        Call<BidModel> modelCall = ApiUtils.getUserService().postBids("Token "+token,id,bidMsg,id);
                        modelCall.enqueue(new Callback<BidModel>() {
                            @Override
                            public void onResponse(Call<BidModel> call, Response<BidModel> response) {
                                if (response.isSuccessful()){
                                    startActivity(getIntent());
                                }
                            }

                            @Override
                            public void onFailure(Call<BidModel> call, Throwable t) {
                                Log.d("kiprob",t.getMessage());
                            }
                        });
                    }
                }
            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        startActivity(new Intent(AdDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","home"));
                        finish();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(AdDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","favourite"));
                        finish();
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            dialog = new Dialog(AdDetailsActivity.this);
                            dialog.setContentView(R.layout.post_ad_popup);
                            ImageView closeIv = dialog.findViewById(R.id.closeIv);
                            TextView sellItemTv = dialog.findViewById(R.id.sellItemTv);
                            TextView rentTv = dialog.findViewById(R.id.rentTv);
                            TextView auctionTv = dialog.findViewById(R.id.auctionTv);
                            TextView exchangeTv = dialog.findViewById(R.id.exchangeTv);
                            TextView jobTv = dialog.findViewById(R.id.jobTv);
                            TextView brideTv = dialog.findViewById(R.id.brideTv);
                            TextView lookforbuyTv = dialog.findViewById(R.id.lookforbuyTv);
                            TextView lookforRentTv = dialog.findViewById(R.id.lookforRentTv);
                            Button closeBtn = dialog.findViewById(R.id.closeBtn);

                            sellItemTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this, PostAdActivity.class));
                                    finish();
                                    dialog.dismiss();
                                }
                            });

                            closeIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                }
                            });

                            closeBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        break;
                    case R.id.chat:
                        startActivity(new Intent(AdDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","chat"));
                        finish();                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(AdDetailsActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }else{
                            //pop-up will be shown
                            dialog = new Dialog(AdDetailsActivity.this);
                            dialog.setContentView(R.layout.profile_option_xml);
                            CardView close  = dialog.findViewById(R.id.closeTv);
                            TextView dashboard = dialog.findViewById(R.id.dashboardTv);
                            TextView myAds = dialog.findViewById(R.id.myAdsTv);
                            TextView favouriteTv = dialog.findViewById(R.id.favouriteTv);
                            TextView membership = dialog.findViewById(R.id.membershipTv);
                            TextView profile = dialog.findViewById(R.id.profileTv);
                            TextView logout = dialog.findViewById(R.id.logoutTv);

                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    finish();
                                    startActivity(getIntent());
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this,MyAdsActivity.class));
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    chipNavigationBar.setItemSelected(R.id.favourite, true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment()).commit();
                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this,ProfileActivity.class));
                                    finish();
                                }
                            });

                            logout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Call<UserDetailsModel> call = ApiUtils.getUserService().logoutUser();
                                    call.enqueue(new Callback<UserDetailsModel>() {
                                        @Override
                                        public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<UserDetailsModel> call, Throwable t) {

                                        }
                                    });
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", "");
                                    editor.putInt("loggedIn", 0);
                                    editor.putInt("id", 0);
                                    editor.commit();
                                    finish();
                                    startActivity(new Intent(AdDetailsActivity.this,MainActivity.class)
                                            .putExtra("fragment","home"));
                                    dialog.dismiss();
                                }
                            });

                            dialog.setCancelable(false);
                            dialog.show();

                            break;
                        }
                }

            }
        });

    }

    private void showBids(int id) {
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Call<List<BidModel>> call = ApiUtils.getUserService().getBids(id);
        call.enqueue(new Callback<List<BidModel>>() {
            @Override
            public void onResponse(Call<List<BidModel>> call, Response<List<BidModel>> response) {
                if (response.isSuccessful()){
                    List<BidModel> bids = response.body();
                    if (bids.size()>0){
                        bidAdapter = new BidsShowAdapter(bids,AdDetailsActivity.this);
                        bidRecycler.setAdapter(bidAdapter);
                        bidAdapter.notifyDataSetChanged();
                    }else{
                        bidRecycler.setVisibility(View.GONE);
                        noBidTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BidModel>> call, Throwable t) {

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
        contactLayout = findViewById(R.id.contactLayout);
        chatTV = findViewById(R.id.chatTV);

        relatedProductRecycler = findViewById(R.id.relatedProductRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedProductRecycler.setLayoutManager(layoutManager);
        bidRecycler = findViewById(R.id.bidRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bidRecycler.setLayoutManager(layoutManager1);

        sliderAdapter = new ImageSliderAdapter(this);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);

        userId = sharedPreferences.getInt("id",0);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        chipNavigationBar=findViewById(R.id.bottom_menu);
        relatedLayout=findViewById(R.id.relatedLayout);
        bidLayout=findViewById(R.id.bidLayout);
        noBidTv=findViewById(R.id.noBidTv);
        bidBtn=findViewById(R.id.bidBtn);
        bidEt=findViewById(R.id.bidEt);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void clickData(int biderId,String name,String email,String phnNo) {

        if (sellerId==userId){
            dialog = new Dialog(AdDetailsActivity.this);
            dialog.setContentView(R.layout.bider_details_popup);
            TextView nameTv = dialog.findViewById(R.id.nameTv);
            TextView emailTv = dialog.findViewById(R.id.emailTv);
            TextView phnTv = dialog.findViewById(R.id.phnTv);
            Button callBtn = dialog.findViewById(R.id.callBtn);
            Button smsBtn = dialog.findViewById(R.id.smsBtn);
            Button closeBtn = dialog.findViewById(R.id.closeBtn);

            nameTv.setText(name);
            emailTv.setText(email);
            phnTv.setText(phnNo);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+phnNo));
                    startActivity(callIntent);
                }
            });

            smsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phnNo, null)));
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setCancelable(false);
            dialog.show();

        }else{
            Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
        }
    }
}