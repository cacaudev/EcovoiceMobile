package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowUsersScreen extends AppCompatActivity {

    ListView listViewUsers;
    ArrayList<User> arrayUsers;
    SessionManagement session;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users_screen);
        this.setTitle("Lista de Usuários");

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());

        this.listViewUsers = findViewById(android.R.id.list);
        arrayUsers = new ArrayList<User>();
        getAllList();
    }


    public void getAllList() {
        mProgressDialog = new ProgressDialog(ShowUsersScreen.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Carregando usuários....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        UserEndpointsAPI apiService = ApiClient.getClient().
                create(UserEndpointsAPI.class);

        // Passa o token do usuário para autenticar a request
        Call<ResponseBody> getList = apiService.getAllUsers(session.getToken());

        getList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.v("API", "Código: " + response.code() +
                                " Status: " + jsonObject.getString("status") +
                                " Message: " + jsonObject.getString("message"));
                        JSONArray list = jsonObject.getJSONArray("data");
                        Log.v("API", "O array tem tamanho: " + list.length());
                        User tmpUser = new User();

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);
                            Log.v("API",
                                    "{ 'id': " + c.getInt("id") + ", \n" +
                                            "'email': " + c.getString("email") + ", \n" +
                                            "'full_name': " + c.getString("full_name") + ", \n" +
                                            "'birthday': " + c.getString("birthday") + ", \n" +
                                            "'website': " + c.getString("website") + ", \n" +
                                            "'avatar_url': " + c.getString("avatar_url") + ", \n" +
                                            "'trees_count': " + c.getInt("trees_count") + ", \n" +
                                            "'kinds_count': " + c.getInt("kinds_count") + ", \n" +
                                            "} \n");

                            tmpUser.setId( c.getInt("id"));
                            tmpUser.setEmail(c.getString("email"));
                            tmpUser.setFull_name(c.getString("full_name"));
                            tmpUser.setAvatar(c.getString("avatar_url"));
                            tmpUser.setTrees_count(c.getInt("trees_count"));
                            tmpUser.setKinds_count(c.getInt("kinds_count"));
                            arrayUsers.add(tmpUser);
                            tmpUser = new User();

                        }

                        mProgressDialog.dismiss();
                        listViewUsers.setAdapter(new UserAdapter(getBaseContext(), arrayUsers));

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção com código 200: " + e.getMessage());
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção Erro de Exceção com outro código: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
