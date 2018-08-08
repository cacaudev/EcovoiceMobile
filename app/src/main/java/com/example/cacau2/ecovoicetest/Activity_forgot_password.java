package com.example.cacau2.ecovoicetest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_forgot_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
    }
    public void sendInstruction(View view){

    }
    public void haveAcount(View view){
        Intent register = new Intent(getBaseContext(), RegisterScreen.class);
        startActivity(register);
    }
}
