package com.example.cacau2.ecovoicetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Cadastro_de_Arvore extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private LatLng currentLocation;
    private int z = 17;
    private Marker myPos;
    private Marker newTree;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("@+id/textLocalizacao"); //Muda nome da barra superior da activity
        setContentView(R.layout.activity_cadastro_de_arvore);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        //Location Manager
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        //Testa se o aparelho tem GPS
        PackageManager packageManager = getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

        //Estabelece critério de precisão
        if(hasGPS){
            criteria.setAccuracy( Criteria.ACCURACY_FINE );
            Log.i("LOCATION", "Usando GPS");
        }else{
            Log.i("LOCATION", "Usando WI-FI ou dados");
            criteria.setAccuracy( Criteria.ACCURACY_COARSE );
        }

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
    public void onMapLongClick(LatLng point) {
        if(mMap!= null) {
            if(newTree == null) {
                newTree = mMap.addMarker(new MarkerOptions()
                        .position(point).zIndex(1).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon)));
            }else{
                newTree.setPosition(point);
            }
        }

    }

    @SuppressLint("MissingPermission")
    public LatLng getLocation(String provider)
    {
        if(provider != null) {
            lm.requestSingleUpdate(provider, this, null);
            Location l = lm.getLastKnownLocation(provider);
            if (l != null){
                LatLng pos = new LatLng(l.getLatitude(), l.getLongitude());
                return pos;
            }else {
                Log.e( "PROVEDOR", "Location null no getLocation!" );
            }
        }
        return null;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapLongClickListener(this);
        if(provider != null) {
            Log.e( "PROVEDOR", "Usou provedor no onMapReady!" );
            currentLocation = getLocation(provider);
            if (currentLocation != null) {
                Log.e( "PROVEDOR", "Localização encontrada: Latitude:" + currentLocation.latitude
                        + " Longitude: " + currentLocation.longitude );
                updateCamera(currentLocation, z);
                createMyLocationMarker(currentLocation);
             } else {
                Log.e( "PROVEDOR", "Current Location está null no onMapReady!" );
            }

        }else {
            Log.e( "PROVEDOR", "Não usou provedor no onMapReady!" );
        }


    }

    public void efetuaCadastro(View v){
        //
        // Cadastra arvore no banco
        //
        LatLng t = newTree.getPosition();
        Toast.makeText(getBaseContext(), "Inserido com sucesso!\n"+t, Toast.LENGTH_SHORT).show();

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




    public void createMyLocationMarker(LatLng c){
        if(myPos == null) {
            myPos = mMap.addMarker(new MarkerOptions()
                    .position(c).zIndex(-1));
            myPos.setTag("My location");
        }else{
            myPos.setPosition(c);
        }

    }
    public void updateCamera(LatLng target,int zoom){
        if(mMap != null) {
            CameraUpdate c = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(target)
                            .zoom(zoom)
                            .build());
            mMap.animateCamera(c);
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();

        //Obtem melhor provedor habilitado com o critério estabelecido
        provider = lm.getBestProvider( criteria, true );

        if ( provider == null ){
            Log.e( "PROVEDOR", "Nenhum provedor encontrado!" );
        }else{
            Log.i( "PROVEDOR", "Está sendo utilizado o provedor: " + provider );

            //Obtem atualizações de posição
           // lm.requestSingleUpdate(provider,this,null );
        }
    }

    @Override
    protected void onDestroy() {
        //interrompe o Location Manager
        lm.removeUpdates(this);

        Log.w("PROVEDOR","Provedor " + provider + " parado!");
        super.onDestroy();
    }
    @SuppressLint("MissingPermission")
    public void myLocation(View v){
        currentLocation = getLocation(provider);
        updateCamera(currentLocation, z);
        createMyLocationMarker(currentLocation);
    }
    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        }
    }

    public void onProviderDisabled(String provider) {
        Log.d("LOCATION", "Desabilitou o provedor");
    }

    public void onProviderEnabled(String provider) {
        Log.d("LOCATION", "Habilitou o provedor");
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LOCATION", "Provedor mudou de estado");
    }

    @Override
    public void onMapClick(LatLng latLng) {


    }


}
