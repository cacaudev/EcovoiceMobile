package com.example.cacau2.ecovoicetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class Activity_my_species extends android.support.v4.app.Fragment {

    private String title;
    private int page;



    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_species, container, false);
        ListView l = v.findViewById(R.id.list_view_my_species);

        l.setEmptyView(v.findViewById(R.id.empty_species));

        return v;
    }
}

