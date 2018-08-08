package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Activity_create_specie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_specie);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN  |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );


    }
    public void back(View view){
        Intent intent = new Intent(getBaseContext(),Activity_list_species.class);
        startActivity(intent);
        finish();
    }
}
