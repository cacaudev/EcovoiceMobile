package com.example.cacau2.ecovoicetest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginScreen extends AppCompatActivity{

    public TextView email_text, password_text, forgot_password;
    public EditText email_field, password_field;
    public Button create_account, access, registerTree;
    private ConexaoDB connectionRemoteDB = null;
    SessionManagement session;
    Switch remember_me_switch;

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
        access = findViewById(R.id.login_access_button);
        forgot_password = findViewById(R.id.login_forgot_password_text);

        remember_me_switch = findViewById(R.id.remember_me_switch);

        //Create new connection to remote db
        connectionRemoteDB = new ConexaoDB();
        connectToHeroku();

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());

        //Get user data if remember-me option was checked
        if(session.isRememberChecked()){
            remember_me_switch.setChecked(true);
            HashMap<String, String> current_user = session.getUserDetails();
            email_field.setText(current_user.get(SessionManagement.KEY_EMAIL));
            password_field.setText(current_user.get(SessionManagement.KEY_PASSWORD));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connectionRemoteDB != null){
            if(connectionRemoteDB.getStatus() != ConexaoDB.Status.FINISHED) {
                connectionRemoteDB.cancel(true);
            }
            connectionRemoteDB.disconecta();
            connectionRemoteDB = null;
        }
        Log.v("BancoDeDados", "Conexao está fechada MAIN.class");
    }

    @Override
    protected  void onResume() {
        super.onResume();
        //Get user data if remember-me option was checked
        if(session.isRememberChecked()){
            remember_me_switch.setChecked(true);
            HashMap<String, String> current_user = session.getUserDetails();
            email_field.setText(current_user.get(SessionManagement.KEY_EMAIL));
            password_field.setText(current_user.get(SessionManagement.KEY_PASSWORD));
        }
        Log.v("BancoDeDados", "loginscreen onResume");
    }

    public Boolean checkInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void connectToHeroku() {
        if (checkInternet()) {
            try {
                connectionRemoteDB = (ConexaoDB) new ConexaoDB().execute();
            } catch (Exception e) {
                Log.v("BancoDeDados", "erro ao tentar conexao com o banco - main.class " + e.getMessage());
            }
        } else
            Toast.makeText(getApplicationContext(),"Não tem conexão com internet.",Toast.LENGTH_SHORT).show();
    }

    public void signIn(View view) {
        Boolean resultado_comparacao = false;
        String email_user = email_field.getText().toString();
        String password_plain = password_field.getText().toString();

        if(email_user.matches(""))
            Toast.makeText(getApplicationContext(),R.string.email_blank,Toast.LENGTH_SHORT).show();
        if(password_plain.matches(""))
            Toast.makeText(getApplicationContext(),R.string.password_blank,Toast.LENGTH_SHORT).show();

        if(!email_user.matches("") && !password_plain.matches("")) {

            if (connectionRemoteDB.getConnection() != null) {
                Log.v("BancoDeDados", "Tentou fazer login com banco. Conexao está aberta - main.class ");
                try {
                    resultado_comparacao = new UserQuery(connectionRemoteDB.getConnection()).
                            checkPassword(email_field.getText().toString(), password_field.getText().toString());
                } catch (Exception e) {
                    Log.v("BancoDeDados", "Erro ao tentar fazer query com o banco - main.class");
                }

                if (resultado_comparacao) {
                    Log.v("BancoDeDados", "Senhas deram match MAIN.class");

                    if(remember_me_switch.isChecked()) {
                        session.createLoginSession(email_user, password_plain, true);
                        Log.v("BancoDeDados", "pediu para lembrar MAIN.class");
                    }
                    else {
                        session.createLoginSession(email_user, password_plain, false);
                        Log.v("BancoDeDados", "não pediu para lembrar MAIN.class");
                    }

                    Intent intent = new Intent(getBaseContext(), MenuMap.class);
                    //intent.putExtra("email", email_field.getText().toString());
                    startActivity(intent);
                } else {
                    Log.v("BancoDeDados", "Senhas deram errado MAIN.class");
                    Toast.makeText(getApplicationContext(), R.string.password_incorrect,
                            Toast.LENGTH_SHORT).show();
                }
            }
            else
                Log.v("DEBUG", "Não há conexão com heroku MAIN.class");
        }
        Log.v("DEBUG", "usuário deixou algum campo em branco MAIN.class");
    }

    public void signUp(View view) {
        Intent register_screen = new Intent(getBaseContext(), RegisterScreen.class);
        startActivity(register_screen);
        finish();
    }

    public void forgotPassword(View view) {
        Toast.makeText(getApplicationContext(),"Tela Mandar Senha ainda não foi implementada.",Toast.LENGTH_SHORT).show();
    }
}