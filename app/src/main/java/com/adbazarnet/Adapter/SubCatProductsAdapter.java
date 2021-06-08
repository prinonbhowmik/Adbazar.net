package com.adbazarnet.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.AdDetailsActivity;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubCatProductsAdapter extends RecyclerView.Adapter<SubCatProductsAdapter.ViewHolder> {
    private List<ProductModel> list;
    private List<ProductModel> filteredList;
    private HomeFragment homeFragment;
    private Context context;

    public SubCatProductsAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        try {
            Picasso.get()
                    .load(model.getThumbnail())
                    .into(holder.productIv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.productNameTv.setText(model.getAd_title());
        holder.productPrice.setText("৳ "+model.getPrice());
        holder.locationTv.setText(model.getSub_location()+","+model.getLocation());
        if (model.isIs_bid()==true){
            holder.productPrice.setVisibility(View.GONE);
            holder.bidTv.setVisibility(View.VISIBLE);
        }else if(model.isIs_job()==true){
            holder.productPrice.setText("Job");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GetID", String.valueOf(model.getId()));
                context.startActivity(new Intent(context, AdDetailsActivity.class).putExtra("id",model.getId()));
                ((Activity)context).finish();
            }
        });

    }

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filteredList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProductModel item : list) {
                    if (item.getAd_title().toLowerCase().contains(filterPattern)) {
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
            list.clear();
            list.addAll((List) results.values);
            homeFragment.adCountTv.setText("("+list.size()+") Ads");
            notifyDataSetChanged();
        }
    };


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productIv;
        private TextView productNameTv,productPrice,locationTv,bidTv;
        public ViewHolder( View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.productImage);
            productNameTv = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productUnitPrice);
            locationTv = itemView.findViewById(R.id.locationTv);
            bidTv = itemView.findViewById(R.id.bidTv);
        }
    }
}
