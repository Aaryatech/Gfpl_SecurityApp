package com.ats.gfpl_securityapp.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ats.gfpl_securityapp.constants.Constants;
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

public class MaintenanceGatePassFragment extends Fragment implements View.OnClickListener{
    private EditText edName, edMobile,edCompany,edNoOfPer,edEmployee,edRemark;
    private TextInputLayout textEmp;
    private TextView tvPerson,tvImageLable;
    private Spinner spPurpose, spPerson,spGate;
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

    ArrayList<String> empNameList = new ArrayList<>();
    ArrayList<Integer> empIdList = new ArrayList<>();

    ArrayList<String> getNameList = new ArrayList<>();
    ArrayList<Integer> getIdList = new ArrayList<>();
    int visitorType;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;

    Bitmap myBitmap1 = null;
    public static String path1, imagePath1 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance_gate_pass, container, false);
        //getActivity().setTitle("Maintenance Gate Pass");
        edName = view.findViewById(R.id.edName);
        edMobile = view.findViewById(R.id.edMobile);
        spPurpose = view.findViewById(R.id.spPurpose);
        spPerson = view.findViewById(R.id.spPerson);
        spGate = view.findViewById(R.id.spGate);
        rbAppointment = view.findViewById(R.id.rbAppointment);
        rbRandom = view.findViewById(R.id.rbRandom);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        edCompany = view.findViewById(R.id.edCompany);
        edNoOfPer = view.findViewById(R.id.edNoOfPer);
        tvPerson=(TextView)view.findViewById(R.id.tvPerson);
        edEmployee=(EditText) view.findViewById(R.id.edEmployee);
        textEmp=(TextInputLayout) view.findViewById(R.id.textEmployee);
        edRemark=(EditText)view.findViewById(R.id.edRemark);

        tvImageLable=(TextView)view.findViewById(R.id.tvImageLable);
        linearLayoutImage=(LinearLayout)view.findViewById(R.id.linearLayoutImage);

        ivCamera1 = view.findViewById(R.id.ivCamera1);
        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        tvPhoto1 = view.findViewById(R.id.tvPhoto1);

        btnSubmit.setOnClickListener(this);
        ivCamera1.setOnClickListener(this);

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
            getActivity().setTitle("Add Maintenance Gate Pass");
        }else{
            getActivity().setTitle("Edit Maintenance Gate Pass");
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

        spPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                purposeId = purposeIdList.get(position);
                Log.e("CUST ID","--------------------------------"+purposeId);
                if(purposeList!=null) {
                    for (int j = 0; j < purposeList.size(); j++) {

                        if (purposeList.get(j).getPurposeId() == purposeId) {

                            purposeType=purposeList.get(j).getPurposeType();
                            Log.e("TYPE", "----------------"+purposeType);
                            edEmployee.setText(purposeList.get(j).getAssignEmpName());
                            Log.e("EMP NAME", "----------------"+purposeList.get(j).getAssignEmpName());
                            empIds= purposeList.get(j).getEmpId();
                            Log.e("EMP ID", "----------------"+empIds);
//                            if(purposeList.get(j).getPurposeType()==1)
//                            {
//                                Log.e("hiii", "----------------"+purposeList.get(j).getPurposeType());
//                                tvPerson.setVisibility(View.VISIBLE);
//                                spPerson.setVisibility(View.VISIBLE);
//                                edEmployee.setVisibility(View.GONE);
//                                textEmp.setVisibility(View.GONE);
//
//                            }else if(purposeList.get(j).getPurposeType()==2) {
//                                Log.e("hiii111", "----------------"+purposeList.get(j).getPurposeType());
//                                tvPerson.setVisibility(View.GONE);
//                                spPerson.setVisibility(View.GONE);
//                                edEmployee.setVisibility(View.VISIBLE);
//                                textEmp.setVisibility(View.VISIBLE);
//                                edEmployee.setText(purposeList.get(j).getAssignEmpName());
//                                empIds= purposeList.get(j).getEmpId();
//
//                            }

                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return view;
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

                            empNameList.add("Select Person");
                            empIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    empIdList.add(response.body().get(i).getEmpId());
                                    empNameList.add(response.body().get(i).getEmpFname()+ " "+response.body().get(i).getEmpMname()+ " "+response.body().get(i).getEmpSname());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empNameList);
                                spPerson.setAdapter(projectAdapter);

                            }

                            if (model != null) {
                                int position = 0;
                                if (empIdList.size() > 0) {
                                    for (int i = 0; i < empIdList.size(); i++) {
                                        if (model.getEmpIds().equalsIgnoreCase(String.valueOf(empIdList.get(i)))) {
                                            position = i;
                                            break;
                                        }
                                    }
                                    spPerson.setSelection(position);

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
            getTypeList.add(2);

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

                            purposeHeadingList.add("Select Purpose");
                            purposeIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    purposeIdList.add(response.body().get(i).getPurposeId());
                                    purposeHeadingList.add(response.body().get(i).getPurposeHeading());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, purposeHeadingList);
                                spPurpose.setAdapter(projectAdapter);

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
                                    spPurpose.setSelection(position);

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCamera1) {

            showCameraDialog("Photo1");

        } else if (v.getId() == R.id.btnSubmit) {

            String strVisitorName, strCompany, strMob, strNoOfPerson, strEmpName, strRemark;
            boolean isValidVisitorName = false, isValidCompany = false, isValidMob = false, isValidNoOfPerson = false, isValidRemark = false;

            strVisitorName = edName.getText().toString();
            strCompany = edCompany.getText().toString();
            strMob = edMobile.getText().toString();
            strNoOfPerson = edNoOfPer.getText().toString();
            strEmpName = edEmployee.getText().toString();
            strRemark = edRemark.getText().toString();
            int noOfper = 0;
            try {
                noOfper = Integer.parseInt(strNoOfPerson);

            } catch (Exception e) {
                e.printStackTrace();
            }

            final int purposeId = purposeIdList.get(spPurpose.getSelectedItemPosition());
            final String purposeHeading = purposeHeadingList.get(spPurpose.getSelectedItemPosition());

//            if(purposeType==1) {
//                empIds = String.valueOf(empIdList.get(spPerson.getSelectedItemPosition()));
//                strEmpName = String.valueOf(empNameList.get(spPerson.getSelectedItemPosition()));
//            }
            Log.e("EMP SUB","------------------"+empIds);
            Log.e("EMP NAE SUB","------------------"+strEmpName);
            int gateID = getIdList.get(spGate.getSelectedItemPosition());

//            if (gateID == 0) {
//                TextView viewProj = (TextView) spGate.getSelectedView();
//                viewProj.setError("required");
//            } else {
//                TextView viewProj = (TextView) spGate.getSelectedView();
//                viewProj.setError(null);
//            }

            if (purposeId == 0) {
                TextView viewProj = (TextView) spPurpose.getSelectedView();
                viewProj.setError("required");
            } else {
                TextView viewProj = (TextView) spPurpose.getSelectedView();
                viewProj.setError(null);
            }

            if (strVisitorName.isEmpty()) {
                edName.setError("required");
            } else {
                edName.setError(null);
                isValidVisitorName = true;
            }
            if (strCompany.isEmpty()) {
                edCompany.setError("required");
            } else {
                edCompany.setError(null);
                isValidCompany = true;
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
//            if (strRemark.isEmpty()) {
//                edRemark.setError("required");
//            } else {
//                edRemark.setError(null);
//                isValidRemark = true;
//            }

            //int
            visitorType = 1;
            if (rbAppointment.isChecked()) {
                visitorType = 1;
            } else if (rbRandom.isChecked()) {
                visitorType = 2;
            }

            if (isValidVisitorName && isValidCompany && isValidMob && isValidNoOfPerson && purposeId!=0) {
                //get Time
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                //  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String Time = dateFormat.format(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                if (model == null) {
                    final Visitor visitor = new Visitor(0, sdf.format(System.currentTimeMillis()), loginUser.getEmpId(), strVisitorName, strCompany, "NA", strMob, "NA", "NA", "NA", purposeId, purposeHeading, strRemark, empIds, strEmpName, 1, 2, 0, visitorType, Time, 0, "NA", 0, "NA", "NA", "NA", 0, 0, sdf.format(System.currentTimeMillis()), "NA", 1, 1, noOfper, 0, 0, "NA", "NA", "NA");
                    if(imagePath1!=null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Do you want Maintenance gate pass?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //saveMaintenance(visitor);
                                // Log.e("MAINTENANCE", "-----------------------" + visitor);

                                ArrayList<String> pathArray = new ArrayList<>();
                                ArrayList<String> fileNameArray = new ArrayList<>();

                                String photo1 = "", photo2 = "", photo3 = "";

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
                    final Visitor visitor = new Visitor(model.getGatepassVisitorId(), model.getVisitDateIn(), model.getSecurityIdIn(), strVisitorName, strCompany, model.getPersonPhoto(), strMob, "NA", "NA", "NA", purposeId, purposeHeading, strRemark, empIds, strEmpName, gateID, model.getGatePasstype(), model.getVisitStatus(), visitorType, model.getInTime(), model.getVisitCardId(), model.getVisitCardNo(), model.getTakeMobile(), model.getMeetingDiscussion(), "NA", model.getVisitOutTime(), model.getTotalTimeDifference(), model.getSecurityIdOut(), model.getVisitDateOut(), model.getUserSignImage(), model.getDelStatus(), model.getIsUsed(), noOfper, model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), model.getExVar3());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to edit Maintenance ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveMaintenance(visitor);
                            Log.e("MAINTENANCE", "-----------------------" + visitor);

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
                saveMaintenance(visitor);
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

    private void saveMaintenance(Visitor visitor) {
        Log.e("PARAMETER","---------------------------------------MAINTENANCE--------------------------"+visitor);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Visitor> listCall = Constants.myInterface.saveGatepassVisitor(visitor);
            listCall.enqueue(new Callback<Visitor>() {
                @Override
                public void onResponse(Call<Visitor> call, Response<Visitor> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE MAINTENANCE : ", " ------------------------------SAVE MAINTENANCE------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
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
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (type.equalsIgnoreCase("Photo1")) {
                    Intent pictureActionIntent = null;
                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pictureActionIntent, 101);
                }
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" +  Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        }

                    } else {

                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis() + "_p1.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        }

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
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
