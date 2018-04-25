package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Login"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_login_screen);
    }

    public void cadastraArvore(View v){
        Intent it = new Intent(getBaseContext(), Cadastro_de_Arvore.class);
        startActivity(it);
    }
}
