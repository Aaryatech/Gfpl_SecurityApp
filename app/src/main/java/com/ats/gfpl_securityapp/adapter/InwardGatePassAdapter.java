package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.InwardGatePassDetailFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassFragment;
import com.ats.gfpl_securityapp.fragment.InwardgatePassListFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InwardGatePassAdapter extends RecyclerView.Adapter<InwardGatePassAdapter.MyViewHolder> {
    private ArrayList<MaterialDetail> materialList;
    private Context context;

    public InwardGatePassAdapter(ArrayList<MaterialDetail> materialList, Context context) {
        this.materialList = materialList;
        this.context = context;
    }

    @NonNull
    @Override
    public InwardGatePassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_inward_gatepass, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InwardGatePassAdapter.MyViewHolder myViewHolder, int i) {
        final MaterialDetail model=materialList.get(i);
        myViewHolder.tvGPNo.setText(""+model.getExVar1());
        myViewHolder.tvInvoice.setText(""+model.getInvoiceNumber());
        myViewHolder.tvDate.setText(""+model.getInwardDate());
        myViewHolder.tvParty.setText(""+model.getPartyName());
        myViewHolder.tvNugs.setText(""+model.getNoOfNugs());
        myViewHolder.tvTime.setText(""+model.getInTime());
        myViewHolder.tvLastDept.setText(model.getToDeptName());
        myViewHolder.tvLastPerson.setText(model.getToEmpName());

        String imageUri = String.valueOf(model.getPersonPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String imageUri1 = String.valueOf(model.getInwardPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri1).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        myViewHolder.linearLayoutInward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                MainActivity activity = (MainActivity) context;
                Fragment adf = new InwardGatePassDetailFragment();
                Bundle args = new Bundle();
                args.putString("model", json);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "InwardGPListFragment").commit();
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
                            Fragment adf = new InwardGatePassFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "InwardGPListFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete Material?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteMaterial(model.getGatepassInwardId());
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
    }

    private void deleteMaterial(Integer gatepassInwardId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteMaterialGatepass(gatepassInwardId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE MATERIAL: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
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
        return materialList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGPNo, tvInvoice, tvDate, tvParty, tvNugs, tvTime, tvLastDept, tvLastPerson;
        public ImageView ivPhoto1, ivPhoto2, ivPhoto3,ivEdit;
        public CheckBox checkBox;
        public LinearLayout linearLayoutInward;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGPNo = itemView.findViewById(R.id.tvGPNo);
            tvInvoice = itemView.findViewById(R.id.tvInvoice);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvParty = itemView.findViewById(R.id.tvParty);
            tvNugs = itemView.findViewById(R.id.tvNugs);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLastDept = itemView.findViewById(R.id.tvLastDept);
            tvLastPerson = itemView.findViewById(R.id.tvLastPerson);
            ivPhoto1 = itemView.findViewById(R.id.ivPhoto1);
            ivPhoto2 = itemView.findViewById(R.id.ivPhoto2);
            ivPhoto3 = itemView.findViewById(R.id.ivPhoto3);
            checkBox = itemView.findViewById(R.id.checkBox);
            linearLayoutInward = itemView.findViewById(R.id.linearLayoutInward);

            ivEdit=itemView.findViewById(R.id.ivEdit);
        }
    }
}
