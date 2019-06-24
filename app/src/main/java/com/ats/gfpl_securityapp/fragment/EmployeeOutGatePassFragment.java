package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.ats.gfpl_securityapp.adapter.EmployeeGatePassListAdapter;
import com.ats.gfpl_securityapp.adapter.VisitorEmployeeAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.OutEmpInterface;
import com.ats.gfpl_securityapp.model.EmpGatePass;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.gfpl_securityapp.fragment.EmployeeFragment.loginUser;
import static com.ats.gfpl_securityapp.fragment.EmployeeFragment.syncArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeOutGatePassFragment extends Fragment implements OutEmpInterface,View.OnClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
//    Login loginUser,loginUserMain;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    // ArrayList<Sync> syncArray = new ArrayList<>();

    String stringId;
    ArrayList<Employee> empList1 = new ArrayList<>();
    RecyclerView recyclerViewFilter;
    public static ArrayList<Employee> assignEmpOutStaticList = new ArrayList<>();
    ArrayList<EmpGatePass> empList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_out_gate_pass, container, false);

        //getActivity().setTitle("Employee Out");

        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(this);

//        try {
//            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
//            Gson gson = new Gson();
//            loginUser = gson.fromJson(userStr, Login.class);
//            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        try {
//            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.MAIN_KEY_USER);
//            Gson gson = new Gson();
//            loginUserMain = gson.fromJson(userStr, Login.class);
//            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUserMain);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

//        try {
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            Gson gson = new Gson();
//            String json = prefs.getString("Sync", null);
//            Type type = new TypeToken<ArrayList<Sync>>() {}.getType();
//            syncArray= gson.fromJson(json, type);
//
//            Log.e("SYNC EMP : ", "--------USER-------" + syncArray);
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        getEmployee();

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);

        ArrayList<Integer> deptList = new ArrayList<>();
        deptList.add(-1);

        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Security")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        ArrayList<Integer> getPassTypeList = new ArrayList<>();
                        getPassTypeList.add(1);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        getEmployeeGetPassList(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),deptList,"-1",statusList);

                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        ArrayList<Integer> getPassTypeList = new ArrayList<>();
                        getPassTypeList.add(1);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        getEmployeeGetPassList(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),deptList, String.valueOf(loginUser.getEmpId()),statusList);

                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                        ArrayList<Integer> getPassTypeList = new ArrayList<>();
                        getPassTypeList.add(1);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        getEmployeeGetPassList(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),deptList,"-1",statusList);

                    }
                }
            }
        }

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
                            empList1.clear();
