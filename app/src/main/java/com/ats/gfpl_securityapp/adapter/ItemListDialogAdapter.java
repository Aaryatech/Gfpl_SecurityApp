package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Item;

import java.util.ArrayList;

public class ItemListDialogAdapter extends RecyclerView.Adapter<ItemListDialogAdapter.MyViewHolder>  {

    private ArrayList<Item> itemList;
    private Context context;

    public ItemListDialogAdapter(ArrayList<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_party_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListDialogAdapter.MyViewHolder myViewHolder, int i) {
        final Item model = itemList.get(i);

        myViewHolder.tvName.setText(model.getItemDesc());
        //holder.tvAddress.setText(model.getCustAddress());

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA1");
                customerDataIntent.putExtra("name", model.getItemDesc());
                customerDataIntent.putExtra("id", model.getItemId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(ArrayList<Item> list) {
        itemList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
