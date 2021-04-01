package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.SubCategoriesActivity;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CategoryNamesAdapter extends RecyclerView.Adapter<CategoryNamesAdapter.ViewHolder> {
    private List<CategoriesModel> categoriesModels;
    private Context context;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubCategoriesActivity.class);
                intent.putExtra("title",list.getName());
                intent.putExtra("ad_type",list.getAd_type());
                intent.putExtra("id",list.getId());
                context.startActivity(intent);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.imageIV);
            categoryName = itemView.findViewById(R.id.categoryName);
            ad_count = itemView.findViewById(R.id.ad_count);
        }
    }
}