//                            empIdList.clear();
                            empList1 = response.body();

                            assignEmpOutStaticList.clear();
                            assignEmpOutStaticList = empList1;

                            for (int i = 0; i < assignEmpOutStaticList.size(); i++) {
                                assignEmpOutStaticList.get(i).setChecked(false);
                            }

                            getAssignUser();

                            VisitorEmployeeAdapter adapter = new VisitorEmployeeAdapter(assignEmpOutStaticList, getContext());
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

        if (assignEmpOutStaticList != null) {
            if (assignEmpOutStaticList.size() > 0) {
                assignedEmpArray.clear();
                for (int i = 0; i < assignEmpOutStaticList.size(); i++) {
                    if (assignEmpOutStaticList.get(i).getChecked()) {
                        assignedEmpArray.add(assignEmpOutStaticList.get(i));
                        assignedEmpIdArray.add(assignEmpOutStaticList.get(i).getEmpId());
                        assignedEmpNameArray.add(assignEmpOutStaticList.get(i).getEmpFname() + " " + assignEmpOutStaticList.get(i).getEmpMname() + " " + assignEmpOutStaticList.get(i).getEmpSname());
                    }
                }
            }
            Log.e("ASSIGN EMP", "---------------------------------" + assignedEmpArray);
            Log.e("ASSIGN EMP SIZE", "---------------------------------" + assignedEmpArray.size());

            String empIds = assignedEmpIdArray.toString().trim();
            Log.e("ASSIGN EMP ID", "---------------------------------" + empIds);

            stringId = "" + empIds.substring(1, empIds.length() - 1).replace("][", ",") + "";

            Log.e("ASSIGN EMP ID STRING", "---------------------------------" + stringId);

//            String empName=assignedEmpNameArray.toString().trim();
////            Log.e("ASSIGN EMP NAME","---------------------------------"+empName);
////
////            stringName = ""+empName.substring(1, empName.length()-1).replace("][", ",")+"";
////
////            Log.e("ASSIGN EMP NAME STRING","---------------------------------"+stringName);
////            edEmployee.setText(stringName);
        }
    }

    private void getEmployeeGetPassList(String formatDate, String toDate,  ArrayList<Integer> deptList, String emp, ArrayList<Integer> statusList) {
        Log.e("PARAMETER","            FROM DATE       "+ formatDate        +"          TO DATE     " +   toDate  +"       Dept   " +  deptList  +"            EMP ID   "+   emp  +"             STATUS"  +statusList);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<EmpGatePass>> listCall = Constants.myInterface.getEmpGatepassListWithDateFilter(formatDate,toDate,deptList,emp,statusList);
            listCall.enqueue(new Callback<ArrayList<EmpGatePass>>() {
                @Override
                public void onResponse(Call<ArrayList<EmpGatePass>> call, Response<ArrayList<EmpGatePass>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " - " + response.body());
                            empList.clear();
                            empList = response.body();

                            EmployeeGatePassListAdapter adapter = new EmployeeGatePassListAdapter(empList, getContext(),syncArray,loginUser);
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
                public void onFailure(Call<ArrayList<EmpGatePass>> call, Throwable t) {
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

    @Override
    public void fragmentBecameVisible() {

    }


    public class FilterDialog extends Dialog {

        EditText edFromDate, edToDate;
        TextView tvFromDate, tvToDate;
        Spinner spDept, spType;
        ImageView ivClose;
        LinearLayout llSup;
        String DateTo;
        CheckBox cbAll;
        CardView cardViewDept,cardViewEmp;
        private VisitorEmployeeAdapter mAdapter;


        public FilterDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_emp_gatepass);
            // dialog_employee_gate_pass_filter
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
            ivClose = findViewById(R.id.ivClose);
            spDept = findViewById(R.id.spDept);
            llSup = findViewById(R.id.llSup);
            cardViewDept = findViewById(R.id.cardViewDept);
            cardViewEmp = findViewById(R.id.cardViewEmp);
            recyclerViewFilter = findViewById(R.id.recyclerViewFilter);
            cbAll = findViewById(R.id.cbAll);

            if(syncArray!=null) {
                for (int j = 0; j < syncArray.size(); j++) {
                    if(syncArray.get(j).getSettingKey().equals("Admin")){
                        if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                            cardViewEmp.setVisibility(View.VISIBLE);
                            cardViewDept.setVisibility(View.VISIBLE);
                        }else{
                            cardViewEmp.setVisibility(View.GONE);
                            cardViewDept.setVisibility(View.GONE);
                        }
                    }
                }
            }

            ArrayList<String> typeArray = new ArrayList<>();
            final ArrayList<Integer> typeIdArray = new ArrayList<>();
            typeArray.add("All");
            typeArray.add("Visitor");
            typeArray.add("Maintenance");

            typeIdArray.add(-1);
            typeIdArray.add(1);
            typeIdArray.add(2);

            ArrayAdapter<String> spTypeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, typeArray);
            spDept.setAdapter(spTypeAdapter);

            try {
                mAdapter = new VisitorEmployeeAdapter(assignEmpOutStaticList, getActivity());
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
                        Log.e("LIST","------------------------"+assignEmpOutStaticList);
                        for(int k=0;k<assignEmpOutStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignEmpOutStaticList.get(k));
                            assignEmpOutStaticList.get(k).setChecked(true);

                        }

                    }else{
                        for(int k=0;k<assignEmpOutStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignEmpOutStaticList.get(k));
                            assignEmpOutStaticList.get(k).setChecked(false);

                        }
                    }

                    mAdapter = new VisitorEmployeeAdapter(assignEmpOutStaticList, getActivity());
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
                    final int getDept = typeIdArray.get(spDept.getSelectedItemPosition());

                    if (edFromDate.getText().toString().isEmpty()) {
                        edFromDate.setError("Select From Date");
                        edFromDate.requestFocus();
                    } else if (edToDate.getText().toString().isEmpty()) {
                        edToDate.setError("Select To Date");
                        edToDate.requestFocus();
                    } else {

//                        TextView viewType = (TextView) spDept.getSelectedView();
//                        viewType.setError(null);
                        getAssignUser();
                        dismiss();

                        ArrayList<Integer> getPassDeptList = new ArrayList<>();
                        getPassDeptList.add(getDept);

                        if(syncArray!=null) {
                            for (int j = 0; j < syncArray.size(); j++) {
                                if (syncArray.get(j).getSettingKey().equals("Security")) {
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(1);

                                        ArrayList<Integer> getPassDeptList1 = new ArrayList<>();
                                        getPassDeptList1.add(-1);

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList1, "-1", statusList);
                                    }
                                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(1);

                                        ArrayList<Integer> getPassDeptList2 = new ArrayList<>();
                                        getPassDeptList2.add(-1);

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList2, String.valueOf(loginUser.getEmpId()), statusList);
                                    }
                                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(1);

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList, stringId, statusList);
                                    }
                                }
                            }
                        }

//                        ArrayList<Integer> statusList = new ArrayList<>();
//                        statusList.add(0);
//
//                        String fromDate = tvFromDate.getText().toString();
//                        String toDate = tvToDate.getText().toString();
//
//                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList, stringId, statusList);

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

}
