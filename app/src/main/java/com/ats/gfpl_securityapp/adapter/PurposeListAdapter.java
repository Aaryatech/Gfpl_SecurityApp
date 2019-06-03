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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.AddPurposeFragment;
import com.ats.gfpl_securityapp.fragment.PurposeListFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurposeListAdapter extends RecyclerView.Adapter<PurposeListAdapter.MyViewHolder> {
    private ArrayList<PurposeList> purposeList;
    private Context context;

    public PurposeListAdapter(ArrayList<PurposeList> purposeList, Context context) {
        this.purposeList = purposeList;
        this.context = context;
    }

    @NonNull
    @Override
    public PurposeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_purpose_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurposeListAdapter.MyViewHolder myViewHolder, int i) {
        final PurposeList model=purposeList.get(i);
        myViewHolder.tvPurHeading.setText(model.getPurposeHeading());
        myViewHolder.tvPurDescription.setText(model.getDescription());
        myViewHolder.tvPurRemark.setText(model.getRemark());


        if(model.getPurposeType()==1) {
            myViewHolder.tvPurType.setText("Type 1");
            myViewHolder.tvPurEmp.setVisibility(View.GONE);
        }else if(model.getPurposeType()==2)
        {
            myViewHolder.tvPurType.setText("Type 2");
            myViewHolder.tvPurEmp.setVisibility(View.VISIBLE);
            myViewHolder.tvPurEmp.setText(model.getAssignEmpName());
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
                            Fragment adf = new AddPurposeFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "DashFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete Purpose?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletePurpose(model.getPurposeId());
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

    private void deletePurpose(int purposeId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deletePurpose(purposeId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE PURPOSE: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new PurposeListFragment(), "DashFragment");
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
        return purposeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPurHeading,tvPurDescription,tvPurRemark,tvPurType,tvPurEmp;
        private ImageView ivEdit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPurHeading=itemView.findViewById(R.id.tvPurHeading);
            tvPurDescription=itemView.findViewById(R.id.tvPurDescription);
            tvPurRemark=itemView.findViewById(R.id.tvPurRemark);
            tvPurType=itemView.findViewById(R.id.tvPurType);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            tvPurEmp=itemView.findViewById(R.id.tvPurEmp);
        }
    }
}
