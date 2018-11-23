package com.example.cacau2.ecovoicetest;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import Base.BaseMenuActivity;

public class Activity_tab_profile extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_profile);
        ViewPager viewPager =  findViewById(R.id.vpPager);

        this.setUpLayout();


        TabLayout tabLayout =  findViewById(R.id.tab_layout_news_feed);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.profile)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.trees)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.species)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




       final com.example.cacau2.ecovoicetest.PagerAdapterProfile adapter = new com.example.cacau2.ecovoicetest.PagerAdapterProfile(getSupportFragmentManager(), tabLayout.getTabCount());
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