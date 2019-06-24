package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.MaterialDetail;

import java.util.ArrayList;

public class MaterialDetailAdapter extends RecyclerView.Adapter<MaterialDetailAdapter.MyViewHolder> {
    private ArrayList<MaterialDetail> detailList;
    private Context context;

    public MaterialDetailAdapter(ArrayList<MaterialDetail> detailList, Context context) {
        this.detailList = detailList;
        this.context = context;
    }

    @NonNull
    @Override
    public MaterialDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_material_detail, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialDetailAdapter.MyViewHolder myViewHolder, int i) {
            MaterialDetail model=detailList.get(i);
            myViewHolder.tvFromPer.setText(model.getDocHandoverDetail().get(i).getFromUserName());
            myViewHolder.tvToPer.setText(model.getDocHandoverDetail().get(i).getToUserName());

            if(model.getDocHandoverDetail().get(i).getStatus()==0)
            {
                myViewHolder.tvStatus.setText("Pending");
            }else if(model.getDocHandoverDetail().get(i).getStatus()==1)
            {
                myViewHolder.tvStatus.setText("Approve");
            }else if(model.getDocHandoverDetail().get(i).getStatus()==2)
            {
                myViewHolder.tvStatus.setText("Rejected");
            }
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFromPer,tvToPer,tvStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFromPer=itemView.findViewById(R.id.tvFromPer);
            tvToPer=itemView.findViewById(R.id.tvToPer);
            tvStatus=itemView.findViewById(R.id.tvStatus);
        }
    }
}
