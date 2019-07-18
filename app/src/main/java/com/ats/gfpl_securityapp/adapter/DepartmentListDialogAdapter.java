package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Department;

import java.util.ArrayList;

public class DepartmentListDialogAdapter extends RecyclerView.Adapter<DepartmentListDialogAdapter.MyViewHolder> {

    private ArrayList<Department> custList;
    private Context context;

    public DepartmentListDialogAdapter(ArrayList<Department> custList, Context context) {
        this.custList = custList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvAddress = view.findViewById(R.id.tvAddress);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_department_dialog, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Department model = custList.get(position);

        holder.tvName.setText(model.getEmpDeptName());
        //holder.tvAddress.setText(model.getCustAddress());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA");
                customerDataIntent.putExtra("name", model.getEmpDeptName());
                customerDataIntent.putExtra("id", model.getEmpDeptId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return custList.size();
    }

    public void updateList(ArrayList<Department> list) {
        custList = list;
        notifyDataSetChanged();
    }

}
