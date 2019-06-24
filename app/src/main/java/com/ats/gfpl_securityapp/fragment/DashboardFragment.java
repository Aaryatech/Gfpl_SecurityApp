package com.ats.gfpl_securityapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Dashboard;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
public LinearLayout linearLayoutVisitor1,linearLayoutVisitor2,LinearLayoutVisitor3,linearLayoutVisitorEmployee1,linearLayoutVisitorEmployee2,linearLayoutVisitorEmployee3,linearLayoutVisitorMaintenance1,linearLayoutMaintenanceEmployee2,linearLayoutGP1,linearLayoutGP2,linearLayoutGP3,linearLayoutDepartment1,linearLayoutEmployee1,linearLayoutEmployee2,linearLayoutSuperGP1,linearLayoutSuperGP2,linearLayoutMainTotal,linearLayoutDeptRejected;
public CardView cardViewMaintenancePending,cardViewEmployeeMeetingCompleted,cardViewParcel,cardViewMeetingCompletedForAdmin,cardViewMaintenancePendingEmp,cardViewDeptpending;
Login loginUserMain,loginUser;
public TextView tvTotalVisitor,tvCurrVisitorComp,tvVisitorMeetingCompleted,tvVisitorRejected,tvVisitorPending,tvCurrMeetingGoingOn,tvEmpTotalVisitor,tvEmpCurrEmployeeMeetingGoingOn,tvEmpWiseRejected,tvEmpWisePending,tvEmpwisemeetingcompleted,tvMaintenanceWisePending,tvMaintenanceWiseApprove,tvMaintenanceWiseRejected,tvMaintenanceWiseWorkCompleted,tvMaintenanceTotal,tvTotalTempGP,tvTotalDayGP,tvNoOfEmpOutsideFactory,tvTotalNoOfInward,tvTotalNoOfParcel,tvDeptWiseTotalPending,tvDeptWiseTotalApprove,tvDeptWiseTotalRejected,tvEmpWiseTotalPending,tvEmpWiseTotalApprove,tvEmpWiseTotalRejected,tvSuptotalTempGP,tvSuptotalDayGP,tvNoOfEmpOutSide;
public TextView tvLabVisitorCount,tvLabEmpVisitorCount,tvLabMaintenanceCount,tvLabEmpGatePassCount,tvlabMaterialCount,tvLabEmpMaterialCount,tvLabSuperWiserCount;
ArrayList<Sync> syncArray = new ArrayList<>();
private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().setTitle("Dashboard");

        tvTotalVisitor=(TextView) view.findViewById(R.id.tvTotalVisitor);
        tvCurrVisitorComp=(TextView) view.findViewById(R.id.tvCurrVisitorComp);
        tvVisitorMeetingCompleted=(TextView) view.findViewById(R.id.tvVisitorMeetingCompleted);
        tvVisitorRejected=(TextView) view.findViewById(R.id.tvVisitorRejected);
        tvVisitorPending=(TextView) view.findViewById(R.id.tvVisitorPending);
        tvCurrMeetingGoingOn=(TextView) view.findViewById(R.id.tvCurrMeetingGoingOn);
        tvEmpTotalVisitor=(TextView) view.findViewById(R.id.tvEmpTotalVisitor);
        tvEmpCurrEmployeeMeetingGoingOn=(TextView) view.findViewById(R.id.tvEmpCurrEmployeeMeetingGoingOn);
        tvEmpWiseRejected=(TextView) view.findViewById(R.id.tvEmpWiseRejected);
        tvEmpWisePending=(TextView) view.findViewById(R.id.tvEmpWisePending);
        tvEmpwisemeetingcompleted=(TextView) view.findViewById(R.id.tvEmpWiseMeetingCompleted);
        tvMaintenanceWisePending=(TextView) view.findViewById(R.id.tvMaintenanceWisePending);
        tvMaintenanceWiseApprove=(TextView) view.findViewById(R.id.tvMaintenanceWiseApprove);
        tvMaintenanceWiseRejected=(TextView) view.findViewById(R.id.tvMaintenanceWiseRejected);
        tvMaintenanceWiseWorkCompleted=(TextView) view.findViewById(R.id.tvMaintenanceWiseWorkCompleted);
        tvMaintenanceTotal=(TextView) view.findViewById(R.id.tvMaintenanceTotal);
        tvTotalTempGP=(TextView) view.findViewById(R.id.tvTotalTempGP);
        tvTotalDayGP=(TextView) view.findViewById(R.id.tvTotalDayGP);
        tvNoOfEmpOutsideFactory=(TextView) view.findViewById(R.id.tvNoOfEmpOutsideFactory);
        tvTotalNoOfInward=(TextView) view.findViewById(R.id.tvTotalNoOfInward);
        tvTotalNoOfParcel=(TextView) view.findViewById(R.id.tvTotalNoOfParcel);
        tvDeptWiseTotalPending=(TextView) view.findViewById(R.id.tvDeptWiseTotalPending);
        tvDeptWiseTotalApprove=(TextView) view.findViewById(R.id.tvDeptWiseTotalApprove);
        tvDeptWiseTotalRejected=(TextView) view.findViewById(R.id.tvDeptWiseTotalRejected);
        tvEmpWiseTotalPending=(TextView) view.findViewById(R.id.tvEmpWiseTotalPending);
        tvEmpWiseTotalApprove=(TextView) view.findViewById(R.id.tvEmpWiseTotalApprove);
        tvEmpWiseTotalRejected=(TextView) view.findViewById(R.id.tvEmpWiseTotalRejected);
        tvSuptotalTempGP=(TextView) view.findViewById(R.id.tvSupTotalTempGP);
        tvSuptotalDayGP=(TextView) view.findViewById(R.id.tvSupTotalDayGP);
        tvNoOfEmpOutSide=(TextView) view.findViewById(R.id.tvNoOfEmpOutside);


        tvLabVisitorCount=(TextView) view.findViewById(R.id.tvLabVisitorCount);
        tvLabEmpVisitorCount=(TextView) view.findViewById(R.id.tvLabEmpVisitorCount);
        tvLabMaintenanceCount=(TextView) view.findViewById(R.id.tvLabMaintenanceCount);
        tvLabEmpGatePassCount=(TextView) view.findViewById(R.id.tvLabEmpgatePassCount);
        tvlabMaterialCount=(TextView) view.findViewById(R.id.tvLabMaterialCount);
        tvLabEmpMaterialCount=(TextView) view.findViewById(R.id.tvLabEmpMaterialCount);
        tvLabSuperWiserCount=(TextView) view.findViewById(R.id.tvLabSuperWiserCount);


        linearLayoutVisitor1=(LinearLayout)view.findViewById(R.id.linearLayoutVisitor1);
        linearLayoutVisitor2=(LinearLayout)view.findViewById(R.id.linearLayoutVisitor2);
        LinearLayoutVisitor3=(LinearLayout)view.findViewById(R.id.linearLayoutVisitor3);
        linearLayoutVisitorEmployee1=(LinearLayout)view.findViewById(R.id.linearLayoutVisitorEmployee1);
        linearLayoutVisitorEmployee2=(LinearLayout)view.findViewById(R.id.linearLayoutVisitorEmployee2);
        linearLayoutVisitorEmployee3=(LinearLayout)view.findViewById(R.id.linearLayoutVisitorEmployee3) ;
        linearLayoutVisitorMaintenance1=(LinearLayout)view.findViewById(R.id.linearLayoutVisitorMaintenance1);
        linearLayoutMaintenanceEmployee2=(LinearLayout)view.findViewById(R.id.linearLayoutMaintenanceEmployee2);
        linearLayoutMainTotal=(LinearLayout)view.findViewById(R.id.linearLayoutMainTotal);
        linearLayoutGP1=(LinearLayout)view.findViewById(R.id.linearLayoutGP1);
        linearLayoutGP2=(LinearLayout)view.findViewById(R.id.linearLayoutGP2);
        linearLayoutGP3=(LinearLayout)view.findViewById(R.id.linearLayoutGP3);
        linearLayoutDepartment1=(LinearLayout)view.findViewById(R.id.linearLayoutDepartment1);
        linearLayoutDeptRejected=(LinearLayout)view.findViewById(R.id.linearLayoutDeptRejected);
        linearLayoutEmployee1=(LinearLayout)view.findViewById(R.id.linearLayoutEmployee1);
        linearLayoutEmployee2=(LinearLayout)view.findViewById(R.id.linearLayoutEmployee2);
        linearLayoutSuperGP1=(LinearLayout)view.findViewById(R.id.linearLayoutSuperGP1);
        linearLayoutSuperGP2=(LinearLayout)view.findViewById(R.id.linearLayoutSuperGP2);

        cardViewMaintenancePending=(CardView)view.findViewById(R.id.cardViewMaintenancePending);
        cardViewEmployeeMeetingCompleted=(CardView)view.findViewById(R.id.cardViewEmployeeMeetingCompleted);
        cardViewParcel=(CardView)view.findViewById(R.id.cardViewParcel);
        cardViewMeetingCompletedForAdmin=(CardView)view.findViewById(R.id.cardViewMeetingCompletedForAdmin);
        cardViewMaintenancePendingEmp=(CardView)view.findViewById(R.id.cardViewMaintenancePendingEmp);

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.MAIN_KEY_USER);
            Gson gson = new Gson();
            loginUserMain = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUserMain);
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

            Log.e("SYNC MAIN : ", "--------USER-------" + syncArray);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(syncArray!=null) {

            for (int j = 0; j < syncArray.size(); j++) {

                if (syncArray.get(j).getSettingKey().equals("Security")) {

                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUserMain.getEmpCatId()))) {

                        linearLayoutVisitor1.setVisibility(View.VISIBLE);
                        linearLayoutVisitor2.setVisibility(View.VISIBLE);
                        LinearLayoutVisitor3.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee1.setVisibility(View.GONE);
                        linearLayoutVisitorEmployee2.setVisibility(View.GONE);
                        linearLayoutVisitorEmployee3.setVisibility(View.GONE);
                        linearLayoutVisitorMaintenance1.setVisibility(View.VISIBLE);
                        linearLayoutMaintenanceEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutMainTotal.setVisibility(View.VISIBLE);
                        linearLayoutGP1.setVisibility(View.VISIBLE);
                        linearLayoutGP2.setVisibility(View.VISIBLE);
                        linearLayoutGP3.setVisibility(View.VISIBLE);
                        linearLayoutDepartment1.setVisibility(View.VISIBLE);
                        linearLayoutDeptRejected.setVisibility(View.VISIBLE);
                        linearLayoutEmployee1.setVisibility(View.VISIBLE);
                        linearLayoutEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutSuperGP1.setVisibility(View.GONE);
                        linearLayoutSuperGP2.setVisibility(View.GONE);

                        tvLabVisitorCount.setVisibility(View.VISIBLE);
                        tvLabEmpVisitorCount.setVisibility(View.GONE);
                        tvLabMaintenanceCount.setVisibility(View.VISIBLE);
                        tvLabEmpGatePassCount.setVisibility(View.VISIBLE);
                        tvlabMaterialCount.setVisibility(View.VISIBLE);
                        tvLabEmpMaterialCount.setVisibility(View.VISIBLE);
                        tvLabSuperWiserCount.setVisibility(View.GONE);
                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUserMain.getEmpCatId()))) {
                        linearLayoutVisitor1.setVisibility(View.GONE);
                        linearLayoutVisitor2.setVisibility(View.GONE);
                        LinearLayoutVisitor3.setVisibility(View.GONE);
                        linearLayoutVisitorEmployee1.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee3.setVisibility(View.VISIBLE);
                        linearLayoutVisitorMaintenance1.setVisibility(View.GONE);
                        linearLayoutMaintenanceEmployee2.setVisibility(View.GONE);
                        linearLayoutMainTotal.setVisibility(View.GONE);
                        linearLayoutGP1.setVisibility(View.GONE);
                        linearLayoutGP2.setVisibility(View.GONE);
                        linearLayoutGP3.setVisibility(View.GONE);
                        linearLayoutDepartment1.setVisibility(View.VISIBLE);
                        linearLayoutDeptRejected.setVisibility(View.VISIBLE);
                        linearLayoutEmployee1.setVisibility(View.VISIBLE);
                        linearLayoutEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutSuperGP1.setVisibility(View.VISIBLE);
                        linearLayoutSuperGP2.setVisibility(View.VISIBLE);

                        tvLabVisitorCount.setVisibility(View.GONE);
                        tvLabEmpVisitorCount.setVisibility(View.VISIBLE);
                        tvLabMaintenanceCount.setVisibility(View.GONE);
                        tvLabEmpGatePassCount.setVisibility(View.GONE);
                        tvlabMaterialCount.setVisibility(View.VISIBLE);
                        tvLabEmpMaterialCount.setVisibility(View.VISIBLE);
                        tvLabSuperWiserCount.setVisibility(View.VISIBLE);
                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUserMain.getEmpCatId()))) {

                        linearLayoutVisitor1.setVisibility(View.VISIBLE);
                        linearLayoutVisitor2.setVisibility(View.VISIBLE);
                        LinearLayoutVisitor3.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee1.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutVisitorEmployee3.setVisibility(View.VISIBLE);
                        linearLayoutVisitorMaintenance1.setVisibility(View.VISIBLE);
                        linearLayoutMaintenanceEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutMainTotal.setVisibility(View.VISIBLE);
                        linearLayoutGP1.setVisibility(View.VISIBLE);
                        linearLayoutGP2.setVisibility(View.VISIBLE);
                        linearLayoutGP3.setVisibility(View.VISIBLE);
                        linearLayoutDepartment1.setVisibility(View.VISIBLE);
                        linearLayoutDeptRejected.setVisibility(View.VISIBLE);
                        linearLayoutEmployee1.setVisibility(View.VISIBLE);
                        linearLayoutEmployee2.setVisibility(View.VISIBLE);
                        linearLayoutSuperGP1.setVisibility(View.GONE);
                        linearLayoutSuperGP2.setVisibility(View.GONE);

                        tvLabVisitorCount.setVisibility(View.VISIBLE);
                        tvLabEmpVisitorCount.setVisibility(View.VISIBLE);
                        tvLabMaintenanceCount.setVisibility(View.VISIBLE);
                        tvLabEmpGatePassCount.setVisibility(View.VISIBLE);
                        tvlabMaterialCount.setVisibility(View.VISIBLE);
                        tvLabEmpMaterialCount.setVisibility(View.VISIBLE);
                        tvLabSuperWiserCount.setVisibility(View.GONE);

                    }
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // sdf.format(System.currentTimeMillis())
        getDashboard(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),loginUserMain.getEmpId());

        return view;
    }

    private void getDashboard(String fromDate, String toDate, Integer empId) {

        Log.e("PARAMETER","            FROM DATE       "+ fromDate        +"          TO DATE     " +   toDate  +"       EMP ID   " +  empId  );

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Dashboard> listCall = Constants.myInterface.dashboardCount(fromDate,toDate,empId);
            listCall.enqueue(new Callback<Dashboard>() {
                @Override
                public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Dashboard LIST : ", " - " + response.body());

                            Dashboard dashboard = response.body();

                            tvTotalVisitor.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorTotal());
                            tvCurrVisitorComp.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorInComp());
                            tvVisitorMeetingCompleted.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorCompleted());
                            tvVisitorRejected.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorRejected());
                            tvVisitorPending.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorPending());
                            tvCurrMeetingGoingOn.setText(""+dashboard.getVisAndMaintGatepassCount().getVisitorApproved());
                            tvEmpTotalVisitor.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorTotal());
                            tvEmpCurrEmployeeMeetingGoingOn.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorApproved());
                            tvEmpWiseRejected.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorRejected());
                            tvEmpWisePending.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorPending());
                            tvEmpwisemeetingcompleted.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorCompleted());
                            tvMaintenanceWisePending.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintPending());
                            tvMaintenanceWiseApprove.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintApproved());
                            tvMaintenanceWiseRejected.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintRejected());
                            tvMaintenanceWiseWorkCompleted.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintCompleted());
                            tvMaintenanceTotal.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintTotal());
                            tvTotalTempGP.setText(""+dashboard.getEmpGatepassCount().getTempGpCount());
                            tvTotalDayGP.setText(""+dashboard.getEmpGatepassCount().getDayGpCount());
                            tvNoOfEmpOutsideFactory.setText(""+dashboard.getEmpGatepassCount().getOutEmpCount());
                            tvTotalNoOfInward.setText(""+dashboard.getMatGatepassCount().getInwardCount());
                            tvTotalNoOfParcel.setText(""+dashboard.getMatGatepassCount().getParcelCount());
                            tvDeptWiseTotalPending.setText(""+dashboard.getMatGatepassCount().getDeptPendingCount());
                            tvDeptWiseTotalApprove.setText(""+dashboard.getMatGatepassCount().getDeptApprovedCount());
                            tvDeptWiseTotalRejected.setText(""+dashboard.getMatGatepassCount().getDeptRejectedCount());
                            tvEmpWiseTotalPending.setText(""+dashboard.getMatGatepassEmpWiseCount().getEmpPendingCount());
                            tvEmpWiseTotalApprove.setText(""+dashboard.getMatGatepassEmpWiseCount().getEmpApprovedCount());
                            tvEmpWiseTotalRejected.setText(""+dashboard.getMatGatepassEmpWiseCount().getEmpRejectedCount());
                            //tvMaintenancePending1.setText(""+dashboard.getVisAndMaintGatepassCount().getMaintPending());
                            tvSuptotalTempGP.setText(""+dashboard.getSupGatepassCount().getSupTempCount());
                            tvSuptotalDayGP.setText(""+dashboard.getSupGatepassCount().getSupDayCount());
                            tvNoOfEmpOutSide.setText(""+dashboard.getSupGatepassCount().getSupOutEmpCount());
                           // tvEmpWiseMeetingCopmleted1.setText(""+dashboard.getVisAndMaintGatepassCount().getEmpVisitorCompleted());
                            //tvDeptWiseTotalPending1.setText(""+dashboard.getMatGatepassCount().getDeptPendingCount());
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
                public void onFailure(Call<Dashboard> call, Throwable t) {
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
