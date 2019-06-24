package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.MaterialTrackingDetailFragment;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InwardGatePassListAdapter extends RecyclerView.Adapter<InwardGatePassListAdapter.MyViewHolder> {
    private ArrayList<MaterialDetail> materialList;
    private Context context;

    public InwardGatePassListAdapter(ArrayList<MaterialDetail> materialList, Context context) {
        this.materialList = materialList;
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
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto1);

        } catch (Exception e) {

        }

        String imageUri1 = String.valueOf(model.getInwardPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri1).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto2);

        } catch (Exception e) {

        }

        if(model.getChecked())
        {
            myViewHolder.checkBox.setChecked(true);
        }else{
            myViewHolder.checkBox.setChecked(false);
        }


        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    model.setChecked(true);

                } else {

                    model.setChecked(false);

                }

            }
        });

        myViewHolder.linearLayoutTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                MainActivity activity = (MainActivity) context;
                Fragment adf = new MaterialTrackingDetailFragment();
                Bundle args = new Bundle();
                args.putString("model", json);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "MaterialTrackingListFragment").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGPNo, tvInvoice, tvDate, tvParty, tvNugs, tvTime, tvLastDept, tvLastPerson;
        public ImageView ivPhoto1, ivPhoto2, ivPhoto3;
        public CheckBox checkBox;
        public LinearLayout linearLayoutPhoto,linearLayoutTracking;

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
            linearLayoutPhoto = itemView.findViewById(R.id.linearLayoutPhoto);
            linearLayoutTracking = itemView.findViewById(R.id.linearLayoutTracking);

        }
    }
}
