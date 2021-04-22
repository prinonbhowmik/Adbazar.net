package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.SubCategoryModel;
import com.adbazarnet.Models.SubLocationsModel;
import com.adbazarnet.R;

import java.util.List;

public class SubLocationAdapter extends RecyclerView.Adapter<SubLocationAdapter.ViewHolder> {
    private List<SubLocationsModel> location;
    private Context context;

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
