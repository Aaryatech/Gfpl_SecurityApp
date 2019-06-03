package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.interfaces.VisitorDetailInterface;

import static com.ats.gfpl_securityapp.fragment.TabFragment.staticVisitorModel;

public class VisitorDetailFragment extends Fragment implements VisitorDetailInterface {
    public TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark,tvGPNo,tvSecurityId,tvOutTime,tvInTime,tvgateName,tvgatePassType,tvPurposeText,tvgEmpName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitor_detail, container, false);
        tvGPNo = view.findViewById(R.id.tvGPNo);
        tvName = view.findViewById(R.id.tvName);
        tvCompany = view.findViewById(R.id.tvCompany);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvType = view.findViewById(R.id.tvType);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvRemark = view.findViewById(R.id.tvRemark);
        tvSecurityId = view.findViewById(R.id.tvSecurityId);
        tvOutTime = view.findViewById(R.id.tvOutTime);
        tvInTime = view.findViewById(R.id.tvInTime);
        tvgateName = view.findViewById(R.id.tvgateName);
        tvgatePassType = view.findViewById(R.id.tvgatePassType);
        tvPurposeText = view.findViewById(R.id.tvPurposeText);
        tvgEmpName = view.findViewById(R.id.tvgEmpName);

        tvGPNo.setText(staticVisitorModel.getExVar1());
        tvName.setText(staticVisitorModel.getPersonName());
        tvCompany.setText(staticVisitorModel.getPersonCompany());
        tvMobile.setText(staticVisitorModel.getMobileNo());
        tvRemark.setText(staticVisitorModel.getPurposeRemark());
        tvSecurityId.setText(""+staticVisitorModel.getSecurityIdIn());
        tvOutTime.setText(staticVisitorModel.getVisitOutTime());
        tvInTime.setText(staticVisitorModel.getInTime());
        tvgateName.setText(staticVisitorModel.getGateName());
        tvPurposeText.setText(staticVisitorModel.getPurposeHeading());
        tvgEmpName.setText(staticVisitorModel.getEmpName());

        if(staticVisitorModel.getVisitType()==1)
        {
            tvType.setText("Appointment");
        }else if(staticVisitorModel.getVisitType()==2){
            tvType.setText("Random");
        }

        if(staticVisitorModel.getVisitStatus()==0)
        {
            tvStatus.setText("Pending");
        }else if(staticVisitorModel.getVisitStatus()==1)
        {
            tvStatus.setText("Approve");
        }else  if(staticVisitorModel.getVisitStatus()==2)
        {
            tvStatus.setText("Rejected");
        }

        if(staticVisitorModel.getGatePasstype()==1)
        {
            tvgatePassType.setText("Visitor");
        }else if(staticVisitorModel.getGatePasstype()==2)
        {
            tvgatePassType.setText("Maintenance");
        }

        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
