package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.BuildConfig;
import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.ItemListDialogAdapter;
import com.ats.gfpl_securityapp.adapter.PartyListDialogAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Item;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Material;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.ats.gfpl_securityapp.model.Party;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.ats.gfpl_securityapp.utils.RealPathUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
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

public class InwardGatePassFragment extends Fragment implements View.OnClickListener {

    private RadioButton rbInward, rbParcel;
    private EditText edInvoice, edNugs,edFromDate,edParty,edItem;
    private ImageView ivCamera1, ivCamera2, ivCamera3, ivPhoto1, ivPhoto2, ivPhoto3;
    private Spinner spParty, spItem;
    private Button btnSubmit;
    private RadioGroup rg;
    private TextInputLayout textInputItem,textInputParty;
    private LinearLayout linearLayout_photo1,LinearLayout_photo2;
    private TextView tvPhoto1,tvPhoto2,tvPhoto3,tvPhoto1lable,tvPhoto2label,tvPartyName,tvPartyId,tvItemName,tvItemId,tvPartyNameLable,tvItemNameLable;
    private View tvItemView,tvPartyView;
    private int gatePassType;
    Login loginUser;
    MaterialDetail model;
    int materialType;

    String selectedText;
    Material material = null;


    Dialog dialog;
    private BroadcastReceiver mBroadcastReceiver;
    PartyListDialogAdapter partyAdapter;
    ItemListDialogAdapter itemAdapter;

    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;


    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;

    Bitmap myBitmap1 = null, myBitmap2 = null, myBitmap3 = null;
    public static String path1, imagePath1 = null, imagePath2 = null, imagePath3 = null;

    ArrayList<String> partyNameList = new ArrayList<>();
    ArrayList<Integer> partyIdList = new ArrayList<>();
    ArrayList<Party> partyList = new ArrayList<>();

