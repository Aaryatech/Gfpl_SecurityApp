package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.NotificationEmployeeListAdapter;
import com.ats.gfpl_securityapp.adapter.VisitorGatePassListAdapter;
import com.ats.gfpl_securityapp.interfaces.NotificationInterface;

import java.util.ArrayList;

public class NotificationListFragment extends Fragment implements NotificationInterface {

    private TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark;
    private RecyclerView recyclerView;

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

        ArrayList<String> strList = new ArrayList<>();
        strList.add("");
        strList.add("");
        strList.add("");
      

        NotificationEmployeeListAdapter adapter = new NotificationEmployeeListAdapter(strList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
