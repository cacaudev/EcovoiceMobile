package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Activity_list_species extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_species);
        ListView l = findViewById(R.id.listview_list_species);

        l.setEmptyView(findViewById(R.id.empty_list_species));
    }
    public void register_specie(View view){
        Intent intent = new Intent(getBaseContext(), Activity_create_specie.class);
            startActivity(intent);

    }
    public void search_specie(View view){
        Intent intent = new Intent(getBaseContext(), Activity_create_specie.class);
        startActivity(intent);
    }
}
