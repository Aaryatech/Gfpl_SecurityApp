package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.EmpGatePass;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeGatePassDetailFragment extends Fragment {
EmpGatePass model;
    public TextView tvName, tvMobile, tvType, tvRemark,tvGPNo,tvOutTime,tvInTime,tvPurposeText,tvgEmpName,tvNewOutTime,tvNewInTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_employee_gate_pass_detail, container, false);
        getActivity().setTitle("Employee Detail");
        tvGPNo = view.findViewById(R.id.tvGPNo);
        tvName = view.findViewById(R.id.tvName);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvType = view.findViewById(R.id.tvType);
        tvRemark = view.findViewById(R.id.tvRemark);
        tvOutTime = view.findViewById(R.id.tvOutTime);
        tvInTime = view.findViewById(R.id.tvInTime);
        tvNewOutTime = view.findViewById(R.id.tvNewOutTime);
        tvNewInTime = view.findViewById(R.id.tvNewInTime);
        tvPurposeText = view.findViewById(R.id.tvPurposeText);
        tvgEmpName = view.findViewById(R.id.tvgEmpName);

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, EmpGatePass.class);
            Log.e("MODEL","-----------------------------------"+model);
            tvGPNo.setText(model.getExVar1());
            tvName.setText(model.getEmpName());
            tvRemark.setText(model.getPurposeRemark());
            tvOutTime.setText(""+model.getOutTime());
            tvInTime.setText(""+model.getInTime());
            tvPurposeText.setText(model.getPurposeText());
            tvgEmpName.setText(model.getUserName());
            tvNewInTime.setText(""+model.getNewInTime());
            tvNewOutTime.setText(""+model.getNewOutTime());

            if(model.getGatePassSubType()==1)
            {
                tvType.setText("Temporary");
            }else if(model.getGatePassSubType()==2)
            {
                tvType.setText("Day");
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return view;
    }

}
