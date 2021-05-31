package com.adbazarnet.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adbazarnet.Activity.MainActivity;
import com.adbazarnet.Activity.MembershipActivity;
import com.adbazarnet.Activity.PostAdActivity;
import com.adbazarnet.Api.ApiUtils;
import com.adbazarnet.Models.MembershipPackage;
import com.adbazarnet.Models.TransactionModel;
import com.adbazarnet.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MembershipPackageAdapter extends RecyclerView.Adapter<MembershipPackageAdapter.ViewHolder> {
    private List<MembershipPackage> list;
    private Context context;
    private String account,msg;
    private int packageId,paymentMethodId;

    public MembershipPackageAdapter(List<MembershipPackage> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.membership_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MembershipPackage model = list.get(position);
        String token = ((MembershipActivity)context).token;

        holder.typeTv.setText(model.getName());
        holder.adLimitTv.setText("Ad Limit "+model.getAd_limit());
        holder.adDurationTv.setText("Ad duration "+model.getAd_duration()+" days");
        holder.priceTv.setText("৳ "+model.getPrice());
        holder.durationTv.setText("/"+model.getMembership_duration()+" days");
        packageId = model.getId();
        if (!model.getType().equals("free")) {
            holder.btn.setText("Upgrade");
        }
        if (model.getColor().equals("violet")){
            holder.cardview.setBackgroundColor(Color.parseColor("#6f42c1"));
            holder.typeTv.setTextColor(Color.parseColor("#6f42c1"));
            holder.priceTv.setTextColor(Color.parseColor("#6f42c1"));
            holder.durationTv.setTextColor(Color.parseColor("#6f42c1"));
            holder.btn.setTextColor(Color.parseColor("#6f42c1"));
        }else  if (model.getColor().equals("green")){
            holder.cardview.setBackgroundColor(Color.parseColor("#28a745"));
            holder.typeTv.setTextColor(Color.parseColor("#28a745"));
            holder.priceTv.setTextColor(Color.parseColor("#28a745"));
            holder.durationTv.setTextColor(Color.parseColor("#28a745"));
            holder.btn.setTextColor(Color.parseColor("#28a745"));
        }else  if (model.getColor().equals("orange")){
            holder.cardview.setBackgroundColor(Color.parseColor("#fd7e14"));
            holder.typeTv.setTextColor(Color.parseColor("#fd7e14"));
            holder.priceTv.setTextColor(Color.parseColor("#fd7e14"));
            holder.durationTv.setTextColor(Color.parseColor("#fd7e14"));
            holder.btn.setTextColor(Color.parseColor("#fd7e14"));
        }else  if (model.getColor().equals("teal")){
            holder.cardview.setBackgroundColor(Color.parseColor("#25938d"));
            holder.typeTv.setTextColor(Color.parseColor("#25938d"));
            holder.priceTv.setTextColor(Color.parseColor("#25938d"));
            holder.durationTv.setTextColor(Color.parseColor("#25938d"));
            holder.btn.setTextColor(Color.parseColor("#25938d"));
        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.upgrade_package_layout);
                TextView titleTv = dialog.findViewById(R.id.titleTv);
                ImageView closeIv = dialog.findViewById(R.id.closeIv);
                TextView infoTxt = dialog.findViewById(R.id.infoTxt);
                TextView infoTxt2 = dialog.findViewById(R.id.infoTxt2);
                Spinner methodSpinner = dialog.findViewById(R.id.methodSpinner);
                EditText PhnNoEt = dialog.findViewById(R.id.PhnNoEt);
                EditText transNoEt = dialog.findViewById(R.id.transNoEt);
                EditText contactNoEt = dialog.findViewById(R.id.contactNoEt);
                Button submitBtn = dialog.findViewById(R.id.submitBtn);
                Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
                dialog.show();
                titleTv.setText(model.getName()+" Membership");
                String[] methodArray = { "Select Payment Method", "Bkash"};
                ArrayAdapter<String> product_color = new ArrayAdapter<String>(context, R.layout.spinner_item_design, methodArray);
                product_color.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                methodSpinner.setSelection(0);
                methodSpinner.setAdapter(product_color);

                Call<List<MembershipPackage>> call = ApiUtils.getUserService().getBkash("Token "+token);
                call.enqueue(new Callback<List<MembershipPackage>>() {
                    @Override
                    public void onResponse(Call<List<MembershipPackage>> call, Response<List<MembershipPackage>> response) {
                        if (response.isSuccessful()){
                            List<MembershipPackage> method  = response.body();
                            infoTxt.setText("Account: "+method.get(0).getAccount()+"\n"+"Amount: "+
                                    model.getPrice());
                            Log.d("checkB",method.get(0).getAccount());
                            paymentMethodId = method.get(0).getId();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MembershipPackage>> call, Throwable t) {

                    }
                });

                methodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                       if (position2==1){
                           infoTxt.setVisibility(View.VISIBLE);
                           infoTxt2.setVisibility(View.VISIBLE);
                       }else {
                           infoTxt.setVisibility(View.GONE);
                           infoTxt2.setVisibility(View.GONE);
                       }
                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> parent) {

                   }
               });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                 closeIv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dialog.dismiss();
                     }
                 });

                 submitBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         String transNumber = PhnNoEt.getText().toString();
                         String transId = transNoEt.getText().toString();
                         String contactNo = contactNoEt.getText().toString();
                         if (!transNumber.equals("") && !transId.equals("") && contactNo.equals("")){
                             Toast.makeText(context, "Please fillup all documents", Toast.LENGTH_LONG).show();
                         }else {
                             TransactionModel model1 = new TransactionModel(transNumber,transId,contactNo,packageId,paymentMethodId);
                             Call<TransactionModel> call1 = ApiUtils.getUserService().membershipUpgrade("Token "+token,model1);
                             call1.enqueue(new Callback<TransactionModel>() {
                                 @Override
                                 public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                                     if (response.isSuccessful()){
                                         Toast.makeText(context, "Request complete please wait for admin approval", Toast.LENGTH_LONG).show();
                                         context.startActivity(new Intent(context, MainActivity.class).putExtra("fragment","home"));
                                         ((Activity)context).finish();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<TransactionModel> call, Throwable t) {

                                 }
                             });
                         }
                     }
                 });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView typeTv,adLimitTv,adDurationTv,priceTv,durationTv;
        private Button btn;
        private RelativeLayout cardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTv = itemView.findViewById(R.id.txt1);
            adLimitTv = itemView.findViewById(R.id.txt2);
            adDurationTv = itemView.findViewById(R.id.txt3);
            priceTv = itemView.findViewById(R.id.txt4);
            durationTv = itemView.findViewById(R.id.txt5);
            btn = itemView.findViewById(R.id.btn);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }
}
