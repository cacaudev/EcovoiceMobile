package com.example.cacau2.ecovoicetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterNewsFeed extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterNewsFeed(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_time_line f1 = new Fragment_time_line();
                return f1;
            case 1:
                Fragment_news_feed_my_trees f2 = new Fragment_news_feed_my_trees();
                return f2;
            case 2:
                Fragment_news_feed_stats f3 = new Fragment_news_feed_stats();
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