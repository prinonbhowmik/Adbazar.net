package com.adbazarnet.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Activity.SubCategoriesActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Interface.SubCategoryClick;
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

import static java.security.AccessController.getContext;

public class CategoryNamesAdapter extends RecyclerView.Adapter<CategoryNamesAdapter.ViewHolder> {
    private List<CategoriesModel> categoriesModels;
    private SubCategoryClick click;
    private SubCategoriesAdapter adapter2;
    private Context context;

    public CategoryNamesAdapter(List<CategoriesModel> categoriesModels, Context context) {
        this.categoriesModels = categoriesModels;
        this.context = context;
        click = (SubCategoryClick) context;
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
                if (holder.subcatLayout.getVisibility()==View.GONE){
                    holder.subcatLayout.setVisibility(View.VISIBLE);
                    holder.recycerlayout1.setBackgroundColor(Color.parseColor("#048F6E"));
                    holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.ad_count.setTextColor(Color.parseColor("#FFFFFF"));
                    Call<CategorisQueryModel> call = ApiUtils.getUserService()
                            .getSubCategories(1,0,list.getAd_type(),list.getName());
                    call.enqueue(new Callback<CategorisQueryModel>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(Call<CategorisQueryModel> call, Response<CategorisQueryModel> response) {
                            if (response.isSuccessful()){
                                List<SubCategoryModel> subCat = response.body().getResults().get(0).getSub_categories();
                                adapter2 = new SubCategoriesAdapter(subCat,context);
                                holder.subCatRecycler.setAdapter(adapter2);
                            }
                            adapter2.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<CategorisQueryModel> call, Throwable t) {
                            Log.d("ErrorKi",t.getMessage());
                        }
                    });


                }else{
                    holder.subcatLayout.setVisibility(View.GONE);
                    holder.recycerlayout1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.categoryName.setTextColor(Color.parseColor("#000000"));
                    holder.ad_count.setTextColor(Color.parseColor("#000000"));
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
        private RelativeLayout subcatLayout,recycerlayout1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.imageIV);
            categoryName = itemView.findViewById(R.id.categoryName);
            ad_count = itemView.findViewById(R.id.ad_count);
            recycerlayout1 = itemView.findViewById(R.id.recycerlayout1);
            subcatLayout = itemView.findViewById(R.id.subcatLayout);
            subCatRecycler = itemView.findViewById(R.id.subCatRecycler);
        }
    }
}
