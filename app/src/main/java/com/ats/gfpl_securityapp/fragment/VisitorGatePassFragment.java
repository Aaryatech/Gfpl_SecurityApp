package com.ats.gfpl_securityapp.fragment;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.BuildConfig;
import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.PersonListDialogAdapter;
import com.ats.gfpl_securityapp.adapter.PurposeListDialogAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Company;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Gate;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.model.Visitor;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.ats.gfpl_securityapp.utils.RealPathUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class VisitorGatePassFragment extends Fragment implements View.OnClickListener {

    private EditText edName, edMobile,edCompany,edNoOfPer,edEmployee,edRemark;
    private TextInputLayout textEmp;
    private TextView tvPerson,tvImageLable,tvPersonName,tvPersonId,tvPurpose,tvPurposeId;
    private Spinner spPurpose, spPerson,spGate,spCompany;
    private RadioButton rbAppointment, rbRandom;
    private Button btnSubmit;
    int purposeId= 0,purposeType;
    private ImageView ivCamera1,ivPhoto1;
    private TextView tvPhoto1;
    private LinearLayout linearLayoutImage;
    String empIds;
    Login loginUser;
    VisitorList model;
    ArrayList<String> purposeHeadingList = new ArrayList<>();
    ArrayList<Integer> purposeIdList = new ArrayList<>();
    ArrayList<PurposeList> purposeList = new ArrayList<>();

    Dialog dialog;
    private BroadcastReceiver mBroadcastReceiver;
    PersonListDialogAdapter personAdapter;
    PurposeListDialogAdapter purposeAdapter;

    ArrayList<String> empNameList = new ArrayList<>();
    ArrayList<Integer> empIdList = new ArrayList<>();
    ArrayList<Employee> empList = new ArrayList<>();

    ArrayList<String> getNameList = new ArrayList<>();
    ArrayList<Integer> getIdList = new ArrayList<>();

    ArrayList<String> getCompNameList = new ArrayList<>();
    ArrayList<Integer> getCompIdList = new ArrayList<>();
    int visitorType;

    //Image Upload

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;

    Bitmap myBitmap1 = null;
    public static String path1, imagePath1 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitor_gate_pass, container, false);
        //getActivity().setTitle("Visitor Gate Pass");

        edName = view.findViewById(R.id.edName);
        edMobile = view.findViewById(R.id.edMobile);
        spPurpose = view.findViewById(R.id.spPurpose);
        spPerson = view.findViewById(R.id.spPerson);
        spGate = view.findViewById(R.id.spGate);
        spCompany = view.findViewById(R.id.spCompany);
        rbAppointment = view.findViewById(R.id.rbAppointment);
        rbRandom = view.findViewById(R.id.rbRandom);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        edCompany = view.findViewById(R.id.edCompany);
        edNoOfPer = view.findViewById(R.id.edNoOfPer);
        tvPerson=(TextView)view.findViewById(R.id.tvPerson);
        tvPersonName=(TextView)view.findViewById(R.id.tvPersonName);
        tvPersonId=(TextView)view.findViewById(R.id.tvPersonId);
        tvPurpose=(TextView)view.findViewById(R.id.tvPurpose);
        tvPurposeId=(TextView)view.findViewById(R.id.tvPurposeId);
        tvImageLable=(TextView)view.findViewById(R.id.tvImageLable);
        edEmployee=(EditText) view.findViewById(R.id.edEmployee);
        textEmp=(TextInputLayout) view.findViewById(R.id.textEmployee);
        edRemark=(EditText)view.findViewById(R.id.edRemark);

        linearLayoutImage=(LinearLayout)view.findViewById(R.id.linearLayoutImage);

        ivCamera1 = view.findViewById(R.id.ivCamera1);
        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        tvPhoto1 = view.findViewById(R.id.tvPhoto1);

        btnSubmit.setOnClickListener(this);
        ivCamera1.setOnClickListener(this);
        tvPersonName.setOnClickListener(this);
        tvPurpose.setOnClickListener(this);


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
            model = gson.fromJson(quoteStr, VisitorList.class);
            Log.e("MODEL","-----------------------------------"+model);

            edName.setText(model.getPersonName());
            edCompany.setText(model.getPersonCompany());
            edMobile.setText(model.getMobileNo());
            edNoOfPer.setText(""+model.getExInt1());
            edRemark.setText(model.getPurposeRemark());
            tvPurpose.setText(model.getPurposeHeading());
            tvPersonName.setText(model.getEmpName());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(model==null)
        {
            linearLayoutImage.setVisibility(View.VISIBLE);
            tvImageLable.setVisibility(View.VISIBLE);
        }else{
            linearLayoutImage.setVisibility(View.GONE);
            tvImageLable.setVisibility(View.GONE);
        }

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        if(model==null)
        {
            getActivity().setTitle("Add Visitor Gate Pass");
        }else{
            getActivity().setTitle("Edit Visitor Gate Pass");
        }

        if(model==null) {
            rbAppointment.setChecked(true);
        }else{
            visitorType = model.getVisitType();
            if (visitorType==1) {
                rbAppointment.setChecked(true);
            } else if (visitorType==2) {
                rbRandom.setChecked(true);
            }
        }

        createFolder();

        getPurpose();
        getPerson();
        getGate();
        getCompany();

        spPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                purposeId = purposeIdList.get(position);
                Log.e("CUST ID","--------------------------------"+purposeId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void getCompany() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Company>> listCall = Constants.myInterface.allCompany();
            listCall.enqueue(new Callback<ArrayList<Company>>() {
                @Override
                public void onResponse(Call<ArrayList<Company>> call, Response<ArrayList<Company>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("GATE LIST : ", " - " + response.body());

                            getCompNameList.clear();
                            getCompIdList.clear();

                            getCompNameList.add("Select Company");
                            getCompIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    getCompIdList.add(response.body().get(i).getCompanyId());
                                    getCompNameList.add(response.body().get(i).getCompanyName());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getCompNameList);
                                spCompany.setAdapter(projectAdapter);

                            }
                            if (model != null) {
                                int position = 0;
                                if (getCompIdList.size() > 0) {
                                    for (int i = 0; i < getCompIdList.size(); i++) {
                                        if (model.getGateId() == getCompIdList.get(i)) {
                                            position = i;
                                            break;
                                        }
                                    }
                                    spCompany.setSelection(position);
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
                public void onFailure(Call<ArrayList<Company>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getGate() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Gate>> listCall = Constants.myInterface.allGate();
            listCall.enqueue(new Callback<ArrayList<Gate>>() {
                @Override
                public void onResponse(Call<ArrayList<Gate>> call, Response<ArrayList<Gate>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("GATE LIST : ", " - " + response.body());

                            getNameList.clear();
                            getIdList.clear();

                            getNameList.add("Select Gate");
                            getIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    getIdList.add(response.body().get(i).getGateId());
                                    getNameList.add(response.body().get(i).getGateName());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getNameList);
                                spGate.setAdapter(projectAdapter);

                            }
                            if (model != null) {
                                int position = 0;
                                if (getIdList.size() > 0) {
                                    for (int i = 0; i < getIdList.size(); i++) {
                                        if (model.getGateId() == getIdList.get(i)) {
                                            position = i;
                                            break;
                                        }
                                    }
                                    spGate.setSelection(position);
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
                public void onFailure(Call<ArrayList<Gate>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPerson() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployees();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PERSON LIST : ", " - " + response.body());

                            empNameList.clear();
                            empIdList.clear();
                            empList=response.body();

//                            empNameList.add("Select Person");
//                            empIdList.add(0);
//
//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    empIdList.add(response.body().get(i).getEmpId());
//                                    empNameList.add(response.body().get(i).getEmpFname()+ " "+response.body().get(i).getEmpMname()+ " "+response.body().get(i).getEmpSname());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
//                                spPerson.setAdapter(projectAdapter);
//
//                            }
//                            if (model != null) {
//                                int position = 0;
//                                if (empIdList.size() > 0) {
//                                    for (int i = 0; i < empIdList.size(); i++) {
//                                        if (model.getEmpIds().equalsIgnoreCase(String.valueOf(empIdList.get(i)))) {
//                                            position = i;
//                                            break;
//                                        }
//                                    }
//                                    spPerson.setSelection(position);
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
            getTypeList.add(1);

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
//                                spPurpose.setAdapter(projectAdapter);
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
//                                    spPurpose.setSelection(position);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCamera1) {

            showCameraDialog("Photo1");

        }else if(v.getId()==R.id.tvPersonName)
        {
            showDialog();

        }else if(v.getId()==R.id.tvPurpose)
        {
            showDialog1();

        }
        else if (v.getId() == R.id.btnSubmit) {

            String strVisitorName, strMob, strNoOfPerson, strEmpName, strRemark,strPhoto,strPurose,strPurposeId,strPerson,strPersonId;
            boolean isValidVisitorName = false, isValidPerson = false, isValidMob = false, isValidNoOfPerson = false, isValidPhoto = false ,isValidPurpose=false;

            strVisitorName = edName.getText().toString();
           // strCompany = edCompany.getText().toString();
            strMob = edMobile.getText().toString();
            strNoOfPerson = edNoOfPer.getText().toString();
            strEmpName = edEmployee.getText().toString();
            strRemark = edRemark.getText().toString();
            strPhoto=tvPhoto1.getText().toString();
            strPurose=tvPurpose.getText().toString();
            strPurposeId=tvPurposeId.getText().toString();
            strPerson=tvPersonName.getText().toString();
            strPersonId=tvPersonId.getText().toString();
            int purpId = 0;
            try {
                 purpId = Integer.parseInt(strPurposeId);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            int noOfper = 0;
            try {
                noOfper = Integer.parseInt(strNoOfPerson);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //final int purposeId = purposeIdList.get(spPurpose.getSelectedItemPosition());
            //final String purposeHeading = purposeHeadingList.get(spPurpose.getSelectedItemPosition());

            int compId = getCompIdList.get(spCompany.getSelectedItemPosition());
            final String companyName = getCompNameList.get(spCompany.getSelectedItemPosition());

//            if(purposeType==1) {
              //  empIds = String.valueOf(empIdList.get(spPerson.getSelectedItemPosition()));
              // int empId = empIdList.get(spPerson.getSelectedItemPosition());
              //  strEmpName = String.valueOf(empNameList.get(spPerson.getSelectedItemPosition()));
            //}
            Log.e("EMP","------------------"+empIds);
            int gateID = getIdList.get(spGate.getSelectedItemPosition());

            if (compId == 0) {
                TextView viewProj = (TextView) spCompany.getSelectedView();
                viewProj.setError("required");
            } else {
                TextView viewProj = (TextView) spCompany.getSelectedView();
                viewProj.setError(null);
            }

            if (strVisitorName.isEmpty()) {
                edName.setError("required");
            } else {
                edName.setError(null);
                isValidVisitorName = true;
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

            if (strMob.isEmpty()) {
                edMobile.setError("required");
            } else {
                edMobile.setError(null);
                isValidMob = true;
            }
            if (strNoOfPerson.isEmpty()) {
                edNoOfPer.setError("required");
            } else {
                edNoOfPer.setError(null);
                isValidNoOfPerson = true;
            }

            visitorType = 1;
            if (rbAppointment.isChecked()) {
                visitorType = 1;
            } else if (rbRandom.isChecked()) {
                visitorType = 2;
            }

            if (isValidVisitorName && isValidMob && isValidNoOfPerson && isValidPerson && isValidPurpose && compId!=0) {
                //get Time
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                //  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String Time = dateFormat.format(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                if (model == null) {
                    final Visitor visitor = new Visitor(0, sdf.format(System.currentTimeMillis()), loginUser.getEmpId(), strVisitorName, companyName, "NA", strMob, "NA", "NA", "NA", purpId, strPurose, strRemark, strPersonId, strPerson, 1, 1, 0, visitorType, Time, 0, "NA", 0, "NA", "NA", "NA", 0, 0, sdf.format(System.currentTimeMillis()), "NA", 1, 1, noOfper, 0, 0, "NA", "NA", "NA");

                    if (imagePath1 != null)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want visitor gate pass ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //saveVisitor(visitor);
                            //Log.e("VISITOR", "-----------------------" + visitor);

                            ArrayList<String> pathArray = new ArrayList<>();
                            ArrayList<String> fileNameArray = new ArrayList<>();

                            String photo1 = "";

                           // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            if (imagePath1 != null) {

                                pathArray.add(imagePath1);

                                File imgFile1 = new File(imagePath1);
                                int pos = imgFile1.getName().lastIndexOf(".");
                                String ext = imgFile1.getName().substring(pos + 1);
                                photo1 = sdf.format(Calendar.getInstance().getTimeInMillis()) + "_p1." + ext;
                                fileNameArray.add(photo1);
                            }

                            visitor.setPersonPhoto(photo1);
                            sendImage(pathArray, fileNameArray, visitor);

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
                }else{
                        Toast.makeText(getActivity(), "Please Select Person Photo", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    final Visitor visitor = new Visitor(model.getGatepassVisitorId(), model.getVisitDateIn(), model.getSecurityIdIn(), strVisitorName, companyName, model.getPersonPhoto(), strMob, "NA", "NA", "NA", purpId, strPurose, strRemark, strPersonId, strPerson, gateID, model.getGatePasstype(), model.getVisitStatus(), visitorType, model.getInTime(), model.getVisitCardId(), model.getVisitCardNo(), model.getTakeMobile(), model.getMeetingDiscussion(), "NA", model.getVisitOutTime(), model.getTotalTimeDifference(), model.getSecurityIdOut(), model.getVisitDateOut(), model.getUserSignImage(), model.getDelStatus(), model.getIsUsed(), noOfper, model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), model.getExVar3());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to edit visitor ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                                saveVisitor(visitor);
                                Log.e("Edit Visitor", "-----------------------" + visitor);

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
        int custId = intent.getIntExtra("id", 0);
        Log.e("CUSTOMER NAME : ", " " + name);
        tvPersonName.setText("" + name);
        tvPersonId.setText("" + custId);


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


    private void sendImage(ArrayList<String> filePath, ArrayList<String> fileName, final Visitor visitor) {

        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName  +"           VISITOR      "+visitor);

        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = null;

        MultipartBody.Part[] uploadImagesParts = new MultipartBody.Part[filePath.size()];

        for (int index = 0; index < filePath.size(); index++) {
            Log.e("ATTACH ACT", "requestUpload:  image " + index + "  " + filePath.get(index));
            imgFile = new File(filePath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            uploadImagesParts[index] = MultipartBody.Part.createFormData("file", "" + fileName.get(index), surveyBody);
        }

        // RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), "photo1");
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<JSONObject> call = Constants.myInterface.imageUpload(uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();

                imagePath1 = null;

                Log.e("Response : ", "--" + response.body());
                saveVisitor(visitor);
                commonDialog.dismiss();

            }
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void saveVisitor(Visitor visitor) {
        Log.e("PARAMETER","---------------------------------------VISITOR--------------------------"+visitor);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Visitor> listCall = Constants.myInterface.saveGatepassVisitor(visitor);
            listCall.enqueue(new Callback<Visitor>() {
                @Override
                public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE VISITOR : ", " ------------------------------SAVE VISITOR------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
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
                public void onFailure(Call<Visitor> call, Throwable t) {
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


    //------------------------------------------IMAGE-----------------------------------------------


    public void showCameraDialog(final String type) {
       // android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
       // builder.setTitle("Choose");
//        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (type.equalsIgnoreCase("Photo1")) {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, 101);
//                }
//            }
//        });
       // builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
          //  @Override
          //  public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        }

                    } else {

                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        }

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
           // }
      //  });
       // builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto1.setImageBitmap(myBitmap1);

                    myBitmap1 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath1 = f.getAbsolutePath();
                tvPhoto1.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap1 = getBitmapFromCameraData(data, getContext());

                ivPhoto1.setImageBitmap(myBitmap1);
                imagePath1 = uriFromPath.getPath();
                tvPhoto1.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    //Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path1 = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
}
