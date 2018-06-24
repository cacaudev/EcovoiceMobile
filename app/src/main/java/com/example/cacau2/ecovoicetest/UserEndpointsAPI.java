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
 * Created by Cacau2 on 19/06/2018.
 */

public interface UserEndpointsAPI {

    //Funcionando -só que não!
    @GET("/api/v1/users")
    Call<ResponseBody> getAllUsers(@Header("Authorization") String token);

    @GET("/api/v1/users/{id}")
    Call<ResponseBody> getSingleUserByID(@Header("Authorization") String token,
                                              @Path("id") int id);

    //Funcionando
    @POST("/api/v1/users")
    Call<ResponseBody> createUser(@Body UserPOST user);

    @PATCH("/api/v1/users/{id}")
    Call<ResponseApiObject> updateSingleUserByID(@Header("Authorization") String token,
                                                 @Path("id") int id,
                                                 @Field("email") String email,
                                                 @Field("first_name") String first_name,
                                                 @Field("last_name") String last_name,
                                                 @Field("password") String password,
                                                 @Field("password_confirmation") String password_confirmation);
    @DELETE("/api/v1/users/{id}")
    Call<ResponseApiObject> deleteSingleUserByID(@Header("Authorization") String token,
                                                 @Path("id") int id);

}
