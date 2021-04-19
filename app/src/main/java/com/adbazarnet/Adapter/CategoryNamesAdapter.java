package com.adbazarnet.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Activity.SubCategoriesActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryNamesAdapter extends RecyclerView.Adapter<CategoryNamesAdapter.ViewHolder> {
    private List<CategoriesModel> categoriesModels;
    private Context context;
    private SubCategoriesAdapter adapter;

    public CategoryNamesAdapter(List<CategoriesModel> categoriesModels, Context context) {
        this.categoriesModels = categoriesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesModel list = categoriesModels.get(position);

        try {
            Picasso.get()
                    .load(list.getIcon())
                    .into(holder.imageIv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.categoryName.setText(list.getName());
        holder.ad_count.setText("("+list.getAd_count()+")");
        holder.subCatRecycler.setLayoutManager(new LinearLayoutManager(context));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (holder.subCatRecycler.getVisibility()==View.GONE){
                    Call<CategorisQueryModel> call = ApiUtils.getUserService()
                            .getSubCategories(1,0,list.getAd_type(),list.getName());
                    call.enqueue(new Callback<CategorisQueryModel>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(Call<CategorisQueryModel> call, Response<CategorisQueryModel> response) {
                            if (response.isSuccessful()){
                                holder.subCatRecycler.setVisibility(View.VISIBLE);
                                holder.categoryName.setTextColor(R.color.black);
                                holder.categoryName.setText(list.getName());
                                List<SubCategoryModel> subCat = response.body().getResults().get(0).getSub_categories();
                                adapter = new SubCategoriesAdapter(subCat);
                                holder.subCatRecycler.setAdapter(adapter);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<CategorisQueryModel> call, Throwable t) {
                            Log.d("ErrorKi",t.getMessage());
                        }
                    });
                }else{
                    holder.subCatRecycler.setVisibility(View.GONE);
                    holder.categoryName.setTextColor(R.color.black);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageIv;
        private TextView categoryName,ad_count;
        private RecyclerView subCatRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.imageIV);
            categoryName = itemView.findViewById(R.id.categoryName);
            ad_count = itemView.findViewById(R.id.ad_count);
            subCatRecycler = itemView.findViewById(R.id.subCatRecycler);
        }
    }
}
