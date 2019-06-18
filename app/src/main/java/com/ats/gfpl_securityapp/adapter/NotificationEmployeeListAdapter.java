package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Notification;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationEmployeeListAdapter  extends RecyclerView.Adapter<NotificationEmployeeListAdapter.MyViewHolder> {
    private ArrayList<Notification> empList;
    private Context context;

    public NotificationEmployeeListAdapter(ArrayList<Notification> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_notification_employee_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // myViewHolder.tvName.setText("");
        final Notification model=empList.get(i);
        myViewHolder.tvName.setText(model.getEmpName());
        if(model.getStatus()==0)
        {
            myViewHolder.tvStatus.setText("Pending");
        }else if(model.getStatus()==1)
        {
            myViewHolder.tvStatus.setText("Approve");
        }else  if(model.getStatus()==2)
        {
            myViewHolder.tvStatus.setText("Rejected");
        }else if(model.getStatus()==3)
        {
            myViewHolder.tvStatus.setText("Allow to Enter");
        }else if(model.getStatus()==4)
        {
            myViewHolder.tvStatus.setText("Close Metting");
        }else if(model.getStatus()==5)
        {
            myViewHolder.tvStatus.setText("Out From Factory");
        }

        myViewHolder.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(model.getGatepassVisitorId(),model.getEmpId());
            }
        });
    }

    private void sendNotification(Integer gatepassVisitorId, Integer empId) {
        Log.e("PARAMETER","               GATE PASS VISITOR TYPE              "+gatepassVisitorId +"             EMP ID      "+empId);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.sendNotification(gatepassVisitorId,empId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NOTIFICATION SEND VIS: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new TabFragment(), "DashFragment");
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
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMobile, tvStatus;
        public ImageView ivNotification;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivNotification = itemView.findViewById(R.id.ivNotification);

        }
    }

}
