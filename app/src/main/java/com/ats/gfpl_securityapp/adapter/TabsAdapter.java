package com.ats.gfpl_securityapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ats.gfpl_securityapp.fragment.NotificationListFragment;
import com.ats.gfpl_securityapp.fragment.VisitorDetailFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm,int NoofTabs) {
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                VisitorDetailFragment visitorDetailFragment = new VisitorDetailFragment();
                return visitorDetailFragment;
            case 1:
                NotificationListFragment notificationListFragment = new NotificationListFragment();
                return notificationListFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
