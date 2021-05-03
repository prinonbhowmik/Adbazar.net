package com.adbazarnet.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serialTv,titleTV,priceTv,statusTv,
                expireTv,topAdTv,editBtn,topAdAction,deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
        }
    }
}
