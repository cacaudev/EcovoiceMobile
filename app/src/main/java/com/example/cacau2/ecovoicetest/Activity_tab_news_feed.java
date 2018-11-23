package com.example.cacau2.ecovoicetest;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import Base.BaseMenuActivity;


public class Activity_tab_news_feed extends BaseMenuActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_tab_news_feed);
            ViewPager viewPager =  findViewById(R.id.view_pager_news_feed);

            this.setUpLayout();

            /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            */


            TabLayout tabLayout =  findViewById(R.id.tab_layout_news_feed);
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.time_line)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_trees)));
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.stats)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




            final PagerAdapterNewsFeed adapter = new PagerAdapterNewsFeed(getSupportFragmentManager(), tabLayout.getTabCount());
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
