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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.EmployeeGatePassListAdapter;
import com.ats.gfpl_securityapp.adapter.VisitorEmployeeAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.CloseEmpInterface;
import com.ats.gfpl_securityapp.model.Department;
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
import static com.ats.gfpl_securityapp.fragment.EmployeeFragment.strIntentMain;
import static com.ats.gfpl_securityapp.fragment.EmployeeFragment.syncArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeCloseGatePassFragment extends Fragment implements CloseEmpInterface,View.OnClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
//    Login loginUser,loginUserMain;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    // ArrayList<Sync> syncArray = new ArrayList<>();

    String stringId;
    ArrayList<Employee> empList1 = new ArrayList<>();
    RecyclerView recyclerViewFilter;
    public static ArrayList<Employee> assignEmpCloseStaticList = new ArrayList<>();

    ArrayList<EmpGatePass> empList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_close_gate_pass, container, false);

        //getActivity().setTitle("Employee Pending");

        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(this);

        getEmployee();

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(2);

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

//                        ArrayList<Integer> getPassTypeList = new ArrayList<>();
//                        getPassTypeList.add(1);

                        ArrayList<Integer> deptList1 = new ArrayList<>();
                        deptList1.add(loginUser.getEmpDeptId());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        getEmployeeGetPassList(sdf.format(System.currentTimeMillis()),sdf.format(System.currentTimeMillis()),deptList1, String.valueOf(loginUser.getEmpId()),statusList);

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

                            assignEmpCloseStaticList.clear();
                            assignEmpCloseStaticList = empList1;

                            for (int i = 0; i < assignEmpCloseStaticList.size(); i++) {
                                assignEmpCloseStaticList.get(i).setChecked(false);
                            }

                            getAssignUser();

                            VisitorEmployeeAdapter adapter = new VisitorEmployeeAdapter(assignEmpCloseStaticList, getContext());
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

        if (assignEmpCloseStaticList != null) {
            if (assignEmpCloseStaticList.size() > 0) {
                assignedEmpArray.clear();
                for (int i = 0; i < assignEmpCloseStaticList.size(); i++) {
                    if (assignEmpCloseStaticList.get(i).getChecked()) {
                        assignedEmpArray.add(assignEmpCloseStaticList.get(i));
                        assignedEmpIdArray.add(assignEmpCloseStaticList.get(i).getEmpId());
                        assignedEmpNameArray.add(assignEmpCloseStaticList.get(i).getEmpFname() + " " + assignEmpCloseStaticList.get(i).getEmpMname() + " " + assignEmpCloseStaticList.get(i).getEmpSname());
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

                            Log.e("EMPLOYEE LIST CLOSE: ", " - " + response.body());
                            empList.clear();
                            //empList = response.body();

                            if(strIntentMain!=null)
                            {
                                Log.e("Employe Type","-----------------------------"+strIntentMain);
                                if(strIntentMain.equalsIgnoreCase("Temp = 1"))
                                {
                                    for(int i=0;i<response.body().size();i++)
                                    {
                                        if(response.body().get(i).getGatePassSubType()==1)
                                        {
                                            EmpGatePass model= new EmpGatePass(response.body().get(i).getGatepassEmpId(),response.body().get(i).getEmpDateOut(),response.body().get(i).getEmpDateIn(),response.body().get(i).getUserId(),response.body().get(i).getUserName(),response.body().get(i).getEmpId(),response.body().get(i).getEmpName(),response.body().get(i).getGatePassType(),response.body().get(i).getGatePassSubType(),response.body().get(i).getPurposeId(),response.body().get(i).getPurposeText(),response.body().get(i).getPurposeRemark(),response.body().get(i).getSecurityIdOut(),response.body().get(i).getSecurityIdIn(),response.body().get(i).getNoOfHr(),response.body().get(i).getOutTime(),response.body().get(i).getInTime(),response.body().get(i).getNewOutTime(),response.body().get(i).getNewInTime(),response.body().get(i).getActualTimeDifference(),response.body().get(i).getGatePassStatus(),response.body().get(i).getDelStatus(),response.body().get(i).getIsUsed(),response.body().get(i).getExInt1(),response.body().get(i).getExInt2(),response.body().get(i).getExInt3(),response.body().get(i).getExVar1(),response.body().get(i).getExVar2(),response.body().get(i).getExVar3());
                                            empList.add(model);
                                        }
                                    }
                                }else if(strIntentMain.equalsIgnoreCase("Day = 2"))
                                {
                                    for(int i=0;i<response.body().size();i++)
                                    {
                                        if(response.body().get(i).getGatePassSubType()==2)
                                        {
                                            EmpGatePass model= new EmpGatePass(response.body().get(i).getGatepassEmpId(),response.body().get(i).getEmpDateOut(),response.body().get(i).getEmpDateIn(),response.body().get(i).getUserId(),response.body().get(i).getUserName(),response.body().get(i).getEmpId(),response.body().get(i).getEmpName(),response.body().get(i).getGatePassType(),response.body().get(i).getGatePassSubType(),response.body().get(i).getPurposeId(),response.body().get(i).getPurposeText(),response.body().get(i).getPurposeRemark(),response.body().get(i).getSecurityIdOut(),response.body().get(i).getSecurityIdIn(),response.body().get(i).getNoOfHr(),response.body().get(i).getOutTime(),response.body().get(i).getInTime(),response.body().get(i).getNewOutTime(),response.body().get(i).getNewInTime(),response.body().get(i).getActualTimeDifference(),response.body().get(i).getGatePassStatus(),response.body().get(i).getDelStatus(),response.body().get(i).getIsUsed(),response.body().get(i).getExInt1(),response.body().get(i).getExInt2(),response.body().get(i).getExInt3(),response.body().get(i).getExVar1(),response.body().get(i).getExVar2(),response.body().get(i).getExVar3());
                                            empList.add(model);
                                        }
                                    }
                                }else if(strIntentMain.equalsIgnoreCase("Employee gate pass list"))
                                {
                                    empList = response.body();
                                }else if(strIntentMain.equalsIgnoreCase("OutEmp")|| strIntentMain.equalsIgnoreCase("EmpTotal"))
                                {
                                    empList = response.body();
                                }
                            }else{
                                empList = response.body();
                            }

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
        //Spinner spDept, spType;
        ImageView ivClose;
        LinearLayout llSup;
        String DateTo;
        CheckBox cbAll;
        CardView cardViewDept,cardViewEmp;
        private VisitorEmployeeAdapter mAdapter;

        TextView spDept,spEmp,tvEmpId,tvDeptId;

        int deptId;
        String employee;
        Dialog dialog;
       DepartmentListDialogAdapter deptAdapter;
        EmployeeListDialogAdapter empAdapter;

        ArrayList<String> deptNameList = new ArrayList<>();
        ArrayList<Integer> deptIdList = new ArrayList<>();
        ArrayList<Department> deptList = new ArrayList<>();

        ArrayList<String> empNameList = new ArrayList<>();
        ArrayList<Integer> empIdList = new ArrayList<>();
        ArrayList<Employee> empListSearch = new ArrayList<>();


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
            spEmp = findViewById(R.id.spEmp);
            tvEmpId = findViewById(R.id.tvEmpId);
            tvDeptId = findViewById(R.id.tvDeptId);
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

            spEmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog1();
                }
            });

            spDept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });


            Department department=new Department(-1,0,"All","","",1,1,0,"",1,1,1,1,1,1);
            deptList.add(department);

            Log.e("PARAMETER","---------------------DEPT--------------------"+deptList);

            getDepartment();

