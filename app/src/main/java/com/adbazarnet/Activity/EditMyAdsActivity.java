package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.adbazarnet.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_ads);
    }
}