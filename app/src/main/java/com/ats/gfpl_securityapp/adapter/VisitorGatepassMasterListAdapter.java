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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.VisitorGatepassMasterFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatepassMasterListFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.VisitorMaster;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorGatepassMasterListAdapter extends RecyclerView.Adapter<VisitorGatepassMasterListAdapter.MyViewHolder> {
    private ArrayList<VisitorMaster> visitorList;
    private Context context;

    public VisitorGatepassMasterListAdapter(ArrayList<VisitorMaster> visitorList, Context context) {
        this.visitorList = visitorList;
        this.context = context;
    }

    @NonNull
    @Override
    public VisitorGatepassMasterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_visitor_gate_pass_master_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorGatepassMasterListAdapter.MyViewHolder myViewHolder, int i) {
        final VisitorMaster model=visitorList.get(i);
        myViewHolder.tvName.setText("Person Name : "+model.getPersonName());
        myViewHolder.tvCompany.setText("Company Name : "+model.getPersonCompany());
        myViewHolder.tvMobile.setText("Mobile No : "+model.getMobileNo());
        myViewHolder.tvRemark.setText("Purpose : "+model.getPurposeRemark());
        myViewHolder.tvEmpName.setText("Employee : "+model.getPersonToMeet());

        try {
            String imageUri = String.valueOf(model.getIdProof());
            Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String imageUri = String.valueOf(model.getIdProof1());
            Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String imageUri = String.valueOf(model.getOtherPhoto());
            Log.e("Image Path","---------------------"+ Constants.IMAGE_URL+imageUri);
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto3);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                            Fragment adf = new VisitorGatepassMasterFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "VisitorGPMasterListFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete visitor master?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteVisitorMaster(model.getVisitorId());
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

    private void deleteVisitorMaster(int visitorId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteVistor(visitorId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE VISITOR: ", " ---------------------------MASTER--------------------------------------- " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new VisitorGatepassMasterListFragment(), "DashFragment");
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
        public TextView tvName, tvCompany, tvMobile, tvType, tvRemark,tvEmpName;
        public ImageView ivEdit,ivPhoto1,ivPhoto2,ivPhoto3;
        public LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvType = itemView.findViewById(R.id.tvType);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvEmpName = itemView.findViewById(R.id.tvEmpName);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivPhoto1=itemView.findViewById(R.id.ivPhoto1);
            ivPhoto2=itemView.findViewById(R.id.ivPhoto2);
            ivPhoto3=itemView.findViewById(R.id.ivPhoto3);

        }
    }
}
