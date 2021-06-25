package com.adbazarnet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Models.AdPhoneNumbers;
import com.adbazarnet.R;

import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {
    private List<AdPhoneNumbers> list;
    private Context context;

    public CallListAdapter(List<AdPhoneNumbers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_list_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.phnNo.setText(list.get(position).getPhone());

        holder.callBtn.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + list.get(position).getPhone()));
            context.startActivity(callIntent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView phnNo;
        private ImageView callBtn,msgBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phnNo = itemView.findViewById(R.id.phnNo);
            callBtn = itemView.findViewById(R.id.callBtn);
            msgBtn = itemView.findViewById(R.id.msgBtn);
        }
    }
}
