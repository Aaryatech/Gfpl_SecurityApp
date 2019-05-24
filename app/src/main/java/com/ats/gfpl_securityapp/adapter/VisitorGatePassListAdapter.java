package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.CloseMeetingActivity;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.DashboardFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;

import java.util.ArrayList;

public class VisitorGatePassListAdapter extends RecyclerView.Adapter<VisitorGatePassListAdapter.MyViewHolder> {
    private ArrayList<String> visitorList;
    private Context context;

    public VisitorGatePassListAdapter(ArrayList<String> visitorList, Context context) {
        this.visitorList = visitorList;
        this.context = context;
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
        // myViewHolder.tvName.setText("");


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

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) context;

                TabFragment adf = new TabFragment();
                Bundle args = new Bundle();
                args.putString("model", "");
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
                intent.setData(Uri.parse("tel:" + number));
                int result = context.checkCallingOrSelfPermission(android.Manifest.permission.CALL_PHONE);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(intent);
                } else {
                      //Toast.makeText(getActivity(), "Device not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCompany, tvMobile, tvType, tvStatus, tvRemark,tvGPNo;
        public ImageView ivReject, ivApprove, ivClose, ivInfo, ivOutSide;
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

        }
    }


}
