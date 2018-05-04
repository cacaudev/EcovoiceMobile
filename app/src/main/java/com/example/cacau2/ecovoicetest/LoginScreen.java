package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity implements OnClickListener {

    public TextView email_text, password_text, forgot_password;
    public EditText email_field, password_field;
    public Button create_account, access, registerTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Login"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_login_screen);

        email_text = findViewById(R.id.login_email_text);
        password_text = findViewById(R.id.login_password_text);

        email_field = findViewById(R.id.login_email_field);
        password_field = findViewById(R.id.login_password_field);

        create_account = findViewById(R.id.login_create_account_button);
        create_account.setOnClickListener(this);
        access = findViewById(R.id.login_access_button);
        access.setOnClickListener(this);
        forgot_password = findViewById(R.id.login_forgot_password_text);
        forgot_password.setOnClickListener(this);
        registerTree = findViewById(R.id.login_cadastrar_arvore);
        registerTree.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.login_access_button:
                Intent n = new Intent(getBaseContext(),MenuMap.class);
                startActivity(n);
                Toast.makeText(getApplicationContext(),"Tela Feed ainda não foi implementada.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_create_account_button:
                Intent register_screen = new Intent(getBaseContext(), RegisterScreen.class);
                startActivity(register_screen);
                finish();
                break;
            case R.id.login_forgot_password_text:
                Toast.makeText(getApplicationContext(),"Tela Mandar Senha ainda não foi implementada.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_cadastrar_arvore:
                Intent it = new Intent(getBaseContext(), Cadastro_de_Arvore.class);
                startActivity(it);
                break;
        }

    }
}
