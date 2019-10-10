package com.ats.gfpl_securityapp.fragment;


import android.app.Dialog;
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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
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
import android.widget.CheckBox;
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
import com.ats.gfpl_securityapp.model.VisitCard;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.model.VisitorMaster;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddInfoFragment extends Fragment implements View.OnClickListener {

    private ImageView ivCamera1, ivCamera2, ivCamera3, ivPhoto1, ivPhoto2, ivPhoto3;
    private Spinner spCard;
    private Button btnSubmit;
    private RadioButton rbYes, rbNo;
    private TextView tvPhoto1, tvPhoto2, tvPhoto3,tvCard,tvCardId;
    private RecyclerView recyclerView;
    VisitorList model;
    int submitMob;
    String type,strCardId;
    String stringId,stringName;

    ArrayList<String> cardNumberList = new ArrayList<>();
    ArrayList<Integer> cardIdList = new ArrayList<>();
    ArrayList<VisitCard> cardList = new ArrayList<>();

    Dialog dialog;
    int deptId;
    CardListDialogAdapter cardAdapter;
    public static ArrayList<Employee> assignCardList = new ArrayList<>();

    public static ArrayList<VisitCard> assignCardNoStaticList = new ArrayList<>();
    ArrayList<VisitorMaster> visitorList = new ArrayList<>();
    VisitorMaster visitorMaster;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;

    Bitmap myBitmap1 = null, myBitmap2 = null, myBitmap3 = null;
    public static String path1, imagePath1 = null, imagePath2 = null, imagePath3 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_info, container, false);
        getActivity().setTitle("Add Information");

        ivCamera1 = view.findViewById(R.id.ivCamera1);
        ivCamera2 = view.findViewById(R.id.ivCamera2);
        ivCamera3 = view.findViewById(R.id.ivCamera3);
        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);
        spCard = view.findViewById(R.id.spCard);
        tvCard = view.findViewById(R.id.tvCard);
        tvCardId = view.findViewById(R.id.tvCardId);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        tvPhoto1 = view.findViewById(R.id.tvPhoto1);
        tvPhoto2 = view.findViewById(R.id.tvPhoto2);
        tvPhoto3 = view.findViewById(R.id.tvPhoto3);
      //  recyclerView = view.findViewById(R.id.recyclerView);

        ivCamera1.setOnClickListener(this);
        ivCamera2.setOnClickListener(this);
        ivCamera3.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvCard.setOnClickListener(this);

        //rbYes.setChecked(true);

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, VisitorList.class);
            Log.e("MODEL INFO", "-----------------------------------" + model);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


       try {
            type = getArguments().getString("type");
           Log.e("TYPE INFO", "-----------------------------------" + type);
       }catch (Exception e)
       {
           e.printStackTrace();
       }

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();
        getCardNumber();
        getVisitorList(model.getExInt2());

        return view;
    }

    private void getVisitorList(Integer visitorId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitorMaster> listCall = Constants.myInterface.getVisitorById(visitorId);
            listCall.enqueue(new Callback<VisitorMaster>() {
                @Override
                public void onResponse(Call<VisitorMaster> call, Response<VisitorMaster> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("VISITOR LIST : ", " ----------------------------INFO VISITOR-------------------------------- " + response.body());
                            visitorMaster = response.body();

                            try {
                                String imageUri = String.valueOf(visitorMaster.getIdProof());
                                Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
                                Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.ic_photo)).into(ivPhoto1);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                String imageUri = String.valueOf(visitorMaster.getIdProof1());
                                Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
                                Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.ic_photo)).into(ivPhoto2);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                String imageUri = String.valueOf(visitorMaster.getOtherPhoto());
                                Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
                                Picasso.with(getActivity()).load(Constants.IMAGE_URL+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.ic_photo)).into(ivPhoto3);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            tvPhoto1.setText(visitorMaster.getIdProof());
                            tvPhoto2.setText(visitorMaster.getIdProof1());
                            tvPhoto3.setText(visitorMaster.getOtherPhoto());

                            if(visitorMaster.getTakeMobile().equalsIgnoreCase("Yes"))
                            {
                                rbYes.setChecked(true);
                            }else if(visitorMaster.getTakeMobile().equalsIgnoreCase("No"))
                            {
                                rbNo.setChecked(true);
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
                public void onFailure(Call<VisitorMaster> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCardNumber() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<VisitCard>> listCall = Constants.myInterface.allVisitCard();
            listCall.enqueue(new Callback<ArrayList<VisitCard>>() {
                @Override
                public void onResponse(Call<ArrayList<VisitCard>> call, Response<ArrayList<VisitCard>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CARD NUMBER LIST : ", " - " + response.body());

                            cardNumberList.clear();
                            cardIdList.clear();
                            cardList.clear();
                            cardList=response.body();

                            assignCardNoStaticList.clear();
                            assignCardNoStaticList = cardList;


                            for (int i = 0; i < assignCardNoStaticList.size(); i++) {
                                assignCardNoStaticList.get(i).setChecked(false);
                            }


//                            CardAdapter mAdapter = new CardAdapter(assignCardNoStaticList, getActivity());
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(mAdapter);

//                            cardNumberList.add("Select Card Number");
////                            cardIdList.add(0);

//                            if (response.body().size() > 0) {
//                                for (int i = 0; i < response.body().size(); i++) {
//                                    cardIdList.add(response.body().get(i).getCardId());
//                                    cardNumberList.add(response.body().get(i).getCardNumber());
//                                }
//
//                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cardNumberList);
//                                spCard.setAdapter(projectAdapter);
//
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
                public void onFailure(Call<ArrayList<VisitCard>> call, Throwable t) {
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

        } else if (v.getId() == R.id.ivCamera2) {

            showCameraDialog("Photo2");

        } else if (v.getId() == R.id.ivCamera3) {

            showCameraDialog("Photo3");

        }else if(v.getId()==R.id.tvCard)
        {
            showDialog();
        }
        else if (v.getId() == R.id.btnSubmit) {

           // getAssignUser();

            submitMob = 1;
            if (rbYes.isChecked()) {
                submitMob = 1;
            } else if (rbNo.isChecked()) {
                submitMob = 2;
            }
            int cardID = 0;
            String cardNumber = null;
            try {
                cardID = cardIdList.get(spCard.getSelectedItemPosition());
                cardNumber = cardNumberList.get(spCard.getSelectedItemPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String Photo1=tvPhoto1.getText().toString();
            String Photo2=tvPhoto2.getText().toString();
            String Photo3=tvPhoto3.getText().toString();

            final VisitorList visitor1 = new VisitorList(model.getGatepassVisitorId(), model.getVisitDateIn(), model.getSecurityIdIn(), model.getPersonName(), model.getPersonCompany(), model.getPersonPhoto(), model.getMobileNo(), Photo1, Photo2, Photo3, model.getPurposeId(), model.getPurposeHeading(), model.getPurposeRemark(), model.getEmpIds(), model.getEmpName(), model.getGateId(), model.getGatePasstype(), 3, model.getVisitType(), model.getInTime(), 1, "1", submitMob, model.getMeetingDiscussion(), "", model.getVisitOutTime(), (int) model.getTotalTimeDifference(), model.getSecurityIdOut(), model.getVisitDateOut(), model.getUserSignImage(), model.getDelStatus(), model.getIsUsed(), model.getExInt1(), model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), stringId, model.getSecurityInName(), model.getSecurityOutName(), model.getPurposeHeading(), model.getGateName(), model.getAssignEmpName());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to add info ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                       // sendImage(pathArray, fileNameArray, visitor1);
                        saveVisitor(visitor1);

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

           // }
           // }
        }
    }

    private void showDialog() {
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_card_layout, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<VisitCard> assignedArray = new ArrayList<>();
                final ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
                final ArrayList<String> assignedEmpNameArray = new ArrayList<>();
                if (assignCardNoStaticList != null) {
                    if (assignCardNoStaticList.size() > 0) {
                        assignedArray.clear();
                        for (int i = 0; i < assignCardNoStaticList.size(); i++) {
                            if (assignCardNoStaticList.get(i).getChecked()) {
                                assignedArray.add(assignCardNoStaticList.get(i));
                                assignedEmpIdArray.add(assignCardNoStaticList.get(i).getCardId());
                                assignedEmpNameArray.add(assignCardNoStaticList.get(i).getCardNumber());

                            }
                        }
                    }
                    Log.e("ASSIGN EMP", "---------------------------------" + assignedArray);
                    Log.e("ASSIGN EMP SIZE", "---------------------------------" + assignedArray.size());
                    Log.e("ASSIGN EMP ID", "---------------------------------" + assignedEmpIdArray);
                    Log.e("ASSIGN EMP Name", "---------------------------------" + assignedEmpNameArray);

                    String empIds=assignedEmpIdArray.toString().trim();
                    Log.e("ASSIGN EMP ID","---------------------------------"+empIds);

                    String a1 = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";
                    stringId = a1.replaceAll("\\s","");

                    Log.e("ASSIGN EMP ID STRING","---------------------------------"+stringId);
                    Log.e("ASSIGN EMP ID STRING1","---------------------------------"+a1);

                    String empName=assignedEmpNameArray.toString().trim();
                    Log.e("ASSIGN EMP NAME","---------------------------------"+empName);

                    stringName = ""+empName.substring(1, empName.length()-1).replace("][", ",")+"";

                    // stringName = a.replaceAll("\\s","");

                    Log.e("ASSIGN EMP NAME STRING","---------------------------------"+stringName);
                    //Log.e("ASSIGN EMP NAME STRING1","---------------------------------"+a);

                    tvCard.setText(""+stringName);
                    tvCardId.setText(""+stringId);
                }
            }
        });

        cardAdapter = new CardListDialogAdapter(cardList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(cardAdapter);

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
                    if (cardAdapter != null) {
                        filterDept(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }
    void filterDept(String text) {
        ArrayList<VisitCard> temp = new ArrayList();
        for (VisitCard d : cardList) {
            if (d.getCardNumber().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        cardAdapter.updateList(temp);
    }

    public class CardListDialogAdapter extends RecyclerView.Adapter<CardListDialogAdapter.MyViewHolder> {

        private ArrayList<VisitCard> cardList;
        private Context context;

        public CardListDialogAdapter(ArrayList<VisitCard> cardList, Context context) {
            this.cardList = cardList;
            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvCardNo;
            public CheckBox checkBox;
            public LinearLayout linearLayout;

            public MyViewHolder(View view) {
                super(view);
                tvCardNo = view.findViewById(R.id.tvCardNo);
                checkBox = view.findViewById(R.id.checkBox);
                linearLayout = view.findViewById(R.id.linearLayout);
            }
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adapter_card_layout, viewGroup, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final VisitCard model = cardList.get(i);
            final int pos = i;
            myViewHolder.tvCardNo.setText(model.getCardNumber());
            //holder.tvAddress.setText(model.getCustAddress());

            myViewHolder.checkBox.setChecked(cardList.get(i).getChecked());

            myViewHolder.checkBox.setTag(cardList.get(i));

            myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    VisitCard visitCard = (VisitCard) cb.getTag();

                    visitCard.setChecked(cb.isChecked());
                    cardList.get(pos).setChecked(cb.isChecked());

                }
            });
//
//            myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                        Intent customerDataIntent = new Intent();
////                        customerDataIntent.setAction("CUSTOMER_DATA");
////                        customerDataIntent.putExtra("name", model.getEmpDeptName());
////                        customerDataIntent.putExtra("id", model.getEmpDeptId());
////                        LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);
//                    dialog.dismiss();
//                    tvCard.setText(""+model.getCardNumber());
//                    tvCardId.setText(""+model.getCardId());
//                  //  deptId= Integer.parseInt(tvDeptId.getText().toString());
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        public void updateList(ArrayList<VisitCard> list) {
            cardList = list;
            notifyDataSetChanged();
        }

    }

    private void getAssignUser() {

        ArrayList<VisitCard> assignedCardArray = new ArrayList<>();
        ArrayList<Integer> assignedCardIdArray = new ArrayList<>();
        ArrayList<String> assignedCardNameArray = new ArrayList<>();

        assignedCardIdArray.clear();
        Log.e("ID ARRAY","------------------------------"+assignedCardIdArray);

        if (assignCardNoStaticList != null) {

            if (assignCardNoStaticList.size() > 0) {

                assignedCardArray.clear();
                assignedCardIdArray.clear();
                assignedCardNameArray.clear();

                for (int i = 0; i < assignCardNoStaticList.size(); i++) {

                    if (assignCardNoStaticList.get(i).getChecked()) {

                        assignedCardArray.add(assignCardNoStaticList.get(i));
                        assignedCardIdArray.add(assignCardNoStaticList.get(i).getCardId());
                        //  assignedEmpNameArray.add(assignVisitorEmpStaticList.get(i).getEmpFname() + " " + assignVisitorEmpStaticList.get(i).getEmpMname() + " " + assignVisitorEmpStaticList.get(i).getEmpSname());
                    }
                }
            }
            Log.e("ASSIGN EMP", "---------------------------------" + assignedCardIdArray);
            Log.e("ASSIGN EMP SIZE", "---------------------------------" + assignedCardIdArray.size());

            String empIds = assignedCardIdArray.toString().trim();
            Log.e("ASSIGN EMP ID", "---------------------------------" + empIds);

            String a1 = "" + empIds.substring(1, empIds.length() - 1).replace("][", ",") + "";
            strCardId = a1.replaceAll("\\s","");
            Log.e("ASSIGN EMP ID STRING", "---------------------------------" + strCardId);

//            String empName=assignedEmpNameArray.toString().trim();
////            Log.e("ASSIGN EMP NAME","---------------------------------"+empName);
////
////            stringName = ""+empName.substring(1, empName.length()-1).replace("][", ",")+"";
////
////            Log.e("ASSIGN EMP NAME STRING","---------------------------------"+stringName);
////            edEmployee.setText(stringName);
        }
    }


    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final VisitorList visitor) {

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
                imagePath3 = null;

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


    private void uploadImage(final ArrayList<String> filePath, final ArrayList<String> fileName) {

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
                imagePath3 = null;

                Log.e("Response : ", "--" + response.body());
                //saveVisitor(visitor);
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

    private void saveVisitor(VisitorList visitor) {
        Log.e("PARAMETER","---------------------------------------VISITOR--------------------------"+visitor);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitorList> listCall = Constants.myInterface.saveGatepassVisitor(visitor);
            listCall.enqueue(new Callback<VisitorList>() {
                @Override
                public void onResponse(Call<VisitorList> call, Response<VisitorList> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE VISITOR INFO : ", " ------------------------------SAVE VISITOR INFO------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            if(type.equalsIgnoreCase("Visitor List")) {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                                ft.commit();
                            }else if(type.equalsIgnoreCase("Maintenance List"))
                            {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
                                ft.commit();
                            }

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
                public void onFailure(Call<VisitorList> call, Throwable t) {
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
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p2.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        } else if (type.equalsIgnoreCase("Photo3")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" +Calendar.getInstance().getTimeInMillis()+ "_p3.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 302);
                        }

                    } else {

                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis() + "_p1.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p2.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        } else if (type.equalsIgnoreCase("Photo3")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p3.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 302);
                        }

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
         //   }
       // });
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

                final ArrayList<String> pathArray = new ArrayList<>();
                final ArrayList<String> fileNameArray = new ArrayList<>();
                pathArray.add(imagePath1);
                fileNameArray.add(f.getName());
                uploadImage(pathArray,fileNameArray);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }  else if (resultCode == RESULT_OK && requestCode == 202) {
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

                final ArrayList<String> pathArray = new ArrayList<>();
                final ArrayList<String> fileNameArray = new ArrayList<>();
                pathArray.add(imagePath2);
                fileNameArray.add(f.getName());
                uploadImage(pathArray,fileNameArray);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }  else if (resultCode == RESULT_OK && requestCode == 302) {
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

                final ArrayList<String> pathArray = new ArrayList<>();
                final ArrayList<String> fileNameArray = new ArrayList<>();
                pathArray.add(imagePath3);
                fileNameArray.add(f.getName());
                uploadImage(pathArray,fileNameArray);

            } catch (Exception e) {
                e.printStackTrace();
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
