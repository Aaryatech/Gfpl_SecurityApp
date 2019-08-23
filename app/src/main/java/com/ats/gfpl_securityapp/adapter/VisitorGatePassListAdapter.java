package com.ats.gfpl_securityapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.CloseMeetingActivity;
import com.ats.gfpl_securityapp.activity.ImageZoomActivity;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassListFragment;
import com.ats.gfpl_securityapp.model.Gate;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorGatePassListAdapter extends RecyclerView.Adapter<VisitorGatePassListAdapter.MyViewHolder> {
    private ArrayList<VisitorList> visitorList;
    private Context context;
    private Login login;
    ArrayList<Sync> syncArray = new ArrayList<>();


//    public VisitorGatePassListAdapter(ArrayList<VisitorList> visitorList, Context context,Login login) {
//        this.visitorList = visitorList;
//        this.context = context;
//        this.login = login;
//    }

    public VisitorGatePassListAdapter(ArrayList<VisitorList> visitorList, Context context, Login login, ArrayList<Sync> syncArray) {
        this.visitorList = visitorList;
        this.context = context;
        this.login = login;
        this.syncArray = syncArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_visitor_gate_pass_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final VisitorList model=visitorList.get(i);

        Log.e("LOGIN USER","---------------------------"+login);
        Log.e("LOGIN USER MODEL","---------------------------"+model);

        myViewHolder.tvGPNo.setText(model.getExVar1());
        myViewHolder.tvName.setText(model.getPersonName());
        myViewHolder.tvCompany.setText(model.getPersonCompany());
        myViewHolder.tvMobile.setText(model.getMobileNo());
       // myViewHolder.tvDate.setText(model.getVisitDateIn());
        myViewHolder.tvEmpName.setText(model.getEmpName());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

        Date TODate = null;
        try {
            TODate = formatter.parse(model.getVisitDateIn());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String visitorDate = formatter1.format(TODate);
        myViewHolder.tvDate.setText(visitorDate);


        try {
            String imageUri = String.valueOf(model.getPersonPhoto());
            Log.e("Image Path","---------------------"+Constants.IMAGE_URL+imageUri);
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile)).into(myViewHolder.ivPhoto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        myViewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putExtra("image", Constants.IMAGE_URL + model.getPersonPhoto());
                context.startActivity(intent);
            }
        });

        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Security")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(login.getEmpCatId()))) {
