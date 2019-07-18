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
import com.ats.gfpl_securityapp.interfaces.ApproveInwardInterface;
import com.ats.gfpl_securityapp.interfaces.PendingInwardInterface;
import com.ats.gfpl_securityapp.interfaces.RejectedInwardInterface;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tab;
    static String type;
    FragmentPagerAdapter adapterViewPager;
    public static Login staticLoginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_material, container, false);
        getActivity().setTitle("Document Tracking");
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
            type = getArguments().getString("Tracking");
            Log.e("TRACKING","----------------------"+type);
            if(type!=null) {
                if (type.equalsIgnoreCase("Employee Pending")) {
                    viewPager.setCurrentItem(0);
                } else if (type.equalsIgnoreCase("Employee Approve")) {
                    viewPager.setCurrentItem(1);
                }else if (type.equalsIgnoreCase("Employee Rejected")) {
                    viewPager.setCurrentItem(2);
                }
            }

        } catch (Exception e) {
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

                    PendingInwardInterface fragmentPending = (PendingInwardInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentPending != null) {
                        fragmentPending.fragmentBecameVisible();
                    }

                } else if (position == 1) {

                    ApproveInwardInterface fragmentApprove = (ApproveInwardInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentApprove != null) {
                        fragmentApprove.fragmentBecameVisible();
                    }
                }else if (position == 2) {

                    RejectedInwardInterface fragmentRejected = (RejectedInwardInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentRejected != null) {
                        fragmentRejected.fragmentBecameVisible();
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
            staticLoginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + staticLoginUser);
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
                return new PendingInwardFragment();
            } else if(position==1) {
                return new ApproveInwardFragment();
            }else {
                return new RejectInwardFragment();
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
                    return "Approve";
                case 2:
                    return "Rejected";
                default:
                    return null;
            }
        }
    }


}
