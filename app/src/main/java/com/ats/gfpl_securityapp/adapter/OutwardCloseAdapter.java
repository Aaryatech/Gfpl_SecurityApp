package com.ats.gfpl_securityapp.adapter;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.ats.gfpl_securityapp.activity.ImageZoomActivity;
import com.ats.gfpl_securityapp.activity.MainActivity;
import com.ats.gfpl_securityapp.activity.OutwardActivity;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.OutwardFragment;
import com.ats.gfpl_securityapp.fragment.OutwardGatePassFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutwardCloseAdapter extends RecyclerView.Adapter<OutwardCloseAdapter.MyViewHolder> {
    private ArrayList<Outward> outwardList;
    private Context context;
    ArrayList<Sync> syncArray = new ArrayList<>();
    Login loginUser;

    public OutwardCloseAdapter(ArrayList<Outward> outwardList, Context context, ArrayList<Sync> syncArray, Login loginUser) {
        this.outwardList = outwardList;
        this.context = context;
        this.syncArray = syncArray;
        this.loginUser = loginUser;
    }

    @NonNull
    @Override
    public OutwardCloseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_out_outward, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutwardCloseAdapter.MyViewHolder myViewHolder, int i) {
        final Outward model=outwardList.get(i);
        myViewHolder.tvGpNo.setText(model.getExVar1());
        myViewHolder.tvOutwardName.setText(model.getOutwardName());
        //  myViewHolder.tvOutDate.setText(model.getDateOut());
        //  myViewHolder.tvExpectedDate.setText(model.getDateInExpected());
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


        String imageUri = String.valueOf(model.getInPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL+imageUri).placeholder(context.getResources().getDrawable(R.drawable.ic_photo)).into(myViewHolder.ivPhoto1);

        } catch (Exception e) {

        }

        myViewHolder.ivPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putExtra("image", Constants.IMAGE_URL + model.getInPhoto());
                context.startActivity(intent);
            }
        });


        for(int j=0;j<syncArray.size();j++)
        {
            if(syncArray.get(j).getSettingKey().equals("Security"))
            {
                if(syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId())))
                {
                    myViewHolder.ivEdit.setVisibility(View.GONE);

                    if(model.getStatus()==0)
                    {
                        myViewHolder.tvOut.setVisibility(View.VISIBLE);

                    } else if(model.getStatus()==1)
                    {
                        myViewHolder.tvIn.setVisibility(View.VISIBLE);
                    }
                }
            }else  if(syncArray.get(j).getSettingKey().equals("Supervisor")) {

                if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                    Log.e("Hiiiii","----------------");

                    if(model.getStatus()==0)
                    {
                        myViewHolder.ivEdit.setVisibility(View.VISIBLE);
                        Log.e("Hiiiii2222222222","----------------");
                    }else{
                        myViewHolder.ivEdit.setVisibility(View.GONE);
                    }
                }
            } else if(syncArray.get(j).getSettingKey().equals("Admin")) {

                if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                    myViewHolder.ivEdit.setVisibility(View.VISIBLE);
                }
            }
        }

        myViewHolder.tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, OutwardActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.putExtra("meeting", "Close Outward");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        myViewHolder.tvIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, OutwardActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.putExtra("meeting", "In Outward");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
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
                            Fragment adf = new OutwardGatePassFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "OutwardListFragment").commit();

                        }else
                        if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Are you want to delete?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteOutward(model.getGpOutwardId());
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

    private void deleteOutward(int gpOutwardId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteOutwardGatepass(gpOutwardId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE OUTWARD: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new OutwardFragment(), "DashFragment");
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
        private TextView tvGpNo,tvOutwardName,tvOutDate,tvExpectedDate,tvToName,tvOut,tvIn,tvType;
        private ImageView ivEdit;
        public ImageView ivPhoto1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGpNo=itemView.findViewById(R.id.tvGPNo);
            tvOutwardName=itemView.findViewById(R.id.tvOutwardName);
            tvOutDate=itemView.findViewById(R.id.tvOutDate);
            tvExpectedDate=itemView.findViewById(R.id.tvExpectedDate);
            tvToName=itemView.findViewById(R.id.tvToName);
            tvOut=itemView.findViewById(R.id.tvOut);
            tvIn=itemView.findViewById(R.id.tvIn);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            tvType=itemView.findViewById(R.id.tvType);
            ivPhoto1 = itemView.findViewById(R.id.ivPhoto1);
        }
    }
}
