package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    private ArrayList<Employee>empList;
    private Context context;

    public EmployeeAdapter(ArrayList<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_employee_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder myViewHolder, int i) {
        final Employee model=empList.get(i);
        myViewHolder.tvEmp.setText(model.getEmpFname()+" "+model.getEmpSname());

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
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmp;
        private CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmp=itemView.findViewById(R.id.tvEmp);
            checkBox=itemView.findViewById(R.id.checkBox);
        }
    }
}
