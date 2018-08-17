package com.example.cacau2.ecovoicetest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Activity_tab_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_profile);
        ViewPager viewPager =  findViewById(R.id.vpPager);
        getSupportActionBar().setElevation(0);




        TabLayout tabLayout =  findViewById(R.id.tab_layout_profile);
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