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
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.VisitorGatepassMasterListAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.VisitorMaster;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitorGatepassMasterListFragment extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<VisitorMaster> visitorList = new ArrayList<>();
    VisitorGatepassMasterListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_visitor_gatepass_master_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        getVisitorGetPassList();
        return view;
    }

    private void getVisitorGetPassList() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<VisitorMaster>> listCall = Constants.myInterface.getVisitorList();
            listCall.enqueue(new Callback<ArrayList<VisitorMaster>>() {
                @Override
                public void onResponse(Call<ArrayList<VisitorMaster>> call, Response<ArrayList<VisitorMaster>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("VISITOR LIST : ", " ----------------------------MASTER-------------------------------- " + response.body());
                            visitorList.clear();
                            visitorList = response.body();

                            adapter = new VisitorGatepassMasterListAdapter(visitorList, getContext());
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
                public void onFailure(Call<ArrayList<VisitorMaster>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
