package com.ats.gfpl_securityapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.AddPurposeFragment;
import com.ats.gfpl_securityapp.fragment.DashboardFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassDetailFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassFragment;
import com.ats.gfpl_securityapp.fragment.InwardgatePassListFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.MaterialFragment;
import com.ats.gfpl_securityapp.fragment.PendingInwardFragment;
import com.ats.gfpl_securityapp.fragment.PurposeListFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassListFragment;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    public String strIntentMain;
    Login loginUser;
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

        View header = navigationView.getHeaderView(0);

        TextView tvNavHeadName = header.findViewById(R.id.tvNavHeadName);
        TextView tvNavHeadDesg = header.findViewById(R.id.tvNavHeadDesg);
        CircleImageView ivNavHeadPhoto = header.findViewById(R.id.ivNavHeadPhoto);

        if (loginUser != null) {
            tvNavHeadName.setText("" + loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname());
            tvNavHeadDesg.setText("" + loginUser.getEmpEmail());

            try {
                Picasso.with(MainActivity.this).load(Constants.IMAGE_URL + "" + loginUser.getEmpPhoto()).placeholder(getResources().getDrawable(R.drawable.profile)).into(ivNavHeadPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            //strIntentMain = getIntent().getStringExtra("model");
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
                    ft.replace(R.id.content_frame, new EmployeeGatePassListFragment(), "DashFragment");
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
                }
                else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
                    ft.commit();
                }
            }else{
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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit instanceof DashboardFragment && exit.isVisible()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else if (dashFragment instanceof VisitorGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof VisitorGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof MaintenanceGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof MaintenanceGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof EmployeeGatePassListFragment && dashFragment.isVisible() ||
                dashFragment instanceof InwardGatePassFragment && dashFragment.isVisible() ||
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
            ft.replace(R.id.content_frame, new EmployeeGatePassListFragment(), "DashFragment");
            ft.commit();

        }else if (inwardGPListFragment instanceof InwardGatePassFragment && inwardGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
            ft.commit();

        }

        else {
            super.onBackPressed();
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

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add visitor getPass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_maintenance_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new MaintenanceGatePassFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Maintenance getPass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_maintenance_gp_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Add Maintenance getPass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_emp_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new EmployeeGatePassFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Employee gate pass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_emp_gp_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new EmployeeGatePassListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Employee gate pass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        } else if (id == R.id.nav_material_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new InwardGatePassFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material gate pass");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_material_gp_list) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new InwardgatePassListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material gate pass list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else if (id == R.id.nav_purpose_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new AddPurposeFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Purpose");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }else if (id == R.id.nav_purpose_list_gp) {

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new PurposeListFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Purpose list");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        }
        else if (id == R.id.nav_material_tracking) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new MaterialFragment(), "DashFragment");
//            ft.commit();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("model", "Material Tracking");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
