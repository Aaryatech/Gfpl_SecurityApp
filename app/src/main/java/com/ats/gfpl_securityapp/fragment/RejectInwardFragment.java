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
import com.ats.gfpl_securityapp.adapter.RejectedInwardAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.RejectedInwardInterface;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.gfpl_securityapp.fragment.MaterialFragment.staticLoginUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RejectInwardFragment extends Fragment implements RejectedInwardInterface {
    private RecyclerView recyclerView;
    ArrayList<MaterialDetail> materialList = new ArrayList<>();
    RejectedInwardAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reject_inward, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(2);

        ArrayList<Integer> deptIdList = new ArrayList<>();
        deptIdList.add(staticLoginUser.getEmpDeptId());

        ArrayList<Integer> empIds = new ArrayList<>();
        empIds.add(staticLoginUser.getEmpId());

        getMaterial(deptIdList,empIds,statusList);

        return view;
    }

    private void getMaterial(ArrayList<Integer> deptIdList, ArrayList<Integer> empIdList, ArrayList<Integer> statusList) {

        Log.e("PARAMETER REJECTED","       DEPT ID   " +  deptIdList  +"           EMP ID   "+   empIdList  +"             STATUS"  +statusList);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MaterialDetail>> listCall = Constants.myInterface.getMaterialTrackGatepassListWithFilter(deptIdList,empIdList,statusList);
            listCall.enqueue(new Callback<ArrayList<MaterialDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<MaterialDetail>> call, Response<ArrayList<MaterialDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("MATERIAL  REJECTED: ", " - " + response.body());
                            materialList.clear();
                            materialList = response.body();

                            adapter = new RejectedInwardAdapter(materialList, getContext());
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
                public void onFailure(Call<ArrayList<MaterialDetail>> call, Throwable t) {
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
