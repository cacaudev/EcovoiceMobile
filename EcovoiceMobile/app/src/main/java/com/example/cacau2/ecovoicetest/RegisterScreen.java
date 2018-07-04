package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RegisterScreen extends AppCompatActivity implements OnClickListener {

    public Button add_account, already_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Register"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_register_screen);

        add_account = findViewById(R.id.register_button);
        add_account.setOnClickListener(this);
        already_have_account = findViewById(R.id.register_back_to_login_button);
        already_have_account.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_button:
                Toast.makeText(getApplicationContext(), "Tela Feed ainda não foi implementada.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_back_to_login_button:
                Intent login_screen = new Intent(getBaseContext(), LoginScreen.class);
                startActivity(login_screen);
                finish();
                break;
        }
    }
}

