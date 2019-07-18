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
import com.ats.gfpl_securityapp.fragment.OutwardApproveFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutwardApproveAdapter extends RecyclerView.Adapter<OutwardApproveAdapter.MyViewHolder> {
    private ArrayList<Outward> outwardList;
    private Context context;
    ArrayList<Sync> syncArray = new ArrayList<>();
    Login loginUser;

    public OutwardApproveAdapter(ArrayList<Outward> outwardList, Context context, ArrayList<Sync> syncArray, Login loginUser) {
        this.outwardList = outwardList;
        this.context = context;
        this.syncArray = syncArray;
        this.loginUser = loginUser;
    }

    @NonNull
    @Override
    public OutwardApproveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_outward_approve, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutwardApproveAdapter.MyViewHolder myViewHolder, int i) {
        final Outward model=outwardList.get(i);
        myViewHolder.tvGpNo.setText(model.getExVar1());
        myViewHolder.tvOutwardName.setText(model.getOutwardName());
        myViewHolder.tvOutDate.setText(model.getDateOut());
        myViewHolder.tvExpectedDate.setText(model.getDateInExpected());
        myViewHolder.tvToName.setText(model.getToName());

        if(model.getExInt1()==1)
        {
            myViewHolder.tvType.setText("Yes");
        }else{
            myViewHolder.tvType.setText("No");
        }

        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Supervisor")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                        myViewHolder.ivApprove.setVisibility(View.GONE);

                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                        myViewHolder.ivApprove.setVisibility(View.VISIBLE);
                    }
                    }
            }
        }

        myViewHolder.ivApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getUpdateStatus(model.getGpOutwardId(),loginUser.getEmpId(),0,"NA");
            }
        });
    }

    private void getUpdateStatus(int gpOutwardId, Integer empId, int status, String photo) {

        Log.e("PARAMETER","                 OUTWARD PASS ID     "+gpOutwardId +"              EMP ID       "+empId +"       SATAUS      "+status  +"      PHOTO         "+photo);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateOutwardGatepassStatus(gpOutwardId,empId,status,photo);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPROVE OUTWARD: ", " - " + response.body());

                            if (!response.body().getError()) {

                                //MainActivity activity = (MainActivity) getApplicationContext();
                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new OutwardApproveFragment(), "DashFragment");
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
        return outwardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGpNo,tvOutwardName,tvOutDate,tvExpectedDate,tvToName,tvType;
        private ImageView ivEdit,ivApprove;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGpNo=itemView.findViewById(R.id.tvGPNo);
            tvOutwardName=itemView.findViewById(R.id.tvOutwardName);
            tvOutDate=itemView.findViewById(R.id.tvOutDate);
            tvExpectedDate=itemView.findViewById(R.id.tvExpectedDate);
            tvToName=itemView.findViewById(R.id.tvToName);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivApprove=itemView.findViewById(R.id.ivApprove);
            tvType=itemView.findViewById(R.id.tvType);
        }
    }
}
