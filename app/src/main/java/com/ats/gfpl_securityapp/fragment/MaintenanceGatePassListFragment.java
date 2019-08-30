package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.MaintenanceGatePassListAdapter;
import com.ats.gfpl_securityapp.adapter.VisitorEmployeeAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaintenanceGatePassListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    public static String strIntentMain;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    ArrayList<Sync> syncArray = new ArrayList<>();
    ArrayList<Integer> statusList = new ArrayList<>();

    ArrayList<VisitorList> temp;
    MaintenanceGatePassListAdapter adapter;

    String stringId;
    ArrayList<Employee> empList = new ArrayList<>();
    RecyclerView recyclerViewFilter;
    public static ArrayList<Employee> assignMaintenanceEmpStaticList = new ArrayList<>();

    Login loginUser;

    ArrayList<VisitorList> visitorList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance_gate_pass_list, container, false);
        getActivity().setTitle("Maintenance List");

        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(this);
//
//        ArrayList<String> strList = new ArrayList<>();
//        strList.add("");
//        strList.add("");
//        strList.add("");
//        strList.add("");
//        strList.add("");

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

            Log.e("SYNC MAIN Mainte : ", "--------USER-------" + syncArray);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            strIntentMain = getArguments().getString("model");
            Log.e("StringMain List", "--------------------------" + strIntentMain);
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Exception","------------------------------"+e);
        }

        getEmployee();

        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Security")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                        if(strIntentMain!=null) {
                            if (strIntentMain.equalsIgnoreCase("Maint 0")) {
                                statusList.add(0);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);

                            } else if (strIntentMain.equalsIgnoreCase("Maint 1")) {
                                statusList.add(1);
                                statusList.add(3);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);

                            } else if (strIntentMain.equalsIgnoreCase("Maint 2")) {
                                statusList.add(2);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);

                            } else if (strIntentMain.equalsIgnoreCase("Maint 3")) {

                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);

                            }  else if (strIntentMain.equalsIgnoreCase("Add Maintenance getPass list") || strIntentMain.equalsIgnoreCase("Maint -1")) {

                                statusList.add(0);
                                statusList.add(1);
                                statusList.add(2);
                                statusList.add(3);
                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                            }
                        }else{
                            statusList.add(0);
                            statusList.add(1);
                            statusList.add(2);
                            statusList.add(3);
                            statusList.add(4);
                            statusList.add(5);

                            ArrayList<Integer> getPassTypeList = new ArrayList<>();
                            getPassTypeList.add(2);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                        }
                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                        if(strIntentMain!=null) {
                            if (strIntentMain.equalsIgnoreCase("Maint 0")) {
                                statusList.add(0);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);

                            } else if (strIntentMain.equalsIgnoreCase("Maint 1")) {
                                statusList.add(1);
                                statusList.add(3);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);
                            } else if (strIntentMain.equalsIgnoreCase("Maint 2")) {
                                statusList.add(2);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);
                            } else if (strIntentMain.equalsIgnoreCase("Maint 3")) {
                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);
                            }  else if (strIntentMain.equalsIgnoreCase("Add Maintenance getPass list") || strIntentMain.equalsIgnoreCase("Maint -1")) {

                                statusList.add(0);
                                statusList.add(1);
                                statusList.add(2);
                                statusList.add(3);
                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);
                            }
                        }else{
                            statusList.add(0);
                            statusList.add(1);
                            statusList.add(2);
                            statusList.add(3);
                            statusList.add(4);
                            statusList.add(5);

                            ArrayList<Integer> getPassTypeList = new ArrayList<>();
                            getPassTypeList.add(2);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, String.valueOf(loginUser.getEmpId()), statusList);
                        }

                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                        if(strIntentMain!=null) {
                            if (strIntentMain.equalsIgnoreCase("Maint 0")) {
                                statusList.add(0);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);

                            } else if (strIntentMain.equalsIgnoreCase("Maint 1")) {
                                statusList.add(1);
                                statusList.add(3);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                            } else if (strIntentMain.equalsIgnoreCase("Maint 2")) {
                                statusList.add(2);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                            } else if (strIntentMain.equalsIgnoreCase("Maint 3")) {

                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                            } else if (strIntentMain.equalsIgnoreCase("Add Maintenance getPass list") || strIntentMain.equalsIgnoreCase("Maint -1")) {

                                statusList.add(0);
                                statusList.add(1);
                                statusList.add(2);
                                statusList.add(3);
                                statusList.add(4);
                                statusList.add(5);

                                ArrayList<Integer> getPassTypeList = new ArrayList<>();
                                getPassTypeList.add(2);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                            }
                        }else{
                            statusList.add(0);
                            statusList.add(1);
                            statusList.add(2);
                            statusList.add(3);
                            statusList.add(4);
                            statusList.add(5);

                            ArrayList<Integer> getPassTypeList = new ArrayList<>();
                            getPassTypeList.add(2);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), getPassTypeList, "-1", statusList);
                        }
                    }
                }
            }
        }
