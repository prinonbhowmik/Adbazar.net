package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Interface.SubCategoryProductsInterface;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCatProductsAdapter extends RecyclerView.Adapter<SubCatProductsAdapter.ViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public SubCatProductsAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        try {
            Picasso.get()
                    .load(model.getThumbnail())
                    .into(holder.productIv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.productNameTv.setText(model.getAd_title());
        holder.productPrice.setText(""+model.getPrice());
        holder.locationTv.setText(model.getSub_location()+","+model.getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productIv;
        private TextView productNameTv,productPrice,locationTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.productImage);
            productNameTv = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productUnitPrice);
            locationTv = itemView.findViewById(R.id.locationTv);
        }
    }
}
