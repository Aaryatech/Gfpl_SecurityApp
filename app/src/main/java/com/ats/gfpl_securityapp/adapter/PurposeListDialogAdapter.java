package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ats.gfpl_securityapp.model.PurposeList;

import java.util.ArrayList;

public class PurposeListDialogAdapter extends RecyclerView.Adapter<PurposeListDialogAdapter.MyViewHolder> {

    private ArrayList<PurposeList> purposeList;
    private Context context;

    public PurposeListDialogAdapter(ArrayList<PurposeList> purposeList, Context context) {
        this.purposeList = purposeList;
        this.context = context;
    }

    @NonNull
    @Override
    public PurposeListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_party_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurposeListDialogAdapter.MyViewHolder myViewHolder, int i) {
        final PurposeList model = purposeList.get(i);

        myViewHolder.tvName.setText(model.getPurposeHeading());
        //holder.tvAddress.setText(model.getCustAddress());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Hiiiiiiiiiiiiiiiiii","----------");
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA1");
                customerDataIntent.putExtra("purposeName", model.getPurposeHeading());
                customerDataIntent.putExtra("purposeId", model.getPurposeId());
                customerDataIntent.putExtra("empName", model.getAssignEmpName());
                customerDataIntent.putExtra("empId", model.getEmpId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return purposeList.size();
    }

    public void updateList(ArrayList<PurposeList> list) {
        purposeList = list;
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
