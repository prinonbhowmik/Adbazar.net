package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adbazarnet.Adapter.ChatDetailsAdapter;
import com.adbazarnet.Adapter.ChatMsgAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.FavouriteFragment;
import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.Models.ChatModel2;
import com.adbazarnet.Models.UserDetailsModel;
import com.adbazarnet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailsActivity extends AppCompatActivity {

    private RecyclerView chatRecycler;
    private EditText msgEt;
    private ImageView msgBtn;
    private int channelId,loggedIn;
    private ChatDetailsAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String token;
    private ChipNavigationBar chipNavigationBar;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        init();

        Intent i = getIntent();
        channelId = i.getIntExtra("channel",0);

       getAllChat();

       msgBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String msg = msgEt.getText().toString();
               if (msg.equals("")){
                   msgEt.setError("Please enter text first");
               }else{
                   Call<ChatChannelModel> chatcall = ApiUtils.getUserService().sendMsg
                           ("Token "+token,channelId,msg);
                   chatcall.enqueue(new Callback<ChatChannelModel>() {
                       @Override
                       public void onResponse(Call<ChatChannelModel> call, Response<ChatChannelModel> response) {
                           if (response.isSuccessful()){
                               msgEt.setText("");
                              getAllChat();
                           }
                       }

                       @Override
                       public void onFailure(Call<ChatChannelModel> call, Throwable t) {

                       }
                   });
               }
           }
       });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){

                    case R.id.home:
                        startActivity(new Intent(ChatDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","home"));
                        finish();
                        break;
                    case R.id.favourite:
                        startActivity(new Intent(ChatDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","favourite"));
                        finish();
                        break;
                    case R.id.adPost:
                        if (loggedIn==0){
                            startActivity(new Intent(ChatDetailsActivity.this, LoginActivity.class));
                            finish();
                        }
                        else {
                            dialog = new Dialog(ChatDetailsActivity.this);
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
                                    startActivity(new Intent(ChatDetailsActivity.this, PostAdActivity.class));
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
                        startActivity(new Intent(ChatDetailsActivity.this,MainActivity.class)
                                .putExtra("fragment","chat"));
                        finish();                        break;
                    case R.id.account:
                        if (loggedIn == 0 ){
                            startActivity(new Intent(ChatDetailsActivity.this,LoginActivity.class));
                            finish();
                            break;
                        }else{
                            //pop-up will be shown
                            dialog = new Dialog(ChatDetailsActivity.this);
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

                            membership.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ChatDetailsActivity.this,MembershipActivity.class));
                                    finish();
                                }
                            });

                            myAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ChatDetailsActivity.this,MyAdsActivity.class));
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
                                    startActivity(new Intent(ChatDetailsActivity.this,DashboardActivity.class));
                                    finish();
                                }
                            });

                            profile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ChatDetailsActivity.this,ProfileActivity.class));
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
                                    startActivity(new Intent(ChatDetailsActivity.this,MainActivity.class)
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

    private void getAllChat() {
        Call<List<ChatChannelModel>> call = ApiUtils.getUserService().getChatDetails("Token "+token,channelId);
        call.enqueue(new Callback<List<ChatChannelModel>>() {
            @Override
            public void onResponse(Call<List<ChatChannelModel>> call, Response<List<ChatChannelModel>> response) {
                if (response.isSuccessful()){
                    List<ChatChannelModel> list = response.body();
                    adapter = new ChatDetailsAdapter(list,ChatDetailsActivity.this);
                    chatRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ChatChannelModel>> call, Throwable t) {

            }
        });
    }

    private void init() {
        msgEt = findViewById(R.id.msgEt);
        msgBtn = findViewById(R.id.msgBtn);
        chatRecycler = findViewById(R.id.chatRecycler);
        chatRecycler.setLayoutManager(new LinearLayoutManager(ChatDetailsActivity.this));
        sharedPreferences = getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        loggedIn = sharedPreferences.getInt("loggedIn",0);
        chipNavigationBar = findViewById(R.id.bottom_menu);
    }
}