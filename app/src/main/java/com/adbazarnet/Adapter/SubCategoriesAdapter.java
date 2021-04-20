package com.adbazarnet.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.R;

import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {
    private List<SubCategoryModel> list;
    private SubCategoryProductsInterface subCategoryProductsInterface;

    public SubCategoriesAdapter(List<SubCategoryModel> list, SubCategoryProductsInterface subCategoryProductsInterface) {
        this.list = list;
        this.subCategoryProductsInterface = subCategoryProductsInterface;
    }

    public SubCategoriesAdapter(List<SubCategoryModel> list) {
        this.list = list;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subCategoryProductsInterface!=null){
                    subCategoryProductsInterface.onClick(model.getSlug());
                }else{
                    Log.d("InterfaceNull","Yes");
                }
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
