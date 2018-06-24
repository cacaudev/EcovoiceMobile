package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTree extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    EditText latitude, longitude;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tree);

        latitude = findViewById(R.id.latitude_test_field);
        longitude = findViewById(R.id.longitude_test_field);

        //Create session manager
        session = new SessionManagement(getApplicationContext());
    }

    public void testCreateButton(View view){
        int user_id = session.getUserID();


        Float latitude_test = Float.parseFloat(latitude.getText().toString());
        Float longitude_test = Float.parseFloat(longitude.getText().toString());

        Log.v("API", "User_id: " + user_id + " Latitude: " + latitude_test + " Longitude: " + longitude_test);

        String endereco = getAddress(latitude_test, longitude_test);
        Log.v("API", "Endereço pelo geocoder: " + endereco );

        createSingleTree(latitude_test, longitude_test, user_id);

    }
    public void createSingleTree(float latitude, float longitude, int user_id) {
        mProgressDialog = new ProgressDialog(RegisterTree.this);
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Cadastrando....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        //Prepara o objeto tree como formulário
        TreeForm treeToSend = new TreeForm(latitude, longitude, user_id);
        TreePOST requestObject = new TreePOST(treeToSend);

        TreeEndpointsAPI apiService = ApiClient.getClient().
                create(TreeEndpointsAPI.class);

        Call<ResponseBody> createTask = apiService.createTree(session.getToken(), requestObject);

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
                        Toast.makeText(getApplicationContext(), "Árvore criada com sucesso", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                //TODO
            }
        });
    }


    public String getAddress(double latitude, double longitude) {
        String address = "Not found";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).toString();
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return address;
    }

}