//            ArrayList<String> typeArray = new ArrayList<>();
//            final ArrayList<Integer> typeIdArray = new ArrayList<>();
//            typeArray.add("All");
//            typeArray.add("Visitor");
//            typeArray.add("Maintenance");
//
//            typeIdArray.add(-1);
//            typeIdArray.add(1);
//            typeIdArray.add(2);
//
//            ArrayAdapter<String> spTypeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, typeArray);
//            spDept.setAdapter(spTypeAdapter);
//
//            try {
//                mAdapter = new VisitorEmployeeAdapter(assignEmpCloseStaticList, getActivity());
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                recyclerViewFilter.setLayoutManager(mLayoutManager);
//                recyclerViewFilter.setItemAnimator(new DefaultItemAnimator());
//                recyclerViewFilter.setAdapter(mAdapter);
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }

            cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        Log.e("LIST","------------------------"+assignEmpCloseStaticList);
                        for(int k=0;k<assignEmpCloseStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignEmpCloseStaticList.get(k));
                            assignEmpCloseStaticList.get(k).setChecked(true);

                        }

                    }else{
                        for(int k=0;k<assignEmpCloseStaticList.size();k++)
                        {
                            Log.e("LIST SET","------------------------"+assignEmpCloseStaticList.get(k));
                            assignEmpCloseStaticList.get(k).setChecked(false);

                        }
                    }

                    mAdapter = new VisitorEmployeeAdapter(assignEmpCloseStaticList, getActivity());
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
                   // final int getDept = typeIdArray.get(spDept.getSelectedItemPosition());

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

                        String empName=spEmp.getText().toString();
                        String deptName=spDept.getText().toString();

                        int empId = -1,deptId = -1;
                        try {
                            empId = Integer.parseInt(tvEmpId.getText().toString());
                            deptId = Integer.parseInt(tvDeptId.getText().toString());

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        ArrayList<Integer> getPassDeptList = new ArrayList<>();
                        getPassDeptList.add(deptId);

                        if(syncArray!=null) {
                            for (int j = 0; j < syncArray.size(); j++) {
                                if (syncArray.get(j).getSettingKey().equals("Security")) {
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(2);

                                        ArrayList<Integer> getPassDeptList1 = new ArrayList<>();
                                        getPassDeptList1.add(-1);

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList1, "-1", statusList);
                                    }
                                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(2);

                                        ArrayList<Integer> getPassDeptList2 = new ArrayList<>();
                                        getPassDeptList2.add(loginUser.getEmpDeptId());

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList2, "-1", statusList);
                                    }
                                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                                        ArrayList<Integer> statusList = new ArrayList<>();
                                        statusList.add(2);

                                        String fromDate = tvFromDate.getText().toString();
                                        String toDate = tvToDate.getText().toString();

                                        getEmployeeGetPassList(fromDate, toDate, getPassDeptList, String.valueOf(empId), statusList);
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

        private void showDialog1() {
            dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
            dialog.setContentView(v);
            dialog.setCancelable(true);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
            EditText edSearch = dialog.findViewById(R.id.edSearch);

            empAdapter = new EmployeeListDialogAdapter(empListSearch, getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvCustomerList.setLayoutManager(mLayoutManager);
            rvCustomerList.setItemAnimator(new DefaultItemAnimator());
            rvCustomerList.setAdapter(empAdapter);

            edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if (empAdapter != null) {
                            filterEmp(editable.toString());
                        }
                    } catch (Exception e) {
                    }
                }
            });

            dialog.show();
        }

        private void filterEmp(String text) {
            ArrayList<Employee> temp = new ArrayList();
            for (Employee d : empListSearch) {
                if (d.getEmpFname().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
            //update recyclerview
            empAdapter.updateList(temp);
        }

        private void showDialog() {
            dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
            dialog.setContentView(v);
            dialog.setCancelable(true);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
            EditText edSearch = dialog.findViewById(R.id.edSearch);

            deptAdapter = new DepartmentListDialogAdapter(deptList, getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvCustomerList.setLayoutManager(mLayoutManager);
            rvCustomerList.setItemAnimator(new DefaultItemAnimator());
            rvCustomerList.setAdapter(deptAdapter);

            edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        if (deptAdapter != null) {
                            filterDept(editable.toString());
                        }
                    } catch (Exception e) {
                    }
                }
            });

            dialog.show();
        }

        private void filterDept(String text) {
            ArrayList<Department> temp = new ArrayList();
            for (Department d : deptList) {
                if (d.getEmpDeptName().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }
            }
            //update recyclerview
            deptAdapter.updateList(temp);
        }

        public class DepartmentListDialogAdapter extends RecyclerView.Adapter<DepartmentListDialogAdapter.MyViewHolder> {

            private ArrayList<Department> custList;
            private Context context;

            public DepartmentListDialogAdapter(ArrayList<Department> custList, Context context) {
                this.custList = custList;
                this.context = context;
            }

            public class MyViewHolder extends RecyclerView.ViewHolder {
                public TextView tvName, tvAddress;
                public LinearLayout linearLayout;

                public MyViewHolder(View view) {
                    super(view);
                    tvName = view.findViewById(R.id.tvName);
                    tvAddress = view.findViewById(R.id.tvAddress);
                    linearLayout = view.findViewById(R.id.linearLayout);
                }
            }


            @NonNull
            @Override
            public DepartmentListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.adapter_department_dialog, viewGroup, false);

                return new DepartmentListDialogAdapter.MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull final DepartmentListDialogAdapter.MyViewHolder myViewHolder, int i) {
                final Department model = custList.get(i);

                myViewHolder.tvName.setText(model.getEmpDeptName());
                //holder.tvAddress.setText(model.getCustAddress());

                myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        spDept.setText(""+model.getEmpDeptName());
                        tvDeptId.setText(""+model.getEmpDeptId());
                        deptId= Integer.parseInt(tvDeptId.getText().toString());
                        getEmployeeSearch(deptId);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return custList.size();
            }

            public void updateList(ArrayList<Department> list) {
                custList = list;
                notifyDataSetChanged();
            }

        }

        private void getEmployeeSearch(final int deptId) {
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
                                empNameList.clear();
                                empIdList.clear();

                                empNameList.add("All");
                                empIdList.add(-1);

                                if (response.body().size() > 0) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        if (response.body().get(i).getEmpDeptId() == deptId) {

                                            // empIdList.add(response.body().get(i).getEmpDeptId());
                                            // empNameList.add(response.body().get(i).getEmpFname() + " " + response.body().get(i).getEmpMname() + " " + response.body().get(i).getEmpSname());

                                            Employee employee = new Employee(response.body().get(i).getEmpId(),response.body().get(i).getEmpDsc(),response.body().get(i).getEmpCode(),response.body().get(i).getCompanyId(),response.body().get(i).getEmpCatId(),response.body().get(i).getEmpTypeId(),response.body().get(i).getEmpDeptId(),response.body().get(i).getLocId(),response.body().get(i).getEmpFname(),response.body().get(i).getEmpMname(),response.body().get(i).getEmpSname(),response.body().get(i).getEmpPhoto(),response.body().get(i).getEmpMobile1(),response.body().get(i).getEmpMobile2(),response.body().get(i).getEmpEmail(),response.body().get(i).getEmpAddressTemp(),response.body().get(i).getEmpAddressPerm(),response.body().get(i).getEmpBloodgrp(),response.body().get(i).getEmpEmergencyPerson1(),response.body().get(i).getEmpEmergencyNo1(),response.body().get(i).getEmpEmergencyPerson2(),response.body().get(i).getEmpEmergencyNo2(),response.body().get(i).getEmpRatePerhr(),response.body().get(i).getEmpJoiningDate(),response.body().get(i).getEmpPrevExpYrs(),response.body().get(i).getEmpPrevExpMonths(),response.body().get(i).getEmpLeavingDate(),response.body().get(i).getEmpLeavingReason(),response.body().get(i).getLockPeriod(),response.body().get(i).getTermConditions(),response.body().get(i).getSalaryId(),response.body().get(i).getDelStatus(),response.body().get(i).getIsActive(),response.body().get(i).getMakerUserId(),response.body().get(i).getMakerEnterDatetime(),response.body().get(i).getExInt1(),response.body().get(i).getExInt2(),response.body().get(i).getExInt3(),response.body().get(i).getExVar1(),response.body().get(i).getExVar2(),response.body().get(i).getExVar3());
                                            empListSearch.add(employee);
                                        }
                                    }

