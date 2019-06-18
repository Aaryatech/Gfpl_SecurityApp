package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import com.ats.gfpl_securityapp.fragment.EmployeeFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassDetailFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassFragment;
import com.ats.gfpl_securityapp.model.EmpGatePass;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeGatePassListAdapter  extends RecyclerView.Adapter<EmployeeGatePassListAdapter.MyViewHolder> {
    private ArrayList<EmpGatePass> empList;
    private Context context;
    ArrayList<Sync> syncArray = new ArrayList<>();
    Login loginUser;

//    public EmployeeGatePassListAdapter(ArrayList<EmpGatePass> empList, Context context) {
//        this.empList = empList;
//        this.context = context;
//    }


    public EmployeeGatePassListAdapter(ArrayList<EmpGatePass> empList, Context context, ArrayList<Sync> syncArray, Login loginUser) {
        this.empList = empList;
        this.context = context;
        this.syncArray = syncArray;
        this.loginUser = loginUser;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_employee_gate_pass_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final EmpGatePass model=empList.get(i);
        myViewHolder.tvEmp.setText(model.getEmpName());
        myViewHolder.tvGPNo.setText(""+model.getExVar1());
        //myViewHolder.tvEmpMobile.setText(model.get());
        myViewHolder.tvHrs.setText(""+model.getNoOfHr()+"("+model.getOutTime()+" to "+model.getInTime()+")");
        myViewHolder.tvSupName.setText(""+model.getUserName());
        myViewHolder.tvTotalHrs.setText(""+model.getActualTimeDifference());
        SimpleDateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
        SimpleDateFormat f2 = new SimpleDateFormat("hh:mm");

//        if(model.getGatePassStatus()==0)
//        {
//            myViewHolder.tvOut.setVisibility(View.VISIBLE);
//        }

        for(int j=0;j<syncArray.size();j++)
        {
            if(syncArray.get(j).getSettingKey().equals("Security"))
            {
                if(syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId())))
                {
                   myViewHolder.ivEdit.setVisibility(View.GONE);

                    if(model.getGatePassStatus()==0)
                    {
                        myViewHolder.tvOut.setVisibility(View.VISIBLE);

                    } else if(model.getGatePassSubType()==1 && model.getGatePassStatus()==1) {

                       myViewHolder.tvIn.setVisibility(View.VISIBLE);
                   }
                }
            }else  if(syncArray.get(j).getSettingKey().equals("Supervisor")) {

                if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {

                    Log.e("Hiiiii","----------------");

                    if(model.getGatePassStatus()==0)
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

        Date fromTime=null;
        try {
            String outTime=model.getNewOutTime();
            Log.e("Time","------------------"+outTime);
            fromTime=f1.parse(outTime);
            Log.e("Time Date","------------------"+fromTime);
            String timeOut = f2.format(fromTime);
            Log.e("Time Convert","------------------"+timeOut);
            myViewHolder.tvOutTime.setText(timeOut);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        Date toTime=null;
        try {
            String inTime=model.getNewInTime();
            Log.e("Time1","------------------"+inTime);
            toTime=f1.parse(inTime);
            Log.e("Time Date1","------------------"+toTime);
            String timeIn = f2.format(toTime);
            Log.e("Time Convert1","------------------"+timeIn);
            myViewHolder.tvInTime.setText(timeIn);

        }catch (Exception e)
        {
            e.printStackTrace();
        }


//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        Date date1 = null, date2 = null;
//        int date1Int,date2INT;
//        long difference;
//        String diff;
//        try {
//
//            String time1=myViewHolder.tvOutTime.getText().toString();
//            String time2=myViewHolder.tvInTime.getText().toString();
////            date1Int = Integer.parseInt(time1);
////            date2INT = Integer.parseInt(time2);
//
//            date1 = format.parse(time1);
//            date2 = format.parse(time2);
//
//            Log.e("PARAMETER1","             TIME 1    "+date1 +"           TIME 2             "+date2);
//            //Log.e("PARAMETER1","             TIME INT1    "+date1Int +"           TIME INT2             "+date2INT);
////            if(date2INT<date1Int)
////            {
////                myViewHolder.tvTotalHrs.setText("00:00");
////            }
//
//             difference = date2.getTime() - date1.getTime();
//
//            int hours = (int) (difference/(1000 * 60 * 60));
//            int mins = (int) ((difference/(1000*60)) % 60);
//
//            // diff=DurationFormatUtils.formatDuration(difference, "HH:mm");
//            diff=hours + "."+mins;
//            Log.e("Total Hrs","----------------------------------------"+diff);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


       // myViewHolder.tvTotalHrs.setText(""+model.getNoOfHr());

        if(model.getGatePassSubType()==1)
        {
            myViewHolder.tvGatePass.setText("Temporary");
        }else if(model.getGatePassSubType()==2)
        {
            myViewHolder.tvGatePass.setText("Day");
        }

        myViewHolder.tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(model.getGatePassSubType()==2)
                {
                    getUpdateStatus(model.getGatepassEmpId(),model.getSecurityIdIn(),2,model.getGatePassSubType(),model);
                }else{
                    getUpdateStatus(model.getGatepassEmpId(),model.getSecurityIdIn(),1,model.getGatePassSubType(),model);
                }

            }
        });

        myViewHolder.tvIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdateStatus(model.getGatepassEmpId(),model.getSecurityIdIn(),2,model.getGatePassSubType(),model);
            }
        });

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                MainActivity activity = (MainActivity) context;
                Fragment adf = new EmployeeGatePassDetailFragment();
                Bundle args = new Bundle();
                args.putString("model", json);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EmployeeGPListFragment").commit();

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
                            Fragment adf = new EmployeeGatePassFragment();
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            adf.setArguments(args);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EmployeeGPListFragment").commit();

                        }else if(menuItem.getItemId()==R.id.action_delete)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("Confirm Action");
                            builder.setMessage("Do you want to delete employee?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteEmployee(model.getGatepassEmpId());
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

    private void getUpdateStatus(int gatepassEmpId, int securityIdIn, int status, int gatePassSubType, final EmpGatePass model) {
        Log.e("PARAMETER","                 EMP GATE PASS ID     "+gatepassEmpId +"       SATAUS      "+status +"      SECURITY ID    "+securityIdIn+"             GATE PASS TYPE     "+gatePassSubType);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateEmpGatepass(gatepassEmpId,securityIdIn,status,gatePassSubType);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE EMP STATUS", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                sendNotification(model.getGatepassEmpId(),model.getUserId());



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

    private void sendNotification(Integer gatepassEmpId, Integer empId) {
        Log.e("PARAMETER","               GATE PASS EMP ID              "+gatepassEmpId +"             EMP ID      "+empId);
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.sendNotification(gatepassEmpId,empId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("NOTIFI SEND EMP : ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
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

    private void deleteEmployee(int gatepassEmpId) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.deleteEmployeeGatepass(gatepassEmpId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("DELETE EMPLOYEE: ", " - " + response.body());

                            if (!response.body().getError()) {

                                MainActivity activity = (MainActivity) context;

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
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
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmp, tvEmpMobile, tvSupMobile,tvSupName, tvOutTime, tvInTime, tvGatePass,tvOut,tvIn,tvHrs,tvTotalHrs,tvGPNo;
        public CircleImageView ivPhoto;
        public ImageView ivEdit;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGPNo = itemView.findViewById(R.id.tvGPNo);
            tvEmp = itemView.findViewById(R.id.tvEmp);
            tvEmpMobile = itemView.findViewById(R.id.tvEmpMobile);
            tvSupMobile = itemView.findViewById(R.id.tvSupMobile);
            tvSupName = itemView.findViewById(R.id.tvSupName);
            tvOutTime = itemView.findViewById(R.id.tvOutTime);
            tvInTime = itemView.findViewById(R.id.tvInTime);
            tvGatePass = itemView.findViewById(R.id.tvGatePass);

            tvOut = itemView.findViewById(R.id.tvOut);
            tvIn = itemView.findViewById(R.id.tvIn);
            tvHrs = itemView.findViewById(R.id.tvHrs);
            tvTotalHrs = itemView.findViewById(R.id.tvTotalHrs);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);

            ivEdit=itemView.findViewById(R.id.ivEdit);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }

}
