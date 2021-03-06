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
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;

import java.util.ArrayList;

public class VisitorEmployeeAdapter extends RecyclerView.Adapter<VisitorEmployeeAdapter.MyViewHolder> {
    private ArrayList<Employee> empList;
    private Context context;
    ArrayList<Sync> syncArray;
    Login loginUser;

    public VisitorEmployeeAdapter(ArrayList<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisitorEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_employee_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorEmployeeAdapter.MyViewHolder myViewHolder, int i) {
        final Employee model=empList.get(i);
        final int pos = i;
        myViewHolder.tvEmp.setText(model.getEmpFname()+" "+model.getEmpSname());

//        myViewHolder.checkBox.setChecked(empList.get(i).getChecked());
//
//        myViewHolder.checkBox.setTag(empList.get(i));
//
//        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox cb = (CheckBox) v;
//                Employee employee = (Employee) cb.getTag();
//
//                employee.setChecked(cb.isChecked());
//                empList.get(pos).setChecked(cb.isChecked());
//
//            }
//        });


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