   // ArrayList<String> itemList = new ArrayList<>();
    ArrayList<Integer> itemIdList = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inward_gate_pass, container, false);
        getActivity().setTitle("Material Inward");

        rbInward = view.findViewById(R.id.rbInward);
        rbParcel = view.findViewById(R.id.rbParcel);
        edInvoice = view.findViewById(R.id.edInvoice);
        edNugs = view.findViewById(R.id.edNugs);
        ivCamera1 = view.findViewById(R.id.ivCamera1);
        ivCamera2 = view.findViewById(R.id.ivCamera2);
        ivCamera3 = view.findViewById(R.id.ivCamera3);
        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);
        spParty = view.findViewById(R.id.spParty);
        spItem = view.findViewById(R.id.spItem);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        edFromDate = view.findViewById(R.id.edFromDate);
        rg=view.findViewById(R.id.rg);

        tvPartyName = view.findViewById(R.id.tvPartyName);
        tvPartyNameLable = view.findViewById(R.id.tvPartyNameLable);
        tvPartyId = view.findViewById(R.id.tvPartyId);
        tvPartyView = view.findViewById(R.id.tvPartyView);

        tvItemName = view.findViewById(R.id.tvItemName);
        tvItemNameLable = view.findViewById(R.id.tvItemNameLable);
        tvItemId = view.findViewById(R.id.tvItemId);
        tvItemView = view.findViewById(R.id.tvItemView);


        edParty=view.findViewById(R.id.edParty);
        edItem=view.findViewById(R.id.edItem);
        textInputItem=view.findViewById(R.id.textInputItem);
        textInputParty=view.findViewById(R.id.textInputParty);


        tvPhoto1 = view.findViewById(R.id.tvPhoto1);
        tvPhoto2 = view.findViewById(R.id.tvPhoto2);
        tvPhoto3 = view.findViewById(R.id.tvPhoto3);

        tvPhoto1lable = view.findViewById(R.id.tvPhoto1lable);
        tvPhoto2label = view.findViewById(R.id.tvPhoto2lable);

        linearLayout_photo1 = view.findViewById(R.id.linearLayout_photo1);
        LinearLayout_photo2 = view.findViewById(R.id.linearLayout_photo2);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int idx = group.indexOfChild(radioButton);
                RadioButton r = (RadioButton) group.getChildAt(idx);
                selectedText = r.getText().toString();
                Log.e(" Radio", "----------" + idx);
                Log.e(" Radio Text", "----------" + selectedText);

                if(selectedText.equalsIgnoreCase("Inward"))
                {
                    tvPartyName.setVisibility(View.VISIBLE);
                    tvPartyNameLable.setVisibility(View.VISIBLE);
                    tvPartyId.setVisibility(View.GONE);
                    tvPartyView.setVisibility(View.VISIBLE);

                    tvItemName.setVisibility(View.VISIBLE);
                    tvItemNameLable.setVisibility(View.VISIBLE);
                    tvItemId.setVisibility(View.GONE);
                    tvItemView.setVisibility(View.VISIBLE);


                    edParty.setVisibility(View.GONE);
                    edItem.setVisibility(View.GONE);
                    textInputItem.setVisibility(View.GONE);
                    textInputParty.setVisibility(View.GONE);


                }else if(selectedText.equalsIgnoreCase("Parcel"))
                {
                    tvPartyName.setVisibility(View.GONE);
                    tvPartyNameLable.setVisibility(View.GONE);
                    tvPartyId.setVisibility(View.GONE);
                    tvPartyView.setVisibility(View.GONE);

                    tvItemName.setVisibility(View.GONE);
                    tvItemNameLable.setVisibility(View.GONE);
                    tvItemId.setVisibility(View.GONE);
                    tvItemView.setVisibility(View.GONE);

                    edParty.setVisibility(View.VISIBLE);
                    edItem.setVisibility(View.VISIBLE);
                    textInputItem.setVisibility(View.VISIBLE);
                    textInputParty.setVisibility(View.VISIBLE);


                }
            }
        });

        ivCamera1.setOnClickListener(this);
        ivCamera2.setOnClickListener(this);
        ivCamera3.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvPartyName.setOnClickListener(this);
        tvItemName.setOnClickListener(this);
        edFromDate.setOnClickListener(this);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);

        fromDateMillis = todayDate.getTime();
        toDateMillis = todayDate.getTime();

        //  Log.e("Mytag", "todayString" + currentDate);
        edFromDate.setText(currentDate);

        getAllItem();
        getAllPart();

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
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

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, MaterialDetail.class);
            Log.e("MODEL MATERIAL","-----------------------------------"+model);

           edNugs.setText(""+model.getNoOfNugs());
           edInvoice.setText(""+model.getInvoiceNumber());
           tvPartyName.setText(""+model.getPartyName());
           tvItemName.setText(""+model.getExVar2());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(model==null)
        {
            getActivity().setTitle("Add Material Gate Pass");
            tvPhoto1lable.setVisibility(View.VISIBLE);
            tvPhoto2label.setVisibility(View.VISIBLE);
            linearLayout_photo1.setVisibility(View.VISIBLE);
            LinearLayout_photo2.setVisibility(View.VISIBLE);

        }else{
            getActivity().setTitle("Edit Material Gate Pass");
            tvPhoto1lable.setVisibility(View.GONE);
            tvPhoto2label.setVisibility(View.GONE);
            linearLayout_photo1.setVisibility(View.GONE);
            LinearLayout_photo2.setVisibility(View.GONE);
        }

        if(model==null) {
            rbInward.setChecked(true);
        }else{
            materialType = model.getGatePassSubType();
            if (materialType==1) {
                rbInward.setChecked(true);
            } else if (materialType==2) {
                rbParcel.setChecked(true);

            }
        }

        createFolder();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
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

    private void getAllPart() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
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
                            partyList=response.body();

