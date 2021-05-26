package com.adbazarnet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.adbazarnet.Adapter.ChatDetailsAdapter;
import com.adbazarnet.Adapter.ChatMsgAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.Models.ChatModel2;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailsActivity extends AppCompatActivity {

    private RecyclerView chatRecycler;
    private EditText msgEt;
    private ImageView msgBtn;
    private int channelId;
    private ChatDetailsAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String token;

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

    }
}