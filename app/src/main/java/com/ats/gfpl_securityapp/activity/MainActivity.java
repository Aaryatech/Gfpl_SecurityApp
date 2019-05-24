package com.ats.gfpl_securityapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.VisitorGatePassListAdapter;
import com.ats.gfpl_securityapp.fragment.AddInfoFragment;
import com.ats.gfpl_securityapp.fragment.DashboardFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassFragment;
import com.ats.gfpl_securityapp.fragment.EmployeeGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassFragment;
import com.ats.gfpl_securityapp.fragment.InwardGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassFragment;
import com.ats.gfpl_securityapp.fragment.MaintenanceGatePassListFragment;
import com.ats.gfpl_securityapp.fragment.TabFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassFragment;
import com.ats.gfpl_securityapp.fragment.VisitorGatePassListFragment;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;

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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment dashFragment = getSupportFragmentManager().findFragmentByTag("DashFragment");
        Fragment visitorGPListFragment = getSupportFragmentManager().findFragmentByTag("VisitorGPListFragment");
        Fragment maintenanceGPListFragment = getSupportFragmentManager().findFragmentByTag("MaintenanceGPListFragment");


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
                dashFragment instanceof InwardGatePassFragment && dashFragment.isVisible() ||
                dashFragment instanceof InwardGatePassListFragment && dashFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new DashboardFragment(), "Exit");
            ft.commit();

        } else if (visitorGPListFragment instanceof AddInfoFragment && visitorGPListFragment.isVisible() ||
                visitorGPListFragment instanceof TabFragment && visitorGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
            ft.commit();

        } else if (maintenanceGPListFragment instanceof AddInfoFragment && maintenanceGPListFragment.isVisible() ||
                maintenanceGPListFragment instanceof TabFragment && maintenanceGPListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
            ft.commit();

        } else {
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

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitorGatePassFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_visitor_gp_list) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_maintenance_gp) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new MaintenanceGatePassFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_maintenance_gp_list) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new MaintenanceGatePassListFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_emp_gp) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EmployeeGatePassFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_emp_gp_list) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EmployeeGatePassListFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_material_gp) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new InwardGatePassFragment(), "DashFragment");
            ft.commit();

        } else if (id == R.id.nav_material_gp_list) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new InwardGatePassListFragment(), "DashFragment");
            ft.commit();


        } else if (id == R.id.nav_material_tracking) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
