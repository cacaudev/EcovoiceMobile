package com.example.cacau2.ecovoicetest;

import com.google.gson.annotations.Expose;

/**
 * Created by Cacau2 on 23/06/2018.
 */

public class TreeForm {
    @Expose
    private Double latitude;

    @Expose
    private Double longitude;

    @Expose
    private int user_id;

    public TreeForm(Double latitude, Double longitude, int user_id){
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
    }
}
