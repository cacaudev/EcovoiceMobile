package com.example.cacau2.ecovoicetest;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Activity_tab_profile extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_profile);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }






    public class PagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return Activity_my_profile.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Activity_my_trees.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Activity_my_species.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            String s = "";
            switch (position){
                case 0:
                    s = getResources().getString(R.string.profile);
                    return s;
                case 1:
                    s =getResources().getString(R.string.trees);
                    return s;
                case 2:
                    s =getResources().getString(R.string.species);
                    return s;

            }
            return "";
        }

    }
    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}