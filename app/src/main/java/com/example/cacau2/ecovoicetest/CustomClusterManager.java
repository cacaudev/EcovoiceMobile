package com.example.cacau2.ecovoicetest;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;

public class CustomClusterManager extends ClusterManager {
    public CustomClusterManager(Context context, GoogleMap map) {
        super(context, map);
    }

}
