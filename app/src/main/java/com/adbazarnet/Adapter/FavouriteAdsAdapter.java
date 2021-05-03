package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.AdDetailsActivity;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.R;

import java.util.List;

public class FavouriteAdsAdapter extends RecyclerView.Adapter<FavouriteAdsAdapter.ViewHolder> {

    private List<FavouriteAds> adsList;
    private Context context;

    public FavouriteAdsAdapter(List<FavouriteAds> adsList, Context context) {
        this.adsList = adsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteAds ads = adsList.get(position);

        holder.serialTv.setText(""+position);
        holder.titleTV.setText(""+ads.getAd_detail().getAd_title());
        holder.priceTv.setText(""+ads.getAd_detail().getPrice());

        holder.titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GetID", String.valueOf(ads.getAd_detail().getId()));
                context.startActivity(new Intent(context, AdDetailsActivity.class).putExtra("id",ads.getAd_detail().getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialTv,titleTV,priceTv,statusTv,actionTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serialTv = itemView.findViewById(R.id.serialTv);
            titleTV = itemView.findViewById(R.id.titleTV);
            priceTv = itemView.findViewById(R.id.priceTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            actionTv = itemView.findViewById(R.id.actionTv);
        }
    }
}
