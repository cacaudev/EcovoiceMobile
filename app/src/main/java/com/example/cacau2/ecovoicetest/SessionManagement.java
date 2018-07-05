package com.example.cacau2.ecovoicetest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cacau2 on 09/05/2018.
 */

public class SessionManagement {

    // Shared Preferences
    SharedPreferences preferences;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "EcovoiceMobilePref";

    // All Shared Preferences Keys
    private static final String IS_LOGGED = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // User ID
    public static final String KEY_ID = "id";

    // User password
    public static final String KEY_PASSWORD = "password";

    // Remember email and password option
    public static final String KEY_REMEMBER = "remember_me";

    // Token used for authentication on api
    public static final String KEY_AUTH_TOKEN = "";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     *  Create login session
     * */
    public void saveLoginSession(String email, String password, String full_name,
                                 Boolean remember_choice, String auth_token, int id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGGED, true);

        // Storing remember option
        editor.putBoolean(KEY_REMEMBER, remember_choice);

        //Storing password value
        editor.putString(KEY_PASSWORD, password);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing full name in pref
        editor.putString(KEY_NAME, full_name);

        // Storing auth_token in pref
        editor.putString(KEY_AUTH_TOKEN, "Token token=\"" + auth_token + "\"");

        // Storing user id
        editor.putInt(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Quick check for login
     * Default is false
     **/
    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED, false);
    }

    /**
     * Quick check for id
     **/
    public int getUserID(){
        return preferences.getInt(KEY_ID, 0);
    }

    /**
     * Quick check for if
     * user marked to remember email and password
     **/
    public boolean isRememberChecked(){
        return preferences.getBoolean(KEY_REMEMBER, false);
    }


    /**
     * Quick check for token
     **/
    public String getToken() { return preferences.getString(KEY_AUTH_TOKEN, "");}

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user password
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

        // user email
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));

        // user full name
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));

        // user token
        user.put(KEY_AUTH_TOKEN, preferences.getString(KEY_AUTH_TOKEN, null));

        // return user
        return user;
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginScreen.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser(String token){
        // Clearing all data from Shared Preferences

        // If user want to remember email and password only remove is_logged
        // and token (is renewed each time user sign in)
        if(this.isRememberChecked()){
            editor.remove(IS_LOGGED);
            editor.remove(KEY_AUTH_TOKEN);
        }
        // if not, clear all info stored
        else {
            editor.clear();
        }

        // Commit changes
        editor.commit();

        // Send delete auth-token request to api (Não consegui pegar a variavel localmente!)
        this.deleteSession(token);

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     *  Send logout request to API
     * */
    public void deleteSession(String token) {
        SessionEndPointsAPI apiService = ApiClient.getClient().
                create(SessionEndPointsAPI.class);

        Call<ResponseApiObject> logoutSession = apiService.logout(token);

        Log.v("API", "Token(Session): " + token);

        logoutSession.enqueue(new Callback<ResponseApiObject>() {
            @Override
            public void onResponse(Call<ResponseApiObject> call, Response<ResponseApiObject> response) {

                if (response.code() == 200) {
                    ResponseApiObject resultado = response.body();
                    Log.v("API", "Código: " + response.code() +
                            " Status: " + response.body().getStatus() +
                            " Message: " + response.body().getMessage());
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

                }
            }

            @Override
            public void onFailure(Call<ResponseApiObject> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                Toast.makeText(_context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
