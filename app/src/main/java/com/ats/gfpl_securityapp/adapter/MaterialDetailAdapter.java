package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.DocHandoverDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MaterialDetailAdapter extends RecyclerView.Adapter<MaterialDetailAdapter.MyViewHolder> {
    private ArrayList<DocHandoverDetail> detailList;
    private Context context;

    public MaterialDetailAdapter(ArrayList<DocHandoverDetail> detailList, Context context) {
        this.detailList = detailList;
        this.context = context;
    }

    @NonNull
    @Override
    public MaterialDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_material_detail, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        DocHandoverDetail model=detailList.get(i);
        Log.e("Material Detail Model","--------------------------------"+detailList.get(i));
        Log.e("Material Detail List","--------------------------------"+detailList);

        myViewHolder.tvFromPer.setText("From User : "+model.getFromUserName());
        myViewHolder.tvToPer.setText("To User : "+model.getToUserName());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

        Date TODate = null;
        try {
            TODate = formatter.parse(model.getHandOverDate());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String outDate = formatter1.format(TODate);
      //  myViewHolder.tvOutDate.setText(outDate);
        myViewHolder.tvDate.setText(outDate);


        if(model.getStatus()==0)
        {
            myViewHolder.tvStatus.setText("Status : Pending");
        }else if(model.getStatus()==1)
        {
            myViewHolder.tvStatus.setText("Status : Approve");
        }else if(model.getStatus()==2)
        {
            myViewHolder.tvStatus.setText("Status : Rejected");
        }


        //            myViewHolder.tvFromPer.setText(model.getDocHandoverDetail().get(i).getFromUserName());
//            myViewHolder.tvToPer.setText(model.getDocHandoverDetail().get(i).getToUserName());
//
//            if(model.getDocHandoverDetail().get(i).getStatus()==0)
//            {
//                myViewHolder.tvStatus.setText("Pending");
//            }else if(model.getDocHandoverDetail().get(i).getStatus()==1)
//            {
//                myViewHolder.tvStatus.setText("Approve");
//            }else if(model.getDocHandoverDetail().get(i).getStatus()==2)
//            {
//                myViewHolder.tvStatus.setText("Rejected");
//            }
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFromPer,tvToPer,tvStatus,tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFromPer=itemView.findViewById(R.id.tvFromPer);
            tvToPer=itemView.findViewById(R.id.tvToPer);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            tvDate=itemView.findViewById(R.id.tvDate);
        }
    }
}
