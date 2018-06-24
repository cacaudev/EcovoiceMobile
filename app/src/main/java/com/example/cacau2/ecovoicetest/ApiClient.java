package com.example.cacau2.ecovoicetest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cacau2 on 19/06/2018.
 */

public class ApiClient {

    //Caminho para a Base do servidor/API
    public static final String BASE_URL="https://ecovoice-api-lab.herokuapp.com";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
