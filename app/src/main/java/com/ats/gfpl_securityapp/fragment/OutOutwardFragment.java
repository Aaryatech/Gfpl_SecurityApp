package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.ats.gfpl_securityapp.adapter.OutwardPendingAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.OutOutwardInterface;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.gfpl_securityapp.fragment.OutwardFragment.loginUser;
import static com.ats.gfpl_securityapp.fragment.OutwardFragment.syncArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutOutwardFragment extends Fragment implements OutOutwardInterface {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    ArrayList<Outward> outwardList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_out_outward, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

       // fab.setOnClickListener(this);

        final ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);

        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Security")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        final ArrayList<Integer> empList = new ArrayList<>();
                        empList.add(-1);

                        getOutardList(empList,statusList);

                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        final ArrayList<Integer> empList = new ArrayList<>();
                        empList.add(loginUser.getEmpId());

                        getOutardList(empList,statusList);

                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        final ArrayList<Integer> empList = new ArrayList<>();
                        empList.add(-1);

                        getOutardList(empList,statusList);

                    }
                }
            }
        }

        return view;
    }

    private void getOutardList(ArrayList<Integer> empList, ArrayList<Integer> statusList) {
        Log.e("PARAMETER","            EMP ID   "+   empList  +"             STATUS"  +statusList);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Outward>> listCall = Constants.myInterface.getOutwardGatepassList(empList,statusList);
            listCall.enqueue(new Callback<ArrayList<Outward>>() {
                @Override
                public void onResponse(Call<ArrayList<Outward>> call, Response<ArrayList<Outward>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("OUTWARD LIST OUT : ", " - " + response.body());
                            outwardList.clear();
                            outwardList = response.body();

                            OutwardPendingAdapter adapter = new OutwardPendingAdapter(outwardList, getContext(),syncArray,loginUser);
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
                public void onFailure(Call<ArrayList<Outward>> call, Throwable t) {
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
