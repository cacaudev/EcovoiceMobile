package com.example.cacau2.ecovoicetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterProfile extends FragmentStatePagerAdapter {





    int mNumOfTabs;

    public PagerAdapterProfile(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Activity_my_profile tab1 = new Activity_my_profile();
                return tab1;
            case 1:
                Activity_my_trees tab2 = new Activity_my_trees();
                return tab2;
            case 2:
                Activity_my_species tab3 = new Activity_my_species();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}