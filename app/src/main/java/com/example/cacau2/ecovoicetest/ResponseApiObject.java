package com.example.cacau2.ecovoicetest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Cacau2 on 19/06/2018.
 */

public class ResponseApiObject implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
