package com.example.cacau2.ecovoicetest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

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
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // User password
    public static final String KEY_PASSWORD = "password";

    // Remember email and password option
    public static final String KEY_REMEMBER = "remember_me";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     *  Create login session
     * */
    public void createLoginSession(String email, String password, Boolean remember_choice){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing remember option
        editor.putBoolean(KEY_REMEMBER, remember_choice);

        //Storing password value
        editor.putString(KEY_PASSWORD, password);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Quick check for login
     * Default is false
     **/
    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN, false);
    }

    /**
     * Quick check for if
     * user marked to remember email and password
     **/
    public boolean isRememberChecked(){
        return preferences.getBoolean(KEY_REMEMBER, false);
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user password
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

        // user email
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));

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
    public void logoutUser(){
        // Clearing all data from Shared Preferences

        // If user want to remember email and password only remove is_logged
        if(this.isRememberChecked()){
            editor.remove(IS_LOGIN);
        }
        // if not, clear all info stored
        else {
            editor.clear();
        }

        //Commit changes
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
}
