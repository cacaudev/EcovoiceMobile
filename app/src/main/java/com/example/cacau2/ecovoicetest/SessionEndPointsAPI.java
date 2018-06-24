package com.example.cacau2.ecovoicetest;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Cacau2 on 19/06/2018.
 */

public interface SessionEndPointsAPI {

    @FormUrlEncoded
    @POST("/api/v1/sessions/login")
    Call<ResponseApiObject> login(@Field("email") String email, @Field("password") String password);


    @DELETE("/api/v1/sessions/logout")
    Call<ResponseApiObject> logout(@Header("Authorization") String token);
}
