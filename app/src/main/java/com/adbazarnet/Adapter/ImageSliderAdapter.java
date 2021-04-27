package com.adbazarnet.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adbazarnet.Models.AdImages;
import com.adbazarnet.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.Holder> {
    private List<AdImages> image;
    private String [] imageList;
    private Context context;

    public ImageSliderAdapter(List<AdImages> image) {
        this.image = image;
    }

    public ImageSliderAdapter(String[] imageList) {
        this.imageList = imageList;
    }

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ImageSliderAdapter.Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_image_slider_design, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(ImageSliderAdapter.Holder viewHolder, int position) {
        AdImages imageList = image.get(position);
        try {
            Picasso.get().load(imageList.getImage())
                    .into(viewHolder.sliderImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError(Exception e) {
                            Log.d("kiKahini", e.getMessage());
                        }
                    });
        }catch (Exception e){
            Log.d("kiKahini", e.getMessage());
        }

    }

    @Override
    public int getCount() {
        return image.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        private ImageView sliderImage;
        public Holder(View itemView) {
            super(itemView);
            sliderImage=itemView.findViewById(R.id.sliderImage);
        }
    }
}
