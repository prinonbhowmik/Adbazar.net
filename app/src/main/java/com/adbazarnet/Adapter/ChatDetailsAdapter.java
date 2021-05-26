package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.ViewHolder> {
    private List<ChatChannelModel> list;
    private Context context;
    private SharedPreferences sharedPreferences;
    private int userId;
    private String date,time;

    public ChatDetailsAdapter(List<ChatChannelModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_details_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatChannelModel model = list.get(position);
        sharedPreferences = context.getSharedPreferences("MyRef", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id",0);


        holder.msgTv.setText(model.getMessage());
        date = model.getCreated();
        time = model.getCreated();

        date = date.split("T")[0];
        time = time.substring(0, time.indexOf("."));
        time = time.substring(time.indexOf("T")+1);

        holder.timeTv.setText(time+","+date);
        if (userId==model.getUser()){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.msgCard.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.msgCard.setLayoutParams(params);
            holder.cardLayout.setBackgroundColor(Color.parseColor("#0b9876"));
            holder.msgTv.setTextColor(Color.parseColor("#FFFFFF"));
            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)holder.timeTv.getLayoutParams();
            params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.timeTv.setLayoutParams(params2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView msgCard;
        private TextView msgTv,timeTv;
        private RelativeLayout cardLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msgCard = itemView.findViewById(R.id.msgCard);
            msgTv = itemView.findViewById(R.id.msgTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }
}
