package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InwardGatePassListAdapter extends RecyclerView.Adapter<InwardGatePassListAdapter.MyViewHolder> {
    private ArrayList<String> visitorList;
    private Context context;

    public InwardGatePassListAdapter(ArrayList<String> visitorList, Context context) {
        this.visitorList = visitorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_inward_gate_pass_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // myViewHolder.tvName.setText("");


    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGPNo, tvInvoice, tvDate, tvParty, tvNugs, tvTime, tvLastDept, tvLastPerson;
        public ImageView ivPhoto1, ivPhoto2, ivPhoto3;
        public CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGPNo = itemView.findViewById(R.id.tvGPNo);
            tvInvoice = itemView.findViewById(R.id.tvInvoice);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvParty = itemView.findViewById(R.id.tvParty);
            tvNugs = itemView.findViewById(R.id.tvNugs);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLastDept = itemView.findViewById(R.id.tvLastDept);
            tvLastPerson = itemView.findViewById(R.id.tvLastPerson);
            ivPhoto1 = itemView.findViewById(R.id.ivPhoto1);
            ivPhoto2 = itemView.findViewById(R.id.ivPhoto2);
            ivPhoto3 = itemView.findViewById(R.id.ivPhoto3);
            checkBox = itemView.findViewById(R.id.checkBox);

        }
    }
}