//                        myViewHolder.ivApprove.setVisibility(View.GONE);
//                        myViewHolder.ivReject.setVisibility(View.GONE);
//                        myViewHolder.ivClose.setVisibility(View.GONE);
//                        myViewHolder.ivInfo.setVisibility(View.VISIBLE);
//                        myViewHolder.ivOutFactory.setVisibility(View.VISIBLE);
//                        myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        if(model.getVisitStatus()==1)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.VISIBLE);
                            myViewHolder.ivOutFactory.setVisibility(View.GONE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }else if(model.getVisitStatus()==4)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.GONE);
                            myViewHolder.ivOutFactory.setVisibility(View.VISIBLE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }

                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(login.getEmpCatId()))) {
//                        myViewHolder.ivApprove.setVisibility(View.VISIBLE);
//                        myViewHolder.ivReject.setVisibility(View.VISIBLE);
//                        myViewHolder.ivClose.setVisibility(View.GONE);
//                        myViewHolder.ivInfo.setVisibility(View.GONE);
//                        myViewHolder.ivOutFactory.setVisibility(View.GONE);
//                        myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        if(model.getVisitStatus()==0)
                        {
                            myViewHolder.ivApprove.setVisibility(View.VISIBLE);
                            myViewHolder.ivReject.setVisibility(View.VISIBLE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.GONE);
                            myViewHolder.ivOutFactory.setVisibility(View.GONE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }else if(model.getVisitStatus()==3)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.VISIBLE);
                            myViewHolder.ivInfo.setVisibility(View.GONE);
                            myViewHolder.ivOutFactory.setVisibility(View.GONE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }


                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(login.getEmpCatId()))) {

//                        myViewHolder.ivApprove.setVisibility(View.VISIBLE);
//                        myViewHolder.ivReject.setVisibility(View.VISIBLE);
//                        myViewHolder.ivClose.setVisibility(View.VISIBLE);
//                        myViewHolder.ivInfo.setVisibility(View.VISIBLE);
//                        myViewHolder.ivOutFactory.setVisibility(View.VISIBLE);
//                        myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        if(model.getVisitStatus()==0)
                        {
                        myViewHolder.ivApprove.setVisibility(View.VISIBLE);
                        myViewHolder.ivReject.setVisibility(View.VISIBLE);
                        myViewHolder.ivClose.setVisibility(View.GONE);
                        myViewHolder.ivInfo.setVisibility(View.GONE);
                        myViewHolder.ivOutFactory.setVisibility(View.GONE);
                        myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        }else if(model.getVisitStatus()==3)
                        {
                        myViewHolder.ivApprove.setVisibility(View.GONE);
                        myViewHolder.ivReject.setVisibility(View.GONE);
                        myViewHolder.ivClose.setVisibility(View.VISIBLE);
                        myViewHolder.ivInfo.setVisibility(View.GONE);
                        myViewHolder.ivOutFactory.setVisibility(View.GONE);
                        myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        }else if(model.getVisitStatus()==1)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.VISIBLE);
                            myViewHolder.ivOutFactory.setVisibility(View.GONE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);

                        }else if(model.getVisitStatus()==4)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.GONE);
                            myViewHolder.ivOutFactory.setVisibility(View.VISIBLE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }else if(model.getVisitStatus()==2)
                        {
                            myViewHolder.ivApprove.setVisibility(View.GONE);
                            myViewHolder.ivReject.setVisibility(View.GONE);
                            myViewHolder.ivClose.setVisibility(View.GONE);
                            myViewHolder.ivInfo.setVisibility(View.GONE);
                            myViewHolder.ivOutFactory.setVisibility(View.GONE);
                            myViewHolder.ivOutSide.setVisibility(View.VISIBLE);
                        }


                    }
                }
            }
        }

        if(model.getVisitType()==1) {
            myViewHolder.tvType.setText("Appointment");
        }else if(model.getVisitType()==2)
        {
            myViewHolder.tvType.setText("Random");
        }

        if(model.getVisitStatus()==0)
        {
            myViewHolder.tvStatus.setText("Pending");
        }else if(model.getVisitStatus()==1)
        {
            myViewHolder.tvStatus.setText("Approve");
        }else if(model.getVisitStatus()==2)
        {
            myViewHolder.tvStatus.setText("Rejected");
        }else if(model.getVisitStatus()==3)
        {
            myViewHolder.tvStatus.setText("Allowed to Enter");
        }else if(model.getVisitStatus()==4)
        {
            myViewHolder.tvStatus.setText("Close Meeting");
        }else if(model.getVisitStatus()==5)
        {
            myViewHolder.tvStatus.setText("Out From Factory");
        }

        myViewHolder.tvRemark.setText(model.getPurposeRemark());

        myViewHolder.ivApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  // getUpdateStatus(model.getGatepassVisitorId(),login.getEmpId(),1,model.getGateId());

                new AddApproveDialog(context,model,login).show();

            }
        });

        myViewHolder.ivReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdateStatus(model.getGatepassVisitorId(),login.getEmpId(),2,model.getGateId());
            }
        });

        myViewHolder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                MainActivity activity = (MainActivity) context;
                AddInfoFragment adf = new AddInfoFragment();
                Bundle args = new Bundle();
                args.putString("model",json);
                args.putString("type","Visitor List");
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "VisitorGPListFragment").commit();

            }
        });

        myViewHolder.ivOutFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddOutDialog(context,model).show();
//                getUpdateFactoryStatus(model.getGatepassVisitorId(),5);
            }
        });

        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_edit) {
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            MainActivity activity = (MainActivity) context;
                            Fragment adf = new VisitorGatePassFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "VisitorGPListFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete Visitor?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteVisitor(model.getGatepassVisitorId());
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);
                MainActivity activity = (MainActivity) context;

                TabFragment adf = new TabFragment();
                Bundle args = new Bundle();
                args.putString("model", json);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "VisitorGPListFragment").commit();

            }
        });

        myViewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(context, CloseMeetingActivity.class);
