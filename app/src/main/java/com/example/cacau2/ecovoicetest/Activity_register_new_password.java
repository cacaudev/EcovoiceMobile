package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_register_new_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_password);
        getSupportActionBar().hide();
    }
    public void cancel(View view){
        Intent intent = new Intent(getBaseContext(),LoginScreen.class);
        startActivity(intent);
        finish();
    }

}
