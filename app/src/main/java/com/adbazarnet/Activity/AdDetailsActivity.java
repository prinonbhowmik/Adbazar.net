package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Adapter.BidsShowAdapter;
import com.adbazarnet.Adapter.CallListAdapter;
import com.adbazarnet.Adapter.ChatMsgAdapter;
import com.adbazarnet.Adapter.ImageSliderAdapter;
import com.adbazarnet.Adapter.RelatedProductAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.BidsFragment;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Interface.GetBiderIdInterface;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.AdImages;
import com.adbazarnet.Models.AdPhoneNumbers;
import com.adbazarnet.Models.BidModel;
import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.Models.ChatModel2;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.Models.RelatedAds;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GetBiderIdInterface {

    private int id, userId, sellerId;
    private SliderView imageSlider;
    private ImageSliderAdapter sliderAdapter;
    private CircleImageView sellerProfileIv, adPost;
    private TextView productName, productPrice, uploadTime, categoryTv, conditionTv,
            warrantyTv, descriptionTv, sellerNameTv, locationTv, noDataTv, membershipTV,
            favouriteTv, callNowTV, noBidTv, bidBtn, chatTV;
    private TextView txt3, txt2, vacancyTv, txtV, txtD, deadlineTv, txtR, requirmentTv, txtA, addressTv, txtO, otherTv, txtW, websiteTv, txtAtt, attchmetnTv, jobTv, jobTypeTv, txtMY, modelYearTv,
            txtM, mileageTv, txtL, landEt, shareTv;
    private RecyclerView relatedProductRecycler, bidRecycler;
    private RelatedProductAdapter relatedProductAdapter;
    private BidsShowAdapter bidAdapter;
    private SharedPreferences sharedPreferences;
    private String token, userPhone, location, prductimg, category;
    private Dialog dialog;
    private BottomNavigationView chipNavigationBar;
    private int loggedIn, receiver;
    private RelativeLayout relatedLayout, bidLayout;
    private boolean is_bid;
    private EditText bidEt;
    private FrameLayout contactLayout;
    private String downloadLink, lang, userName;
    private ImageView navIcon;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Spinner spinner;
    String[] languageArray = {"English", "বাংলা"};
    private String language;
    private List<AdPhoneNumbers> numberslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        getLocale();
        init();

        Call<AdDetails> call = ApiUtils.getUserService().getAdDetails(lang, id);
        call.enqueue(new Callback<AdDetails>() {
            @Override
            public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                if (response.isSuccessful()) {
                    List<AdImages> images = response.body().getAd_images();
                    List<RelatedAds> relatedAds = response.body().getRelated_ads();
                    numberslist = response.body().getAd_phone_numbers();
                    productName.setText(response.body().getAd_title());
                    productPrice.setText("৳ " + response.body().getPrice());
                    String date = String.valueOf(response.body().getCreated());
                    String time = String.valueOf(response.body().getCreated());
                    date = date.split("T")[0];
                    time = time.substring(0, time.indexOf("."));
                    time = time.substring(time.indexOf("T") + 1);

                    uploadTime.setText(date + "," + time);
                    categoryTv.setText("" + response.body().getCategory().getCategory_name() + ","
                            + response.body().getCategory().getName());
                    category = response.body().getCategory().getCategory_name();
                    if (response.body().getCondition() == null) {
                        conditionTv.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                    } else {
                        conditionTv.setText(response.body().getCondition());
                        conditionTv.setText(response.body().getCondition());
                        txt2.setVisibility(View.VISIBLE);
                    }
                    descriptionTv.setText(response.body().getDescription());
                    userName = response.body().getUser().getName();
                    sellerNameTv.setText(response.body().getUser().getName());
                    locationTv.setText(response.body().getLocation().getName() + ", " +
                            response.body().getLocation().getLocation_name());
                    location = response.body().getLocation().getName();
                    membershipTV.setText(response.body().getUser().getMembership_name() + " Member");
                    sellerId = response.body().getUser().getId();
                    userPhone = response.body().getUser().getPhone_number();
                    is_bid = response.body().isIs_bid();
                    receiver = response.body().getUser().getId();

                    if (is_bid == true) {
                        relatedLayout.setVisibility(View.GONE);
                        bidLayout.setVisibility(View.VISIBLE);
                        contactLayout.setVisibility(View.GONE);
                        productPrice.setVisibility(View.GONE);

                        showBids(id);
                    }

                    if (response.body().getUser().getAvatar() != null) {
                        try {
                            Picasso.get()
                                    .load(response.body().getUser().getAvatar())
                                    .into(sellerProfileIv);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        prductimg = response.body().getUser().getAvatar();
                    } else {
                        sellerProfileIv.setImageResource(R.drawable.ic_user);
                    }
                    if (response.body().getWarranty() == null || response.body().getWarranty().equals("")) {
                        warrantyTv.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                    } else {
                        warrantyTv.setVisibility(View.VISIBLE);
                        warrantyTv.setText(response.body().getWarranty());
                        txt3.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getTotal_vacancies() == 0) {
                        vacancyTv.setVisibility(View.GONE);
                        txtV.setVisibility(View.GONE);
                    } else {
                        vacancyTv.setText("" + response.body().getTotal_vacancies());
                        vacancyTv.setVisibility(View.VISIBLE);
                        txtV.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getApplication_deadline() == null || response.body().getApplication_deadline().equals("")) {
                        txtD.setVisibility(View.GONE);
                        deadlineTv.setVisibility(View.GONE);
                    } else {
                        deadlineTv.setText("" + response.body().getApplication_deadline());
                        txtD.setVisibility(View.VISIBLE);
                        deadlineTv.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getMinimum_requirement() == null || response.body().getMinimum_requirement().equals("")) {
                        txtR.setVisibility(View.GONE);
                        requirmentTv.setVisibility(View.GONE);
                    } else {
                        requirmentTv.setText("" + response.body().getMinimum_requirement());
                        txtR.setVisibility(View.VISIBLE);
                        requirmentTv.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getPlot_size() == null || response.body().getPlot_size().equals("")) {
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                    } else {
                        landEt.setText("" + response.body().getPlot_size());
                        txtL.setVisibility(View.VISIBLE);
                        landEt.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getAddress() == null || response.body().getAddress().equals("")) {
                        txtA.setVisibility(View.GONE);
                        addressTv.setVisibility(View.GONE);
                    } else {
                        addressTv.setText("" + response.body().getAddress());
                        txtA.setVisibility(View.VISIBLE);
                        addressTv.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getOther_information() == null || response.body().getOther_information().equals("")) {
                        txtO.setVisibility(View.GONE);
                        otherTv.setVisibility(View.GONE);
                    } else {
                        otherTv.setText("" + response.body().getOther_information());
                        txtO.setVisibility(View.VISIBLE);
                        otherTv.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getCompany_website() == null || response.body().getCompany_website().equals("")) {
                        txtW.setVisibility(View.GONE);
                        websiteTv.setVisibility(View.GONE);
                    } else {
                        websiteTv.setText("" + response.body().getCompany_website());
                        txtW.setVisibility(View.VISIBLE);
                        websiteTv.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getAttached_file() == null || response.body().getAttached_file().equals("")) {
                        txtAtt.setVisibility(View.GONE);
                        attchmetnTv.setVisibility(View.GONE);
                    } else {
                        txtAtt.setVisibility(View.VISIBLE);
                        attchmetnTv.setVisibility(View.VISIBLE);
                        downloadLink = response.body().getAttached_file();
                    }
                    if (response.body().getJob_type() == null || response.body().getJob_type().equals("")) {
                        jobTv.setVisibility(View.GONE);
                        jobTypeTv.setVisibility(View.GONE);
                    } else {
                        jobTv.setVisibility(View.VISIBLE);
                        jobTypeTv.setVisibility(View.VISIBLE);
                        jobTypeTv.setText(response.body().getJob_type());
                    }
                    if (response.body().getModel_and_year() == null || response.body().getModel_and_year().equals("")) {
                        txtMY.setVisibility(View.GONE);
                        modelYearTv.setVisibility(View.GONE);
                    } else {
                        modelYearTv.setVisibility(View.VISIBLE);
                        txtMY.setVisibility(View.VISIBLE);
                        modelYearTv.setText(response.body().getModel_and_year());
                    }
                    if (response.body().getMileage() == null || response.body().getMileage().equals("")) {
                        txtM.setVisibility(View.GONE);
                        mileageTv.setVisibility(View.GONE);
                    } else {
                        mileageTv.setVisibility(View.VISIBLE);
                        txtM.setVisibility(View.VISIBLE);
                        mileageTv.setText(response.body().getMileage());
                    }


                    getSliderImage(images);
                    getRelatedProduct(relatedAds);
                }
            }

            @Override
            public void onFailure(Call<AdDetails> call, Throwable t) {

            }
        });

        websiteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websiteTv.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        attchmetnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(downloadLink));
                startActivity(i);
            }
        });

        favouriteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<FavouriteAds>> call1 = ApiUtils.getUserService().addToFav("Token " + token, userId, id);
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
                dialog = new Dialog(AdDetailsActivity.this);
                dialog.setContentView(R.layout.call_list_dialog);
                TextView header = dialog.findViewById(R.id.header);
                Button closeBtn = dialog.findViewById(R.id.closeBtn);
                RecyclerView callRecycler = dialog.findViewById(R.id.callRecycler);
                callRecycler.setLayoutManager(new LinearLayoutManager(AdDetailsActivity.this));

                header.setText("Contact " + userName);
                closeBtn.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                callRecycler.setAdapter(new CallListAdapter(numberslist, AdDetailsActivity.this));

                dialog.show();
            }
        });

        chatTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog2 = new Dialog(AdDetailsActivity.this);
                Call<ChatModel> call = ApiUtils.getUserService().createChannel("Token " + token, id, receiver);
                call.enqueue(new Callback<ChatModel>() {
                    @Override
                    public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                        if (response.isSuccessful()) {
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

                            headerTv.setText("Chat With " + sellerNameTv.getText().toString());
                            try {
                                Picasso.get()
                                        .load(prductimg)
                                        .into(productIv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            productNameTv.setText(productName.getText().toString());
                            locationNameTv.setText(location + "," + category);
                            priceTv.setText(productPrice.getText().toString());

                            msgBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String msg = msgEt.getText().toString();

                                    Call<ChatChannelModel> chatcall = ApiUtils.getUserService().sendMsg
                                            ("Token " + token, channelID, msg);
                                    chatcall.enqueue(new Callback<ChatChannelModel>() {
                                        @Override
                                        public void onResponse(Call<ChatChannelModel> call, Response<ChatChannelModel> response) {
                                            if (response.isSuccessful()) {
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
                if (bidMsg.equals("")) {
                    bidEt.setError("Please Enter bid amount");
                } else {
                    if (loggedIn == 0) {
                        startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));

                    } else {
                        Call<BidModel> modelCall = ApiUtils.getUserService().postBids("Token " + token, id, bidMsg, id);
                        modelCall.enqueue(new Callback<BidModel>() {
                            @Override
                            public void onResponse(Call<BidModel> call, Response<BidModel> response) {
                                if (response.isSuccessful()) {
                                    startActivity(getIntent());
                                }
                            }

                            @Override
                            public void onFailure(Call<BidModel> call, Throwable t) {
                                Log.d("kiprob", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

        chipNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(AdDetailsActivity.this, MainActivity.class)
                                .putExtra("fragment", "home"));
                        finish();
                        break;
                    case R.id.favourite:
                        if (loggedIn == 0) {
                            startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(AdDetailsActivity.this, MainActivity.class)
                                    .putExtra("fragment", "favourite"));
                            finish();

                        }
                        break;
                    case R.id.chat:
                        if (loggedIn == 0) {
                            startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(AdDetailsActivity.this, MainActivity.class)
                                    .putExtra("fragment", "chat"));
                            finish();
                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));
finish();
                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(AdDetailsActivity.this);
                            dialog.setContentView(R.layout.profile_option_xml);
                            Button close = dialog.findViewById(R.id.closeTv);
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
                                }
                            });

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    startActivity(new Intent(AdDetailsActivity.this, MembershipActivity.class));
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this, MyAdsActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*chipNavigationBar.setSelectedItemId(R.id.favourite, true);*/
                                    startActivity(new Intent(AdDetailsActivity.this, MainActivity.class)
                                            .putExtra("fragment", "favourite"));

                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this, DashboardActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(AdDetailsActivity.this, ProfileActivity.class));
                                    dialog.dismiss();
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
                                    startActivity(new Intent(AdDetailsActivity.this, MainActivity.class)
                                            .putExtra("fragment", "home"));

                                }
                            });

                            dialog.setCancelable(false);
                            if (!isFinishing()) {
                                dialog.show();
                            }
                            break;
                        }
                }
                return false;
            }
        });

        adPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn == 0) {
                    startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));

                } else {
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
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "sell"));

                            dialog.dismiss();
                        }
                    });
                    rentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "rent"));

                            dialog.dismiss();
                        }
                    });
                    auctionTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "bid"));

                            dialog.dismiss();
                        }
                    });
                    exchangeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "exchange"));

                            dialog.dismiss();
                        }
                    });
                    jobTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "job"));

                            dialog.dismiss();
                        }
                    });
                    lookforbuyTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforbuy"));

                            dialog.dismiss();
                        }
                    });
                    lookforRentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AdDetailsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforrent"));

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
            }
        });

        shareTv.setOnClickListener(v -> {

            String message = "https://adbazar.net/listing/ad-details?adId=" + id;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(share, "www.facebook.com"));
        });

    }

    private void getLocale() {

        lang = sharedPreferences.getString("lang", "");

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(getResources().getConfiguration());
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }

    private void showBids(int id) {
        Call<List<BidModel>> call = ApiUtils.getUserService().getBids(id);
        call.enqueue(new Callback<List<BidModel>>() {
            @Override
            public void onResponse(Call<List<BidModel>> call, Response<List<BidModel>> response) {
                if (response.isSuccessful()) {
                    List<BidModel> bids = response.body();
                    if (bids.size() > 0) {
                        bidAdapter = new BidsShowAdapter(bids, AdDetailsActivity.this);
                        bidRecycler.setAdapter(bidAdapter);
                        bidAdapter.notifyDataSetChanged();
                    } else {
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
        if (relatedAds.size() > 0) {
            relatedProductAdapter = new RelatedProductAdapter(relatedAds, this);
            relatedProductRecycler.setAdapter(relatedProductAdapter);
            relatedProductAdapter.notifyDataSetChanged();
        } else {
            relatedProductRecycler.setVisibility(View.GONE);
            noDataTv.setVisibility(View.VISIBLE);
        }
    }

    private void getSliderImage(List<AdImages> images) {
        sliderAdapter = new ImageSliderAdapter(images);
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();
    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
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
        adPost = findViewById(R.id.adPost);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txtV = findViewById(R.id.txtV);
        vacancyTv = findViewById(R.id.vacancyTv);
        txtD = findViewById(R.id.txtD);
        deadlineTv = findViewById(R.id.deadlineTv);
        txtR = findViewById(R.id.txtR);
        requirmentTv = findViewById(R.id.requirmentTv);
        txtA = findViewById(R.id.txtA);
        addressTv = findViewById(R.id.addressTv);
        shareTv = findViewById(R.id.shareTv);
        txtO = findViewById(R.id.txtO);
        otherTv = findViewById(R.id.otherTv);
        txtW = findViewById(R.id.txtW);
        websiteTv = findViewById(R.id.websiteTv);
        txtAtt = findViewById(R.id.txtAtt);
        attchmetnTv = findViewById(R.id.attachmentTv);
        jobTv = findViewById(R.id.jobTv);
        jobTypeTv = findViewById(R.id.jobTypeTv);
        txtMY = findViewById(R.id.txtMY);
        modelYearTv = findViewById(R.id.modelYearTv);
        txtM = findViewById(R.id.txtM);
        mileageTv = findViewById(R.id.mileageTv);
        txtL = findViewById(R.id.txtL);
        landEt = findViewById(R.id.landTv);
        navIcon = findViewById(R.id.navIcon);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.home_navigation_drawer);

        token = sharedPreferences.getString("token", null);

        userId = sharedPreferences.getInt("id", 0);
        loggedIn = sharedPreferences.getInt("loggedIn", 0);
        lang = sharedPreferences.getString("lang", "en");

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboardFrom(AdDetailsActivity.this);
            }
        });

        relatedProductRecycler = findViewById(R.id.relatedProductRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedProductRecycler.setLayoutManager(layoutManager);
        bidRecycler = findViewById(R.id.bidRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bidRecycler.setLayoutManager(layoutManager1);

        sliderAdapter = new ImageSliderAdapter(this);

        chipNavigationBar = findViewById(R.id.bottom_menu);
        chipNavigationBar.getMenu().clear();
        chipNavigationBar.inflateMenu(R.menu.bottom_drawer_menu);
        relatedLayout = findViewById(R.id.relatedLayout);
        bidLayout = findViewById(R.id.bidLayout);
        noBidTv = findViewById(R.id.noBidTv);
        bidBtn = findViewById(R.id.bidBtn);
        bidEt = findViewById(R.id.bidEt);
        numberslist = new ArrayList<>();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(AdDetailsActivity.this, LoginActivity.class));

                break;
            case R.id.home:
                startActivity(new Intent(AdDetailsActivity.this, MainActivity.class).
                        putExtra("fragment", "home"));

                chipNavigationBar.setSelectedItemId(R.id.home);
                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                startActivity(new Intent(AdDetailsActivity.this, MainActivity.class).
                        putExtra("fragment", "home"));

                drawerLayout.closeDrawers();
                break;
            case R.id.contact:

                break;
            case R.id.language:

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.langugae_question)
                        .setPositiveButton("English", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Locale locale = new Locale("en");
                                Locale.setDefault(locale);
                                Configuration configuration = new Configuration();
                                configuration.locale = locale;
                                getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
                                SharedPreferences.Editor editor = getSharedPreferences("MyRef", MODE_PRIVATE).edit();
                                editor.putString("lang", "en");
                                editor.apply();
                                startActivity(getIntent());

                            }
                        })
                        .setNegativeButton("বাংলা", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Locale locale2 = new Locale("bn");
                                Locale.setDefault(locale2);
                                Configuration configuration2 = new Configuration();
                                configuration2.locale = locale2;
                                getBaseContext().getResources().updateConfiguration(configuration2,
                                        getBaseContext().getResources().getDisplayMetrics());
                                SharedPreferences.Editor editor2 = getSharedPreferences("MyRef",
                                        MODE_PRIVATE).edit();
                                editor2.putString("lang", "bn");
                                editor2.apply();
                                startActivity(getIntent());

                            }
                        })
                        .show();

                break;

        }

        return false;
    }

    @Override
    public void clickData(int biderId, String name, String email, String phnNo) {

        if (sellerId == userId) {
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
                    callIntent.setData(Uri.parse("tel:" + phnNo));
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

        } else {
            Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}