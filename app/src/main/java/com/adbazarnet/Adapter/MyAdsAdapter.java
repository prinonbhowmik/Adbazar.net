package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.AdDetailsActivity;
import com.adbazarnet.Models.FavouriteAdDetails;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.ViewHolder> {
    private List<FavouriteAdDetails> list;
    private Context context;

    public MyAdsAdapter(List<FavouriteAdDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ads_recycler, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteAdDetails ads = list.get(position);

        holder.serialTv.setText(""+position);
        holder.titleTV.setText(""+ads.getAd_title());
        holder.priceTv.setText(""+ads.getPrice());
        holder.statusTv.setText(""+ads.getPublish_status());
        //holder.expireTv.setText(""+ads.getPrice());
        if (ads.isTop_ad()==true){
            holder.topAdTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_correct,0,0);
        }else{
            holder.topAdTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_close_red,0,0);
        }
        try {
            Picasso.get()
                    .load(ads.getThumbnail())
                    .into(holder.adPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AdDetailsActivity.class).putExtra("id",ads.getId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serialTv,titleTV,priceTv,statusTv,
                expireTv,topAdTv,editBtn,topAdAction,deleteBtn;
        private ImageView adPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serialTv = itemView.findViewById(R.id.serialTv);
            titleTV = itemView.findViewById(R.id.titleTV);
            priceTv = itemView.findViewById(R.id.priceTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            expireTv = itemView.findViewById(R.id.expireTv);
            topAdTv = itemView.findViewById(R.id.topAdTv);
            editBtn = itemView.findViewById(R.id.editBtn);
            topAdAction = itemView.findViewById(R.id.topAdAction);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            adPic = itemView.findViewById(R.id.adPic);
        }
    }
}
