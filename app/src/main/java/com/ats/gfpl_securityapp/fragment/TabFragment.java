package com.ats.gfpl_securityapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.interfaces.NotificationInterface;
import com.ats.gfpl_securityapp.interfaces.VisitorDetailInterface;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;
    public static VisitorList staticVisitorModel;
    public static String  fromDate,toDate,empId;
    public  static ArrayList<Integer> getPassType = new ArrayList<>();
    public  static ArrayList<Integer> status = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        getActivity().setTitle("Details");

        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tab.setupWithViewPager(viewPager);
                } catch (Exception e) {
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {

                    VisitorDetailInterface fragmentDetail = (VisitorDetailInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentDetail != null) {
                        fragmentDetail.fragmentBecameVisible();
                    }

                } else if (position == 1) {

                    NotificationInterface fragmentNotify = (NotificationInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentNotify != null) {
                        fragmentNotify.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        try {
            String json = getArguments().getString("model");
            Gson gsonPlant = new Gson();
            staticVisitorModel = gsonPlant.fromJson(json, VisitorList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
             fromDate = getArguments().getString("fromDate");
             Log.e("From Date","--------------------------------------------"+fromDate);
             toDate = getArguments().getString("toDate");
            Log.e("To Date","--------------------------------------------"+toDate);
             empId = getArguments().getString("empId");
            Log.e("Emp Id","--------------------------------------------"+empId);
            getPassType = getArguments().getIntegerArrayList("getPassType");
            Log.e("Gate Pass Type","--------------------------------------------"+getPassType);
            status = getArguments().getIntegerArrayList("status");
            Log.e("Status","--------------------------------------------"+status);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }




    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new VisitorDetailFragment();
            } else {
                return new NotificationListFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Detail";
                case 1:
                    return "Notification";
                default:
                    return null;
            }
        }
    }

}
