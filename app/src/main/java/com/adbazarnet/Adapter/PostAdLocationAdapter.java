package com.adbazarnet.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.PostAdActivity;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.SubLocationsModel;
import com.adbazarnet.R;

import java.util.List;

public class PostAdLocationAdapter extends RecyclerView.Adapter<PostAdLocationAdapter.ViewHolder> {
    private List<LocationsModel> locationsModels;
    private Context context;
    private PostAdSubLocationAdapter adapter;
    private PostAdActivity activity;

    public PostAdLocationAdapter(List<LocationsModel> locationsModels, Context context) {
        this.locationsModels = locationsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recycler, parent, false);
        return new ViewHolder(view);
}

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        LocationsModel list = locationsModels.get(position);

        holder.locationName.setText(list.getName());
        holder.ad_count.setText("(" + list.getAd_count() + ")");
        holder.subLocRecycler.setLayoutManager(new LinearLayoutManager(context));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.subLocLayout.getVisibility() == View.GONE) {
                    holder.subLocLayout.setVisibility(View.VISIBLE);
                    holder.imageIV.setBackgroundResource(R.drawable.ic_baseline_location_on_24);
                    holder.locationlayout1.setBackgroundColor(Color.parseColor("#048F6E"));
                    holder.locationName.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.ad_count.setTextColor(Color.parseColor("#FFFFFF"));
                    List<SubLocationsModel> location = list.getSub_locations();
                    adapter = new PostAdSubLocationAdapter(location, context);
                    holder.subLocRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    holder.subLocLayout.setVisibility(View.GONE);
                    holder.locationName.setTextColor(Color.parseColor("#000000"));
                    holder.ad_count.setTextColor(Color.parseColor("#000000"));
                    holder.locationlayout1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.imageIV.setBackgroundResource(R.drawable.location_theme_color);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return locationsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationName, ad_count;
        private RecyclerView subLocRecycler;
        private ImageView imageIV;
        private RelativeLayout subLocLayout, locationlayout1;

        public ViewHolder( View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            ad_count = itemView.findViewById(R.id.locad_count);
            locationlayout1 = itemView.findViewById(R.id.locationlayout1);
            subLocLayout = itemView.findViewById(R.id.subLocLayout);
            imageIV = itemView.findViewById(R.id.imageIV);
            subLocRecycler = itemView.findViewById(R.id.subLocRecycler);
        }
    }
}
