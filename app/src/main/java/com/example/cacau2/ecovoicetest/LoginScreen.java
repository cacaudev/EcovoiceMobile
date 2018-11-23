package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class LoginScreen extends AppCompatActivity{

    public TextView email_text, password_text, forgot_password;
    public EditText email_field, password_field;
    public Button create_account, access;

    SessionManagement session;
    Switch remember_me_switch;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        email_text = findViewById(R.id.login_email_text);
        password_text = findViewById(R.id.login_password_text);

        email_field = findViewById(R.id.login_email_field);
        password_field = findViewById(R.id.login_password);

        TextInputLayout layout = findViewById(R.id.login_input_layout);
        layout.setHintAnimationEnabled(false);
        layout.setAnimation(null);


        create_account = findViewById(R.id.login_create_account_button);
        access = findViewById(R.id.login_access_button);
        forgot_password = findViewById(R.id.login_forgot_password_text);

        remember_me_switch = findViewById(R.id.remember_me_switch);

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());
        remember_me_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remember_me_switch.setText(getResources().getString(R.string.forgot_me));
                    remember_me_switch.setTextOn(getResources().getString(R.string.forgot_me));
                }
                else {
                    remember_me_switch.setText(getResources().getString(R.string.remember_me));
                    remember_me_switch.setTextOn(getResources().getString(R.string.remember_me));
                }
            }
        });


    }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    protected  void onResume() {
        super.onResume();

        /**
         * If the user is logged, but the activity is on background, load home activity
         * if not logged, check if remember-me option was marked and complete fields
         **/

        if(session.isLoggedIn()) {
            Intent intent = new Intent(getBaseContext(), MenuMap.class);
            startActivity(intent);
            finish();
        } else if(session.isRememberChecked()){
            remember_me_switch.setChecked(true);
            HashMap<String, String> current_user = session.getUserDetails();
            email_field.setText(current_user.get(SessionManagement.KEY_EMAIL));
            password_field.setText(current_user.get(SessionManagement.KEY_PASSWORD));
        }
    }

    //TODO COLOCAR ESSA FUNÇÃO COMO HELPER PARA QUE OUTRAS ACTIVITIES POSSAM ACESSÁ-LO
    public Boolean checkInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected){
            Toast.makeText(getApplicationContext(), R.string.device_not_connected, Toast.LENGTH_SHORT).show();
        }

        return isConnected;
    }

    public void signIn(View view) {

        if (!checkInternet()) return;

        mProgressDialog = new ProgressDialog(LoginScreen.this);
        //mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage(getResources().getString(R.string.accessing));
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
        if (!checkInternet()) return;
        Intent forgot_password = new Intent(getBaseContext(), Activity_forgot_password.class);
        startActivity(forgot_password);

    }

    public void callCreateSession(String email, String password) {

        /**
         * Codes for API in Sessions:
         * 201 - Session created (User logged successfully and token was generated).
         * 401 - User not authenticated. Token was'nt sent with request (In login means the password
         *  sent was incorrect).
         * 404 - Resource not found (Means the email sent was not found).
         **/

        SessionEndPointsAPI apiService = ApiClient.getClient().create(SessionEndPointsAPI.class);
        Call<ResponseApiObject> createSession = apiService.login(email, password);

        createSession.enqueue(new Callback<ResponseApiObject>() {
            @Override
            public void onResponse(Call<ResponseApiObject> call, Response<ResponseApiObject> response) {

                if (response.code() == 201) {
                    try {
                        Log.v("API", "Código: " + response.code() +
                                " Status: " + response.body().getStatus() +
                                " Message: " + response.body().getMessage());

                        User current_user = response.body().getData().getUser();
                        String auth_token = response.body().getData().getAuth_token();

                        Log.v("API", "usuario encontrado: " + current_user.getFull_name());
                        Log.v("API", "Auth-Token: " + response.body().getData().getAuth_token());

                        if (remember_me_switch.isChecked()) {
                            session.saveLoginSession(email, password, current_user.getFull_name(),
                                    true, auth_token, current_user.getId());
                            remember_me_switch.setText(getResources().getString(R.string.forgot_me));
                             remember_me_switch.setTextOn(getResources().getString(R.string.forgot_me));
                        }
                        else {
                            session.saveLoginSession(email, password, current_user.getFull_name(),
                                    false, auth_token, current_user.getId());
                            remember_me_switch.setText(getResources().getString(R.string.remember_me));
                            remember_me_switch.setTextOn(getResources().getString(R.string.remember_me));
                        }

                        mProgressDialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), Activity_tab_news_feed.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        Log.v("API", "Erro de exceção ao converter o objeto json data: " + e.getMessage());
                    }
                }
                if (response.code() == 401) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.password_incorrect, Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 404) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.email_not_found, Toast.LENGTH_SHORT).show();
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