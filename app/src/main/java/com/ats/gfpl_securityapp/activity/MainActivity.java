package com.ats.gfpl_securityapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.fragment.AddCompanyFragment;
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.AddPurposeFragment;
import com.ats.gfpl_securityapp.fragment.AddVisitingCardFragment;
import com.ats.gfpl_securityapp.fragment.CompanyListFragment;
import com.ats.gfpl_securityapp.fragment.DashboardFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassDetailFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassDetailFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassFragment;
import com.ats.gfpl_securityapp.fragment.InwardgatePassListFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.MaterialFragment;
import com.ats.gfpl_securityapp.fragment.MaterialTrackingDetailFragment;
import com.ats.gfpl_securityapp.fragment.OutwardApproveFragment;
import com.ats.gfpl_securityapp.fragment.OutwardFragment;
import com.ats.gfpl_securityapp.fragment.OutwardGatePassFragment;
import com.ats.gfpl_securityapp.fragment.PendingInwardFragment;
import com.ats.gfpl_securityapp.fragment.PurposeListFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.fragment.VisitingCardListFragment;
import com.ats.gfpl_securityapp.fragment.VisitorDetailFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassListFragment;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    public String strIntentMain,strMeeting;
    Login loginUser;
    Sync sync;
    ArrayList<Sync> syncArray = new ArrayList<>();
    ArrayList<Integer> syncId= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (PermissionsUtil.checkAndRequestPermissions(this)) {
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        try {
            String userStr = CustomSharedPreference.getString(getApplication(), CustomSharedPreference.MAIN_KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUser);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gson = new Gson();
            String json = prefs.getString("Sync", null);
            Type type = new TypeToken<ArrayList<Sync>>() {}.getType();
            syncArray= gson.fromJson(json, type);

            Log.e("SYNC MAIN : ", "--------USER-------" + syncArray);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        for(int i=0;i<syncArray.size();i++)
        {
            Log.e("MY TAG","-----syncArray-------");
           if(syncArray.get(i).getSettingKey().equals("Security"))
           {
               Log.e("MY TAG1","-----Security-------");
                if(syncArray.get(i).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId())))
                {
                    navigationView.getMenu().findItem(R.id.nav_dash).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_tracking).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_purpose_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_purpose_list_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_add_card).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_card_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_add_comp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_add_comp_list).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_approve).setVisible(false);
                    Log.e("MY TAG","-----Security-------");
                }
           }
//           else{
//               navigationView.getMenu().findItem(R.id.nav_emp_gp).setVisible(true);
//               navigationView.getMenu().findItem(R.id.nav_material_gp).setVisible(false);
//           }
            if(syncArray.get(i).getSettingKey().equals("Admin"))
            {
                Log.e("MY TAG1","-----Admin-------");
                if(syncArray.get(i).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId())))
                {
                    navigationView.getMenu().findItem(R.id.nav_dash).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_material_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_tracking).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_purpose_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_purpose_list_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_add_card).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_card_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_add_comp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_add_comp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_approve).setVisible(true);
                    Log.e("MY TAG","-----Admin-------");
                }
//                else {
//                    navigationView.getMenu().findItem(R.id.nav_purpose_gp).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.nav_purpose_list_gp).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.nav_add_card).setVisible(false);
//                    navigationView.getMenu().findItem(R.id.nav_card_list).setVisible(false);
//                }
            }
            if(syncArray.get(i).getSettingKey().equals("Supervisor")) {
                Log.e("MY TAG1","------Supervisor------");
                if (syncArray.get(i).getSettingValue().equals(String.valueOf(loginUser.getEmpCatId()))) {
                    navigationView.getMenu().findItem(R.id.nav_dash).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_visitor_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_emp_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_material_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_material_tracking).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_purpose_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_purpose_list_gp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_add_card).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_card_list).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_list).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_add_comp).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_add_comp_list).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_outward_gp_approve).setVisible(true);
                    Log.e("MY TAG","------Supervisor------");
                }
