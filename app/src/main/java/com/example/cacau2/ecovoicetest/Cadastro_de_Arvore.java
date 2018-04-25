package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Cadastro_de_Arvore extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("@+id/textLocalizacao"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_cadastro_de_arvore);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng dpi = new LatLng(-20.7645121, -42.8679292);
        mMap.addMarker(new MarkerOptions().position(dpi).title("Marker on DPI"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(dpi));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(dpi));
    }

    public void efetuaCadastro(View v){
        //
        // Cadastra arvore no banco
        //
        Toast.makeText(getBaseContext(), "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },1000);
    }

    public void insertPhoto(View v){
        //
        // Codigo da inserção de foto aqui
        //
        Toast.makeText(getBaseContext(), "Quem sabe amanhã", Toast.LENGTH_SHORT).show();
    }
}
