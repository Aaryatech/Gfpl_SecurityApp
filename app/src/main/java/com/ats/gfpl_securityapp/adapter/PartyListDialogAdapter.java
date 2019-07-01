package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Party;


import java.util.ArrayList;

public class PartyListDialogAdapter extends RecyclerView.Adapter<PartyListDialogAdapter.MyViewHolder> {

    private ArrayList<Party> partyList;
    private Context context;

    public PartyListDialogAdapter(ArrayList<Party> partyList, Context context) {
        this.partyList = partyList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvAddress = view.findViewById(R.id.tvAddress);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_party_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Party model = partyList.get(position);

        holder.tvName.setText(model.getVendorName());
        //holder.tvAddress.setText(model.getCustAddress());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA");
                customerDataIntent.putExtra("name", model.getVendorName());
                customerDataIntent.putExtra("id", model.getVendorId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public void updateList(ArrayList<Party> list) {
        partyList = list;
        notifyDataSetChanged();
    }

}
