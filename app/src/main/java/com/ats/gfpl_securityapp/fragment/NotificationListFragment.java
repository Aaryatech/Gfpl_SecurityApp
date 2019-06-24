package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.NotificationEmployeeListAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.NotificationInterface;
import com.ats.gfpl_securityapp.model.Notification;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.gfpl_securityapp.fragment.TabFragment.staticVisitorModel;

public class NotificationListFragment extends Fragment implements NotificationInterface {

    private TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark;
    private RecyclerView recyclerView;
    //private ArrayList<Notification> notificationList;
    private  ArrayList<Notification> notificationList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvCompany = view.findViewById(R.id.tvCompany);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvType = view.findViewById(R.id.tvType);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvRemark = view.findViewById(R.id.tvRemark);

        recyclerView = view.findViewById(R.id.recyclerView);

        tvName.setText(staticVisitorModel.getPersonName());
        tvCompany.setText(staticVisitorModel.getPersonCompany());
        tvMobile.setText(staticVisitorModel.getMobileNo());
        tvRemark.setText(staticVisitorModel.getPurposeRemark());

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
        }else if(staticVisitorModel.getVisitStatus()==2)
        {
            tvStatus.setText("Rejected");
        }else if(staticVisitorModel.getVisitStatus()==3)
        {
            tvStatus.setText("Allow to Enter");
        }else if(staticVisitorModel.getVisitStatus()==4)
        {
            tvStatus.setText("Close Meeting");
        }else if(staticVisitorModel.getVisitStatus()==5)
        {
            tvStatus.setText("Out From Factory");
        }


        getNotifiaction(staticVisitorModel.getGatepassVisitorId());

//        ArrayList<String> strList = new ArrayList<>();
//        strList.add("");
//        strList.add("");
//        strList.add("");
      

        return view;
    }

    private void getNotifiaction(Integer gatepassVisitorId) {

        Log.e("PARAMETER","            VISITOR ID       "+ gatepassVisitorId);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Notification>> listCall = Constants.myInterface.getNotificationByGatepassId(gatepassVisitorId);
            listCall.enqueue(new Callback<ArrayList<Notification>>() {
                @Override
                public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NOTIFICATION LIST : ", " - " + response.body());
                            notificationList.clear();
                            notificationList = response.body();

                            NotificationEmployeeListAdapter adapter = new NotificationEmployeeListAdapter(notificationList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
