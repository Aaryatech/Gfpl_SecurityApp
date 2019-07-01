package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Employee;

import java.util.ArrayList;

public class PersonListDialogAdapter extends RecyclerView.Adapter<PersonListDialogAdapter.MyViewHolder> {
    private ArrayList<Employee> empList;
    private Context context;

    public PersonListDialogAdapter(ArrayList<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_party_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonListDialogAdapter.MyViewHolder myViewHolder, int i) {
        final Employee model = empList.get(i);

        myViewHolder.tvName.setText(model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());
        //holder.tvAddress.setText(model.getCustAddress());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA");
                customerDataIntent.putExtra("name", model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());
                customerDataIntent.putExtra("id", model.getEmpId());
                customerDataIntent.putExtra("image", model.getEmpPhoto());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public void updateList(ArrayList<Employee> list) {
        empList = list;
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
