package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.adapter.InwardGatePassListAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.interfaces.ApproveInwardInterface;
import com.ats.gfpl_securityapp.model.Department;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.ats.gfpl_securityapp.model.Party;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.gfpl_securityapp.fragment.MaterialFragment.staticLoginUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApproveInwardFragment extends Fragment implements ApproveInwardInterface,View.OnClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Button btnSubmit,btnRejected;
    CheckBox checkBox;
    InwardGatePassListAdapter adapter;
    String stringId;

    long fromDateMillis, toDateMillis,dateMillis;
    int yyyy, mm, dd;

    ArrayList<MaterialDetail> assignedArray = new ArrayList<>();
    final ArrayList<Integer> assignedMaterialIdArray = new ArrayList<>();

    ArrayList<MaterialDetail> materialList = new ArrayList<>();
    public static ArrayList<MaterialDetail> assignStaticMaterialList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_approve_inward, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnRejected = view.findViewById(R.id.btnRejected);
        checkBox = view.findViewById(R.id.cbAll);

        fab.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnRejected.setOnClickListener(this);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);

        ArrayList<Integer> deptIdList = new ArrayList<>();
        deptIdList.add(staticLoginUser.getEmpDeptId());

        ArrayList<Integer> empIdList = new ArrayList<>();
        empIdList.add(staticLoginUser.getEmpId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getMaterial(deptIdList,empIdList,statusList);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Log.e("LIST","------------------------"+materialList);
                    for(int k=0;k<materialList.size();k++)
                    {
                        Log.e("LIST SET","------------------------"+materialList.get(k));
                        materialList.get(k).setChecked(true);

                    }
                }else{
                    for(int k=0;k<materialList.size();k++)
                    {
                        Log.e("LIST SET","------------------------"+materialList.get(k));
                        materialList.get(k).setChecked(false);

                    }
                }

                adapter = new InwardGatePassListAdapter(materialList, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
        });

        return view;

    }

    private void getMaterial(ArrayList<Integer> deptIdList, ArrayList<Integer> empIdList, ArrayList<Integer> statusList) {

        Log.e("PARAMETER","       DEPT ID   " +  deptIdList  +"           SUPPLY ID   "+   empIdList  +"             STATUS"  +statusList);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MaterialDetail>> listCall = Constants.myInterface.getMaterialTrackGatepassListWithFilter(deptIdList,empIdList,statusList);
            listCall.enqueue(new Callback<ArrayList<MaterialDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<MaterialDetail>> call, Response<ArrayList<MaterialDetail>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("MATERIAL LIST : ", " - " + response.body());
                            materialList.clear();
                            materialList = response.body();

                            assignStaticMaterialList.clear();
                            assignStaticMaterialList = materialList;

                            for (int i = 0; i < assignStaticMaterialList.size(); i++) {
                                assignStaticMaterialList.get(i).setChecked(false);
                            }

                            adapter = new InwardGatePassListAdapter(materialList, getContext());
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
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {

            //new FilterDialog(getContext()).show();

        }if(v.getId()==R.id.btnRejected)
        {
            ArrayList<MaterialDetail> assignedArray = new ArrayList<>();
            final ArrayList<Integer> assignedMaterialIdArray = new ArrayList<>();
            if (assignStaticMaterialList != null) {
                if (assignStaticMaterialList.size() > 0) {
                    assignedArray.clear();
                    for (int i = 0; i < assignStaticMaterialList.size(); i++) {
                        if (assignStaticMaterialList.get(i).getChecked()) {
                            assignedArray.add(assignStaticMaterialList.get(i));
                            assignedMaterialIdArray.add(assignStaticMaterialList.get(i).getGatepassInwardId());

                        }
                    }
                }
                Log.e("ASSIGN EMP", "---------------------------------" + assignedArray);
                Log.e("ASSIGN EMP ID", "---------------------------------" + assignedMaterialIdArray);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to Reject Material ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        rejectAssigneMaterial(assignedMaterialIdArray, 2);


                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else if (v.getId() == R.id.btnSubmit) {


            if (assignStaticMaterialList != null) {
                if (assignStaticMaterialList.size() > 0) {
                    assignedArray.clear();
                    for (int i = 0; i < assignStaticMaterialList.size(); i++) {
                        if (assignStaticMaterialList.get(i).getChecked()) {
                            assignedArray.add(assignStaticMaterialList.get(i));
                            assignedMaterialIdArray.add(assignStaticMaterialList.get(i).getGatepassInwardId());

                        }
                    }
                }
                Log.e("ASSIGN EMP", "---------------------------------" + assignedArray);
                Log.e("ASSIGN EMP ID", "---------------------------------" + assignedMaterialIdArray);

                new ApproveDialog(getContext(),assignedMaterialIdArray).show();

            }

        }
    }

    private void rejectAssigneMaterial(ArrayList<Integer> assignedIDArray, int status) {
        Log.e("PARAMETER","---------------------------------------ASSIGNE MATERIAL REJECTED--------------------------"+assignedIDArray +"           STATUS       "+status);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateMaterialGatepass(assignedIDArray,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ASSIGNE MATERIAL : ", " ------------------------------ASSIGNE MATERIAL REJECTED------------------------ " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
                            ft.commit();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }
    public class FilterDialog extends Dialog {

        EditText edFromDate, edToDate, edDate;
        TextView tvFromDate, tvToDate, tvDate;
        Spinner spDept, spParty;
        RadioButton rbAsOnDate, rbFromToDate, rbDept, rbParty;
        ImageView ivClose;
        CardView cvDate, cvFromToDate, cvDept, cvParty;
        ArrayList<String> deptNameList = new ArrayList<>();
        ArrayList<Integer> deptIdList = new ArrayList<>();
        int dateType,type,deptId,partyId;
        ArrayList<String> partyNameList = new ArrayList<>();
        ArrayList<Integer> partyIdList = new ArrayList<>();
        String DateTo;

        // String dateFrom,dateTo;

        public FilterDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_inward_gate_pass_filter);
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
            spParty = findViewById(R.id.spParty);
            ivClose = findViewById(R.id.ivClose);
            tvDate = findViewById(R.id.tvDate);
            edDate = findViewById(R.id.edDate);
            rbAsOnDate = findViewById(R.id.rbAsOnDate);
            rbFromToDate = findViewById(R.id.rbFromToDate);
            rbDept = findViewById(R.id.rbDept);
            rbParty = findViewById(R.id.rbParty);
            cvDate = findViewById(R.id.cvDate);
            cvFromToDate = findViewById(R.id.cvFromToDate);
            cvDept = findViewById(R.id.cvDept);
            cvParty = findViewById(R.id.cvParty);

            getDepartment();
            getAllParty();


//            partyNameList.add("All");
//            partyNameList.add("Party1");
//            partyNameList.add("Party2");
//            partyNameList.add("Party3");
//
//            partyIdList.add(-1);
//            partyIdList.add(1);
//            partyIdList.add(2);
//            partyIdList.add(3);
//
//            ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, partyNameList);
//            spParty.setAdapter(projectAdapter);


            rbAsOnDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cvDate.setVisibility(View.VISIBLE);
                        cvFromToDate.setVisibility(View.GONE);
                    } else {
                        cvDate.setVisibility(View.GONE);
                    }
                }
            });

            rbFromToDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cvFromToDate.setVisibility(View.GONE);
                        cvFromToDate.setVisibility(View.VISIBLE);
                    } else {
                        cvFromToDate.setVisibility(View.GONE);
                    }
                }
            });

            rbDept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cvDept.setVisibility(View.VISIBLE);
                        cvParty.setVisibility(View.GONE);
                    } else {
                        cvDept.setVisibility(View.GONE);
                    }
                }
            });

            rbParty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cvDept.setVisibility(View.GONE);
                        cvParty.setVisibility(View.VISIBLE);
                    } else {
                        cvParty.setVisibility(View.GONE);
                    }
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

            edDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int yr, mn, dy;
                    if (dateMillis > 0) {
                        Calendar purchaseCal = Calendar.getInstance();
                        purchaseCal.setTimeInMillis(dateMillis);
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        Calendar purchaseCal = Calendar.getInstance();
                        yr = purchaseCal.get(Calendar.YEAR);
                        mn = purchaseCal.get(Calendar.MONTH);
                        dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                    }
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateListener, yr, mn, dy);
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
                    String strFromDate = null,strToDate = null,dateFrom,dateTo;
                    dateType = 0;
                    if (rbAsOnDate.isChecked()) {
                        dateType = 1;
                    } else if (rbFromToDate.isChecked()) {
                        dateType = 2;
                    }
                    Log.e("DATE TYPE","---------------------------------"+dateType);

                    if(dateType==1)
                    {
                        strFromDate=edDate.getText().toString();
                        strToDate=edDate.getText().toString();
                    }else if(dateType==2)
                    {
                        strFromDate=edFromDate.getText().toString();
                        strToDate=edToDate.getText().toString();
                    }

                    //select department or party

                    type = 0;
                    if (rbDept.isChecked()) {
                        type = 1;
                    } else if (rbParty.isChecked()) {
                        type = 2;
                    }
                    Log.e("TYPE","---------------------------------"+type);

                    if(type!=0)
                    {
                        deptId=deptIdList.get(spDept.getSelectedItemPosition());
                        Log.e("Dept Id","------------------------"+deptId);
                        partyId=partyIdList.get(spParty.getSelectedItemPosition());
                        Log.e("Party Id","------------------------"+partyId);
                    }

                    ArrayList<Integer> statusList = new ArrayList<>();
                    statusList.add(1);

                    ArrayList<Integer> deptIdList = new ArrayList<>();
                    deptIdList.add(deptId);

                    ArrayList<Integer> suppIdList = new ArrayList<>();
                    suppIdList.add(partyId);

                    if(dateType!=0 && type!=0)
                    {
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

                        Date fromDate = null;
                        try {
                            fromDate = formatter1.parse(strFromDate);//catch exception
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        dateFrom = formatter2.format(fromDate);
                        Log.e("dateFrom","--------------------------"+dateFrom);

                        Date toDate = null;
                        try {
                            toDate = formatter1.parse(strToDate);//catch exception
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        dateTo = formatter2.format(toDate);
                        Log.e("dateTo","--------------------------"+dateTo);

                        //getMaterial(dateFrom,dateTo,deptIdList,suppIdList,statusList);
                        dismiss();
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

        private void getAllParty() {
            if (Constants.isOnline(getContext())) {
                final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
                commonDialog.show();

                Call<ArrayList<Party>> listCall = Constants.myInterface1.getAllVendorByIsUsed();
                listCall.enqueue(new Callback<ArrayList<Party>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Party>> call, Response<ArrayList<Party>> response) {
                        try {
                            if (response.body() != null) {

                                Log.e("PARTY LIST : ", " - " + response.body());

                                partyIdList.clear();
                                partyNameList.clear();

                                partyNameList.add("All");
                                partyIdList.add(-1);

                                if (response.body().size() > 0) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        partyIdList.add(response.body().get(i).getVendorId());
                                        partyNameList.add(response.body().get(i).getVendorName());
                                    }

                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, partyNameList);
                                    spParty.setAdapter(projectAdapter);

                                }
//                                if (model != null) {
//                                    int position = 0;
//                                    if (partyIdList.size() > 0) {
//                                        for (int i = 0; i < partyIdList.size(); i++) {
//                                            if (model.getPartyId().equals(partyIdList.get(i))) {
//                                                position = i;
//                                                break;
//                                            }
//                                        }
//                                        spParty.setSelection(position);
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
                    public void onFailure(Call<ArrayList<Party>> call, Throwable t) {
                        commonDialog.dismiss();
                        Log.e("onFailure : ", "-----------" + t.getMessage());
                        t.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
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

                                deptNameList.clear();
                                deptIdList.clear();

                                deptNameList.add("All");
                                deptIdList.add(-1);

                                if (response.body().size() > 0) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        deptIdList.add(response.body().get(i).getEmpDeptId());
                                        deptNameList.add(response.body().get(i).getEmpDeptName());
                                    }

                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, deptNameList);
                                    spDept.setAdapter(projectAdapter);

                                }

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

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yyyy = year;
                mm = month + 1;
                dd = dayOfMonth;
                edDate.setText(dd + "-" + mm + "-" + yyyy);
                tvDate.setText(yyyy + "-" + mm + "-" + dd);
                //CustomSharedPreference.putString(getActivity(), CustomSharedPreference.KEY_SP_FROM_DATE, yyyy + "-" + mm + "-" + dd);

                Calendar calendar = Calendar.getInstance();
                calendar.set(yyyy, mm - 1, dd);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                dateMillis = calendar.getTimeInMillis();
            }
        };
    }


    private class ApproveDialog extends Dialog {
        public Button btnCancel,btnSubmit;
        public Spinner spDept,spEmp;

        ArrayList<String> deptNameList = new ArrayList<>();
        ArrayList<Integer> deptIdList = new ArrayList<>();

        ArrayList<String> empNameList = new ArrayList<>();
        ArrayList<Integer> empIdList = new ArrayList<>();


        ArrayList<Integer> assignedMaterialIdArray = new ArrayList<>();

        public ApproveDialog( Context context,ArrayList<Integer> assignedMaterialIdArray) {
            super(context);
            this.assignedMaterialIdArray=assignedMaterialIdArray;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_approve);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            spDept=(Spinner)findViewById(R.id.spDept);
            spEmp=(Spinner)findViewById(R.id.spEmp);

            getAllEmp();
            getAllDept();

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int empId = empIdList.get(spEmp.getSelectedItemPosition());
                     int deptId = deptIdList.get(spDept.getSelectedItemPosition());

                      if(deptId==0)
                     {
                         TextView viewType = (TextView) spDept.getSelectedView();
                         viewType.setError("required");
                     }else if(empId==0)
                      {
                          TextView viewType = (TextView) spEmp.getSelectedView();
                          viewType.setError("required");

                          TextView viewProj = (TextView) spDept.getSelectedView();
                          viewProj.setError(null);
                      }else{
                          TextView viewProj = (TextView) spEmp.getSelectedView();
                          viewProj.setError(null);
                          saveApprove(assignedMaterialIdArray,staticLoginUser.getEmpId(),staticLoginUser.getEmpDeptId(),empId,deptId);

                          dismiss();
                          //saveApprove()
                      }

                }

                private void saveApprove(ArrayList<Integer> assignedMaterialIdArray, Integer empIdfrom, Integer empDeptIdfrom, int empIdto, int deptIdto) {
                    if (Constants.isOnline(getActivity())) {
                        final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
                        commonDialog.show();

                        Call<Info> listCall = Constants.myInterface.materialGatepassHandover(assignedMaterialIdArray,empIdfrom,empDeptIdfrom,empIdto,deptIdto);
                        listCall.enqueue(new Callback<Info>() {
                            @Override
                            public void onResponse(Call<Info> call, Response<Info> response) {
                                try {
                                    if (response.body() != null) {

                                        Log.e("SAVE APP MATERIAL: ", " - " + response.body());

                                        if (!response.body().getError()) {

                                            MainActivity activity = (MainActivity) getActivity();

                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
                                            ft.commit();

                                        } else {
                                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                                        }

                                        commonDialog.dismiss();

                                    } else {
                                        commonDialog.dismiss();
                                        Log.e("Data Null : ", "-----------");
                                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    commonDialog.dismiss();
                                    Log.e("Exception : ", "-----------" + e.getMessage());
                                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<Info> call, Throwable t) {
                                commonDialog.dismiss();
                                Log.e("onFailure : ", "-----------" + t.getMessage());
                                Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        private void getAllEmp() {
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

                                empNameList.add("Select Employee");
                                empIdList.add(0);

                                if (response.body().size() > 0) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        empIdList.add(response.body().get(i).getEmpDeptId());
                                        empNameList.add(response.body().get(i).getEmpFname()+" "+response.body().get(i).getEmpMname()+" "+response.body().get(i).getEmpSname());
                                    }

                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
                                    spEmp.setAdapter(projectAdapter);

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

        private void getAllDept() {
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

                                deptNameList.clear();
                                deptIdList.clear();

                                deptNameList.add("Select Department");
                                deptIdList.add(0);

                                if (response.body().size() > 0) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        deptIdList.add(response.body().get(i).getEmpDeptId());
                                        deptNameList.add(response.body().get(i).getEmpDeptName());
                                    }

                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, deptNameList);
                                    spDept.setAdapter(projectAdapter);

                                }

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
    }

}
