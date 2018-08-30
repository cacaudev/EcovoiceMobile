package com.example.cacau2.ecovoicetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapterEditProfile extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterEditProfile(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_edit_personal_info f1 = new Fragment_edit_personal_info();
                return f1;
            case 1:
                Fragment_edit_personal_info f2 = new Fragment_edit_personal_info();
                return f2;
            case 2:
                Fragment_edit_personal_info f3 = new Fragment_edit_personal_info();
                return f3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}