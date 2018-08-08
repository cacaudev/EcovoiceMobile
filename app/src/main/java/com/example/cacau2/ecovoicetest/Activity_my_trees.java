package com.example.cacau2.ecovoicetest;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Activity_my_trees extends android.support.v4.app.Fragment {

   private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static Activity_my_trees newInstance(int page, String title) {
        Activity_my_trees fragmentFirst = new Activity_my_trees();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_trees, container, false);
        ListView l = v.findViewById(R.id.list_view_my_trees);
        l.setEmptyView(v.findViewById(R.id.empty_trees));

        return v;
    }
}

