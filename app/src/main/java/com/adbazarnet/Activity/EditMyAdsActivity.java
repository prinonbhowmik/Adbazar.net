package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Adapter.AdEditCategoryAdapter;
import com.adbazarnet.Adapter.AdEditLocationAdapter;
import com.adbazarnet.Adapter.PostAdCategoryAdapter;
import com.adbazarnet.Adapter.PostAdLocationAdapter;
import com.adbazarnet.Api.ApiInterface;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.ChatFragment;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.EditAdModel;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.PhoneNoModel;
import com.adbazarnet.Models.PostAdModel;
import com.adbazarnet.Models.PostImageModel;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.adbazarnet.Activity.PostAdActivity.hideKeyboard;

public class EditMyAdsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    private AdEditCategoryAdapter categoryNamesAdapter;
    private AdEditLocationAdapter locationAdapter;
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
    private boolean negotiable = false, hidePhone = false;
    private SharedPreferences sharedPreferences;
    private int loggedIn, vacancy, adId;
    private EditAdModel model;
    public String postType;
    private File file1, file2, file3, file4, file5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private String file_path,lang;
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5;
    private ProgressDialog progressDialog;
    private boolean is_sell =false,is_bid = false,is_job=false;
    private ImageView navIcon;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView chipNavigationBar;
    private CircleImageView adPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_ads);

        init();

        getLocale();

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

                Call<List<CategoriesModel>> call = apiInterface.getProductsCategories(lang);
                call.enqueue(new Callback<List<CategoriesModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                        if (response.isSuccessful()) {
                            List<CategoriesModel> list = response.body();
                            categoryNamesAdapter = new AdEditCategoryAdapter(list, EditMyAdsActivity.this);
                            categoriesRecycler.setAdapter(categoryNamesAdapter);
                            if (ad_Type.equals("job")){
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

                Call<List<LocationsModel>> call = apiInterface.getAllLocations(lang);
                call.enqueue(new Callback<List<LocationsModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationsModel>> call, Response<List<LocationsModel>> response) {
                        if (response.isSuccessful()) {
                            List<LocationsModel> list = response.body();
                            locationAdapter = new AdEditLocationAdapter(list, EditMyAdsActivity.this);
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
                        .setShowCropOverlay(false)
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
                        .setShowCropOverlay(false)
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
                        .setShowCropOverlay(false)
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
                        .setShowCropOverlay(false)
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
                        .setShowCropOverlay(false)
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

        chipNavigationBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(EditMyAdsActivity.this,MainActivity.class).
                                putExtra("fragment","home"));

                        break;
                    case R.id.favourite:
                        if (loggedIn == 0) {
                            startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(EditMyAdsActivity.this,MainActivity.class).
                                    putExtra("fragment","favourite"));

                        }
                        break;
                    case R.id.chat:
                        if (loggedIn == 0) {
                            startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(EditMyAdsActivity.this,MainActivity.class).
                                    putExtra("fragment","chat"));

                        }
                        break;
                    case R.id.account:
                        if (loggedIn == 0) {
                            startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));

                            break;
                        } else {
                            //pop-up will be shown
                            dialog = new Dialog(EditMyAdsActivity.this);
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

                                    startActivity(getIntent());
                                }
                            });

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, MembershipActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, MyAdsActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            favouriteTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this,MainActivity.class).
                                            putExtra("fragment","favourite"));
                                    dialog.dismiss();
                                }
                            });

                            dashboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, DashboardActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(EditMyAdsActivity.this, ProfileActivity.class));
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

                                    startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                                            .putExtra("fragment","home"));

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
                    startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));

                }
                else {
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

                            dialog.dismiss();
                        }
                    });
                    rentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "rent"));

                            dialog.dismiss();
                        }
                    });
                    auctionTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "bid"));

                            dialog.dismiss();
                        }
                    });
                    exchangeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "exchange"));

                            dialog.dismiss();
                        }
                    });
                    jobTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "job"));

                            dialog.dismiss();
                        }
                    });
                    lookforbuyTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforbuy"));

                            dialog.dismiss();
                        }
                    });
                    lookforRentTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditMyAdsActivity.this,
                                    PostAdActivity.class).putExtra("type", "lookforrent"));

                            dialog.dismiss();
                        }
                    });

                    closeIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            chipNavigationBar.setSelectedItemId(R.id.home);
                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            chipNavigationBar.setSelectedItemId(R.id.home);
                        }
                    });
                    dialog.show();
                }
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

                List<PhoneNoModel> phoneNumbers = new ArrayList<>();
                if (!TextUtils.isEmpty(phn1)) {
                    PhoneNoModel phn = new PhoneNoModel(phn1);
                    phoneNumbers.add(phn);
                }
                if (!TextUtils.isEmpty(phn2)) {
                    PhoneNoModel phnn = new PhoneNoModel(phn2);
                    phoneNumbers.add(phnn);
                }
                if (!TextUtils.isEmpty(phn3)) {
                    PhoneNoModel phnnn = new PhoneNoModel(phn3);
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

                Log.d("whatif",new Gson().toJson(imgArray));

                if (ad_Type.equals("electronics")) {
                    model = new EditAdModel(adTitle, condition, price, warranty, otherInfo, phoneNumbers, description,
                            locationId, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                }
                else if (ad_Type.equals("vehicle")) {
                    model = new EditAdModel(adTitle, condition, price, warranty, otherInfo, phoneNumbers, description,
                            locationId, modelYear, mileage, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                }
                else if (ad_Type.equals("property")) {
                    model = new EditAdModel(adTitle, price, otherInfo, phoneNumbers, description,
                            locationId,address,land, categoryId, imgArray, negotiable, ad_Type, hidePhone);
                }
                else if (ad_Type.equals("general")) {
                    model = new EditAdModel(adTitle, price, otherInfo, phoneNumbers, description,
                            locationId, categoryId, imgArray, negotiable,hidePhone, ad_Type);
                }
                else if (ad_Type.equals("service")) {
                    model = new EditAdModel( adTitle,hidePhone ,price,  otherInfo,  phoneNumbers,
                            description, locationId, address, service, categoryId,
                            imgArray, negotiable, ad_Type);
                }
                else  if (ad_Type.equals("job")){
                    model = new EditAdModel(adTitle,jobType,vacancy,requirment,deadline,employeer,website
                            ,otherInfo,description,locationId,address,categoryId,imgArray,ad_Type);
                }

                Call<AdDetails> call = ApiUtils.getUserService().editMyAds("Token " + token, adId ,model);
                call.enqueue(new Callback<AdDetails>() {
                    @Override
                    public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                        Log.d("whatifs",new Gson().toJson(response.body()));
                        if (response.isSuccessful()){
                            Dialog dialog2 = new Dialog(EditMyAdsActivity.this);
                            dialog2.setContentView(R.layout.success_popup);
                            dialog2.setCancelable(false);
                            dialog2.show();
                            TextView textView = dialog2.findViewById(R.id.textview);
                            Button okBtn = dialog2.findViewById(R.id.okBtn);
                            textView.setText("Ad Updated Successfully");

                            okBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog2.dismiss();
                                    Intent intent = new Intent(EditMyAdsActivity.this, MainActivity.class);
                                    intent.putExtra("fragment", "home");
                                    startActivity(intent);

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<AdDetails> call, Throwable t) {
                        Log.d("kiproblem",t.getMessage());
                    }
                });
            }
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

    private void getData() {
        Call<AdDetails> call = apiInterface.getAdDetails(lang,adId);
        call.enqueue(new Callback<AdDetails>() {
            @Override
            public void onResponse(Call<AdDetails> call, Response<AdDetails> response) {
                if (response.isSuccessful()) {
                    categoryId = response.body().getCategory().getId();
                    locationId = response.body().getLocation().getId();
                    is_sell=response.body().isIs_sell();
                    is_bid=response.body().isIs_bid();
                    is_job=response.body().isIs_job();
                    ad_Type = response.body().getAd_type();
                    Log.d("ad_type",ad_Type);
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
                            uri1 = Uri.parse(response.body().getAd_images().get(0).getImage());
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
                        priceEt.setText(""+response.body().getPrice());
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
                        }
                        else if (response.body().getAd_phone_numbers().size() == 3) {
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
                        }
                        else if (response.body().getAd_images().size() == 2) {
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
                        }
                        else if (response.body().getAd_images().size() == 3) {
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
                        }
                        else if (response.body().getAd_images().size() == 4) {
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
                        }
                        else if (response.body().getAd_images().size() == 5) {
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
                        mileageEt.setVisibility(View.GONE);
                        txtL.setVisibility(View.GONE);
                        landEt.setVisibility(View.GONE);
                        txtA.setVisibility(View.VISIBLE);
                        addressEt.setVisibility(View.VISIBLE);
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

                        categoryTv.setText(response.body().getCategory().getName());
                        locationTv.setText(response.body().getLocation().getName());
                        titleEt.setText(response.body().getAd_title());
                        jobTypeSpinner.setText(response.body().getJob_type());
                        Log.d("ad_type",response.body().getJob_type());
                        vacancyEt.setText(""+response.body().getTotal_vacancies());
                        requirmetntSpinner.setText(response.body().getMinimum_requirement());
                        deadlineEt.setText(response.body().getApplication_deadline());
                        otherInfoEt.setText(response.body().getOther_information());
                        addressEt.setText(response.body().getAddress());
                        websiteEt.setText(response.body().getCompany_website());
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
                        }
                        else if (response.body().getAd_images().size() == 2) {
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
                        }
                        else if (response.body().getAd_images().size() == 3) {
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
                        }
                        else if (response.body().getAd_images().size() == 4) {
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
                        }
                        else if (response.body().getAd_images().size() == 5) {
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
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        Log.d("TAGc",token);
        loggedIn = sharedPreferences.getInt("loggedIn", 0);

        navIcon = findViewById(R.id.navIcon);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.home_navigation_drawer);
        navigationView.getMenu().removeItem(R.id.login);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        chipNavigationBar = findViewById(R.id.bottom_menu);
        chipNavigationBar.getMenu().clear();
        chipNavigationBar.inflateMenu(R.menu.bottom_drawer_menu);
        lang = sharedPreferences.getString("lang","en");
        adPost = findViewById(R.id.adPost);

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
                hideKeyboard(EditMyAdsActivity.this);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(EditMyAdsActivity.this, LoginActivity.class));

                break;
            case R.id.home:
                startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));

                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                startActivity(new Intent(EditMyAdsActivity.this, MainActivity.class)
                        .putExtra("fragment","home"));

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}