package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.adbazarnet.R;

public class PostAdActivity extends AppCompatActivity {

    private TextView locationTv,categoryTv;
    private EditText titleEt,otherInfoEt,priceEt,phnnoEt1,phnnoEt2,phnnoEt3,descriptionEt;
    private Switch negotiableBtn,hidePhnBtn;
    private Button addPhnBtn,addImgBtn;
    private ImageView img1,img2,img3,img4,img5;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        init();

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
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
    }

    public void setNegotiable(View view) {
    }
}