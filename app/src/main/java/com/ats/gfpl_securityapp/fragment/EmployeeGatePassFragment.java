package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.PersonListDialogAdapter;
import com.ats.gfpl_securityapp.adapter.PurposeListDialogAdapter;
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

    private EditText edRemark, edFrom, edTo, edTotalHrs,edFromDate;
    private Spinner spEmployee, spReason;
    private RadioButton rbTemp, rbDay;
    private RadioGroup rg;
    private Button btnSubmit;
    private TextView tvName,tvLabelTotalHrs,tvPurpose,tvPurposeId,tvPersonName,tvPersonId;
    private CircleImageView ivPhoto;
    int gatePassType,employeeType;;
    String selectedtext,employeeName,employeeImage;
    int empId;
    Login loginUser;
    EmpGatePass model;
    private SwipeRefreshLayout swipeRefreshLayout;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;

    ArrayList<String> empNameList = new ArrayList<>();
    ArrayList<String> empImageList = new ArrayList<>();
    ArrayList<Integer> empIdList = new ArrayList<>();
    ArrayList<String> deptNameList = new ArrayList<>();
    ArrayList<Employee> empList = new ArrayList<>();

    ArrayList<String> purposeHeadingList = new ArrayList<>();
    ArrayList<Integer> purposeIdList = new ArrayList<>();
    ArrayList<PurposeList> purposeList = new ArrayList<>();

    Dialog dialog;
    private BroadcastReceiver mBroadcastReceiver;
    PersonListDialogAdapter personAdapter;
    PurposeListDialogAdapter purposeAdapter;


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
        edFromDate = view.findViewById(R.id.edFromDate);
        tvPurpose=(TextView)view.findViewById(R.id.tvPurpose);
        tvPurposeId=(TextView)view.findViewById(R.id.tvPurposeId);
        tvPersonName=(TextView)view.findViewById(R.id.tvPersonName);
        tvPersonId=(TextView)view.findViewById(R.id.tvPersonId);


        edFrom.setOnClickListener(this);
        edFromDate.setOnClickListener(this);
        edTo.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvPurpose.setOnClickListener(this);
        tvPersonName.setOnClickListener(this);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);

        fromDateMillis = todayDate.getTime();
        toDateMillis = todayDate.getTime();

        //  Log.e("Mytag", "todayString" + currentDate);
        edFromDate.setText(currentDate);

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
            tvPersonName.setText(model.getEmpName());
            tvPurpose.setText(model.getPurposeText());
            tvName.setText(model.getEmpName());

            try {
                String imageUri = String.valueOf(model.getExVar2());
                Log.e("Image Path","---------------------"+Constants.IMAGE_URL+imageUri);
                Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

            } catch (Exception e) {

            }

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

//        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                employeeName = deptNameList.get(position);
//                employeeImage = empImageList.get(position);
//                empId = empIdList.get(position);
//                Log.e("EMP Name","---------------------"+employeeName);
//                Log.e("EMP Image","---------------------"+employeeImage);
//                Log.e("EMP Image","---------------------"+Constants.IMAGE_URL+employeeImage);
//                tvName.setText(employeeName);
//
//                try {
//                    Picasso.with(getActivity()).load(Constants.IMAGE_URL+employeeImage).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);
//
//                } catch (Exception e) {
//
//                }
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("Broad cast data","---------------"+intent);
                if (intent.getAction().equals("CUSTOMER_DATA")) {
                    handlePushNotification(intent);

                }else if(intent.getAction().equals("CUSTOMER_DATA1"))
                {
                    handlePushNotification1(intent);
                }
            }
        };

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFrom) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            final int am_pm = mcurrentTime.get(Calendar.AM_PM);
                    TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    Log.e("Selecte Hrs","------------------"+selectedHour);
                    Log.e("Selecte Min","------------------"+selectedMinute);

                    if(selectedHour>12) {
                        edFrom.setText(String.valueOf(selectedHour-12)+ ":"+(String.valueOf(selectedMinute)+" pm"));
                        //edFrom.setText(selectedHour + ":" + selectedMinute);
                    } else if(selectedHour==12) {
                        edFrom.setText("12"+ ":"+(String.valueOf(selectedMinute)+" pm"));
                    } else if(selectedHour<12) {
                        if(selectedHour!=0) {
                            edFrom.setText(String.valueOf(selectedHour) + ":" + (String.valueOf(selectedMinute) + " am"));
                        } else {
                            edFrom.setText("12" + ":" + (String.valueOf(selectedMinute) + " am"));
                        }
                    }

                    String strTo=edTo.getText().toString();
                    String strFrom=edFrom.getText().toString();
                   calculateTimeDiff(strFrom,strTo);
                }
            }, hour, minute,false);//Yes 24 hour time
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
                    //edTo.setText(selectedHour + ":" + selectedMinute);
                    Log.e("Selecte Hrs","------------------"+selectedHour);
                    Log.e("Selecte Min","------------------"+selectedMinute);
                    Log.e("Selecte hrs Length","------------------"+String.valueOf(selectedHour).length());

