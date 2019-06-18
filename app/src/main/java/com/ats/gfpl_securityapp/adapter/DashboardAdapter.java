package com.ats.gfpl_securityapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.model.Dashboard;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    private ArrayList<Dashboard> dashboardList;
    private Context context;
    private Login loginMain;
    ArrayList<Sync> syncArray = new ArrayList<>();

    public DashboardAdapter(ArrayList<Dashboard> dashboardList, Context context, Login loginMain, ArrayList<Sync> syncArray) {
        this.dashboardList = dashboardList;
        this.context = context;
        this.loginMain = loginMain;
        this.syncArray = syncArray;
    }

    @NonNull
    @Override
    public DashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_dashboard_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.MyViewHolder myViewHolder, int i) {
        Dashboard model=dashboardList.get(i);


        if(syncArray!=null) {
            for (int j = 0; j < syncArray.size(); j++) {
                if (syncArray.get(j).getSettingKey().equals("Security")) {
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginMain.getEmpCatId()))) {

                        myViewHolder.linearLayoutVisitor1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitor2.setVisibility(View.VISIBLE);
                        myViewHolder.LinearLayoutVisitor3.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutVisitorEmployee2.setVisibility(View.GONE);
                        myViewHolder.linearLayoutVisitorEmployee3.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewEmployeeMeetingCompleted.setVisibility(View.GONE);
                        //cardViewMaintenancePending.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorMaintenance1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutMaintenanceEmployee2.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewMaintenancePendingEmp.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewParcel.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP3.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutDepartment1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutEmployee1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutEmployee2.setVisibility(View.VISIBLE);
                    }
                } else if(syncArray.get(j).getSettingKey().equals("Supervisor")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginMain.getEmpCatId()))) {
                        myViewHolder.linearLayoutVisitor1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutVisitor2.setVisibility(View.GONE);
                        myViewHolder.LinearLayoutVisitor3.setVisibility(View.GONE);
                        myViewHolder.linearLayoutVisitorEmployee1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee3.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewEmployeeMeetingCompleted.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewMaintenancePending.setVisibility(View.GONE);
                        myViewHolder.linearLayoutVisitorMaintenance1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutMaintenanceEmployee2.setVisibility(View.GONE);
                        myViewHolder.linearLayoutSuperGP1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutSuperGP2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutGP2.setVisibility(View.GONE);
                        myViewHolder.linearLayoutGP3.setVisibility(View.GONE);
                        myViewHolder.linearLayoutDepartment1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutEmployee1.setVisibility(View.GONE);
                        myViewHolder.linearLayoutEmployee2.setVisibility(View.GONE);
                    }
                }else if(syncArray.get(j).getSettingKey().equals("Admin")){
                    if (syncArray.get(j).getSettingValue().equals(String.valueOf(loginMain.getEmpCatId()))) {

                        myViewHolder.linearLayoutVisitor1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitor2.setVisibility(View.VISIBLE);
                        myViewHolder.LinearLayoutVisitor3.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorEmployee3.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewMeetingCompletedForAdmin.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewEmployeeMeetingCompleted.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewMaintenancePending.setVisibility(View.VISIBLE);
                        myViewHolder.cardViewParcel.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutVisitorMaintenance1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutMaintenanceEmployee2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP2.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutGP3.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutDepartment1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutEmployee1.setVisibility(View.VISIBLE);
                        myViewHolder.linearLayoutEmployee2.setVisibility(View.VISIBLE);


                    }
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayoutVisitor1,linearLayoutVisitor2,LinearLayoutVisitor3,linearLayoutVisitorEmployee1,linearLayoutVisitorEmployee2,linearLayoutVisitorEmployee3,linearLayoutVisitorMaintenance1,linearLayoutMaintenanceEmployee2,linearLayoutGP1,linearLayoutGP2,linearLayoutGP3,linearLayoutDepartment1,linearLayoutEmployee1,linearLayoutEmployee2,linearLayoutSuperGP1,linearLayoutSuperGP2;
        public CardView cardViewMaintenancePending,cardViewEmployeeMeetingCompleted,cardViewParcel,cardViewMeetingCompletedForAdmin,cardViewMaintenancePendingEmp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutVisitor1=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitor1);
            linearLayoutVisitor2=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitor2);
            LinearLayoutVisitor3=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitor3);
            linearLayoutVisitorEmployee1=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitorEmployee1);
            linearLayoutVisitorEmployee2=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitorEmployee2);
            linearLayoutVisitorEmployee3=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitorEmployee3) ;
            linearLayoutVisitorMaintenance1=(LinearLayout)itemView.findViewById(R.id.linearLayoutVisitorMaintenance1);
            linearLayoutMaintenanceEmployee2=(LinearLayout)itemView.findViewById(R.id.linearLayoutMaintenanceEmployee2);
            linearLayoutGP1=(LinearLayout)itemView.findViewById(R.id.linearLayoutGP1);
            linearLayoutGP2=(LinearLayout)itemView.findViewById(R.id.linearLayoutGP2);
            linearLayoutGP3=(LinearLayout)itemView.findViewById(R.id.linearLayoutGP3);
            linearLayoutDepartment1=(LinearLayout)itemView.findViewById(R.id.linearLayoutDepartment1);
            linearLayoutEmployee1=(LinearLayout)itemView.findViewById(R.id.linearLayoutEmployee1);
            linearLayoutEmployee2=(LinearLayout)itemView.findViewById(R.id.linearLayoutEmployee2);
            linearLayoutSuperGP1=(LinearLayout)itemView.findViewById(R.id.linearLayoutSuperGP1);
            linearLayoutSuperGP2=(LinearLayout)itemView.findViewById(R.id.linearLayoutSuperGP2);
            cardViewMaintenancePending=(CardView)itemView.findViewById(R.id.cardViewMaintenancePending);
            cardViewEmployeeMeetingCompleted=(CardView)itemView.findViewById(R.id.cardViewEmployeeMeetingCompleted);
            cardViewParcel=(CardView)itemView.findViewById(R.id.cardViewParcel);
            cardViewMeetingCompletedForAdmin=(CardView)itemView.findViewById(R.id.cardViewMeetingCompletedForAdmin);
            cardViewMaintenancePendingEmp=(CardView)itemView.findViewById(R.id.cardViewMaintenancePendingEmp);
        }
    }
}
