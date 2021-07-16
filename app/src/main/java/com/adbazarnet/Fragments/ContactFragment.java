package com.adbazarnet.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adbazarnet.Activity.AdDetailsActivity;
import com.adbazarnet.R;

public class ContactFragment extends Fragment {
    private ImageView fav_navIcon;
    private DrawerLayout drawerLayout;
    private EditText nameEt,emailEt,msgEt;
    private Button submitBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact, container, false);
        init(view);

        fav_navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboardFrom(view.getContext());
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();
                String email = emailEt.getText().toString();
                String msg = msgEt.getText().toString();

                if (name==null){
                    Toast.makeText(getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                }else if (email==null || !email.contains("@")){
                    Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                }else if (msg == null){
                    Toast.makeText(getContext(), "Enter your message", Toast.LENGTH_SHORT).show();
                }else{
                    Dialog dialog2 = new Dialog(getContext());
                    dialog2.setContentView(R.layout.success_popup);
                    dialog2.setCancelable(false);
                    dialog2.show();
                    TextView textView = dialog2.findViewById(R.id.textview);
                    Button okBtn = dialog2.findViewById(R.id.okBtn);
                    textView.setText("Thanks for contacting us. We will reach you soon");

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                            nameEt.setText("");
                            emailEt.setText("");
                            msgEt.setText("");
                        }
                    });
                }
            }
        });


        return view;
    }

    private void init(View view) {
        fav_navIcon = view.findViewById(R.id.fav_navIcon);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        nameEt = view.findViewById(R.id.nameEt);
        emailEt = view.findViewById(R.id.emailEt);
        msgEt = view.findViewById(R.id.msgEt);
        submitBtn = view.findViewById(R.id.submitBtn);
    }

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }
}