package com.adbazarnet.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.Models.SubLocationsModel;
import com.adbazarnet.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubLocationAdapter extends RecyclerView.Adapter<SubLocationAdapter.ViewHolder> {
    private List<SubLocationsModel> location;
    private Context context;
    private HomeFragment fragment;
    private List<ProductModel> adlist = new ArrayList<>();
    private SubCatProductsAdapter subProductAdapter;

    public SubLocationAdapter(List<SubLocationsModel> location, Context context) {
        this.location = location;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategories_recycler, parent, false);
        return new ViewHolder(view);    }

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
                            adlist.clear();
                            adlist = response.body().getResults();
                            subProductAdapter = new SubCatProductsAdapter(adlist, context);
                            fragment.adsRecycler.setAdapter(subProductAdapter);
                            fragment.adCountTv.setText("("+adlist.size()+") Ads ,"+model.getName());
                            fragment.dialog.dismiss();
                        }
                        subProductAdapter.notifyDataSetChanged();
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
