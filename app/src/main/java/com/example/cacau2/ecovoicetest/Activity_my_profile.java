package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Activity_my_profile extends android.support.v4.app.Fragment {



    private String title;
    private int page;
    View view;


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_profile, container, false);
        Button newPage = (Button) view.findViewById(R.id.btn_edit_prof);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_tab_edit_profile.class);

                startActivity(intent);
                getActivity().finish();

            }
        });

        return view;
    }
    public void btn_edit(){
        Intent intent = new Intent(getContext(),Activity_edit_profile.class);
        getActivity().startActivity(intent);

    }
}
