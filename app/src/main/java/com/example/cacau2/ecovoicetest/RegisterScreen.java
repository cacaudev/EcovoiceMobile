package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    EditText email, first_name, last_name, password, password_confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Registrar"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_register_screen);

        email = findViewById(R.id.register_email_field);
        first_name = findViewById(R.id.register_first_name_field);
        last_name = findViewById(R.id.register_last_name_field);
        password = findViewById(R.id.register_password_field);
        password_confirmation = findViewById(R.id.register_confirm_password_field);
    }

    public void butttonCreate(View view) {

        String email_user = email.getText().toString();
        String first_name_user = first_name.getText().toString();
        String last_name_user =  last_name.getText().toString();
        String password_user = password.getText().toString();
        String password_confirm = password_confirmation.getText().toString();

        if (email_user.matches(""))
            Toast.makeText(getApplicationContext(), R.string.email_blank, Toast.LENGTH_SHORT).show();
        if (first_name_user.matches(""))
            Toast.makeText(getApplicationContext(), R.string.first_name_blank, Toast.LENGTH_SHORT).show();
        if (last_name_user.matches(""))
            Toast.makeText(getApplicationContext(), R.string.last_name_blank, Toast.LENGTH_SHORT).show();
        if (password_user.matches(""))
            Toast.makeText(getApplicationContext(), R.string.password_blank, Toast.LENGTH_SHORT).show();
        if (password_confirm.matches(""))
            Toast.makeText(getApplicationContext(), R.string.password_confirmation_blank, Toast.LENGTH_SHORT).show();


        if(password_user.equals(password_confirm)){
            if (!email_user.matches("") && !first_name_user.matches("")
                && !last_name_user.matches("") && !password_user.matches("")) {
                createSingleUser(email_user, first_name_user, last_name_user, password_user);
            }
        } else
            Toast.makeText(getApplicationContext(), "Senha e confirmação não são iguais", Toast.LENGTH_SHORT).show();



    }

    public void buttonBackToLogin(View view) {
        Intent login_screen = new Intent(getBaseContext(), LoginScreen.class);
        startActivity(login_screen);
        finish();
    }

    public void createSingleUser(String email, String first_name, String last_name, String password) {
        mProgressDialog = new ProgressDialog(RegisterScreen.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Acessando....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        //Prepara o objeto user como formulário
        UserForm userToSend = new UserForm(email, first_name, last_name, password, password);
        UserPOST requestCreation = new UserPOST(userToSend);

        UserEndpointsAPI apiService = ApiClient.getClient().
                create(UserEndpointsAPI.class);

        Call<ResponseBody> createTask = apiService.createUser(requestCreation);

        createTask.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    try {
                        JSONObject apiResponse = new JSONObject(response.body().string());
                        Log.v("API", "Código: " + response.code() +
                                " Status: " + apiResponse.getString("status") +
                                " Message: " + apiResponse.getString("message"));

                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), R.string.user_created_success, Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(getBaseContext(), LoginScreen.class);
                        startActivity(home);
                        finish();

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                }
                if (response.code() == 422) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                        Toast.makeText(getApplicationContext(), "Email já em uso", Toast.LENGTH_SHORT).show();
                        //TODO
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Erro na criação", Toast.LENGTH_SHORT).show();
                //TODO
            }
        });
    }
}