//                intent.putExtra("model", "");
//                context.startActivity(intent);

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, CloseMeetingActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.putExtra("meeting", "Close Meeting Visitor");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

        myViewHolder.tvMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "90909010001";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + model.getMobileNo()));
                int result = context.checkCallingOrSelfPermission(android.Manifest.permission.CALL_PHONE);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(intent);
                } else {
                      //Toast.makeText(getActivity(), "Device not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUpdateFactoryStatus(Integer gatepassVisitorId, int status) {
        Log.e("PARAMETER","                 GATE PASS ID     "+gatepassVisitorId +"       SATAUS      "+status);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateVisitorStatus(gatepassVisitorId,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE FACTORY STATUS: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                                ft.commit();

                            } else {
                                Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                            }
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteVisitor(Integer gatepassVisitorId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteVisitor(gatepassVisitorId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE VISITOR: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                                ft.commit();

                            } else {
                                Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUpdateStatus(Integer gatepassVisitorId, Integer empId, int status, Integer gateId) {
        Log.e("PARAMETER","                 GATE PASS ID     "+gatepassVisitorId +"              EMP ID       "+empId +"       SATAUS      "+status  +"      GATE ID         "+gateId);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateGatepassStatus(gatepassVisitorId,empId,status,gateId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPROVE & REJECT EMP: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                                ft.commit();

                            } else {
                                Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(context, "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public void updateList(ArrayList<VisitorList> temp) {
        visitorList = temp;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark,tvGPNo,ivInfo,ivOutFactory,ivClose,tvDate,tvEmpName;
        public ImageView ivReject, ivApprove, ivOutSide,ivEdit;
        public CircleImageView ivPhoto;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGPNo = itemView.findViewById(R.id.tvGPNo);
            tvName = itemView.findViewById(R.id.tvName);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvType = itemView.findViewById(R.id.tvType);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvEmpName = itemView.findViewById(R.id.tvEmpName);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            ivReject = itemView.findViewById(R.id.ivReject);
            ivApprove = itemView.findViewById(R.id.ivApprove);
            ivClose = itemView.findViewById(R.id.ivClose);
            ivInfo = itemView.findViewById(R.id.ivInfo);
            ivOutSide = itemView.findViewById(R.id.ivOutSide);
            ivOutFactory = itemView.findViewById(R.id.ivOutFactory);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);

            ivEdit=itemView.findViewById(R.id.ivEdit);
            tvDate=itemView.findViewById(R.id.tvDate);

        }
    }


    private class AddApproveDialog extends Dialog {
        public Button btnCancel, btnSubmit;
        public Spinner spGate;

        ArrayList<String> getNameList = new ArrayList<>();
        ArrayList<Integer> getIdList = new ArrayList<>();

        VisitorList visitorList;
        Login login;

        public AddApproveDialog(Context context, VisitorList visitorList,Login login) {
            super(context);
            this.visitorList = visitorList;
            this.login = login;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_approve_visitor);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
          //  wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.gravity = Gravity.CENTER_VERTICAL;
            wlp.x = 5;
            wlp.y = 5;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            spGate=(Spinner)findViewById(R.id.spGate);

            getGate();

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int gateId = getIdList.get(spGate.getSelectedItemPosition());

                    if (gateId == 0) {

                        TextView viewProj = (TextView) spGate.getSelectedView();
                        viewProj.setError("required");
                    } else {
                        TextView viewProj = (TextView) spGate.getSelectedView();
                        viewProj.setError(null);

                        getUpdateStatus(visitorList.getGatepassVisitorId(), Integer.valueOf(visitorList.getEmpIds()),1,gateId);
                        dismiss();

                    }

                }
            });

        }

        private void getGate() {
            if (Constants.isOnline(getContext())) {
                final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
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

                                    ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, getNameList);
                                    spGate.setAdapter(projectAdapter);

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
    }

    private class AddOutDialog extends Dialog {
        public Button btnCancel, btnSubmit;
        private RadioButton rbYes, rbNo;

        ArrayList<String> getNameList = new ArrayList<>();
        ArrayList<Integer> getIdList = new ArrayList<>();

        VisitorList model;
        Login login;

        public AddOutDialog(Context context, VisitorList model) {
            super(context);
            this.model = model;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_out);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            //  wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.gravity = Gravity.CENTER_VERTICAL;
            wlp.x = 5;
            wlp.y = 5;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            rbYes = (RadioButton)findViewById(R.id.rbYes);
            rbNo = (RadioButton)findViewById(R.id.rbNo);

            rbYes.setChecked(true);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getUpdateFactoryStatus(model.getGatepassVisitorId(),5);
                    dismiss();
                }
            });

        }


    }
}
