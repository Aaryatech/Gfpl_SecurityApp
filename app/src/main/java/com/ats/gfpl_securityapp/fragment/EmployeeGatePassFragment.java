package com.ats.gfpl_securityapp.fragment;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.EmpGatePass;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeGatePassFragment extends Fragment implements View.OnClickListener {

    private EditText edRemark, edFrom, edTo, edTotalHrs;
    private Spinner spEmployee, spReason;
    private RadioButton rbTemp, rbDay;
    private RadioGroup rg;
    private Button btnSubmit;
    private TextView tvName,tvLabelTotalHrs;
    private CircleImageView ivPhoto;
    int gatePassType,employeeType;;
    String selectedtext,employeeName,employeeImage;
    Login loginUser;
    EmpGatePass model;

    ArrayList<String> empNameList = new ArrayList<>();
    ArrayList<String> empImageList = new ArrayList<>();
    ArrayList<Integer> empIdList = new ArrayList<>();

    ArrayList<String> purposeHeadingList = new ArrayList<>();
    ArrayList<Integer> purposeIdList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_gate_pass, container, false);
        getActivity().setTitle("Employee Gate pass");

        edRemark = view.findViewById(R.id.edRemark);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);
        spEmployee = view.findViewById(R.id.spEmployee);
        spReason = view.findViewById(R.id.spReason);
        edTotalHrs = view.findViewById(R.id.edTotalHrs);
        rbTemp = view.findViewById(R.id.rbTemp);
        rbDay = view.findViewById(R.id.rbDay);
        rg = view.findViewById(R.id.rg);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        tvName = view.findViewById(R.id.tvName);
        tvLabelTotalHrs = view.findViewById(R.id.tvLabelTotalHrs);

        edFrom.setOnClickListener(this);
        edTo.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

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
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, EmpGatePass.class);
            Log.e("MODEL","-----------------------------------"+model);

            edRemark.setText(model.getPurposeRemark());
            edFrom.setText(model.getOutTime());
            edTo.setText(model.getInTime());
            edTotalHrs.setText(""+model.getNoOfHr());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(model==null)
        {
            getActivity().setTitle("Add Employee Gate Pass");
        }else{
            getActivity().setTitle("Edit Employee Gate Pass");
        }

        if(model==null) {
            rbTemp.setChecked(true);
        }else{
            employeeType = model.getGatePassSubType();
            if (employeeType==1) {
                rbTemp.setChecked(true);
                tvLabelTotalHrs.setVisibility(View.VISIBLE);
                edTotalHrs.setVisibility(View.VISIBLE);
            } else if (employeeType==2) {
                rbDay.setChecked(true);
                tvLabelTotalHrs.setVisibility(View.GONE);
                edTotalHrs.setVisibility(View.GONE);
            }
        }

        getEmployee();
        getPurpose();

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 employeeName = empNameList.get(position);
                employeeImage = empImageList.get(position);
                Log.e("EMP Name","---------------------"+employeeName);
                Log.e("EMP Image","---------------------"+employeeImage);
                tvName.setText(employeeName);

                try {
                    Picasso.with(getActivity()).load(Constants.IMAGE_URL+ " " +employeeImage).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int idx = group.indexOfChild(radioButton);
                RadioButton r = (RadioButton) group.getChildAt(idx);
                selectedtext = r.getText().toString();
                Log.e("Log Radio", "----------" + idx);
                Log.e("Log Radio1", "----------" + selectedtext);

                if(selectedtext.equalsIgnoreCase("Temporary"))
                {
                    tvLabelTotalHrs.setVisibility(View.VISIBLE);
                    edTotalHrs.setVisibility(View.VISIBLE);
                }else if(selectedtext.equalsIgnoreCase("Day"))
                {
                    tvLabelTotalHrs.setVisibility(View.GONE);
                    edTotalHrs.setVisibility(View.GONE);
                }

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFrom) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    edFrom.setText(selectedHour + ":" + selectedMinute);
                    String strTo=edTo.getText().toString();
                    String strFrom=edFrom.getText().toString();
                    calculateTimeDiff(strFrom,strTo);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        } else if (v.getId() == R.id.edTo) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    edTo.setText(selectedHour + ":" + selectedMinute);
                    String strTo=edTo.getText().toString();
                    String strFrom=edFrom.getText().toString();
                    calculateTimeDiff(strFrom,strTo);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        } else if (v.getId() == R.id.btnSubmit) {
            String strRemark, strFromTime, strToTime, strTotalHrs;
            boolean isValidRemark = false, isValidFromTime = false, isValidToTime = false;

            strRemark = edRemark.getText().toString();
            strFromTime = edFrom.getText().toString();
            strToTime = edTo.getText().toString();
            strTotalHrs = edTotalHrs.getText().toString();
            Log.e("Total Hrs String", "---------------------" + strTotalHrs);

            float totalHrs = 0;
            try {
                totalHrs = Float.parseFloat(strTotalHrs);
                Log.e("Total Hrs Float111", "---------------------" + totalHrs);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (strRemark.isEmpty()) {
                edRemark.setError("required");
            } else {
                edRemark.setError(null);
                isValidRemark = true;
            }
            if (strFromTime.isEmpty()) {
                edFrom.setError("required");
            } else {
                edFrom.setError(null);
                isValidFromTime = true;
            }
            if (strToTime.isEmpty()) {
                edTo.setError("required");
            } else {
                edTo.setError(null);
                isValidToTime = true;
            }
            gatePassType = 1;
            if (rbTemp.isChecked()) {
                gatePassType = 1;
            } else if (rbDay.isChecked()) {
                gatePassType = 2;
            }
            int employeeID = empIdList.get(spEmployee.getSelectedItemPosition());
            int purposeID = purposeIdList.get(spReason.getSelectedItemPosition());
            String purposeHeading = purposeHeadingList.get(spReason.getSelectedItemPosition());

            if (employeeID == 0) {
                TextView viewType = (TextView) spEmployee.getSelectedView();
                viewType.setError("required");
            } else {
                TextView viewType = (TextView) spEmployee.getSelectedView();
                viewType.setError(null);
            }
            if (purposeID == 0) {
                TextView viewType = (TextView) spReason.getSelectedView();
                viewType.setError("required");
            } else {
                TextView viewType = (TextView) spReason.getSelectedView();
                viewType.setError(null);
            }

            if (isValidRemark && isValidFromTime && isValidToTime) {
                if (model == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final EmpGatePass empGatePass = new EmpGatePass(0, sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), loginUser.getEmpId(), loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), employeeID, employeeName, 1, gatePassType, purposeID, purposeHeading, strRemark, loginUser.getEmpId(), loginUser.getEmpId(), totalHrs, strFromTime, strToTime, "NA", "NA", "NA", 0, 1, 1, 0, 0, 0, "NA", "NA", "NA");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want employee gate pass ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveEmployeeGatePass(empGatePass);
                            Log.e("EMPLOYEE GATE PASS", "-----------------------" + empGatePass);

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
            } else {
                final EmpGatePass empGatePass = new EmpGatePass(model.getGatepassEmpId(), model.getEmpDateOut(), model.getEmpDateIn(), model.getUserId(), model.getUserName(), employeeID, employeeName, model.getGatePassType(), gatePassType, purposeID, purposeHeading, strRemark, model.getSecurityIdOut(), model.getSecurityIdIn(), totalHrs, strFromTime, strToTime, model.getNewOutTime(), model.getNewInTime(), model.getActualTimeDifference(), model.getGatePassStatus(), model.getDelStatus(), model.getIsUsed(), model.getExInt1(), model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), model.getExVar3());

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to edit employee gate pass ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        saveEmployeeGatePass(empGatePass);
                        Log.e("EMPLOYEE GATE PASS EDIT", "-----------------------" + empGatePass);

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

        }
    }

    private void saveEmployeeGatePass(EmpGatePass empGatePass) {
        Log.e("PARAMETER","---------------------------------------EMPLOYEE GATE PASS--------------------------"+empGatePass);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<EmpGatePass> listCall = Constants.myInterface.saveEmployeeGatepass(empGatePass);
            listCall.enqueue(new Callback<EmpGatePass>() {
                @Override
                public void onResponse(Call<EmpGatePass> call, Response<EmpGatePass> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMP GATE PASS : ", " ------------------------------EMP GATE PASS------------------------ " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
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
                public void onFailure(Call<EmpGatePass> call, Throwable t) {
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


    public String calculateTimeDiff(String time1, String time2) {
        Log.e("PARAMETER","             TIME 1    "+time1 +"           TIME 2             "+time2);
        String diff = "";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null, date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);

            Log.e("PARAMETER1","             TIME 1    "+date1 +"           TIME 2             "+date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();

       int hours = (int) (difference/(1000 * 60 * 60));
       int mins = (int) ((difference/(1000*60)) % 60);

       // diff=DurationFormatUtils.formatDuration(difference, "HH:mm");
       diff=hours + "."+mins;
       Log.e("Total Hrs","----------------------------------------"+diff);
       edTotalHrs.setText(diff);

        return diff;
    }


    private void getEmployee() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployees();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " - " + response.body());

                            empNameList.clear();
                            empIdList.clear();

                            empNameList.add("Select Employee");
                            empIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    empIdList.add(response.body().get(i).getEmpId());
                                    empImageList.add(response.body().get(i).getEmpPhoto());
                                    empNameList.add(response.body().get(i).getEmpFname()+ " "+response.body().get(i).getEmpMname()+ " "+response.body().get(i).getEmpSname());

                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);

                                spEmployee.setAdapter(projectAdapter);

                            }

                            if (model != null) {
                                int position = 0;
                                if (empIdList.size() > 0) {
                                    for (int i = 0; i < empIdList.size(); i++) {
                                        if (model.getEmpId()==empIdList.get(i)) {
                                            position = i;
                                            break;
                                        }
                                    }
                                    spEmployee.setSelection(position);

                                }
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
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPurpose() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<PurposeList>> listCall = Constants.myInterface.allPurposes();
            listCall.enqueue(new Callback<ArrayList<PurposeList>>() {
                @Override
                public void onResponse(Call<ArrayList<PurposeList>> call, Response<ArrayList<PurposeList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PURPOSE LIST : ", " - " + response.body());

                            purposeHeadingList.clear();
                            purposeIdList.clear();


                            purposeHeadingList.add("Select Purpose");
                            purposeIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    purposeIdList.add(response.body().get(i).getPurposeId());
                                    purposeHeadingList.add(response.body().get(i).getPurposeHeading());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, purposeHeadingList);
                                spReason.setAdapter(projectAdapter);

                            }

                            if (model != null) {
                                int position = 0;
                                if (purposeIdList.size() > 0) {
                                    for (int i = 0; i < purposeIdList.size(); i++) {
                                        if (model.getPurposeId() == purposeIdList.get(i)) {
                                            position = i;
                                            break;
                                        }
                                    }
                                    spReason.setSelection(position);

                                }
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
                public void onFailure(Call<ArrayList<PurposeList>> call, Throwable t) {
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
