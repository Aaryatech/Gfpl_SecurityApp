package com.ats.gfpl_securityapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.ats.gfpl_securityapp.adapter.OutwardRejectAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutwardRejectFragment extends Fragment {
    private RecyclerView recyclerView;
    public static Login loginUser;
    public static ArrayList<Sync> syncArray = new ArrayList<>();
    ArrayList<Outward> outwardList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outward_reject, container, false);
        getActivity().setTitle("Material Gate Pass Reject");
        recyclerView = view.findViewById(R.id.recyclerView);

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Gson gson = new Gson();
            String json = prefs.getString("Sync", null);
            Type type = new TypeToken<ArrayList<Sync>>() {}.getType();
            syncArray= gson.fromJson(json, type);

            Log.e("SYNC EMP : ", "--------USER-------" + syncArray);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        final ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(4);

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

                            Log.e("OUTWARD REJECT LIST : ", " - " + response.body());
                            outwardList.clear();
                            outwardList = response.body();

                            OutwardRejectAdapter adapter = new OutwardRejectAdapter(outwardList, getContext(),syncArray,loginUser);
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

}
