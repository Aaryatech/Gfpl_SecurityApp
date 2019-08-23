package com.ats.gfpl_securityapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.ats.gfpl_securityapp.interfaces.CloseEmpInterface;
import com.ats.gfpl_securityapp.interfaces.OutEmpInterface;
import com.ats.gfpl_securityapp.interfaces.PendingEmpInterface;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;
    public static Login loginUser,loginUserMain;
    public static String strIntentMain;
    public static ArrayList<Sync> syncArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        getActivity().setTitle("Employee Details");

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

        try {
            strIntentMain = getArguments().getString("model");
            Log.e("StringMain Emp", "--------------------------" + strIntentMain);

            if(strIntentMain!=null) {
                if (strIntentMain.equalsIgnoreCase("OutEmp")) {
                    viewPager.setCurrentItem(1);
                }else if(strIntentMain.equalsIgnoreCase("SupOut"))
                {
                    viewPager.setCurrentItem(1);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {

                    PendingEmpInterface fragmentPending = (PendingEmpInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentPending != null) {
                        fragmentPending.fragmentBecameVisible();
                    }

                } else if (position == 1) {

                    OutEmpInterface fragmentOut = (OutEmpInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentOut != null) {
                        fragmentOut.fragmentBecameVisible();
                    }
                }else if (position == 2) {

                    CloseEmpInterface fragmentClose = (CloseEmpInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentClose != null) {
                        fragmentClose.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.MAIN_KEY_USER);
            Gson gson = new Gson();
            loginUserMain = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUserMain);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Gson gson = new Gson();
            String json = prefs.getString("Sync", null);
            Type type = new TypeToken<ArrayList<Sync>>() {}.getType();
            syncArray= gson.fromJson(json, type);

            Log.e("SYNC EMP : ", "--------USER-------" + syncArray);

        }catch (Exception e)
        {
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
                return new EmployeeGatePassListFragment();
            }else  if (position == 1) {
                return new EmployeeOutGatePassFragment();
            }  else {
                return new EmployeeCloseGatePassFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pending";
                case 1:
                    return "Out";
                case 2:
                    return "Close";
                default:
                    return null;
            }
        }
    }


}
