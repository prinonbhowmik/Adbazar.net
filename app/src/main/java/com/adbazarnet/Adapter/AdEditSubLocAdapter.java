package com.adbazarnet.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.EditMyAdsActivity;
import com.adbazarnet.Activity.PostAdActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.Models.SubLocationsModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdEditSubLocAdapter extends RecyclerView.Adapter<AdEditSubLocAdapter.ViewHolder> {
    private List<SubLocationsModel> location;
    private Context context;
    private EditMyAdsActivity activity;

    public AdEditSubLocAdapter(List<SubLocationsModel> location, Context context) {
        this.location = location;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategories_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubLocationsModel model = location.get(position);
        holder.locationName.setText(model.getName());
        holder.ad_count.setText("("+model.getAd_count()+")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SubCategoryProductModel> call = ApiUtils.getUserService().getProductByLocation(50,0,model.getSlug());
                call.enqueue(new Callback<SubCategoryProductModel>() {
                    @Override
                    public void onResponse(Call<SubCategoryProductModel> call, Response<SubCategoryProductModel> response) {
                        if (response.isSuccessful()){
                            activity.locationTv.setText(model.getName());
                            activity.locationId = model.getId();
                            activity.dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubCategoryProductModel> call, Throwable t) {
                        Log.d("ErrorKi",t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return location.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationName,ad_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.categoryName);
            ad_count = itemView.findViewById(R.id.ad_count);
        }
    }
}
