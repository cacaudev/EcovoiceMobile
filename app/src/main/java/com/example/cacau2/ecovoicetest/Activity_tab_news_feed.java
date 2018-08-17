package com.example.cacau2.ecovoicetest;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class Activity_tab_news_feed extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_news_feed);
            ViewPager viewPager =  findViewById(R.id.view_pager_news_feed);
            getSupportActionBar().setElevation(0);




            TabLayout tabLayout =  findViewById(R.id.tab_layout_news_feed);
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.time_line)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_trees)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.stats)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




            final com.example.cacau2.ecovoicetest.PagerAdapterNewsFeed adapter = new com.example.cacau2.ecovoicetest.PagerAdapterNewsFeed(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
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






    }