//                else {
//                    navigationView.getMenu().findItem(R.id.nav_maintenance_gp).setVisible(true);
//                    navigationView.getMenu().findItem(R.id.nav_visitor_gp).setVisible(true);
//                }
            }

        }
        View header = navigationView.getHeaderView(0);

        TextView tvNavHeadName = header.findViewById(R.id.tvNavHeadName);
        TextView tvNavHeadDesg = header.findViewById(R.id.tvNavHeadDesg);
        CircleImageView ivNavHeadPhoto = header.findViewById(R.id.ivNavHeadPhoto);

        if (loginUser != null) {
            tvNavHeadName.setText("" + loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname());
            if(loginUser.getEmpCatId()==1) {
                tvNavHeadDesg.setText("Superwiser");
            }else  if(loginUser.getEmpCatId()==2) {
                tvNavHeadDesg.setText("Admin");
            }else  if(loginUser.getEmpCatId()==3) {
                tvNavHeadDesg.setText("Employee");
            }else  if(loginUser.getEmpCatId()==4) {
                tvNavHeadDesg.setText("Security");
            }

            try {
                Picasso.with(MainActivity.this).load(Constants.IMAGE_URL+loginUser.getEmpPhoto()).placeholder(getResources().getDrawable(R.drawable.profile)).into(ivNavHeadPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Intent intent = getIntent();
            strIntentMain = intent.getExtras().getString("model");
            Log.e("StringMain","--------------------------"+strIntentMain);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(strIntentMain!=null) {
                if (strIntentMain.equalsIgnoreCase("Add visitor getPass")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new VisitorGatePassFragment(), "DashFragment");
                    ft.commit();
                } else if (strIntentMain.equalsIgnoreCase("Add visitor getPass list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Add Maintenance getPass")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaintenanceGatePassFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Add Maintenance getPass list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
                    ft.commit();
                } else if (strIntentMain.equalsIgnoreCase("Employee gate pass")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new EmployeeGatePassFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Employee gate pass list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Material gate pass")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new InwardGatePassFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Material gate pass list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Purpose")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new AddPurposeFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Purpose list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new PurposeListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Material Tracking")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Close Meeting Visitor")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Close Meeting Maintenance")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Visit Card")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new AddVisitingCardFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Visit Card List")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new VisitingCardListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("1")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("2")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("3")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("4")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("5")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Outward gate pass list")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new OutwardFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Outward Close")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new OutwardFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Outward gate pass")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new OutwardGatePassFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Outward gate pass approve")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new OutwardApproveFragment(), "DashFragment");
                    ft.commit();
                }
                else if (strIntentMain.equalsIgnoreCase("Add Company")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new AddCompanyFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Add Company List")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new CompanyListFragment(), "DashFragment");
                    ft.commit();
                }else if (strIntentMain.equalsIgnoreCase("Employee Pending")) {

//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
//                    ft.commit();

                    Fragment adf = new MaterialFragment();
                    Bundle args = new Bundle();
                    args.putString("Tracking", "Employee Pending");
                    adf.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "DashFragment").commit();

                }else if (strIntentMain.equalsIgnoreCase("Employee Approve")) {

//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
//                    ft.commit();

                    Fragment adf = new MaterialFragment();
                    Bundle args = new Bundle();
                    args.putString("Tracking", "Employee Approve");
                    adf.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "DashFragment").commit();

                }else if (strIntentMain.equalsIgnoreCase("Employee Rejected")) {

//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
//                    ft.commit();

                    Fragment adf = new MaterialFragment();
                    Bundle args = new Bundle();
                    args.putString("Tracking", "Employee Rejected");
                    adf.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "DashFragment").commit();

                }
                else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
                    ft.commit();
                }
            }
        else{

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
            ft.commit();

            }

    }

    @Override
    public void onBackPressed() {

        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment dashFragment = getSupportFragmentManager().findFragmentByTag("DashFragment");
        Fragment visitorGPListFragment = getSupportFragmentManager().findFragmentByTag("VisitorGPListFragment");
        Fragment maintenanceGPListFragment = getSupportFragmentManager().findFragmentByTag("MaintenanceGPListFragment");
        Fragment employeeGPListFragment = getSupportFragmentManager().findFragmentByTag("EmployeeGPListFragment");
        Fragment inwardGPListFragment = getSupportFragmentManager().findFragmentByTag("InwardGPListFragment");
        Fragment materialTrackingListFragment = getSupportFragmentManager().findFragmentByTag("MaterialTrackingListFragment");
        Fragment visitCardListFragment = getSupportFragmentManager().findFragmentByTag("VisitCardListFragment");
        Fragment outwardListFragment = getSupportFragmentManager().findFragmentByTag("OutwardListFragment");
        Fragment companyListFragment = getSupportFragmentManager().findFragmentByTag("CompanyListFragment");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit instanceof DashboardFragment && exit.isVisible()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
            builder.setMessage("Exit Application ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateToken(loginUser.getEmpId(), "");
                    finish();
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

        } else if (dashFragment instanceof VisitorGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof VisitorGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof VisitorDetailFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeFragment && dashFragment.isVisible() ||
                dashFragment instanceof MaintenanceGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof MaintenanceGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassDetailFragment && dashFragment.isVisible() ||
                dashFragment instanceof InwardGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof InwardgatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof VisitingCardListFragment && dashFragment.isVisible() ||
                dashFragment instanceof AddVisitingCardFragment && dashFragment.isVisible() ||
                dashFragment instanceof InwardGatePassDetailFragment && dashFragment.isVisible() ||
                dashFragment instanceof MaterialFragment && dashFragment.isVisible() ||
                dashFragment instanceof AddCompanyFragment && dashFragment.isVisible() ||
                dashFragment instanceof CompanyListFragment && dashFragment.isVisible() ||
                dashFragment instanceof OutwardApproveFragment && dashFragment.isVisible() ||
                dashFragment instanceof OutwardGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof OutwardFragment && dashFragment.isVisible() ||
                dashFragment instanceof PendingInwardFragment && dashFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
            ft.commit();

        } else if (visitorGPListFragment instanceof AddInfoFragment && visitorGPListFragment.isVisible() ||
                visitorGPListFragment instanceof VisitorGatePassFragment && visitorGPListFragment.isVisible() ||
                visitorGPListFragment instanceof TabFragment && visitorGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
            ft.commit();

        } else if (maintenanceGPListFragment instanceof AddInfoFragment && maintenanceGPListFragment.isVisible() ||
                maintenanceGPListFragment instanceof MaintenanceGatePassFragment && maintenanceGPListFragment.isVisible() ||
                maintenanceGPListFragment instanceof TabFragment && maintenanceGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
            ft.commit();

        }else if (employeeGPListFragment instanceof EmployeeGatePassFragment && employeeGPListFragment.isVisible() ||
                employeeGPListFragment instanceof EmployeeGatePassDetailFragment && employeeGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EmployeeFragment(), "DashFragment");
            ft.commit();

        }else if (inwardGPListFragment instanceof InwardGatePassFragment && inwardGPListFragment.isVisible() ||
                inwardGPListFragment instanceof InwardGatePassDetailFragment && inwardGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
            ft.commit();

        }else if (materialTrackingListFragment instanceof MaterialTrackingDetailFragment && materialTrackingListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
            ft.commit();
        }else if (visitCardListFragment instanceof AddVisitingCardFragment && visitCardListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitingCardListFragment(), "DashFragment");
            ft.commit();
        }else if (outwardListFragment instanceof OutwardGatePassFragment && outwardListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new OutwardFragment(), "DashFragment");
            ft.commit();
        }else if (companyListFragment instanceof AddCompanyFragment && companyListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new CompanyListFragment(), "DashFragment");
            ft.commit();
        }

        else {
            super.onBackPressed();
        }
    }

    private void updateToken(Integer empId, String token) {
        Log.e("PARAMETERS : ", "       EMP ID : " + empId +"             TOKEN:"  +token);

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(MainActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateUserToken(empId, token);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("TOKEN INFO NULL : ", "------------" + response.body());
                            finish();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    // Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dash) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
            ft.commit();

        } else if (id == R.id.nav_visitor_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new VisitorGatePassFragment(), "DashFragment");
//            ft.commit();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                 intent.putExtra("model", "Add visitor getPass");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


        } else if (id == R.id.nav_visitor_gp_list) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add visitor getPass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_maintenance_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Maintenance getPass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_maintenance_gp_list) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Maintenance getPass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_emp_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Employee gate pass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_emp_gp_list) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Employee gate pass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else if (id == R.id.nav_outward_gp_approve) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Outward gate pass approve");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new OutwardGatePassFragment(), "DashFragment");
//            ft.commit();

        }
        else if (id == R.id.nav_outward_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Outward gate pass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new OutwardGatePassFragment(), "DashFragment");
//            ft.commit();

        } else if (id == R.id.nav_outward_gp_list) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Outward gate pass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new OutwardFragment(), "DashFragment");
//            ft.commit();
        }
        else if (id == R.id.nav_material_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material gate pass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_material_gp_list) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material gate pass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else if (id == R.id.nav_purpose_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Purpose");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.nav_purpose_list_gp) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Purpose list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (id == R.id.nav_material_tracking) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material Tracking");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_add_card) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new AddVisitingCardFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Visit Card");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.nav_card_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new VisitingCardListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Visit Card List");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (id == R.id.nav_add_comp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new AddCompanyFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Company");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.nav_add_comp_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new CompanyListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Company List");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
