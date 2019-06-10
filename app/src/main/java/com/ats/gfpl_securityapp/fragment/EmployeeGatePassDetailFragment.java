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

import java.text.SimpleDateFormat;
import java.util.Date;

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
//            tvNewInTime.setText(""+model.getNewInTime());
//            tvNewOutTime.setText(""+model.getNewOutTime());

            SimpleDateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
            SimpleDateFormat f2 = new SimpleDateFormat("hh:mm");

            Date fromTime=null;
            try {
                String outTime=model.getNewOutTime();
                Log.e("Time","------------------"+outTime);
                fromTime=f1.parse(outTime);
                Log.e("Time Date","------------------"+fromTime);
                String timeOut = f2.format(fromTime);
                Log.e("Time Convert","------------------"+timeOut);
                tvNewOutTime.setText(timeOut);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            Date toTime=null;
            try {
                String inTime=model.getNewInTime();
                Log.e("Time1","------------------"+inTime);
                toTime=f1.parse(inTime);
                Log.e("Time Date1","------------------"+toTime);
                String timeIn = f2.format(toTime);
                Log.e("Time Convert1","------------------"+timeIn);
                tvNewInTime.setText(timeIn);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

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