//        ArrayList<Integer> getPassTypeList = new ArrayList<>();
//        getPassTypeList.add(2);
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        getMaintenanceGetPassList(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),getPassTypeList,"-1",statusList);



        return view;
    }

    private void getEmployee() {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployees();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " -----------------------------------EMPLOYEE LIST---------------------------- " + response.body());
                            empList.clear();
//                            empIdList.clear();
                            empList = response.body();

                            assignMaintenanceEmpStaticList.clear();
                            assignMaintenanceEmpStaticList = empList;

                            for (int i = 0; i < assignMaintenanceEmpStaticList.size(); i++) {
                                assignMaintenanceEmpStaticList.get(i).setChecked(false);
                            }


//                            empNameList.add("All");
//                            empIdList.add(-1);
//
//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    empIdList.add(response.body().get(i).getEmpDeptId());
//                                    empNameList.add(response.body().get(i).getEmpFname()+" "+response.body().get(i).getEmpMname()+" "+response.body().get(i).getEmpSname());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
//                                spEmp.setAdapter(projectAdapter);
                            getAssignUser();

                            VisitorEmployeeAdapter adapter = new VisitorEmployeeAdapter(assignMaintenanceEmpStaticList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewFilter.setLayoutManager(mLayoutManager);
                            recyclerViewFilter.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewFilter.setAdapter(adapter);

                            // }
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
                public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAssignUser() {
        ArrayList<Employee> assignedEmpArray = new ArrayList<>();
        ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
        ArrayList<String> assignedEmpNameArray = new ArrayList<>();

        if (assignMaintenanceEmpStaticList != null) {
            if (assignMaintenanceEmpStaticList.size() > 0) {
                assignedEmpArray.clear();
                for (int i = 0; i < assignMaintenanceEmpStaticList.size(); i++) {
                    if (assignMaintenanceEmpStaticList.get(i).getChecked()) {
                        assignedEmpArray.add(assignMaintenanceEmpStaticList.get(i));
                        assignedEmpIdArray.add(assignMaintenanceEmpStaticList.get(i).getEmpId());
                        assignedEmpNameArray.add(assignMaintenanceEmpStaticList.get(i).getEmpFname() + " " + assignMaintenanceEmpStaticList.get(i).getEmpMname() + " " + assignMaintenanceEmpStaticList.get(i).getEmpSname());
                    }
                }
            }
            Log.e("ASSIGN EMP", "---------------------------------" + assignedEmpArray);
            Log.e("ASSIGN EMP SIZE", "---------------------------------" + assignedEmpArray.size());

            String empIds = assignedEmpIdArray.toString().trim();
            Log.e("ASSIGN EMP ID", "---------------------------------" + empIds);

            String a1 = "" + empIds.substring(1, empIds.length() - 1).replace("][", ",") + "";
            stringId = a1.replaceAll("\\s","");

            Log.e("ASSIGN EMP ID STRING", "---------------------------------" + stringId);
            Log.e("ASSIGN EMP ID STRING1", "---------------------------------" + a1);

//            String empName=assignedEmpNameArray.toString().trim();
////            Log.e("ASSIGN EMP NAME","---------------------------------"+empName);
////
////            stringName = ""+empName.substring(1, empName.length()-1).replace("][", ",")+"";
////
////            Log.e("ASSIGN EMP NAME STRING","---------------------------------"+stringName);
////            edEmployee.setText(stringName);
        }
    }


    private void getMaintenanceGetPassList(String formatDate, String toDate, ArrayList<Integer> getPassType, String empIds, ArrayList<Integer> status) {
        Log.e("PARAMETER","            FROM DATE       "+ formatDate        +"          TO DATE     " +   toDate  +"       GEAT PASS TYPE   " +  getPassType  +"            EMP ID   "+   empIds  +"             STATUS"  +status);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<VisitorList>> listCall = Constants.myInterface.getVisitorGatepassListInDate(formatDate,toDate,getPassType,empIds,status);
            listCall.enqueue(new Callback<ArrayList<VisitorList>>() {
                @Override
                public void onResponse(Call<ArrayList<VisitorList>> call, Response<ArrayList<VisitorList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("MAINTENANCE LIST : ", " - " + response.body());
                            visitorList.clear();
                            visitorList = response.body();

                             adapter = new MaintenanceGatePassListAdapter(visitorList, getContext(),loginUser,syncArray);
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
                public void onFailure(Call<ArrayList<VisitorList>> call, Throwable t) {
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
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            new FilterDialog(getContext()).show();
        }
    }


    public class FilterDialog extends Dialog {

        EditText edFromDate, edToDate;
        TextView tvFromDate, tvToDate, tvType, tvEmp;
        ImageView ivClose;
        CardView cardViewDept,cardViewEmp;
        CheckBox cbAll;
        String DateTo;
         Spinner spDept,spEmp,spStatus;
        private VisitorEmployeeAdapter mAdapter;

        ArrayList<String> empNameList = new ArrayList<>();
        ArrayList<Integer> empIdList = new ArrayList<>();


        public FilterDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_visitor_gate_pass_filter);
            //dialog_visitor_gate_pass_filter
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            edFromDate = findViewById(R.id.edFromDate);
            edToDate = findViewById(R.id.edToDate);
            tvFromDate = findViewById(R.id.tvFromDate);
            tvToDate = findViewById(R.id.tvToDate);
            Button btnFilter = findViewById(R.id.btnFilter);
            spDept = findViewById(R.id.spDept);
            spStatus = findViewById(R.id.spStatus);
             //spEmp = findViewById(R.id.spEmp);
            LinearLayout llEmp = findViewById(R.id.llEmp);
            ivClose = findViewById(R.id.ivClose);
            tvType = findViewById(R.id.tvType);
            tvEmp = findViewById(R.id.tvEmp);
            cardViewDept = findViewById(R.id.cardViewDept);
            cardViewEmp = findViewById(R.id.cardViewEmp);
            cbAll = findViewById(R.id.cbAll);
            recyclerViewFilter = findViewById(R.id.recyclerViewFilter);

            if(syncArray!=null) {
                for (int j = 0; j < syncArray.size(); j++) {
                    if(syncArray.get(j).getSettingKey().equals("Admin")){
                        if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                            cardViewEmp.setVisibility(View.VISIBLE);
                            //cardViewDept.setVisibility(View.VISIBLE);
                        }else{
                            cardViewEmp.setVisibility(View.GONE);
                           // cardViewDept.setVisibility(View.GONE);
                        }
                    }
                }
            }

            ArrayList<String> typeArray = new ArrayList<>();
            final ArrayList<Integer> typeIdArray = new ArrayList<>();;
            typeArray.add("All");
            typeArray.add("Visitor");
            typeArray.add("Maintenance");

            typeIdArray.add(-1);
            typeIdArray.add(1);
            typeIdArray.add(2);

            ArrayAdapter<String> spTypeAdapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item,typeArray);
            spDept.setAdapter(spTypeAdapter);

            ArrayList<String> statusArray = new ArrayList<>();
            final ArrayList<Integer> statusIdArray = new ArrayList<>();
            statusArray.add("All");
            statusArray.add("Pending");
            statusArray.add("Approve");
            statusArray.add("Rejected");
            statusArray.add("Allowed to Enter");
            statusArray.add("Close Metting");
            statusArray.add("Out From Factory");

            statusIdArray.add(-1);
            statusIdArray.add(0);
            statusIdArray.add(1);
            statusIdArray.add(2);
            statusIdArray.add(3);
            statusIdArray.add(4);
            statusIdArray.add(5);

            ArrayAdapter<String> spStatusAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, statusArray);
            spStatus.setAdapter(spStatusAdapter);



            try {
                mAdapter = new VisitorEmployeeAdapter(assignMaintenanceEmpStaticList, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerViewFilter.setLayoutManager(mLayoutManager);
                recyclerViewFilter.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFilter.setAdapter(mAdapter);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


            cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        Log.e("LIST","------------------------"+assignMaintenanceEmpStaticList);
                        for(int k=0;k<assignMaintenanceEmpStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignMaintenanceEmpStaticList.get(k));
                            assignMaintenanceEmpStaticList.get(k).setChecked(true);

                        }

                    }else{
                        for(int k=0;k<assignMaintenanceEmpStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignMaintenanceEmpStaticList.get(k));
                            assignMaintenanceEmpStaticList.get(k).setChecked(false);

                        }
                    }

                    mAdapter = new VisitorEmployeeAdapter(assignMaintenanceEmpStaticList, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewFilter.setLayoutManager(mLayoutManager);
                    recyclerViewFilter.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewFilter.setAdapter(mAdapter);
                }
            });


            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String currentDate = formatter.format(todayDate);
            Log.e("Mytag","todayString"+currentDate);

            edToDate.setText(currentDate);

            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;
            try {
                ToDate = formatter1.parse(currentDate);//catch exception
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            DateTo = formatter2.format(ToDate);
            tvToDate.setText(DateTo);

//            final String frmDate = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_SP_FROM_DATE);
//            String toDate = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_SP_TO_DATE);

            // tvFromDate.setText("" + frmDate);
            //  tvToDate.setText("" + toDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

         /*   try {

                Date d1 = sdf.parse(frmDate);
                Date d2 = sdf.parse(toDate);

                edFromDate.setText("" + sdf1.format(d1.getTime()));
                edToDate.setText("" + sdf1.format(d2.getTime()));

            } catch (Exception e) {
                e.printStackTrace();

                edFromDate.setText("" + frmDate);
                edToDate.setText("" + toDate);

            }*/



          /*  llEmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (staticAllMenu != null) {
                        showMenuDialog(staticAllMenu);
                    } else {
                        Toast.makeText(getContext(), "Reload Data", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


          edFromDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (fromDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(fromDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), fromDateListener, yr, mn, dy);
                    dialog.show();
                }
            });

            edToDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (toDateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(toDateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), toDateListener, yr, mn, dy);
                    dialog.show();
                }
            });


            btnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edFromDate.getText().toString().isEmpty()) {
                        edFromDate.setError("Select From Date");
                        edFromDate.requestFocus();
                    } else if (edToDate.getText().toString().isEmpty()) {
                        edToDate.setError("Select To Date");
                        edToDate.requestFocus();
                    } else {

                        getAssignUser();
                        dismiss();

                        final int getPassType = typeIdArray.get(spDept.getSelectedItemPosition());
                        final int getPassStatus = statusIdArray.get(spStatus.getSelectedItemPosition());

                        ArrayList<Integer> getPassTypeList = new ArrayList<>();
                        getPassTypeList.add(2);

                        ArrayList<Integer> statusList1 = new ArrayList<>();

                        if(syncArray!=null) {
                            for (int j = 0; j < syncArray.size(); j++) {
                                if (syncArray.get(j).getSettingKey().equals("Security")) {
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                                        if(getPassStatus==-1) {
                                            statusList1.add(0);
                                            statusList1.add(1);
                                            statusList1.add(2);
                                            statusList1.add(3);
                                            statusList1.add(4);
                                            statusList1.add(5);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==0)
                                        {
                                            statusList1.add(0);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==1)
                                        {

                                            statusList1.add(1);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==2)
                                        {

                                            statusList1.add(2);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==3)
                                        {

                                            statusList1.add(3);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==4)
                                        {

                                            statusList1.add(4);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }else if(getPassStatus==5)
                                        {

                                            statusList1.add(5);

                                            ArrayList<Integer> getPassTypeList1 = new ArrayList<>();
                                            getPassTypeList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList1, "-1", statusList1);
                                        }
                                    }
                                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                                        if(getPassStatus==-1) {
                                            statusList1.add(0);
                                            statusList1.add(1);
                                            statusList1.add(2);
                                            statusList1.add(3);
                                            statusList1.add(4);
                                            statusList1.add(5);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);
                                        }else if(getPassStatus==0)
                                        {
                                            statusList1.add(0);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);

                                        }else if(getPassStatus==1)
                                        {

                                            statusList1.add(1);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);
                                        }else if(getPassStatus==2)
                                        {

                                            statusList1.add(2);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);

                                        }else if(getPassStatus==3)
                                        {

                                            statusList1.add(3);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);

                                        }else if(getPassStatus==4)
                                        {

                                            statusList1.add(4);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);

                                        }else if(getPassStatus==5)
                                        {

                                            statusList1.add(5);

                                            ArrayList<Integer> getPassTypeList2 = new ArrayList<>();
                                            getPassTypeList2.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList2, String.valueOf(loginUser.getEmpId()), statusList1);

                                        }
                                    }
                                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                                        if(getPassStatus==-1) {
                                            statusList1.add(0);
                                            statusList1.add(1);
                                            statusList1.add(2);
                                            statusList1.add(3);
                                            statusList1.add(4);
                                            statusList1.add(5);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);
                                        }else if(getPassStatus==0)
                                        {
                                            statusList1.add(0);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);
                                        }else if(getPassStatus==1)
                                        {
                                            statusList1.add(1);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);
                                        }else if(getPassStatus==2)
                                        {
                                            statusList1.add(2);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);

                                        }else if(getPassStatus==3)
                                        {

                                            statusList1.add(3);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);

                                        }else if(getPassStatus==4)
                                        {

                                            statusList1.add(4);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);

                                        }else if(getPassStatus==5)
                                        {
                                            statusList1.add(5);

                                            String fromDate = tvFromDate.getText().toString();
                                            String toDate = tvToDate.getText().toString();

                                            getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);

                                        }

                                    }
                                }
                            }
                        }

//                        String fromDate = tvFromDate.getText().toString();
//                        String toDate = tvToDate.getText().toString();
//
//                        getMaintenanceGetPassList(fromDate, toDate, getPassTypeList, stringId, statusList1);
//                        dismiss();


                    }
                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }

//      private void getEmployee() {
//            if (Constants.isOnline(getActivity())) {
//                final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//                commonDialog.show();
//
//                Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployees();
//                listCall.enqueue(new Callback<ArrayList<Employee>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
//                        try {
//                            if (response.body() != null) {
//
//                                Log.e("EMPLOYEE LIST : ", " -----------------------------------EMPLOYEE LIST---------------------------- " + response.body());
//                                empNameList.clear();
//                                empIdList.clear();
//
//                                empNameList.add("All");
//                                empIdList.add(-1);
//
//                                if (response.body().size() > 0) {
//                                    for (int i = 0; i < response.body().size(); i++) {
//                                        empIdList.add(response.body().get(i).getEmpDeptId());
//                                        empNameList.add(response.body().get(i).getEmpFname()+" "+response.body().get(i).getEmpMname()+" "+response.body().get(i).getEmpSname());
//                                    }
//
//                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
//                                    spEmp.setAdapter(projectAdapter);
//
//                                }
//
//                                commonDialog.dismiss();
//
//                            } else {
//                                commonDialog.dismiss();
//                                Log.e("Data Null : ", "-----------");
//                            }
//                        } catch (Exception e) {
//                            commonDialog.dismiss();
//                            Log.e("Exception : ", "-----------" + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
//                        commonDialog.dismiss();
//                        Log.e("onFailure : ", "-----------" + t.getMessage());
//                        t.printStackTrace();
//                    }
//                });
//            } else {
//                Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
//            }
//        }

        DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edFromDate.setText(dd + "-" + mm + "-" + yyyy);
                tvFromDate.setText(yyyy + "-" + mm + "-" + dd);
                //CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_SP_FROM_DATE, yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                fromDateMillis = calendar.getTimeInMillis();
            }
        };

        DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edToDate.setText(dd + "-" + mm + "-" + yyyy);
                tvToDate.setText(yyyy + "-" + mm + "-" + dd);
                // CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_SP_TO_DATE, yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                toDateMillis = calendar.getTimeInMillis();
            }
        };
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);

        SearchView searchView = (SearchView) item.getActionView();
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorWhite));
        ImageView v = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        v.setImageResource(R.drawable.ic_search_white); //Changing the image

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FilterSearch(charSequence.toString());
                // empAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchView.setQueryHint("search");
    }

    private void FilterSearch(String s) {
        temp = new ArrayList();
        for (VisitorList d : visitorList) {
            if (d.getPersonName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
