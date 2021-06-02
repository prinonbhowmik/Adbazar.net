package com.adbazarnet.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Adapter.PostAdCategoryAdapter;
import com.adbazarnet.Adapter.PostAdLocationAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.EditAdModel;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.PhoneNoModel;
import com.adbazarnet.Models.PostAdModel;
import com.adbazarnet.Models.PostImageModel;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.adbazarnet.Activity.PostAdActivity.hideKeyboard;

public class EditMyAdsActivity extends AppCompatActivity {
    public static TextView locationTv, categoryTv;
    public static String ad_Type = null;
    public static TextView txt3, txt4, txt5, txt6, txtC, txtW, txtMY, txtM, txtL, txtA, txtS, txtF, txtJT, txtVC, txtMR, txtD, txtCE, txtWeb, txtAtt, txt87;
    public static EditText titleEt, otherInfoEt, priceEt, phnnoEt1, phnnoEt2, phnnoEt3, descriptionEt,
            warrantyEt, modelYearEt, mileageEt, addressEt, landEt, featureEt, vacancyEt, deadlineEt, employeerEt, websiteEt;
    private Switch negotiableBtn, hidePhnBtn;
    public static Button addPhnBtn, addImgBtn, browseBtn;
    private ImageView img1, img2, img3, img4, img5;
    public static int categoryId, locationId;
    private CardView submitBtn;
    public static Dialog dialog;
    private ApiInterface apiInterface;
    private PostAdCategoryAdapter categoryNamesAdapter;
    private PostAdLocationAdapter locationAdapter;
    public static AutoCompleteTextView conditionSpinner, serviceSpinner, jobTypeSpinner, requirmetntSpinner;
    private String[] conditionArray = {"used", "new", "recondition"};
    private String[] serviceArray = {"computer & laptop", "courier", "electronics and engineering", "facility management"
            , "marketing & social media", "printing", "security", "software & web development"};
    private String[] jobTypeArray = {"full time", "part time", "contract", "internship"};
    private String[] requirmentArray = {"primary school", "high school", "ssc/O level", "hsc/A level"
            , "diploma", "bachelors/honours", "PhD/Doctorate"};
    private String condition, service, warranty = null, phn1, phn2, phn3, token, jobType, requirment;
    private int phnCounter = 0, imgCounter = 0, imgSelect = 0;
    private Uri uri1, uri2, uri3, uri4, uri5;
    private List<PostImageModel> imgArray = new ArrayList<>();
    private boolean negotiable = false, hidePhone = false, is_sell;
    private SharedPreferences sharedPreferences;
    private ChipNavigationBar chipNavigationBar;
    private int loggedIn, vacancy, adId;
    private EditAdModel model;
    public String postType;
    private File file1, file2, file3, file4, file5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private String file_path;
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_ads);

        init();

        Intent i = getIntent();
        adId = i.getIntExtra("id", 0);

        getData();

        categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditMyAdsActivity.this);
                dialog.setContentView(R.layout.select_category_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPop);
                TextView allCategoryTv = dialog.findViewById(R.id.allCategoryTv);
                RecyclerView categoriesRecycler = dialog.findViewById(R.id.categoriesRecycler);
                categoriesRecycler.setLayoutManager(new LinearLayoutManager(EditMyAdsActivity.this));


                Call<List<CategoriesModel>> call = apiInterface.getProductsCategories();
                call.enqueue(new Callback<List<CategoriesModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                        if (response.isSuccessful()) {
                            List<CategoriesModel> list = response.body();
                            categoryNamesAdapter = new PostAdCategoryAdapter(list, EditMyAdsActivity.this);
                            categoriesRecycler.setAdapter(categoryNamesAdapter);
                            if (postType.equals("job")){
                                categoryNamesAdapter.getFilter().filter(postType);
                            }
                            getData();
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
                dialog = new Dialog(EditMyAdsActivity.this);
                dialog.setContentView(R.layout.select_location_xml);
                ImageView closeCatPop = dialog.findViewById(R.id.closeCatPopL);
                TextView allLocationTv = dialog.findViewById(R.id.allLocationTv);
                RecyclerView locationRecycler = dialog.findViewById(R.id.locationRecycler);
                locationRecycler.setLayoutManager(new LinearLayoutManager(EditMyAdsActivity.this));

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
                            locationAdapter = new PostAdLocationAdapter(list, EditMyAdsActivity.this);
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
                        .start(EditMyAdsActivity.this);
                imgSelect = 1;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(EditMyAdsActivity.this);
                imgSelect = 2;
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(EditMyAdsActivity.this);
                imgSelect = 3;
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(EditMyAdsActivity.this);
                imgSelect = 4;
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setFixAspectRatio(false)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(EditMyAdsActivity.this);
                imgSelect = 5;
            }
        });
        deadlineEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(EditMyAdsActivity.this);
                getDate();
            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {

                    case R.id.home:
                        startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                                .putExtra("fragment", "home"));
                        finish();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                                .putExtra("fragment", "favourite"));
                        finish();
                        break;
                    case R.id.adPost:
                        if (loggedIn == 0) {
                            startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            dialog = new Dialog(EditMyAdsActivity.this);
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
                                    startActivity(new Intent(EditMyAdsActivity.this,
                                            PostAdActivity.class).putExtra("type", "sell"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            rentTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this,
                                            PostAdActivity.class).putExtra("type", "rent"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            auctionTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this,
                                            PostAdActivity.class).putExtra("type", "bid"));
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                            exchangeTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this,
                                            PostAdActivity.class).putExtra("type", "exchange"));
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
                        startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                                .putExtra("fragment", "chat"));
                        finish();
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));
                            finish();
                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(EditMyAdsActivity.this);
                            dialog.setContentView(R.layout.profile_option_xml);
                            CardView close = dialog.findViewById(R.id.closeTv);
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

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, MyAdsActivity.class));
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
                                    startActivity(new Intent(EditMyAdsActivity.this, DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, ProfileActivity.class));
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
                                    startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                                            .putExtra("fragment", "home"));
                                }
                            });

                            dialog.setCancelable(false);
                            dialog.show();

                            break;
                        }
                }

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*progressDialog = new ProgressDialog(EditMyAdsActivity.this);
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();*/
                String adTitle = titleEt.getText().toString();
                if (conditionSpinner.getVisibility() == View.VISIBLE) {
                    condition = conditionSpinner.getText().toString();
                } else {
                    condition = null;
                }
                if (serviceSpinner.getVisibility() == View.VISIBLE) {
                    service = serviceSpinner.getText().toString();
                } else {
                    service = null;
                }
                String otherInfo = otherInfoEt.getText().toString();
                String price = priceEt.getText().toString();
                if (warrantyEt.getVisibility() == View.VISIBLE) {
                    warranty = warrantyEt.getText().toString();
                } else {
                    warranty = "No warranty";
                }
                if (jobTypeSpinner.getVisibility() == View.VISIBLE) {
                    jobType = jobTypeSpinner.getText().toString();
                } else {
                    jobType = null;
                }
                if (requirmetntSpinner.getVisibility() == View.VISIBLE) {
                    requirment = requirmetntSpinner.getText().toString();
                } else {
                    requirment = null;
                }
                phn1 = phnnoEt1.getText().toString();
                phn2 = phnnoEt2.getText().toString();
                phn3 = phnnoEt3.getText().toString();
                PhoneNoModel phn = new PhoneNoModel(phn1);
                PhoneNoModel phnn = new PhoneNoModel(phn2);
                PhoneNoModel phnnn = new PhoneNoModel(phn3);
                List<PhoneNoModel> phoneNumbers = new ArrayList<>();
                if (phn1 != null) {
                    phoneNumbers.add(phn);
                } else if (phn2 != null) {
                    phoneNumbers.add(phnn);
                } else if (phn3 != null) {
                    phoneNumbers.add(phnnn);
                }

                if (uri1!=null){
                    bitmap1 = decodeUriToBitmap(EditMyAdsActivity.this,uri1);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    PostImageModel imageModel = new PostImageModel(encoded);
                    imgArray.add(imageModel);
                }
                if(uri2!=null){
                    bitmap2 = decodeUriToBitmap(EditMyAdsActivity.this,uri2);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    PostImageModel imageModel2 = new PostImageModel(encoded);
                    imgArray.add(imageModel2);
                }
                if(uri3!=null){
                    bitmap3 = decodeUriToBitmap(EditMyAdsActivity.this,uri3);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    PostImageModel imageModel3 = new PostImageModel(encoded);
                    imgArray.add(imageModel3);
                }
                if(uri4!=null){
                    Bitmap bitmap4 = decodeUriToBitmap(EditMyAdsActivity.this,uri4);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    PostImageModel imageModel4 = new PostImageModel(encoded);
                    imgArray.add(imageModel4);
                }
                if(uri5!=null){
                    bitmap5 = decodeUriToBitmap(EditMyAdsActivity.this,uri5);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    PostImageModel imageModel5 = new PostImageModel(encoded);
                    imgArray.add(imageModel5);
                }


                String mileage = mileageEt.getText().toString();
                String modelYear = modelYearEt.getText().toString();
                String description = descriptionEt.getText().toString();
                String address = addressEt.getText().toString();
                String land = landEt.getText().toString();

                String deadline = deadlineEt.getText().toString();
                String employeer = employeerEt.getText().toString();
                String website = websiteEt.getText().toString();

                if (ad_Type.equals("electronics")) {
                    model = new EditAdModel(adTitle, condition, price, warranty, otherInfo, phoneNumbers, description,
                            locationId, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                } else if (ad_Type.equals("vehicle")) {
                    model = new EditAdModel(adTitle, condition, price, warranty, otherInfo, phoneNumbers, description,
                            locationId, modelYear, mileage, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                } else if (ad_Type.equals("property")) {
                    model = new EditAdModel(adTitle, condition, price, otherInfo, phoneNumbers, description,
                            locationId,address,land, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                } else if (ad_Type.equals("general")) {
                    model = new EditAdModel(adTitle, price, otherInfo, phoneNumbers, description,
                            locationId, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                } else if (ad_Type.equals("service")) {
                    model = new EditAdModel(adTitle, price, otherInfo, phoneNumbers, description, locationId, address,
                            service, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                }else  if (ad_Type.equals("job")){
                    model = new EditAdModel(adTitle,jobType,vacancy,requirment,deadline,employeer,website
                            ,otherInfo,description,locationId,address,categoryId,imgArray,ad_Type);
                }
                Call<AdDetails> call = ApiUtils.getUserService().editMyAds("Token " + token, adId ,model);
                call.enqueue(new Callback<AdDetails>() {
                    @Override
                    public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                        if (response.isSuccessful()){
                            //progressDialog.dismiss();

                            Dialog dialog2 = new Dialog(EditMyAdsActivity.this);
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
                                    Intent intent = new Intent(EditMyAdsActivity.this, MainActivity.class);
                                    intent.putExtra("fragment", "home");
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<AdDetails> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void getData() {
        Call<AdDetails> call = apiInterface.getAdDetails(adId);
        call.enqueue(new Callback<AdDetails>() {
            @Override
            public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                if (response.isSuccessful()) {
                    categoryId = response.body().getCategory().getId();
                    locationId = response.body().getLocation().getId();
                    ad_Type = response.body().getAd_type();
                    if (ad_Type.equals("electronics")) {
                        txtC.setVisibility(View.VISIBLE);
                        txtW.setVisibility(View.VISIBLE);
                        conditionSpinner.setVisibility(View.VISIBLE);
                        warrantyEt.setVisibility(View.VISIBLE);

                        txtMY.setVisibility(View.GONE);
                        modelYearEt.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        mileageEt.setVisibility(View.GONE);
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtA.setVisibility(View.GONE);
                        addressEt.setVisibility(View.GONE);
                        txtS.setVisibility(View.GONE);
                        serviceSpinner.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.GONE);
                        jobTypeSpinner.setVisibility(View.GONE);
                        txtVC.setVisibility(View.GONE);
                        vacancyEt.setVisibility(View.GONE);
                        txtMR.setVisibility(View.GONE);
                        requirmetntSpinner.setVisibility(View.GONE);
                        txtD.setVisibility(View.GONE);
                        deadlineEt.setVisibility(View.GONE);
                        txtCE.setVisibility(View.GONE);
                        employeerEt.setVisibility(View.GONE);
                        txtWeb.setVisibility(View.GONE);
                        websiteEt.setVisibility(View.GONE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        titleEt.setText(response.body().getAd_title());
                        conditionSpinner.setText(response.body().getCondition());
                        otherInfoEt.setText(response.body().getOther_information());
                        priceEt.setText("" + response.body().getPrice());
                        negotiableBtn.setChecked(response.body().isNegotiable());
                        warrantyEt.setText(response.body().getWarranty());
                        if (response.body().getAd_phone_numbers().size() == 1) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 2) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 3) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                            txt6.setVisibility(View.VISIBLE);
                            phnnoEt3.setVisibility(View.VISIBLE);
                            phnnoEt3.setText(response.body().getAd_phone_numbers().get(2).getPhone());
                        }
                        hidePhnBtn.setChecked(response.body().isHide_phone());
                        descriptionEt.setText(response.body().getDescription());
                        if (response.body().getAd_images().size() == 1) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 2) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 3) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 4) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 5) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img5.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(4).getImage())
                                        .into(img5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    else if (ad_Type.equals("vehicle")) {
                        txtC.setVisibility(View.VISIBLE);
                        txtW.setVisibility(View.VISIBLE);
                        conditionSpinner.setVisibility(View.VISIBLE);
                        warrantyEt.setVisibility(View.VISIBLE);
                        txtM.setVisibility(View.VISIBLE);
                        txtMY.setVisibility(View.VISIBLE);
                        modelYearEt.setVisibility(View.VISIBLE);
                        mileageEt.setVisibility(View.VISIBLE);

                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtA.setVisibility(View.GONE);
                        addressEt.setVisibility(View.GONE);
                        txtS.setVisibility(View.GONE);
                        serviceSpinner.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.GONE);
                        jobTypeSpinner.setVisibility(View.GONE);
                        txtVC.setVisibility(View.GONE);
                        vacancyEt.setVisibility(View.GONE);
                        txtMR.setVisibility(View.GONE);
                        requirmetntSpinner.setVisibility(View.GONE);
                        txtD.setVisibility(View.GONE);
                        deadlineEt.setVisibility(View.GONE);
                        txtCE.setVisibility(View.GONE);
                        employeerEt.setVisibility(View.GONE);
                        txtWeb.setVisibility(View.GONE);
                        websiteEt.setVisibility(View.GONE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        titleEt.setText(response.body().getAd_title());
                        conditionSpinner.setText(response.body().getCondition());
                        otherInfoEt.setText(response.body().getOther_information());
                        priceEt.setText("" + response.body().getPrice());
                        negotiableBtn.setChecked(response.body().isNegotiable());
                        warrantyEt.setText(response.body().getWarranty());
                        modelYearEt.setText(response.body().getModel_and_year());
                        mileageEt.setText(response.body().getMileage());
                        if (response.body().getAd_phone_numbers().size() == 1) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 2) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 3) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                            txt6.setVisibility(View.VISIBLE);
                            phnnoEt3.setVisibility(View.VISIBLE);
                            phnnoEt3.setText(response.body().getAd_phone_numbers().get(2).getPhone());
                        }
                        hidePhnBtn.setChecked(response.body().isHide_phone());
                        descriptionEt.setText(response.body().getDescription());
                        if (response.body().getAd_images().size() == 1) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 2) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 3) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 4) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 5) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img5.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(4).getImage())
                                        .into(img5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (ad_Type.equals("property")) {
                        txtA.setVisibility(View.VISIBLE);
                        txtL.setVisibility(View.VISIBLE);
                        addressEt.setVisibility(View.VISIBLE);
                        landEt.setVisibility(View.VISIBLE);
                        txtC.setVisibility(View.GONE);
                        txtW.setVisibility(View.GONE);
                        conditionSpinner.setVisibility(View.GONE);
                        warrantyEt.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        txtMY.setVisibility(View.GONE);
                        modelYearEt.setVisibility(View.GONE);
                        mileageEt.setVisibility(View.GONE);
                        txtS.setVisibility(View.GONE);
                        serviceSpinner.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.GONE);
                        jobTypeSpinner.setVisibility(View.GONE);
                        txtVC.setVisibility(View.GONE);
                        vacancyEt.setVisibility(View.GONE);
                        txtMR.setVisibility(View.GONE);
                        requirmetntSpinner.setVisibility(View.GONE);
                        txtD.setVisibility(View.GONE);
                        deadlineEt.setVisibility(View.GONE);
                        txtCE.setVisibility(View.GONE);
                        employeerEt.setVisibility(View.GONE);
                        txtWeb.setVisibility(View.GONE);
                        websiteEt.setVisibility(View.GONE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        titleEt.setText(response.body().getAd_title());
                        otherInfoEt.setText(response.body().getOther_information());
                        priceEt.setText(response.body().getPrice());
                        addressEt.setText(response.body().getAddress());
                        landEt.setText(response.body().getPlot_size());
                        negotiableBtn.setChecked(response.body().isNegotiable());
                        if (response.body().getAd_phone_numbers().size() == 1) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 2) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 3) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                            txt6.setVisibility(View.VISIBLE);
                            phnnoEt3.setVisibility(View.VISIBLE);
                            phnnoEt3.setText(response.body().getAd_phone_numbers().get(2).getPhone());
                        }
                        hidePhnBtn.setChecked(response.body().isHide_phone());
                        descriptionEt.setText(response.body().getDescription());
                        if (response.body().getAd_images().size() == 1) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 2) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 3) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 4) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 5) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img5.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(4).getImage())
                                        .into(img5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    else if (ad_Type.equals("service")) {
                        txtA.setVisibility(View.VISIBLE);
                        addressEt.setVisibility(View.VISIBLE);
                        txtS.setVisibility(View.VISIBLE);
                        serviceSpinner.setVisibility(View.VISIBLE);
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        txtMY.setVisibility(View.GONE);
                        modelYearEt.setVisibility(View.GONE);
                        mileageEt.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.GONE);
                        jobTypeSpinner.setVisibility(View.GONE);
                        txtVC.setVisibility(View.GONE);
                        vacancyEt.setVisibility(View.GONE);
                        txtMR.setVisibility(View.GONE);
                        requirmetntSpinner.setVisibility(View.GONE);
                        txtD.setVisibility(View.GONE);
                        deadlineEt.setVisibility(View.GONE);
                        txtCE.setVisibility(View.GONE);
                        employeerEt.setVisibility(View.GONE);
                        txtWeb.setVisibility(View.GONE);
                        websiteEt.setVisibility(View.GONE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        otherInfoEt.setText(response.body().getOther_information());
                        titleEt.setText(response.body().getAd_title());
                        priceEt.setText("" + response.body().getPrice());
                        addressEt.setText("" + response.body().getAddress());
                        serviceSpinner.setText("" + response.body().getService_type());
                        negotiableBtn.setChecked(response.body().isNegotiable());
                        if (response.body().getAd_phone_numbers().size() == 1) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 2) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 3) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                            txt6.setVisibility(View.VISIBLE);
                            phnnoEt3.setVisibility(View.VISIBLE);
                            phnnoEt3.setText(response.body().getAd_phone_numbers().get(2).getPhone());
                        }
                        descriptionEt.setText(response.body().getDescription());
                        if (response.body().getAd_images().size() == 1) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 2) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 3) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 4) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 5) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img5.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(4).getImage())
                                        .into(img5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    else if (ad_Type.equals("general")) {
                        txtC.setVisibility(View.GONE);
                        txtW.setVisibility(View.GONE);
                        conditionSpinner.setVisibility(View.GONE);
                        warrantyEt.setVisibility(View.GONE);

                        txtMY.setVisibility(View.GONE);
                        modelYearEt.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        mileageEt.setVisibility(View.GONE);
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtA.setVisibility(View.GONE);
                        addressEt.setVisibility(View.GONE);
                        txtS.setVisibility(View.GONE);
                        serviceSpinner.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.GONE);
                        jobTypeSpinner.setVisibility(View.GONE);
                        txtVC.setVisibility(View.GONE);
                        vacancyEt.setVisibility(View.GONE);
                        txtMR.setVisibility(View.GONE);
                        requirmetntSpinner.setVisibility(View.GONE);
                        txtD.setVisibility(View.GONE);
                        deadlineEt.setVisibility(View.GONE);
                        txtCE.setVisibility(View.GONE);
                        employeerEt.setVisibility(View.GONE);
                        txtWeb.setVisibility(View.GONE);
                        websiteEt.setVisibility(View.GONE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        titleEt.setText(response.body().getAd_title());
                        otherInfoEt.setText(response.body().getOther_information());
                        priceEt.setText("" + response.body().getPrice());
                        negotiableBtn.setChecked(response.body().isNegotiable());

                        if (response.body().getAd_phone_numbers().size() == 1) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 2) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                        } else if (response.body().getAd_phone_numbers().size() == 3) {
                            txt4.setVisibility(View.VISIBLE);
                            phnnoEt1.setVisibility(View.VISIBLE);
                            phnnoEt1.setText(response.body().getAd_phone_numbers().get(0).getPhone());
                            txt5.setVisibility(View.VISIBLE);
                            phnnoEt2.setVisibility(View.VISIBLE);
                            phnnoEt2.setText(response.body().getAd_phone_numbers().get(1).getPhone());
                            txt6.setVisibility(View.VISIBLE);
                            phnnoEt3.setVisibility(View.VISIBLE);
                            phnnoEt3.setText(response.body().getAd_phone_numbers().get(2).getPhone());
                        }
                        hidePhnBtn.setChecked(response.body().isHide_phone());
                        descriptionEt.setText(response.body().getDescription());
                        if (response.body().getAd_images().size() == 1) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 2) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 3) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 4) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (response.body().getAd_images().size() == 5) {
                            img1.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(0).getImage())
                                        .into(img1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(1).getImage())
                                        .into(img2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img3.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(2).getImage())
                                        .into(img3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img4.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(3).getImage())
                                        .into(img4);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            img5.setVisibility(View.VISIBLE);
                            try {
                                Picasso.get()
                                        .load(response.body().getAd_images().get(4).getImage())
                                        .into(img5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    else if (ad_Type.equals("job")) {
                        txtC.setVisibility(View.GONE);
                        txtW.setVisibility(View.GONE);
                        conditionSpinner.setVisibility(View.GONE);
                        warrantyEt.setVisibility(View.GONE);
                        txtMY.setVisibility(View.GONE);
                        modelYearEt.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        txtM.setVisibility(View.GONE);
                        mileageEt.setVisibility(View.GONE);
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtA.setVisibility(View.GONE);
                        addressEt.setVisibility(View.GONE);
                        txtS.setVisibility(View.GONE);
                        serviceSpinner.setVisibility(View.GONE);
                        txtF.setVisibility(View.GONE);
                        featureEt.setVisibility(View.GONE);
                        txtJT.setVisibility(View.VISIBLE);
                        jobTypeSpinner.setVisibility(View.VISIBLE);
                        txtVC.setVisibility(View.VISIBLE);
                        vacancyEt.setVisibility(View.VISIBLE);
                        txtMR.setVisibility(View.VISIBLE);
                        requirmetntSpinner.setVisibility(View.VISIBLE);
                        txtD.setVisibility(View.VISIBLE);
                        deadlineEt.setVisibility(View.VISIBLE);
                        txtCE.setVisibility(View.VISIBLE);
                        employeerEt.setVisibility(View.VISIBLE);
                        txtWeb.setVisibility(View.VISIBLE);
                        websiteEt.setVisibility(View.VISIBLE);
                        txtAtt.setVisibility(View.GONE);
                        browseBtn.setVisibility(View.GONE);
                        txt87.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AdDetails> call, Throwable t) {

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
        modelYearEt = findViewById(R.id.modelYearEt);
        mileageEt = findViewById(R.id.mileageEt);
        addressEt = findViewById(R.id.addressEt);
        landEt = findViewById(R.id.landEt);
        featureEt = findViewById(R.id.featureEt);
        vacancyEt = findViewById(R.id.vacancyEt);
        employeerEt = findViewById(R.id.employeerEt);
        websiteEt = findViewById(R.id.websiteEt);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txtC = findViewById(R.id.txtC);
        txtW = findViewById(R.id.txtW);
        txtM = findViewById(R.id.txtM);
        txtMY = findViewById(R.id.txtMY);
        txtA = findViewById(R.id.txtA);
        txtL = findViewById(R.id.txtL);
        txtS = findViewById(R.id.txtS);
        txtF = findViewById(R.id.txtF);
        txtJT = findViewById(R.id.txtJT);
        txtVC = findViewById(R.id.txtVC);
        txtMR = findViewById(R.id.txtMR);
        txtD = findViewById(R.id.txtD);
        txtCE = findViewById(R.id.txtCE);
        txtWeb = findViewById(R.id.txtWeb);
        txtAtt = findViewById(R.id.txtAtt);
        txt87 = findViewById(R.id.txt87);
        descriptionEt = findViewById(R.id.descriptionEt);
        warrantyEt = findViewById(R.id.warrantyEt);
        deadlineEt = findViewById(R.id.deadlineEt);
        addImgBtn = findViewById(R.id.addImgBtn);
        addPhnBtn = findViewById(R.id.addPhnBtn);
        submitBtn = findViewById(R.id.submitBtn);
        browseBtn = findViewById(R.id.browseBtn);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        serviceSpinner = findViewById(R.id.serviceSpinner);
        jobTypeSpinner = findViewById(R.id.jobTypeSpinner);
        requirmetntSpinner = findViewById(R.id.requirmetntSpinner);
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
        ArrayAdapter<String> services = new ArrayAdapter<String>(this, R.layout.spinner_item_design, R.id.simpleSpinner, serviceArray);
        product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        serviceSpinner.setText(services.getItem(0), false);
        serviceSpinner.setAdapter(services);
        serviceSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceSpinner.showDropDown();
            }
        });
        ArrayAdapter<String> jobType = new ArrayAdapter<String>(this, R.layout.spinner_item_design, R.id.simpleSpinner, jobTypeArray);
        product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        jobTypeSpinner.setText(jobType.getItem(0), false);
        jobTypeSpinner.setAdapter(jobType);
        jobTypeSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobTypeSpinner.showDropDown();
            }
        });
        ArrayAdapter<String> requirment = new ArrayAdapter<String>(this, R.layout.spinner_item_design, R.id.simpleSpinner, requirmentArray);
        product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        requirmetntSpinner.setText(requirment.getItem(0), false);
        requirmetntSpinner.setAdapter(requirment);
        requirmetntSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requirmetntSpinner.showDropDown();
            }
        });

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        apiInterface = ApiUtils.getUserService();
        chipNavigationBar = findViewById(R.id.bottom_menu);
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        Log.d("TAGc",token);
        loggedIn = sharedPreferences.getInt("loggedIn", 0);
    }

    public void setNegotiable(View view) {
        if (negotiableBtn.isChecked()) {
            negotiable = true;
        } else {
            negotiable = false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void getDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String currentDate = year + "-" + month + "-" + day;
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;

                try {
                    date = dateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                deadlineEt.setText(dateFormat.format(date));

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditMyAdsActivity.this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
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
                Toast.makeText(EditMyAdsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

}