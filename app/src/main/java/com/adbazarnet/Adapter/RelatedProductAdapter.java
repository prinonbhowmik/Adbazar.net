package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.RelatedAds;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.ViewHolder> {
    private List<RelatedAds> list;
    private Context context;

    public RelatedProductAdapter(List<RelatedAds> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_product_recycler_xml, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        RelatedAds ads = list.get(position);

        holder.productName.setText(ads.getAd_title());
        holder.productPrice.setText("৳ "+ads.getPrice());

        try {
            Picasso.get()
                    .load(ads.getThumbnail())
                    .into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,productPrice;
        private ImageView image;
        public ViewHolder( View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
