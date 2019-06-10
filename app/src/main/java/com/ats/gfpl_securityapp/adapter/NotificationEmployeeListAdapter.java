package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Notification;

import java.util.ArrayList;

public class NotificationEmployeeListAdapter  extends RecyclerView.Adapter<NotificationEmployeeListAdapter.MyViewHolder> {
    private ArrayList<Notification> empList;
    private Context context;

    public NotificationEmployeeListAdapter(ArrayList<Notification> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_notification_employee_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // myViewHolder.tvName.setText("");
        Notification model=empList.get(i);
        myViewHolder.tvName.setText(model.getEmpName());
        if(model.getStatus()==0)
        {
            myViewHolder.tvStatus.setText("Pending");
        }else if(model.getStatus()==1)
        {
            myViewHolder.tvStatus.setText("Approve");
        }else  if(model.getStatus()==2)
        {
            myViewHolder.tvStatus.setText("Rejected");
        }
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMobile, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }

}