//                            partyNameList.add("Select Party");
//                            partyIdList.add(0);
//
//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    partyIdList.add(response.body().get(i).getVendorId());
//                                    partyNameList.add(response.body().get(i).getVendorName());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, partyNameList);
//                                spParty.setAdapter(projectAdapter);
//
//                            }
//                            if (model != null) {
//                                int position = 0;
//                                if (partyIdList.size() > 0) {
//                                    for (int i = 0; i < partyIdList.size(); i++) {
//                                        if (model.getPartyId().equals(partyIdList.get(i))) {
//                                            position = i;
//                                            break;
//                                        }
//                                    }
//                                    spParty.setSelection(position);
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

    private void getAllItem() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Item>> listCall = Constants.myInterface1.getAllItems();
            listCall.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ITEM LIST : ", " - " + response.body());

                            itemIdList.clear();
                            itemList=response.body();
                            Log.e("ITEM ","------------------"+itemList);

//                            itemList.add("Select Item");
//                            itemIdList.add(0);
//
//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    itemIdList.add(response.body().get(i).getItemId());
//                                    itemList.add(response.body().get(i).getItemDesc());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemList);
//                                spItem.setAdapter(projectAdapter);
//
//                            }
//
//                            if (model != null) {
//                                int position = 0;
//                                if (itemIdList.size() > 0) {
//                                    for (int i = 0; i < itemIdList.size(); i++) {
//                                        if (model.getItemType()==itemIdList.get(i)) {
//                                            position = i;
//                                            break;
//                                        }
//                                    }
//                                    spItem.setSelection(position);
//
//                                }
//                            }
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null1 : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception1 : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "-----------" + t.getMessage());
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

        } else if (v.getId() == R.id.ivCamera2) {

            showCameraDialog("Photo2");

        } else if (v.getId() == R.id.ivCamera3) {

            showCameraDialog("Photo3");

        } else if (v.getId() == R.id.tvPartyName) {

            showDialog();

        }else if (v.getId() == R.id.tvItemName) {

            showDialog1();

        }else if(v.getId()==R.id.edFromDate)
        {
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
            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
           // dialog.getDatePicker().setMinDate(minDate);
            dialog.show();
        }
        else if (v.getId() == R.id.btnSubmit) {
            String strInvoiceNumber,strNoOfNugs,strPartyId,strItemId,strFromDate,strParty,strItem,strEdParty,strEdItem;
            boolean isValidInvoiceNumber = false,isvalidNoNuges = false,isvalidParty = false,isvalidItem = false;

            strInvoiceNumber=edInvoice.getText().toString();
            strParty=tvPartyName.getText().toString();
            strEdParty=edParty.getText().toString();
            strPartyId=tvPartyId.getText().toString();
            strItem=tvItemName.getText().toString();
            strEdItem=edItem.getText().toString();
            strItemId=tvItemId.getText().toString();
            strNoOfNugs=edNugs.getText().toString();
            strFromDate = edFromDate.getText().toString();

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;
            try {
                ToDate = formatter1.parse(strFromDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String inwardDate = formatter3.format(ToDate);
            Log.e("Date Formate","--------------------"+inwardDate);

            float noOfNuges = 0;
            int partyId = 0,itemId=0;
            try {
                 noOfNuges = Float.parseFloat(strNoOfNugs);
                  partyId= Integer.parseInt(strPartyId);
                itemId= Integer.parseInt(strItemId);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            gatePassType = 1;
            if (rbInward.isChecked()) {
                gatePassType = 1;
            } else if (rbParcel.isChecked()) {
                gatePassType = 2;
            }

            if (strInvoiceNumber.isEmpty()) {
                edInvoice.setError("required");
            } else {
                edInvoice.setError(null);
                isValidInvoiceNumber = true;
            }
            if (strNoOfNugs.isEmpty()) {
                edNugs.setError("required");
            } else {
                edNugs.setError(null);
                isvalidNoNuges = true;
            }

            if(gatePassType==1) {
                if (strParty.isEmpty()) {
                    tvPartyName.setError("required");
                } else {
                    tvPartyName.setError(null);
                    isvalidParty = true;
                }
                if (strItem.isEmpty()) {
                    tvItemName.setError("required");
                } else {
                    tvItemName.setError(null);
                    isvalidItem = true;
                }
            }else if(gatePassType==2)
            {
                if (strEdParty.isEmpty()) {
                    edParty.setError("required");
                } else {
                    edParty.setError(null);
                    isvalidParty = true;
                }
                if (strEdItem.isEmpty()) {
                    edItem.setError("required");
                } else {
                    edItem.setError(null);
                    isvalidItem = true;
                }
            }


            //int partyId = partyIdList.get(spParty.getSelectedItemPosition());
            //int itemId = itemIdList.get(spItem.getSelectedItemPosition());
            //String partyName = partyNameList.get(spParty.getSelectedItemPosition());

            if(isValidInvoiceNumber && isvalidNoNuges && isvalidParty &&isvalidItem)
            {
                Calendar calender = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm");
                String strTime = mdformat.format(calender.getTime());
                Log.e("Current time", "---------------------" + strTime);

                if (model == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //sdf.format(System.currentTimeMillis())
                    if(gatePassType==1) {
                         material = new Material(0, inwardDate, 1, gatePassType, strInvoiceNumber, strParty, partyId, loginUser.getEmpId(), loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), "", "", strTime, noOfNuges, itemId, 1, 0, loginUser.getEmpId(), loginUser.getEmpDeptId(), 0, loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), "", 0, 0, 0, "", strItem, "");
                    }else if(gatePassType==2) {
                         material = new Material(0, inwardDate, 1, gatePassType, strInvoiceNumber, strEdParty, partyId, loginUser.getEmpId(), loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), "", "", strTime, noOfNuges, itemId, 1, 0, loginUser.getEmpId(), loginUser.getEmpDeptId(), 0, loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), "", 0, 0, 0, "", strEdItem, "");
                    }
                    if (imagePath1 != null && imagePath2 != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Do you want to submit?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //saveVisitor(visitor);
                                //Log.e("VISITOR", "-----------------------" + visitor);

                                ArrayList<String> pathArray = new ArrayList<>();
                                ArrayList<String> fileNameArray = new ArrayList<>();

                                String photo1 = "", photo2 = "";

                               // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                if (imagePath1 != null) {

                                    pathArray.add(imagePath1);

                                    File imgFile1 = new File(imagePath1);
                                    int pos = imgFile1.getName().lastIndexOf(".");
                                    String ext = imgFile1.getName().substring(pos + 1);
                                    photo1 = sdf.format(System.currentTimeMillis()) +"_ProfilePhoto"+ "_p1." + ext;
                                    fileNameArray.add(photo1);
                                }

                                if (imagePath2 != null) {

                                    pathArray.add(imagePath2);

                                    File imgFile1 = new File(imagePath2);
                                    int pos = imgFile1.getName().lastIndexOf(".");
                                    String ext = imgFile1.getName().substring(pos + 1);
                                    photo2 = sdf.format(System.currentTimeMillis()) +"_InwardPhoto"+"_p1." + ext;
                                    fileNameArray.add(photo2);
                                }

                                material.setPersonPhoto(photo1);
                                material.setInwardPhoto(photo2);
                                sendImage(pathArray, fileNameArray, material);
                                Log.e("Material Bin", "------------------------------" + material);

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
                        Toast.makeText(getActivity(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                    }
                }else {
                     material = new Material(model.getGatepassInwardId(), model.getInwardDate(), model.getGatePassType(), gatePassType, strInvoiceNumber, strParty, partyId, model.getSecurityId(), model.getSecurityName(), model.getPersonPhoto(), model.getInwardPhoto(), strTime, noOfNuges, itemId, model.getDelStatus(), model.getStatus(), model.getToEmpId(), model.getToDeptId(), model.getToStatus(), model.getToEmpName(), model.getToDeptName(), model.getExInt1(), model.getExInt2(), model.getExInt3(), "",strItem,"");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to edit Material gate pass ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveMaterial(material);
                            Log.e("MATERIAL GATE PASS EDIT", "-----------------------" + material);

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
           // calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();


        }
    };


    private void showDialog1() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);

        itemAdapter = new ItemListDialogAdapter(itemList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(itemAdapter);

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
                    if (itemAdapter != null) {
                        filterCustomer1(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    void filterCustomer1(String text) {
        ArrayList<Item> temp1 = new ArrayList();
        for (Item d : itemList) {
            if (d.getItemDesc().toLowerCase().contains(text.toLowerCase())) {
                temp1.add(d);
            }
        }
        //update recyclerview
        itemAdapter.updateList(temp1);
    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String name = intent.getStringExtra("name");
        int custId = intent.getIntExtra("id", 0);
        Log.e("CUSTOMER NAME : ", " " + name);
        tvItemName.setText("" + name);
        tvItemId.setText("" + custId);

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

        partyAdapter = new PartyListDialogAdapter(partyList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(partyAdapter);

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
                    if (partyAdapter != null) {
                        filterCustomer(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    void filterCustomer(String text) {
        ArrayList<Party> temp = new ArrayList();
        for (Party d : partyList) {
            if (d.getVendorName().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        partyAdapter.updateList(temp);
    }

    private void handlePushNotification(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String name = intent.getStringExtra("name");
        int custId = intent.getIntExtra("id", 0);
        Log.e("CUSTOMER NAME : ", " " + name);
        tvPartyName.setText("" + name);
        tvPartyId.setText("" + custId);

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


    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final Material material) {
        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName);

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
                imagePath2 = null;
               
                Log.e("Response : ", "--" + response.body());
                saveMaterial(material);
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

    private void saveMaterial(Material material) {
        Log.e("PARAMETER","---------------------------------------MATERIAL--------------------------"+material);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Material> listCall = Constants.myInterface.saveMaterialGatepass(material);
            listCall.enqueue(new Callback<Material>() {
                @Override
                public void onResponse(Call<Material> call, Response<Material> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE MATERIAL : ", " ------------------------------SAVE MATERIAL------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
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
                public void onFailure(Call<Material> call, Throwable t) {
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


    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }


    //------------------------------------------IMAGE-----------------------------------------------


    public void showCameraDialog(final String type) {
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
//        builder.setTitle("Choose");
//        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (type.equalsIgnoreCase("Photo1")) {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, 101);
//                } else if (type.equalsIgnoreCase("Photo2")) {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, 201);
//                } else if (type.equalsIgnoreCase("Photo3")) {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, 301);
//                }
//            }
//        });
//        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        } else if (type.equalsIgnoreCase("Photo3")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p3.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 302);
                        }

                    } else {

                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        } else if (type.equalsIgnoreCase("Photo3")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p3.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 302);
                        }

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
           // }
       // });
        //builder.show();
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
        } else if (resultCode == RESULT_OK && requestCode == 202) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap2 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto2.setImageBitmap(myBitmap2);

                    myBitmap2 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath2 = f.getAbsolutePath();
                tvPhoto2.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 201) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap2 = getBitmapFromCameraData(data, getContext());

                ivPhoto2.setImageBitmap(myBitmap2);
                imagePath2 = uriFromPath.getPath();
                tvPhoto2.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
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
        } else if (resultCode == RESULT_OK && requestCode == 302) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap3 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto3.setImageBitmap(myBitmap3);

                    myBitmap3 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap3.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath3 = f.getAbsolutePath();
                tvPhoto3.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 301) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap3 = getBitmapFromCameraData(data, getContext());

                ivPhoto3.setImageBitmap(myBitmap3);
                imagePath3 = uriFromPath.getPath();
                tvPhoto3.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap3.compress(Bitmap.CompressFormat.PNG, 100, out);
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
