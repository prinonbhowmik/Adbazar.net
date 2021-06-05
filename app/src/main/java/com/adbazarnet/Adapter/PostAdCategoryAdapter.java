package com.adbazarnet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.PostAdActivity;
import com.adbazarnet.Interface.SubCategoryClick;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdCategoryAdapter extends RecyclerView.Adapter<PostAdCategoryAdapter.ViewHolder>{
    private List<CategoriesModel> categoriesModels;
    private List<CategoriesModel> categoriesModelFiltered;
    private SubCategoryClick click;
    private PostAdSubCatAdapter adapter2;
    private PostAdActivity activity;
    private Context context;

    public PostAdCategoryAdapter(List<CategoriesModel> categoriesModels, Context context) {
        this.categoriesModels = categoriesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdCategoryAdapter.ViewHolder holder, int position) {
        CategoriesModel list = categoriesModels.get(position);

        String getType = ((PostAdActivity)context).postType;

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
                activity.ad_Type = list.getAd_type();
                Log.d("Callio",activity.ad_Type+","+getType);
                if(getType.equals("sell") || getType.equals("rent") || getType.equals("exchange")
                        || getType.equals("lookforbuy")|| getType.equals("lookforrent")){
                    if (activity.ad_Type.equals("electronics")){
                        activity.txtC.setVisibility(View.VISIBLE);
                        activity.txtW.setVisibility(View.VISIBLE);
                        activity.conditionSpinner.setVisibility(View.VISIBLE);
                        activity.warrantyEt.setVisibility(View.VISIBLE);
                        activity.txtMY.setVisibility(View.GONE);
                        activity.modelYearEt.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.mileageEt.setVisibility(View.GONE);
                        activity.txtL.setVisibility(View.GONE);
                        activity.landEt.setVisibility(View.GONE);
                        activity.txtA.setVisibility(View.GONE);
                        activity.addressEt.setVisibility(View.GONE);
                        activity.txtS.setVisibility(View.GONE);
                        activity.serviceSpinner.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.GONE);
                        activity.jobTypeSpinner.setVisibility(View.GONE);
                        activity.txtVC.setVisibility(View.GONE);
                        activity.vacancyEt.setVisibility(View.GONE);
                        activity.txtMR.setVisibility(View.GONE);
                        activity.requirmetntSpinner.setVisibility(View.GONE);
                        activity.txtD.setVisibility(View.GONE);
                        activity.deadlineEt.setVisibility(View.GONE);
                        activity.txtCE.setVisibility(View.GONE);
                        activity.employeerEt.setVisibility(View.GONE);
                        activity.txtWeb.setVisibility(View.GONE);
                        activity.websiteEt.setVisibility(View.GONE);
                        activity.txtAtt.setVisibility(View.GONE);
                        activity.browseBtn.setVisibility(View.GONE);
                        activity.txt87.setVisibility(View.GONE);
                    }
                    else if(activity.ad_Type.equals("vehicle")){
                        activity.txtC.setVisibility(View.VISIBLE);
                        activity.txtW.setVisibility(View.VISIBLE);
                        activity.conditionSpinner.setVisibility(View.VISIBLE);
                        activity.warrantyEt.setVisibility(View.VISIBLE);
                        activity.txtM.setVisibility(View.VISIBLE);
                        activity.txtMY.setVisibility(View.VISIBLE);
                        activity.modelYearEt.setVisibility(View.VISIBLE);
                        activity.mileageEt.setVisibility(View.VISIBLE);
                        activity.txtL.setVisibility(View.GONE);
                        activity.landEt.setVisibility(View.GONE);
                        activity.txtA.setVisibility(View.GONE);
                        activity.addressEt.setVisibility(View.GONE);
                        activity.txtS.setVisibility(View.GONE);
                        activity.serviceSpinner.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.GONE);
                        activity.jobTypeSpinner.setVisibility(View.GONE);
                        activity.txtVC.setVisibility(View.GONE);
                        activity.vacancyEt.setVisibility(View.GONE);
                        activity.txtMR.setVisibility(View.GONE);
                        activity.requirmetntSpinner.setVisibility(View.GONE);
                        activity.txtD.setVisibility(View.GONE);
                        activity.deadlineEt.setVisibility(View.GONE);
                        activity.txtCE.setVisibility(View.GONE);
                        activity.employeerEt.setVisibility(View.GONE);
                        activity.txtWeb.setVisibility(View.GONE);
                        activity.websiteEt.setVisibility(View.GONE);
                        activity.txtAtt.setVisibility(View.GONE);
                        activity.browseBtn.setVisibility(View.GONE);
                        activity.txt87.setVisibility(View.GONE);
                    }
                    else if(activity.ad_Type.equals("property")){
                        activity.txtA.setVisibility(View.VISIBLE);
                        activity.txtL.setVisibility(View.VISIBLE);
                        activity.addressEt.setVisibility(View.VISIBLE);
                        activity.landEt.setVisibility(View.VISIBLE);
                        activity.txtC.setVisibility(View.GONE);
                        activity.txtW.setVisibility(View.GONE);
                        activity.conditionSpinner.setVisibility(View.GONE);
                        activity.warrantyEt.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.txtMY.setVisibility(View.GONE);
                        activity.modelYearEt.setVisibility(View.GONE);
                        activity.mileageEt.setVisibility(View.GONE);
                        activity.txtS.setVisibility(View.GONE);
                        activity.serviceSpinner.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.GONE);
                        activity.jobTypeSpinner.setVisibility(View.GONE);
                        activity.txtVC.setVisibility(View.GONE);
                        activity.vacancyEt.setVisibility(View.GONE);
                        activity.txtMR.setVisibility(View.GONE);
                        activity.requirmetntSpinner.setVisibility(View.GONE);
                        activity.txtD.setVisibility(View.GONE);
                        activity.deadlineEt.setVisibility(View.GONE);
                        activity.txtCE.setVisibility(View.GONE);
                        activity.employeerEt.setVisibility(View.GONE);
                        activity.txtWeb.setVisibility(View.GONE);
                        activity.websiteEt.setVisibility(View.GONE);
                        activity.txtAtt.setVisibility(View.GONE);
                        activity.browseBtn.setVisibility(View.GONE);
                        activity.txt87.setVisibility(View.GONE);
                    }
                    else if(activity.ad_Type.equals("service")){
                        activity.txtA.setVisibility(View.VISIBLE);
                        activity.addressEt.setVisibility(View.VISIBLE);
                        activity.txtS.setVisibility(View.VISIBLE);
                        activity.serviceSpinner.setVisibility(View.VISIBLE);
                        activity.txtL.setVisibility(View.GONE);
                        activity.landEt.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.txtMY.setVisibility(View.GONE);
                        activity.modelYearEt.setVisibility(View.GONE);
                        activity.mileageEt.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.GONE);
                        activity.jobTypeSpinner.setVisibility(View.GONE);
                        activity.txtVC.setVisibility(View.GONE);
                        activity.vacancyEt.setVisibility(View.GONE);
                        activity.txtMR.setVisibility(View.GONE);
                        activity.requirmetntSpinner.setVisibility(View.GONE);
                        activity.txtD.setVisibility(View.GONE);
                        activity.deadlineEt.setVisibility(View.GONE);
                        activity.txtCE.setVisibility(View.GONE);
                        activity.employeerEt.setVisibility(View.GONE);
                        activity.txtWeb.setVisibility(View.GONE);
                        activity.websiteEt.setVisibility(View.GONE);
                        activity.txtAtt.setVisibility(View.GONE);
                        activity.browseBtn.setVisibility(View.GONE);
                        activity.txt87.setVisibility(View.GONE);
                    }
                    else if (activity.ad_Type.equals("general")){
                        activity.txtC.setVisibility(View.GONE);
                        activity.txtW.setVisibility(View.GONE);
                        activity.conditionSpinner.setVisibility(View.GONE);
                        activity.warrantyEt.setVisibility(View.GONE);
                        activity.txtMY.setVisibility(View.GONE);
                        activity.modelYearEt.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.mileageEt.setVisibility(View.GONE);
                        activity.txtL.setVisibility(View.GONE);
                        activity.landEt.setVisibility(View.GONE);
                        activity.txtA.setVisibility(View.GONE);
                        activity.addressEt.setVisibility(View.GONE);
                        activity.txtS.setVisibility(View.GONE);
                        activity.serviceSpinner.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.GONE);
                        activity.jobTypeSpinner.setVisibility(View.GONE);
                        activity.txtVC.setVisibility(View.GONE);
                        activity.vacancyEt.setVisibility(View.GONE);
                        activity.txtMR.setVisibility(View.GONE);
                        activity.requirmetntSpinner.setVisibility(View.GONE);
                        activity.txtD.setVisibility(View.GONE);
                        activity.deadlineEt.setVisibility(View.GONE);
                        activity.txtCE.setVisibility(View.GONE);
                        activity.employeerEt.setVisibility(View.GONE);
                        activity.txtWeb.setVisibility(View.GONE);
                        activity.websiteEt.setVisibility(View.GONE);
                        activity.txtAtt.setVisibility(View.GONE);
                        activity.browseBtn.setVisibility(View.GONE);
                        activity.txt87.setVisibility(View.GONE);
                    }
                    else if (activity.ad_Type.equals("job")){
                        activity.txtC.setVisibility(View.GONE);
                        activity.txtW.setVisibility(View.GONE);
                        activity.conditionSpinner.setVisibility(View.GONE);
                        activity.warrantyEt.setVisibility(View.GONE);
                        activity.txtMY.setVisibility(View.GONE);
                        activity.modelYearEt.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.txtM.setVisibility(View.GONE);
                        activity.mileageEt.setVisibility(View.GONE);
                        activity.txtL.setVisibility(View.GONE);
                        activity.landEt.setVisibility(View.GONE);
                        activity.txtA.setVisibility(View.VISIBLE);
                        activity.addressEt.setVisibility(View.VISIBLE);
                        activity.txtS.setVisibility(View.GONE);
                        activity.serviceSpinner.setVisibility(View.GONE);
                        activity.txtF.setVisibility(View.GONE);
                        activity.featureEt.setVisibility(View.GONE);
                        activity.txtJT.setVisibility(View.VISIBLE);
                        activity.jobTypeSpinner.setVisibility(View.VISIBLE);
                        activity.txtVC.setVisibility(View.VISIBLE);
                        activity.vacancyEt.setVisibility(View.VISIBLE);
                        activity.txtMR.setVisibility(View.VISIBLE);
                        activity.requirmetntSpinner.setVisibility(View.VISIBLE);
                        activity.txtD.setVisibility(View.VISIBLE);
                        activity.deadlineEt.setVisibility(View.VISIBLE);
                        activity.txtCE.setVisibility(View.VISIBLE);
                        activity.employeerEt.setVisibility(View.VISIBLE);
                        activity.txtWeb.setVisibility(View.VISIBLE);
                        activity.websiteEt.setVisibility(View.VISIBLE);
                        activity.txtAtt.setVisibility(View.VISIBLE);
                        activity.browseBtn.setVisibility(View.VISIBLE);
                        activity.txt87.setVisibility(View.VISIBLE);
                    }
                }

                if (holder.subcatLayout.getVisibility()==View.GONE){
                    holder.subcatLayout.setVisibility(View.VISIBLE);
                    holder.recycerlayout1.setBackgroundColor(Color.parseColor("#048F6E"));
                    holder.categoryName.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.ad_count.setTextColor(Color.parseColor("#FFFFFF"));
                    Log.d("CheckCalling",list.getAd_type()+"'"+list.getName());

                    List<SubCategoryModel> subCat = list.getSub_categories();
                    adapter2 = new PostAdSubCatAdapter(subCat,context);
                    holder.subCatRecycler.setAdapter(adapter2);



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

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoriesModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.clear();
                filteredList.addAll(categoriesModelFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CategoriesModel item : categoriesModels) {
                    if (item.getAd_type().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoriesModels.clear();
            categoriesModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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
