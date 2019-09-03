package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.VisitCard;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private ArrayList<VisitCard> cardList;
    private Context context;

    public CardAdapter(ArrayList<VisitCard> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_card_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder myViewHolder, int i) {
        final VisitCard model=cardList.get(i);
        final int pos = i;
        myViewHolder.tvCardNo.setText(model.getCardNumber());

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

//        if(model.getChecked())
//        {
//            myViewHolder.checkBox.setChecked(true);
//        }else{
//            myViewHolder.checkBox.setChecked(false);
//        }
//
//        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    model.setChecked(true);
//                } else {
//                    model.setChecked(false);
//
//                }
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardNo;
        private CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardNo=itemView.findViewById(R.id.tvCardNo);
            checkBox=itemView.findViewById(R.id.checkBox);
        }
    }
}
