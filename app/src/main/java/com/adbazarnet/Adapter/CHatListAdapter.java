package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CHatListAdapter extends RecyclerView.Adapter<CHatListAdapter.ViewHolder> {
    private List<ChatModel> models;
    private Context context;

    public CHatListAdapter(List<ChatModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatModel list = models.get(position);

        if (list.getReceiver_details().getAvatar()!=null) {
            try {
                Picasso.get()
                        .load(list.getReceiver_details().getAvatar())
                        .into(holder.receiverimg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            holder.receiverimg.setImageResource(R.drawable.ic_user);
        }
        holder.receiverNameTv.setText(list.getReceiver_details().getName());
        holder.productNameTv.setText(list.getAd_details().getAd_title());
        holder.productNameTv.setText(list.getAd_details().getAd_title());

        String time = list.getUpdated();
        time = time.split("T")[0];

        holder.timeTv.setText(time);


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView receiverimg;
        private TextView receiverNameTv,productNameTv,timeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverimg = itemView.findViewById(R.id.receiverimg);
            receiverNameTv = itemView.findViewById(R.id.receiverNameTv);
            productNameTv = itemView.findViewById(R.id.productNameTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
