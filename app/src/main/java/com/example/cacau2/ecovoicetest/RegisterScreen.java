package com.example.cacau2.ecovoicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Register"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_register_screen);
    }
}
