package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<LocationsModel> locationsModels;
    private Context context;

    public LocationAdapter(List<LocationsModel> locationsModels, Context context) {
        this.locationsModels = locationsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationsModel list = locationsModels.get(position);

        holder.locationName.setText(list.getName());
        holder.ad_count.setText("("+list.getAd_count()+")");
        holder.subLocRecycler.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return locationsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationName,ad_count;
        private RecyclerView subLocRecycler;
        private RelativeLayout subLocLayout,locationlayout1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            ad_count = itemView.findViewById(R.id.ad_count);
            locationlayout1 = itemView.findViewById(R.id.locationlayout1);
            subLocLayout = itemView.findViewById(R.id.subLocLayout);
            subLocRecycler = itemView.findViewById(R.id.subLocRecycler);
        }
    }
}
