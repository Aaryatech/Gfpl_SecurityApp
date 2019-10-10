package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.VisitorMaster;
import com.google.gson.Gson;

import java.util.ArrayList;

public class VisitorNameListDialogAdapter extends RecyclerView.Adapter<VisitorNameListDialogAdapter.MyViewHolder> {
    private ArrayList<VisitorMaster> visitorList;
    private Context context;

    public VisitorNameListDialogAdapter(ArrayList<VisitorMaster> purposeList, Context context) {
        this.visitorList = purposeList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisitorNameListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_party_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorNameListDialogAdapter.MyViewHolder myViewHolder, int i) {
        final VisitorMaster model = visitorList.get(i);

        myViewHolder.tvName.setText(model.getPersonName());
        //holder.tvAddress.setText(model.getCustAddress());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Hiiiiiiiiiiiiiiiiii","----------");

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA2");
                Bundle args = new Bundle();
                args.putString("model", json);
                customerDataIntent.putExtra("model", json);
                customerDataIntent.putExtra("visitorName", model.getPersonName());
                customerDataIntent.putExtra("visitorId", model.getVisitorId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public void updateList(ArrayList<VisitorMaster> list) {
        visitorList = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
