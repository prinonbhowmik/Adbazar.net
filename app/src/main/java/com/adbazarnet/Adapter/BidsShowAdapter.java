package com.adbazarnet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Interface.GetBiderIdInterface;
import com.adbazarnet.Models.BidModel;
import com.adbazarnet.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BidsShowAdapter extends RecyclerView.Adapter<BidsShowAdapter.ViewHolder> {
    private List<BidModel> list;
    private GetBiderIdInterface biderIdInterfaceer;

    public BidsShowAdapter(List<BidModel> list, GetBiderIdInterface biderIdInterfaceer) {
        this.list = list;
        this.biderIdInterfaceer = biderIdInterfaceer;
    }

    @NonNull
    @Override
    public BidsShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_recycler, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull BidsShowAdapter.ViewHolder holder, int position) {

        BidModel bids = list.get(position);

        holder.biderName.setText(""+bids.getUser().getName());
        holder.bidTimeTv.setText(""+bids.getCreated());
        holder.bidMsgTv.setText(""+bids.getAmount());

        if (bids.getUser().getAvatar()!=null){
            try {
                Picasso.get()
                        .load(bids.getUser().getAvatar())
                        .into(holder.biderImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biderIdInterfaceer!=null){
                    biderIdInterfaceer.clickData(bids.getUser().getId(),bids.getUser().getName(),
                            bids.getUser().getEmail(), bids.getUser().getPhone_number());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView biderImg;
        private TextView biderName,bidTimeTv,bidMsgTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            biderImg = itemView.findViewById(R.id.biderImg);
            biderName = itemView.findViewById(R.id.biderName);
            bidTimeTv = itemView.findViewById(R.id.bidTimeTv);
            bidMsgTv = itemView.findViewById(R.id.bidMsgTv);
        }
    }
}
