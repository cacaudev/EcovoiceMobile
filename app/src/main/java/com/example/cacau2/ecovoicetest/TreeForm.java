package com.example.cacau2.ecovoicetest;

import com.google.gson.annotations.Expose;

/**
 * Created by Cacau2 on 23/06/2018.
 */

public class TreeForm {
    @Expose
    private float latitude;

    @Expose
    private float longitude;

    @Expose
    private int user_id;

    public TreeForm(float latitude, float longitude, int user_id){
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
    }
}
