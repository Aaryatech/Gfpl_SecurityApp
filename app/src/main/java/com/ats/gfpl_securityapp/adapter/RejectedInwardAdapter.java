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
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RejectedInwardAdapter extends RecyclerView.Adapter<RejectedInwardAdapter.MyViewHolder> {
    private ArrayList<MaterialDetail> materialList;
    private Context context;

    public RejectedInwardAdapter(ArrayList<MaterialDetail> materialList, Context context) {
        this.materialList = materialList;
        this.context = context;
    }

    @NonNull
    @Override
    public RejectedInwardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_rejected_gate_pass, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectedInwardAdapter.MyViewHolder myViewHolder, int i) {
        final MaterialDetail model=materialList.get(i);
        myViewHolder.tvGPNo.setText(""+model.getExVar1());
        myViewHolder.tvInvoice.setText(""+model.getInvoiceNumber());
        myViewHolder.tvDate.setText(""+model.getInwardDate());
        myViewHolder.tvParty.setText(""+model.getPartyName());
        myViewHolder.tvNugs.setText(""+model.getNoOfNugs());
        myViewHolder.tvTime.setText(""+model.getInTime());
        myViewHolder.tvLastDept.setText(model.getToDeptName());
        myViewHolder.tvLastPerson.setText(model.getToEmpName());

        String imageUri = String.valueOf(model.getPersonPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+ " " +imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto1);

        } catch (Exception e) {

        }

        String imageUri1 = String.valueOf(model.getInwardPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+ " " +imageUri1).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto2);

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return materialList.size();
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
