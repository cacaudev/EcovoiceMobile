package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadTrees {
    public static ArrayList<Tree> arrayTrees = new ArrayList<Tree>();;
    public static SessionManagement session;
    public static ProgressDialog mProgressDialog;
    public static Context context;
    public final GoogleMap map;

    EditText tree_id;

    public LoadTrees(SessionManagement session, Context context,GoogleMap map){

        this.session = session;
        this.context = context;
        this.map = map;
    }


    void insert_trees(ArrayList<Tree> list, GoogleMap map){
        LatLng pos;
        Tree aux;
        if(map != null) {
            for (int i = 0; i < list.size(); i++) {
                aux = list.get(i);
                pos = new LatLng(aux.getLatitude(),aux.getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(pos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon)));
            }
        }

    }
    public ArrayList<Tree> getArrayTree(){
        this.getAllTrees();
        return this.arrayTrees;
    }
    public void/*ArrayList<Tree>*/ getAllTrees() {
        mProgressDialog = new ProgressDialog(LoadTrees.context);
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
            public ArrayList<Tree> trees =  new ArrayList<Tree>();

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

                            this.trees.add(tmpTree);
                            tmpTree = new Tree();
                        }
                        Toast.makeText(context, list.length() +" árvores carregadas.", Toast.LENGTH_SHORT).show();
                        Log.d("LOADS", this.trees.size() + "  ARVoRES");
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                    insert_trees(this.trees,map);

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
                    Toast.makeText(context, "Erro na request ", Toast.LENGTH_SHORT).show();
                }
                Log.d("LOADS", this.trees.size() + "  ARVoRES");
                arrayTrees.addAll(this.trees);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
            }



        });


    }

    public void getSingleTree(View view) {
        String tree_id_str = tree_id.getText().toString();
        if(tree_id_str.matches("")) {
            Toast.makeText(context, "Id em branco", Toast.LENGTH_SHORT).show();
            return;
        }


        mProgressDialog = new ProgressDialog(context);
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
                    Toast.makeText(context, "Erro na request ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
