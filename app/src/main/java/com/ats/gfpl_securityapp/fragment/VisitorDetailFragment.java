package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.VisitorDetailInterface;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ats.gfpl_securityapp.fragment.TabFragment.staticVisitorModel;

public class VisitorDetailFragment extends Fragment implements VisitorDetailInterface {
    public TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark,tvGPNo,tvDate,tvOutTime,tvInTime,tvgateName,tvgatePassType,tvPurposeText,tvgEmpName,tvPhone,tvMeetingDisc,tvVisitCardNumber,tvSecurityName;
    public CircleImageView ivPhoto;
    public ImageView ivPhoto1,ivPhoto2,ivPhoto3;
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
        tvDate = view.findViewById(R.id.tvSecurityId);
        tvOutTime = view.findViewById(R.id.tvOutTime);
        tvInTime = view.findViewById(R.id.tvInTime);
        tvgateName = view.findViewById(R.id.tvgateName);
        tvgatePassType = view.findViewById(R.id.tvgatePassType);
        tvPurposeText = view.findViewById(R.id.tvPurposeText);
        tvgEmpName = view.findViewById(R.id.tvgEmpName);
        tvPhone = view.findViewById(R.id.tvMob);
        tvMeetingDisc = view.findViewById(R.id.tvgMeetingDisc);
        tvVisitCardNumber = view.findViewById(R.id.tvVisiteCardNumber);
        tvSecurityName = view.findViewById(R.id.tvSecurityName);

        ivPhoto = view.findViewById(R.id.ivPhoto);
        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);

        String imageUri = String.valueOf(staticVisitorModel.getPersonPhoto());
        try {
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+staticVisitorModel.getIdProof()).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+staticVisitorModel.getIdProof1()).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+staticVisitorModel.getOtherPhoto()).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tvGPNo.setText(""+staticVisitorModel.getExVar1());
        tvName.setText(staticVisitorModel.getPersonName());
        tvCompany.setText(staticVisitorModel.getPersonCompany());
        tvMobile.setText(staticVisitorModel.getMobileNo());
        tvRemark.setText("Remark : "+staticVisitorModel.getPurposeRemark());
        tvDate.setText(""+staticVisitorModel.getVisitDateIn());
        tvOutTime.setText(staticVisitorModel.getVisitOutTime());
        tvInTime.setText(staticVisitorModel.getInTime());
        tvgateName.setText("Gate Name : "+staticVisitorModel.getGateName());
        tvPurposeText.setText("Purpose : "+staticVisitorModel.getPurposeHeading());
        tvgEmpName.setText("Employee Name : "+staticVisitorModel.getEmpName());
        tvMeetingDisc.setText("Meeting Discusstion : "+staticVisitorModel.getMeetingDiscussion());
        tvVisitCardNumber.setText("Card No : "+staticVisitorModel.getVisitCardNo());
        tvSecurityName.setText("Security Name : "+staticVisitorModel.getSecurityInName());

        if(staticVisitorModel.getTakeMobile()==1)
        {
            tvPhone.setText("Take Mobile : "+"Yes");
        }else if(staticVisitorModel.getTakeMobile()==2)
        {
            tvPhone.setText("Take Mobile : "+"No");
        }

        if(staticVisitorModel.getVisitType()==1)
        {
            tvType.setText("Appointment");
        }else if(staticVisitorModel.getVisitType()==2){
            tvType.setText("Random");
        }

        if(staticVisitorModel.getVisitStatus()==0)
        {
            tvStatus.setText("Status : "+"Pending");
        }else if(staticVisitorModel.getVisitStatus()==1)
        {
            tvStatus.setText("Status : "+"Approve");
        }else if(staticVisitorModel.getVisitStatus()==2)
        {
            tvStatus.setText("Status : "+"Rejected");
        }else if(staticVisitorModel.getVisitStatus()==3)
        {
            tvStatus.setText("Status : "+"Allowed to Enter");
        }else if(staticVisitorModel.getVisitStatus()==4)
        {
            tvStatus.setText("Status : "+"Close Meeting");
        }else if(staticVisitorModel.getVisitStatus()==5)
        {
            tvStatus.setText("Status : "+"Out From Factory");
        }

        if(staticVisitorModel.getGatePasstype()==1)
        {
            tvgatePassType.setText("Visitor Type : "+"Visitor");
        }else if(staticVisitorModel.getGatePasstype()==2)
        {
            tvgatePassType.setText("Visitor Type : "+"Maintenance");
        }

        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
