package com.ats.gfpl_securityapp.adapter;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.CloseMeetingActivity;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassListFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorGatePassListAdapter extends RecyclerView.Adapter<VisitorGatePassListAdapter.MyViewHolder> {
    private ArrayList<VisitorList> visitorList;
    private Context context;
    private Login login;

    public VisitorGatePassListAdapter(ArrayList<VisitorList> visitorList, Context context,Login login) {
        this.visitorList = visitorList;
        this.context = context;
        this.login = login;
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

        myViewHolder.tvGPNo.setText(model.getExVar1());
        myViewHolder.tvName.setText(model.getPersonName());
        myViewHolder.tvCompany.setText(model.getPersonCompany());
        myViewHolder.tvMobile.setText(model.getMobileNo());

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
        }

        myViewHolder.tvRemark.setText(model.getPurposeRemark());

        myViewHolder.ivApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   getUpdateStatus(model.getGatepassVisitorId(),login.getEmpId(),1,model.getGateId());
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

                MainActivity activity = (MainActivity) context;
                AddInfoFragment adf = new AddInfoFragment();
                Bundle args = new Bundle();
                args.putString("model", "");
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "VisitorGPListFragment").commit();

            }
        });

        myViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_visitor_edit, popupMenu.getMenu());
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
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "DashFragment").commit();

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

                Intent intent = new Intent(context, CloseMeetingActivity.class);
                intent.putExtra("model", "");
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

    private void getUpdateStatus(Integer gatepassVisitorId, Integer empId, int status, Integer gateId) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark,tvGPNo;
        public ImageView ivReject, ivApprove, ivClose, ivInfo, ivOutSide,ivEdit;
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
            linearLayout = itemView.findViewById(R.id.linearLayout);

            ivReject = itemView.findViewById(R.id.ivReject);
            ivApprove = itemView.findViewById(R.id.ivApprove);
            ivClose = itemView.findViewById(R.id.ivClose);
            ivInfo = itemView.findViewById(R.id.ivInfo);
            ivOutSide = itemView.findViewById(R.id.ivOutSide);

            ivEdit=itemView.findViewById(R.id.ivEdit);

        }
    }


}
