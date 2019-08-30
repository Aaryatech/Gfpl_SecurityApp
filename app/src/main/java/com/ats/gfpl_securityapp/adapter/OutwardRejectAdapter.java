package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.model.Sync;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OutwardRejectAdapter extends RecyclerView.Adapter<OutwardRejectAdapter.MyViewHolder>  {
    private ArrayList<Outward> outwardList;
    private Context context;
    ArrayList<Sync> syncArray = new ArrayList<>();
    Login loginUser;

    public OutwardRejectAdapter(ArrayList<Outward> outwardList, Context context, ArrayList<Sync> syncArray, Login loginUser) {
        this.outwardList = outwardList;
        this.context = context;
        this.syncArray = syncArray;
        this.loginUser = loginUser;
    }

    @NonNull
    @Override
    public OutwardRejectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_outward_approve, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutwardRejectAdapter.MyViewHolder myViewHolder, int i) {
        final Outward model=outwardList.get(i);
        myViewHolder.tvGpNo.setText(model.getExVar1());
        myViewHolder.tvOutwardName.setText(model.getOutwardName());
        // myViewHolder.tvOutDate.setText(model.getDateOut());
        //myViewHolder.tvExpectedDate.setText(model.getDateInExpected());
        myViewHolder.tvToName.setText(model.getToName());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

        Date TODate = null;
        try {
            TODate = formatter.parse(model.getDateOut());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String outDate = formatter1.format(TODate);
        myViewHolder.tvOutDate.setText(outDate);


        Date TODate1 = null;
        try {
            TODate1 = formatter.parse(model.getDateInExpected());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String expDate = formatter1.format(TODate1);
        myViewHolder.tvExpectedDate.setText(expDate);

        if(model.getExInt1()==1)
        {
            myViewHolder.tvType.setText("Yes");
        }else{
            myViewHolder.tvType.setText("No");
        }
    }

    @Override
    public int getItemCount() {
        return outwardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGpNo,tvOutwardName,tvOutDate,tvExpectedDate,tvToName,tvType;
        private ImageView ivEdit,ivApprove,ivReject;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGpNo=itemView.findViewById(R.id.tvGPNo);
            tvOutwardName=itemView.findViewById(R.id.tvOutwardName);
            tvOutDate=itemView.findViewById(R.id.tvOutDate);
            tvExpectedDate=itemView.findViewById(R.id.tvExpectedDate);
            tvToName=itemView.findViewById(R.id.tvToName);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivApprove=itemView.findViewById(R.id.ivApprove);
            ivReject=itemView.findViewById(R.id.ivReject);
            tvType=itemView.findViewById(R.id.tvType);
        }
    }
}