//                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
//                                    spEmp.setAdapter(projectAdapter);


//                                    if(deptId==1)
//                                    {
//                                        Log.e("DEPT 1","------------------------------");
//                                        empIdList.add(response.body().get(i).getEmpId());
//                                        empNameList.add(response.body().get(i).getEmpFname() + " " + response.body().get(i).getEmpMname() + " " + response.body().get(i).getEmpSname());
//                                    }else {
//                                        Log.e("DEPT OTHER","------------------------------");
//                                        if (deptId.equals(response.body().get(i).getEmpDeptId())) {
//                                            empIdList.add(response.body().get(i).getEmpId());
//                                            empNameList.add(response.body().get(i).getEmpFname() + " " + response.body().get(i).getEmpMname() + " " + response.body().get(i).getEmpSname());
//                                        }
//                                    }

                                }

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

        public class EmployeeListDialogAdapter extends RecyclerView.Adapter<EmployeeListDialogAdapter.MyViewHolder> {

            private ArrayList<Employee> empList;
            private Context context;

            public EmployeeListDialogAdapter(ArrayList<Employee> empList, Context context) {
                this.empList = empList;
                this.context = context;
            }

            public class MyViewHolder extends RecyclerView.ViewHolder {
                public TextView tvName, tvAddress;
                public LinearLayout linearLayout;

                public MyViewHolder(View view) {
                    super(view);
                    tvName = view.findViewById(R.id.tvName);
                    tvAddress = view.findViewById(R.id.tvAddress);
                    linearLayout = view.findViewById(R.id.linearLayout);
                }
            }


            @NonNull
            @Override
            public EmployeeListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.adapter_department_dialog, viewGroup, false);

                return new EmployeeListDialogAdapter.MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull final EmployeeListDialogAdapter.MyViewHolder myViewHolder, int i) {
                final Employee model = empList.get(i);

                myViewHolder.tvName.setText(model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());
                //holder.tvAddress.setText(model.getCustAddress());

                myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent customerDataIntent = new Intent();
//                        customerDataIntent.setAction("CUSTOMER_DATA");
//                        customerDataIntent.putExtra("name", model.getEmpDeptName());
//                        customerDataIntent.putExtra("id", model.getEmpDeptId());
//                        LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);
                        dialog.dismiss();
                        spEmp.setText(""+model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());
                        tvEmpId.setText(""+model.getEmpId());

                    }
                });
            }



            @Override
            public int getItemCount() {
                return empList.size();
            }

            public void updateList(ArrayList<Employee> list) {
                empList = list;
                notifyDataSetChanged();
            }

        }


        private void getDepartment() {
            if (Constants.isOnline(getContext())) {
                final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
                commonDialog.show();

                Call<ArrayList<Department>> listCall = Constants.myInterface.allEmployeeDepartment();
                listCall.enqueue(new Callback<ArrayList<Department>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Department>> call, Response<ArrayList<Department>> response) {
                        try {
                            if (response.body() != null) {

                                Log.e("DEPT LIST : ", " - " + response.body());

                                for(int i=0;i<response.body().size();i++)
                                {
                                    deptList.add(response.body().get(i));
                                }
                                // deptList=response.body();

                                Log.e("DEPARTMENT LIST","---------------------------"+deptList);

//                                deptNameList.clear();
//                                deptIdList.clear();
//
//                                deptNameList.add("All");
//                                deptIdList.add(-1);
//
//                                if (response.body().size() > 0) {
//                                    for (int i = 0; i < response.body().size(); i++) {
//                                        deptIdList.add(response.body().get(i).getEmpDeptId());
//                                        deptNameList.add(response.body().get(i).getEmpDeptName());
//                                    }
//                                    Log.e("DEPT ID LIST","------------------------------"+deptIdList);
//
//                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, deptNameList);
//                                    spDept.setAdapter(projectAdapter);
//
//                                }

//                                if (model != null) {
//                                    int position = 0;
//                                    if (purposeIdList.size() > 0) {
//                                        for (int i = 0; i < purposeIdList.size(); i++) {
//                                            if (model.getPurposeId() == purposeIdList.get(i)) {
//                                                position = i;
//                                                break;
//                                            }
//                                        }
//                                        spPurpose.setSelection(position);
//
//                                    }
//                                }
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
                    public void onFailure(Call<ArrayList<Department>> call, Throwable t) {
                        commonDialog.dismiss();
                        Log.e("onFailure : ", "-----------" + t.getMessage());
                        t.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
            }
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