//                    if(String.valueOf(selectedHour).length()==1)
//                    {
//                        Log.e("Hiiiiiiiiiiiiiiii","---------------");
//                        String newHrs="0"+selectedHour;
//                        selectedHour=Integer.parseInt(newHrs);
//                        Log.e("New Hrs1","---------------"+selectedHour);
//                    }
//                    Log.e("New Hrs","---------------"+selectedHour);
                    String format = "%1$02d";
                    if(selectedHour>12) {
                        edTo.setText(String.valueOf(selectedHour-12)+ ":"+(String.valueOf(selectedMinute)+" pm"));
                        //edTo.setText(String.valueOf(String.format("%02d:%02d",selectedHour,selectedMinute+" pm")));

                    } else if(selectedHour==12) {
                       edTo.setText("12"+ ":"+(String.valueOf(selectedMinute)+" pm"));
                        //edTo.setText("12"+ ":"+(String.valueOf(String.format("%02d:%02d",selectedMinute)+" pm")));
                    } else if(selectedHour<12) {
                        if(selectedHour!=0) {
                            edTo.setText(String.valueOf(selectedHour) + ":" + (String.valueOf(selectedMinute) + " am"));
                           // edTo.setText(String.valueOf(String.format("%02d:%02d",selectedHour,selectedMinute + " am")));
                        } else {
                            edTo.setText("12" + ":" + (String.valueOf(selectedMinute) + " am"));
                            //edTo.setText("12" + ":" + (String.valueOf(String.format("%02d:%02d",selectedMinute) + " am")));
                        }
                    }

                    String strTo=edTo.getText().toString();
                    String strFrom=edFrom.getText().toString();
                    calculateTimeDiff(strFrom,strTo);

                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }else if(v.getId()==R.id.edFromDate)
        {
                int yr, mn, dy;

                Calendar purchaseCal;

                long minDate = 0;

                purchaseCal = Calendar.getInstance();
                //purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
                minDate = purchaseCal.getTime().getTime();
                purchaseCal.setTimeInMillis(fromDateMillis);

                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
                dialog.getDatePicker().setMinDate(minDate);
                dialog.show();
        }else if(v.getId()==R.id.tvPurpose)
        {
            showDialog1();
        }else if(v.getId()==R.id.tvPersonName)
        {
            showDialog();
        }
        else if (v.getId() == R.id.btnSubmit) {
            String strRemark, strFromTime, strToTime, strTotalHrs,strFromDate,strPurose,strPurposeId,strPerson,strPersonId;
            boolean isValidTime = false, isValidFromTime = false, isValidToTime = false,isValidPerson=false,isValidPurpose=false;

            strFromDate = edFromDate.getText().toString();
            strRemark = edRemark.getText().toString();
            strFromTime = edFrom.getText().toString();
            strToTime = edTo.getText().toString();
            strTotalHrs = edTotalHrs.getText().toString();
            strPurose=tvPurpose.getText().toString();
            strPurposeId=tvPurposeId.getText().toString();
            strPerson=tvPersonName.getText().toString();
            strPersonId=tvPersonId.getText().toString();
            Log.e("Total Hrs String", "---------------------" + strTotalHrs);

            int purpId = 0,perId = 0;
            try {
                purpId = Integer.parseInt(strPurposeId);
                perId = Integer.parseInt(strPersonId);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");

            Log.e("in Time", "---------------------" + strToTime);
            Log.e("out Time", "---------------------" + strFromTime);

            Date outTime = null,inTime = null;
            try {
                 inTime = sdf2.parse(strToTime);
                outTime = sdf2.parse(strFromTime);

                Log.e("In Time","--------------------"+sdf1.format(inTime));
                Log.e("Out Time","--------------------"+sdf1.format(outTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            String  strInTime=sdf1.format(inTime);
            String  strOutTime=sdf1.format(outTime);

            Date outTime1 = null,inTime1 = null;
            try {
                inTime1 = sdf1.parse(strInTime);
                outTime1 = sdf1.parse(strOutTime);

                Log.e("In Time1","--------------------"+inTime1);
                Log.e("Out Time1","--------------------"+outTime1);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (inTime1.before(outTime1))
            //if(inTime.compareTo(outTime) > 0)
            {
                Toast.makeText(getActivity(), "To Time Should Be Greater Than From Time", Toast.LENGTH_SHORT).show();
                Log.e("Hiiiiiiiiiiiiiiiiiii","--------------------");
            }else{
                isValidTime =true;
                Log.e("Hiii","--------------------");
            }

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;
            try {
                ToDate = formatter1.parse(strFromDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String DateFrom = formatter3.format(ToDate);
            Log.e("Date Formate","--------------------"+DateFrom);


            float totalHrs = 0;
            try {
                totalHrs = Float.parseFloat(strTotalHrs);
                Log.e("Total Hrs Float111", "---------------------" + totalHrs);
            } catch (Exception e) {
                e.printStackTrace();
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

            if (strPerson.isEmpty()) {
                tvPersonName.setError("required");
            } else {
                tvPersonName.setError(null);
                isValidPerson = true;
            }

            if (strPurose.isEmpty()) {
                tvPurpose.setError("required");
            } else {
                tvPurpose.setError(null);
                isValidPurpose = true;
            }
            gatePassType = 1;
            if (rbTemp.isChecked()) {
                gatePassType = 1;
            } else if (rbDay.isChecked()) {
                gatePassType = 2;
            }
//            int employeeID = empIdList.get(spEmployee.getSelectedItemPosition());
//            int purposeID = purposeIdList.get(spReason.getSelectedItemPosition());
//            String purposeHeading = purposeHeadingList.get(spReason.getSelectedItemPosition());

//            if (employeeID == 0) {
//                TextView viewType = (TextView) spEmployee.getSelectedView();
//                viewType.setError("required");
//            } else {
//                TextView viewType = (TextView) spEmployee.getSelectedView();
//                viewType.setError(null);
//            }
//            if (purposeID == 0) {
//                TextView viewType = (TextView) spReason.getSelectedView();
//                viewType.setError("required");
//            } else {
//                TextView viewType = (TextView) spReason.getSelectedView();
//                viewType.setError(null);
//            }

            if (isValidFromTime && isValidToTime && isValidPerson && isValidPurpose && isValidTime) {
                if (model == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final EmpGatePass empGatePass = new EmpGatePass(0, DateFrom, sdf.format(System.currentTimeMillis()), loginUser.getEmpId(), loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), perId, strPerson, 1, gatePassType, purpId, strPurose, strRemark, loginUser.getEmpId(), loginUser.getEmpId(), totalHrs, strFromTime, strToTime, "NA", "NA", "NA", 0, 1, 1, 0, 0, 0, "NA",employeeImage, "NA");

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
                final EmpGatePass empGatePass = new EmpGatePass(model.getGatepassEmpId(), model.getEmpDateOut(), model.getEmpDateIn(), model.getUserId(), model.getUserName(), perId, strPerson, model.getGatePassType(), gatePassType, purpId, strPurose, strRemark, model.getSecurityIdOut(), model.getSecurityIdIn(), totalHrs, strFromTime, strToTime, model.getNewOutTime(), model.getNewInTime(), model.getActualTimeDifference(), model.getGatePassStatus(), model.getDelStatus(), model.getIsUsed(), model.getExInt1(), model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), model.getExVar3());

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

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();


        }
    };

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

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Date date1 = null, date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);

            Log.e("PARAMETER1","             TIME 1    "+date1 +"           TIME 2             "+date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();

//       int hours = (int) (difference/(1000 * 60 * 60));
//       int mins = (int) ((difference/(1000*60)) % 60);

        int days = (int) (difference / (1000*60*60*12));
        int hours = (int) ((difference - (1000*60*60*12*days)) / (1000*60*60));
        //int hours = (int) (difference / (60 * 60 * 1000*24) % 12);
        int min = (int) (difference - (1000*60*60*12*days) - (1000*60*60*hours)) / (1000*60);
        if(hours < 0){
            hours+=12;
        }if(min < 0){
            float  newone = (float)min/60 ;
            min +=60;
            hours =(int) (hours +newone);}

        diff=hours + "."+min;
      // int diffInt= Integer.parseInt(diff);
       Log.e("Total Hrs","----------------------------------------"+diff);
     //  Log.e("Total Hrs Int","----------------------------------------"+diffInt);
        edTotalHrs.setText(diff);

//       if(hours <=0 && min <=0)
//      {
//           edTotalHrs.setText("0.0");
//       }else {
//           edTotalHrs.setText(diff);
//       }

        return diff;
    }

    private void getEmployee() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployee();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " - " + response.body());

                            empNameList.clear();
                            empIdList.clear();
                            empList.clear();

                            deptNameList.add("Select Employee");
                            empIdList.add(0);
                            empImageList.add("");

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {

                                    empNameList.add(response.body().get(i).getEmpFname()+ " "+response.body().get(i).getEmpMname()+ " "+response.body().get(i).getEmpSname());
                                    if(response.body().get(i).getEmpDeptId()==loginUser.getEmpDeptId())
                                    {
                                        Employee employee = new Employee(response.body().get(i).getEmpId(),response.body().get(i).getEmpDsc(),response.body().get(i).getEmpCode(),response.body().get(i).getCompanyId(),response.body().get(i).getEmpCatId(),response.body().get(i).getEmpTypeId(),response.body().get(i).getEmpDeptId(),response.body().get(i).getLocId(),response.body().get(i).getEmpFname(),response.body().get(i).getEmpMname(),response.body().get(i).getEmpSname(),response.body().get(i).getEmpPhoto(),response.body().get(i).getEmpMobile1(),response.body().get(i).getEmpMobile2(),response.body().get(i).getEmpEmail(),response.body().get(i).getEmpAddressTemp(),response.body().get(i).getEmpAddressPerm(),response.body().get(i).getEmpBloodgrp(),response.body().get(i).getEmpEmergencyPerson1(),response.body().get(i).getEmpEmergencyNo1(),response.body().get(i).getEmpEmergencyPerson2(),response.body().get(i).getEmpEmergencyNo2(),response.body().get(i).getEmpRatePerhr(),response.body().get(i).getEmpJoiningDate(),response.body().get(i).getEmpPrevExpYrs(),response.body().get(i).getEmpPrevExpMonths(),response.body().get(i).getEmpLeavingDate(),response.body().get(i).getEmpLeavingReason(),response.body().get(i).getLockPeriod(),response.body().get(i).getTermConditions(),response.body().get(i).getSalaryId(),response.body().get(i).getDelStatus(),response.body().get(i).getIsActive(),response.body().get(i).getMakerUserId(),response.body().get(i).getMakerEnterDatetime(),response.body().get(i).getExInt1(),response.body().get(i).getExInt2(),response.body().get(i).getExInt3(),response.body().get(i).getExVar1(),response.body().get(i).getExVar2(),response.body().get(i).getExVar3());
                                      //  deptNameList.add(response.body().get(i).getEmpFname()+ " "+response.body().get(i).getEmpMname()+ " "+response.body().get(i).getEmpSname());
                                      //  empImageList.add(response.body().get(i).getEmpPhoto());
                                      //  empIdList.add(response.body().get(i).getEmpId());
                                        empList.add(employee);
                                    }

                                }

//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, deptNameList);
//
//                                spEmployee.setAdapter(projectAdapter);

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

            ArrayList<Integer> getTypeList = new ArrayList<>();
            getTypeList.add(3);

            Call<ArrayList<PurposeList>> listCall = Constants.myInterface.getAllPurposesByType(getTypeList);
            listCall.enqueue(new Callback<ArrayList<PurposeList>>() {
                @Override
                public void onResponse(Call<ArrayList<PurposeList>> call, Response<ArrayList<PurposeList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PURPOSE LIST : ", " - " + response.body());

                            purposeHeadingList.clear();
                            purposeIdList.clear();
                            purposeList=response.body();

//                            purposeHeadingList.add("Select Purpose");
//                            purposeIdList.add(0);
//
//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    purposeIdList.add(response.body().get(i).getPurposeId());
//                                    purposeHeadingList.add(response.body().get(i).getPurposeHeading());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, purposeHeadingList);
//                                spReason.setAdapter(projectAdapter);
//
//                            }
//
//                            if (model != null) {
//                                int position = 0;
//                                if (purposeIdList.size() > 0) {
//                                    for (int i = 0; i < purposeIdList.size(); i++) {
//                                        if (model.getPurposeId() == purposeIdList.get(i)) {
//                                            position = i;
//                                            break;
//                                        }
//                                    }
//                                    spReason.setSelection(position);
//
//                                }
//                            }
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

    private void showDialog1() {

        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);

        purposeAdapter = new PurposeListDialogAdapter(purposeList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(purposeAdapter);

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
                    if (purposeAdapter != null) {
                        filterCustomer1(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    void filterCustomer1(String text) {
        ArrayList<PurposeList> temp1 = new ArrayList();
        for (PurposeList d : purposeList) {
            if (d.getPurposeHeading().toLowerCase().contains(text.toLowerCase()) ) {
                temp1.add(d);
            }
        }
        //update recyclerview
        purposeAdapter.updateList(temp1);
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

        personAdapter = new PersonListDialogAdapter(empList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(personAdapter);

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
                    if (personAdapter != null) {
                        filterCustomer(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    void filterCustomer(String text) {
        ArrayList<Employee> temp = new ArrayList();
        for (Employee d : empList) {
            if (d.getEmpFname().toLowerCase().contains(text.toLowerCase()) || d.getEmpMname().toLowerCase().contains(text.toLowerCase()) || d.getEmpSname().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        personAdapter.updateList(temp);
    }

    private void handlePushNotification(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String name = intent.getStringExtra("name");
         employeeImage = intent.getStringExtra("image");
        int custId = intent.getIntExtra("id", 0);
        Log.e("CUSTOMER NAME : ", " " + name);
        tvPersonName.setText("" + name);
        tvPersonId.setText("" + custId);

        tvName.setText(name);

        try {
            Picasso.with(getActivity()).load(Constants.IMAGE_URL+employeeImage).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

        } catch (Exception e) {

        }



    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String purposeName = intent.getStringExtra("purposeName");
        int purposeId = intent.getIntExtra("purposeId", 0);
        Log.e("CUSTOMER NAME : ", " " + purposeName);
        tvPurpose.setText("" + purposeName);
        tvPurposeId.setText("" + purposeId);

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("CUSTOMER_DATA"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("CUSTOMER_DATA1"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
    }


}
