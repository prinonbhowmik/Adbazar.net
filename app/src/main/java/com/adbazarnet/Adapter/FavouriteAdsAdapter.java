package com.adbazarnet.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.AdDetailsActivity;
import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavouriteAdsAdapter extends RecyclerView.Adapter<FavouriteAdsAdapter.ViewHolder> {

    private List<FavouriteAds> adsList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String token;

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

        holder.actionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.delete_popup);
                Button deleteAd = dialog.findViewById(R.id.deleteAd);
                Button cancelPopup = dialog.findViewById(R.id.cancelPopup);
                dialog.setCancelable(false);
                dialog.show();

                sharedPreferences = context.getSharedPreferences("MyRef", MODE_PRIVATE);
                token = sharedPreferences.getString("token",null);

                deleteAd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<FavouriteAds> call = ApiUtils.getUserService().deleteFavourites("Token "+token,ads.getId());
                        call.enqueue(new Callback<FavouriteAds>() {
                            @Override
                            public void onResponse(Call<FavouriteAds> call, Response<FavouriteAds> response) {
                                dialog.dismiss();
                                Dialog dialog2 = new Dialog(context);
                                dialog2.setContentView(R.layout.success_popup);
                                dialog2.setCancelable(false);
                                dialog2.show();
                                TextView textView = dialog2.findViewById(R.id.textview);
                                Button okBtn = dialog2.findViewById(R.id.okBtn);
                                textView.setText("Ad deleted Successfully");

                                okBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog2.dismiss();
                                        adsList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<FavouriteAds> call, Throwable t) {

                            }
                        });
                    }
                });

                cancelPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

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
