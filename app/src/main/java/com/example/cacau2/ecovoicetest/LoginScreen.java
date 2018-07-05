package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity{

    public TextView email_text, password_text, forgot_password;
    public EditText email_field, password_field;
    public Button create_account, access, registerTree;

    SessionManagement session;
    Switch remember_me_switch;
    User current_user;
    String auth_token;
    ProgressDialog mProgressDialog;

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

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());
    }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    protected  void onResume() {
        super.onResume();

        // Se o usuario ainda estiver logado, mas apenas colocou a activity em espera
        if(session.isLoggedIn()) {
            Intent intent = new Intent(getBaseContext(), MenuMap.class);
            startActivity(intent);
            finish();
        }
        else
            //Get user data if remember-me option was checked (auth-token is always renewed so dont save it)
            if(session.isRememberChecked()){
                remember_me_switch.setChecked(true);
                HashMap<String, String> current_user = session.getUserDetails();
                email_field.setText(current_user.get(SessionManagement.KEY_EMAIL));
                password_field.setText(current_user.get(SessionManagement.KEY_PASSWORD));
            }
    }

    public Boolean checkInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){
            Toast.makeText(getApplicationContext(), "Não há conexão internet", Toast.LENGTH_SHORT).show();
        }

        return isConnected;
    }

    public void signIn(View view) {

        if (!checkInternet()) return;

        mProgressDialog = new ProgressDialog(LoginScreen.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Acessando....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        String email_user = email_field.getText().toString();
        String password_plain = password_field.getText().toString();

        if (email_user.matches(""))
            Toast.makeText(getApplicationContext(), R.string.email_blank, Toast.LENGTH_SHORT).show();
        if (password_plain.matches(""))
            Toast.makeText(getApplicationContext(), R.string.password_blank, Toast.LENGTH_SHORT).show();

        if (!email_user.matches("") && !password_plain.matches(""))
            callCreateSession(email_user, password_plain);
    }


    public void signUp(View view) {
        if (!checkInternet()) return;

        Intent register_screen = new Intent(getBaseContext(), RegisterScreen.class);
        startActivity(register_screen);
    }

    public void forgotPassword(View view) {
        Toast.makeText(getApplicationContext(),"Tela Mandar Senha ainda não foi implementada.",Toast.LENGTH_SHORT).show();
    }

    public void callCreateSession(String email, String password) {

        SessionEndPointsAPI apiService = ApiClient.getClient().
                create(SessionEndPointsAPI.class);

        Call<ResponseApiObject> createSession = apiService.login(email, password);

        createSession.enqueue(new Callback<ResponseApiObject>() {
            @Override
            public void onResponse(Call<ResponseApiObject> call, Response<ResponseApiObject> response) {

                if (response.code() == 201) {
                    ResponseApiObject resultado = response.body();
                    Log.v("API", "Código: " + response.code() +
                            " Status: " + response.body().getStatus() +
                            " Message: " + response.body().getMessage());

                    //Toast.makeText(getApplicationContext(), R.string.user_logged, Toast.LENGTH_SHORT).show();

                    try {
                        User current_user = response.body().getData().getUser();
                        Log.v("API", "usuario encontrado: " + current_user.getFull_name());
                        Log.v("API", "Auth-Token: " + response.body().getData().getAuth_token());

                        String auth_token = response.body().getData().getAuth_token();

                        if (remember_me_switch.isChecked())
                            session.saveLoginSession(email, password,current_user.getFull_name(),
                                    true, auth_token, current_user.getId());
                        else
                            session.saveLoginSession(email, password,current_user.getFull_name(),
                                    false, auth_token, current_user.getId());

                        mProgressDialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), MenuMap.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        Log.v("API", "Erro ao converter o objeto json data: " + e.getMessage());
                    }

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                        mProgressDialog.dismiss();

                        if (response.code() == 401) {
                            Toast.makeText(getApplicationContext(), R.string.password_incorrect, Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 404) {
                            Toast.makeText(getApplicationContext(), R.string.email_not_found, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseApiObject> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
            }
        });
    }
}