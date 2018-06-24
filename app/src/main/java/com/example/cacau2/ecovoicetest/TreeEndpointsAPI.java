package com.example.cacau2.ecovoicetest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Cacau2 on 23/06/2018.
 */

public interface TreeEndpointsAPI {

    @GET("/api/v1/trees/index")
    Call<ResponseBody> getAllTrees(@Header("Authorization") String token);

    @GET("/api/v1/trees/{id}")
    Call<ResponseBody> getSingleTreeByID(@Header("Authorization") String token,
                                         @Path("id") int id);

    @POST("/api/v1/trees")
    Call<ResponseBody> createTree(@Header("Authorization") String token,
                                  @Body TreePOST user);

    @PATCH("/api/v1/trees/{id}")
    Call<ResponseApiObject> updateSingleTreeByID(@Header("Authorization") String token,
                                                 @Path("id") int id,
                                                 @Field("email") String email,
                                                 @Field("first_name") String first_name,
                                                 @Field("last_name") String last_name,
                                                 @Field("password") String password,
                                                 @Field("password_confirmation") String password_confirmation);
    @DELETE("/api/v1/trees/{id}")
    Call<ResponseApiObject> deleteSingleTreeByID(@Header("Authorization") String token,
                                                 @Path("id") int id);
}
