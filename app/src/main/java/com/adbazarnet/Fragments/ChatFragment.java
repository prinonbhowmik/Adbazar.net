package com.adbazarnet.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adbazarnet.Adapter.CHatListAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {

    private RecyclerView chatRecycler;
    private SharedPreferences sharedPreferences;
    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatRecycler = view.findViewById(R.id.chatRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycler.setLayoutManager(layoutManager1);
        sharedPreferences = getContext().getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);


        Call<List<ChatModel>> call = ApiUtils.getUserService().getChatList("Token "+token);
        call.enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                if (response.isSuccessful()){
                    List<ChatModel> list = response.body();
                    CHatListAdapter adapter = new CHatListAdapter(list,getContext());
                    chatRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                Log.d("somossa",t.getMessage());
            }
        });



        return view;
    }
}