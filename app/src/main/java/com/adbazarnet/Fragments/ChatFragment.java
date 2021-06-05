package com.adbazarnet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.adbazarnet.Activity.LoginActivity;
import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Adapter.CHatListAdapter;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {

    private RecyclerView chatRecycler;
    private SharedPreferences sharedPreferences;
    private String token;
    private ImageView navIcon;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatRecycler = view.findViewById(R.id.chatRecycler);
        navIcon = view.findViewById(R.id.chat_navIcon);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycler.setLayoutManager(layoutManager1);
        sharedPreferences = getContext().getSharedPreferences("MyRef", MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                hideKeyboardFrom(view.getContext());
            }
        });


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

    private void hideKeyboardFrom(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }





}