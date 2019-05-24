package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeGatePassListAdapter  extends RecyclerView.Adapter<EmployeeGatePassListAdapter.MyViewHolder> {
    private ArrayList<String> visitorList;
    private Context context;

    public EmployeeGatePassListAdapter(ArrayList<String> visitorList, Context context) {
        this.visitorList = visitorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_employee_gate_pass_list, viewGroup, false);

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
        public TextView tvEmp, tvEmpMobile, tvSupMobile,tvSupName, tvOutTime, tvInTime, tvGatePass,tvOut,tvIn,tvHrs,tvTotalHrs,tvGPNo;
        public CircleImageView ivPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGPNo = itemView.findViewById(R.id.tvGPNo);
            tvEmp = itemView.findViewById(R.id.tvEmp);
            tvEmpMobile = itemView.findViewById(R.id.tvEmpMobile);
            tvSupMobile = itemView.findViewById(R.id.tvSupMobile);
            tvSupName = itemView.findViewById(R.id.tvSupName);
            tvOutTime = itemView.findViewById(R.id.tvOutTime);
            tvInTime = itemView.findViewById(R.id.tvInTime);
            tvGatePass = itemView.findViewById(R.id.tvGatePass);

            tvOut = itemView.findViewById(R.id.tvOut);
            tvIn = itemView.findViewById(R.id.tvIn);
            tvHrs = itemView.findViewById(R.id.tvHrs);
            tvTotalHrs = itemView.findViewById(R.id.tvTotalHrs);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);

        }
    }

}
