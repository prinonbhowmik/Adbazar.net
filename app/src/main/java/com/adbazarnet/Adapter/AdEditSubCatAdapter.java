package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.EditMyAdsActivity;
import com.adbazarnet.Activity.PostAdActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdEditSubCatAdapter extends RecyclerView.Adapter<AdEditSubCatAdapter.ViewHolder> {
    private List<SubCategoryModel> list;
    private Context context;
    private EditMyAdsActivity activity;
    private SharedPreferences sharedPreferences;


    public AdEditSubCatAdapter(List<SubCategoryModel> list, Context context) {
        this.list = list;
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
        SubCategoryModel model = list.get(position);
        holder.categoryName.setText(model.getName());
        holder.ad_count.setText("("+model.getAd_count()+")");
        sharedPreferences= context.getSharedPreferences("MyRef",MODE_PRIVATE);
        String lang = sharedPreferences.getString("lang","");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SubCategoryProductModel> call = ApiUtils.getUserService().getSubCategoriesProduct(lang,50,0,model.getSlug());
                call.enqueue(new Callback<SubCategoryProductModel>() {
                    @Override
                    public void onResponse(Call<SubCategoryProductModel> call, Response<SubCategoryProductModel> response) {
                        if (response.isSuccessful()){
                            activity.categoryTv.setText(model.getName());
                            activity.categoryId = model.getId();
                            Log.d("checkData", String.valueOf(model.getId()));
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName,ad_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            ad_count = itemView.findViewById(R.id.ad_count);
        }
    }
}
