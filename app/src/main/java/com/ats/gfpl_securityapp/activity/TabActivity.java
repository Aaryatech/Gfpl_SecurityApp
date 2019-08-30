package com.ats.gfpl_securityapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.TabsAdapter;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.google.gson.Gson;

public class TabActivity extends AppCompatActivity {
    public static VisitorList staticVisitorModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        try {
            String strJson = getIntent().getStringExtra("model");
            Gson gson = new Gson();
            staticVisitorModel = gson.fromJson(strJson, VisitorList.class);
            Log.e("Visitor Bin","--------------------------------------Activity----------------------"+staticVisitorModel);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Notification"));
       // tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
