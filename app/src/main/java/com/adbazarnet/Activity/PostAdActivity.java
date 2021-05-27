package com.adbazarnet.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.adbazarnet.Adapter.CategoryNamesAdapter;
import com.adbazarnet.Adapter.LocationAdapter;
import com.adbazarnet.Adapter.PostAdCategoryAdapter;
import com.adbazarnet.Adapter.PostAdLocationAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.PostAdModel;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.TestOnly;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdActivity extends AppCompatActivity {

    public static TextView locationTv, categoryTv;
    public static String ad_Type = null;
    public static TextView txt4, txt5, txt6, txtC, txtW;
    public static EditText titleEt, otherInfoEt, priceEt, phnnoEt1, phnnoEt2, phnnoEt3, descriptionEt, warrantyEt;
    private Switch negotiableBtn, hidePhnBtn;
    private Button addPhnBtn, addImgBtn;
    private ImageView img1, img2, img3, img4, img5;
    public static int categoryId,locationId;
    private Uri imageUri;
    private CardView submitBtn;
    public static Dialog dialog;
    private ApiInterface apiInterface;
    private PostAdCategoryAdapter categoryNamesAdapter;
    private PostAdLocationAdapter locationAdapter;
    public static AutoCompleteTextView conditionSpinner;
    private String[] conditionArray = {"Used", "New", "Recondition"};
    private String condition, warranty =null, phn1, phn2, phn3,token;
    private int phnCounter = 0, imgCounter = 0, imgSelect = 0;
    private Uri uri1, uri2, uri3, uri4, uri5;
    private JSONArray array = new JSONArray();
    private JSONArray imgArray = new JSONArray();
    private RequestBody requestFile1;
    private boolean negotiable = false, hidePhone = false;
    private SharedPreferences sharedPreferences;
    private ChipNavigationBar chipNavigationBar;
    private int loggedIn;

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
                        if (response.isSuccessful()) {
                            List<CategoriesModel> list = response.body();
                            categoryNamesAdapter = new PostAdCategoryAdapter(list, PostAdActivity.this);
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
                        if (response.isSuccessful()) {
                            List<LocationsModel> list = response.body();
                            locationAdapter = new PostAdLocationAdapter(list, PostAdActivity.this);
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

        addPhnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phnCounter++;
                if (phnCounter == 1) {
                    phnnoEt1.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);
                } else if (phnCounter == 2) {
                    phnnoEt2.setVisibility(View.VISIBLE);
                    txt5.setVisibility(View.VISIBLE);
                } else if (phnCounter == 3) {
                    phnnoEt3.setVisibility(View.VISIBLE);
                    txt6.setVisibility(View.VISIBLE);
                }
            }
        });

        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCounter++;
                if (imgCounter == 1) {
                    img1.setVisibility(View.VISIBLE);
                } else if (imgCounter == 2) {
                    img2.setVisibility(View.VISIBLE);
                } else if (imgCounter == 3) {
                    img3.setVisibility(View.VISIBLE);
                } else if (imgCounter == 4) {
                    img4.setVisibility(View.VISIBLE);
                } else if (imgCounter == 5) {
                    img5.setVisibility(View.VISIBLE);
                }
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(PostAdActivity.this);
                imgSelect = 1;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(PostAdActivity.this);
                imgSelect = 2;
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(PostAdActivity.this);
                imgSelect = 3;
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(PostAdActivity.this);
                imgSelect = 4;
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(PostAdActivity.this);
                imgSelect = 5;
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adTitle = titleEt.getText().toString();
                if (conditionSpinner.getVisibility() == View.VISIBLE) {
                    condition = conditionSpinner.getText().toString();
                } else {
                    condition = null;
                }
                String otherInfo = otherInfoEt.getText().toString();
                String price = priceEt.getText().toString();
                if (warrantyEt.getVisibility() == View.VISIBLE) {
                    warranty = warrantyEt.getText().toString();
                } else {
                    warranty = "No warranty";
                }
                phn1 = phnnoEt1.getText().toString();
                phn2 = phnnoEt2.getText().toString();
                phn3 = phnnoEt3.getText().toString();

                JSONObject obj1 = new JSONObject();
                try {
                    obj1.put("phone",phn1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject obj2 = new JSONObject();
                try {
                    obj2.put("phone",phn2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject obj3 = new JSONObject();
                try {
                    obj3.put("phone",phn3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                array.put(obj1);
                array.put(obj2);
                array.put(obj3);

                Log.d("phnNoList", String.valueOf(array));

                String description = descriptionEt.getText().toString();

                PostAdModel model = new PostAdModel(adTitle,condition,price,warranty,otherInfo,array,
                        description,categoryId,locationId,imgArray,negotiable,ad_Type,hidePhone);

                JSONObject object = new JSONObject();
                try {
                    object.put("ad_title",adTitle);
                    object.put("condition",condition);
                    object.put("price",price);
                    object.put("warranty",warranty);
                    object.put("other_information",otherInfo);
                    object.put("ad_phone_numbers",array);
                    object.put("description",description);
                    object.put("category",categoryId);
                    object.put("location",locationId);
                    object.put("images",imgArray);
                    object.put("negotiable",negotiable);
                    object.put("ad_type",ad_Type);
                    object.put("hide_phone",hidePhone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("kikorbo", String.valueOf(object));

                Call<JSONObject> call = apiInterface.postsellAd("Token "+token,object);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.isSuccessful()){
                            Dialog dialog2 = new Dialog(PostAdActivity.this);
                            dialog2.setContentView(R.layout.success_popup);
                            dialog2.setCancelable(false);
                            dialog2.show();
                            TextView textView = dialog2.findViewById(R.id.textview);
                            Button okBtn = dialog2.findViewById(R.id.okBtn);
                            textView.setText("Ad created Successfully");

                            okBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog2.dismiss();
                                    Intent intent = new Intent(PostAdActivity.this,MainActivity.class);
                                    intent.putExtra("fragment","home");
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        Toast.makeText(PostAdActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        startActivity(new Intent(PostAdActivity.this,MainActivity.class)
                                .putExtra("fragment","home"));
                        finish();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(PostAdActivity.this,MainActivity.class)
                                .putExtra("fragment","favourite"));
                        finish();
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(PostAdActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            dialog = new Dialog(PostAdActivity.this);
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
                                    startActivity(new Intent(PostAdActivity.this, PostAdActivity.class));
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
                        startActivity(new Intent(PostAdActivity.this,MainActivity.class)
                                .putExtra("fragment","chat"));
                        finish();
                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(PostAdActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }else{
                            //pop-up will be shown
                            dialog = new Dialog(PostAdActivity.this);
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
                                    startActivity(new Intent(PostAdActivity.this,MyAdsActivity.class));
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
                                    startActivity(new Intent(PostAdActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(PostAdActivity.this,ProfileActivity.class));
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
                                    startActivity(new Intent(PostAdActivity.this,MainActivity.class)
                                            .putExtra("fragment","home"));
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

    private void init() {
        locationTv = findViewById(R.id.locationTv);
        categoryTv = findViewById(R.id.categoryTv);
        titleEt = findViewById(R.id.titleEt);
        otherInfoEt = findViewById(R.id.otherInfoEt);
        priceEt = findViewById(R.id.priceEt);
        phnnoEt1 = findViewById(R.id.phnnoEt1);
        phnnoEt2 = findViewById(R.id.phnnoEt2);
        phnnoEt3 = findViewById(R.id.phnnoEt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txtC = findViewById(R.id.txtC);
        txtW = findViewById(R.id.txtW);
        descriptionEt = findViewById(R.id.descriptionEt);
        warrantyEt = findViewById(R.id.warrantyEt);
        addImgBtn = findViewById(R.id.addImgBtn);
        addPhnBtn = findViewById(R.id.addPhnBtn);
        submitBtn = findViewById(R.id.submitBtn);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        negotiableBtn = findViewById(R.id.negotiableBtn);
        hidePhnBtn = findViewById(R.id.hidePhnBtn);
        ArrayAdapter<String> product_color = new ArrayAdapter<String>(this, R.layout.spinner_item_design, R.id.simpleSpinner, conditionArray);
        product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        conditionSpinner.setText(product_color.getItem(0), false);
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
        chipNavigationBar = findViewById(R.id.bottom_menu);
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
    }

    public void setNegotiable(View view) {
        if (negotiableBtn.isChecked()) {
            negotiable = true;
        } else {
            negotiable = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (imgSelect == 1) {
                    uri1 = resultUri;
                    img1.setImageURI(uri1);

                } else if (imgSelect == 2) {
                    uri2 = resultUri;
                    img2.setImageURI(uri2);
                } else if (imgSelect == 3) {
                    uri3 = resultUri;
                    img3.setImageURI(uri3);
                } else if (imgSelect == 4) {
                    uri4 = resultUri;
                    img4.setImageURI(uri4);
                } else if (imgSelect == 5) {
                    uri5 = resultUri;
                    img5.setImageURI(uri5);
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(PostAdActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}