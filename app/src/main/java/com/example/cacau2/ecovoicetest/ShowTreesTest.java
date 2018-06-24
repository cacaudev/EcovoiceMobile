package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowTreesTest extends AppCompatActivity {
    ArrayList<Tree> arrayTrees;
    SessionManagement session;
    ProgressDialog mProgressDialog;

    EditText tree_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trees_test);
        this.setTitle("Teste de Lista de Árvores ");

        //Create a session manager
        session = new SessionManagement(getApplicationContext());
        arrayTrees = new ArrayList<Tree>();
        tree_id = findViewById(R.id.edit_tree_id);
    }

    public void getAllTrees(View view) {
        mProgressDialog = new ProgressDialog(ShowTreesTest.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Carregando árvores....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        TreeEndpointsAPI apiService = ApiClient.getClient().
                create(TreeEndpointsAPI.class);

        // Passa o token do usuário para autenticar a request
        Call<ResponseBody> getList = apiService.getAllTrees(session.getToken());

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
                        Tree tmpTree = new Tree();

                        // looping through All Contacts
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject c = list.getJSONObject(i);

                            Log.v("API",
                                    "{ 'id': " + c.getInt("id") + ", \n" +
                                            "'kind_id': " + c.getString("kind_id") + ", \n" +
                                            "'latitude': " + c.getString("latitude") + ", \n" +
                                            "'longitude': " + c.getString("longitude") + ", \n" +
                                            "'address': " + c.getString("address") + ", \n" +
                                            "'user_id': " + c.getString("user_id") + ", \n" +
                                            "'company_id': " + c.getString("company_id") + ", \n" +
                                            "'pictures_count': " + c.getInt("pictures_count") + ", \n" +
                                            "'old_pictures_count': " + c.getInt("old_pictures_count") + ", \n" +
                                            "} \n");

                            tmpTree.setId( c.getInt("id"));

                            if(!c.getString("kind_id").equals("null"))
                                tmpTree.setKind_id(c.getInt("kind_id"));

                            tmpTree.setLatitude(Float.parseFloat(c.getString("latitude")));
                            tmpTree.setLongitude(Float.parseFloat(c.getString("longitude")));
                            tmpTree.setAddress(c.getString("address"));
                            tmpTree.setUser_id(c.getInt("user_id"));

                            if(!c.getString("company_id").equals("null"))
                                tmpTree.setCompany_id(c.getInt("company_id"));

                            tmpTree.setPictures_count(c.getInt("pictures_count"));
                            tmpTree.setOld_pictures_count(c.getInt("old_pictures_count"));
                            arrayTrees.add(tmpTree);
                            tmpTree = new Tree();
                        }

                        Toast.makeText(getApplicationContext(), list.length() +" árvores carregadas.", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro na request ", Toast.LENGTH_SHORT).show();
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

    public void getSingleTree(View view) {
        String tree_id_str = tree_id.getText().toString();
        if(tree_id_str.matches("")) {
            Toast.makeText(getApplicationContext(), "Id em branco", Toast.LENGTH_SHORT).show();
            return;
        }


        mProgressDialog = new ProgressDialog(ShowTreesTest.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Procurando árvore....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        TreeEndpointsAPI apiService = ApiClient.getClient().
                create(TreeEndpointsAPI.class);

        // Passa o token do usuário para autenticar a request
        Call<ResponseBody> getList = apiService.getSingleTreeByID(session.getToken(), Integer.parseInt(tree_id_str));

        getList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.v("API", "Código: " + response.code() +
                                " Status: " + jsonObject.getString("status") +
                                " Message: " + jsonObject.getString("message"));

                        JSONObject c = jsonObject.getJSONObject("data");
                        Tree tmpTree = new Tree();


                        Log.v("API", "{ 'id': " + c.getInt("id") + ", \n" +
                                "'kind_id': " + c.getString("kind_id") + ", \n" +
                                "'latitude': " + c.getString("latitude") + ", \n" +
                                "'longitude': " + c.getString("longitude") + ", \n" +
                                "'address': " + c.getString("address") + ", \n" +
                                "'user_id': " + c.getString("user_id") + ", \n" +
                                "'company_id': " + c.getString("company_id") + ", \n" +
                                "'pictures_count': " + c.getInt("pictures_count") + ", \n" +
                                "'old_pictures_count': " + c.getInt("old_pictures_count") + ", \n" +
                                "} \n");

                        tmpTree.setId( c.getInt("id"));

                        if(!c.getString("kind_id").equals("null"))
                            tmpTree.setKind_id(c.getInt("kind_id"));

                        tmpTree.setLatitude(Float.parseFloat(c.getString("latitude")));
                        tmpTree.setLongitude(Float.parseFloat(c.getString("longitude")));
                        tmpTree.setAddress(c.getString("address"));
                        tmpTree.setUser_id(c.getInt("user_id"));

                        if(!c.getString("company_id").equals("null"))
                            tmpTree.setCompany_id(c.getInt("company_id"));

                        tmpTree.setPictures_count(c.getInt("pictures_count"));
                        tmpTree.setOld_pictures_count(c.getInt("old_pictures_count"));

                        // >>>>>>>>>>>>> tmpTree é o objeto Tree que retorna com todas os atributos.
                        // Para pegar as informações do user (criador da árvore) e kind, apenas faça o mesmo
                        // processo de jsonObject.getJSONObject("user") e jsonObject.getJSONObject("kind")

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro na request ", Toast.LENGTH_SHORT).show();
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
