package com.example.cacau2.ecovoicetest;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


public class AddTree_Step1 extends android.support.v4.app.Fragment implements OnMapReadyCallback,LocationListener,GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener{

    MapView mMapView;
    private GoogleMap googleMap;
    ViewPager viewPager;
    TextView textView;
    public static Activity primeiroPasso;
    private GoogleMap mMap;
    private LatLng currentLocation;
    private int z = 17;
    private Marker myPos;
    private Marker newTree;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    private LatLng vicosa = new LatLng(-20.752946, -42.879097);
    View view;
    Geocoder geocoder;


    private SupportMapFragment mSupportMapFragment;

 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
     View rootView = inflater.inflate(R.layout.activity_add_tree__step1, container, false);
     view = rootView;
     textView = view.findViewById(R.id.tree_address);

     mMapView = (MapView) rootView.findViewById(R.id.add_tree_map);
     mMapView.onCreate(savedInstanceState);

     mMapView.onResume(); // needed to get the map to display immediately

     try {
         MapsInitializer.initialize(getActivity().getApplicationContext());
     } catch (Exception e) {
         e.printStackTrace();
     }



     mMapView.getMapAsync(this);
     viewPager = getActivity().findViewById(R.id.pager);
     FloatingActionButton newPage =  view.findViewById(R.id.floatingActionButton);
     newPage.setOnClickListener(new View.OnClickListener() {


         @Override
         public void onClick(View v) {

             SendNewTree();

         }
     });
     return rootView;
 }


    public void  SendNewTree(){
        //Pega a posição do Marcador Selecionado
        //Se tiver sido selecionado
        if(newTree != null){
            LatLng selectedTree = newTree.getPosition();
            Intent it = new Intent(view.getContext(),AddTree_Step2.class);
            Bundle params = new Bundle();
            params.putDouble("lat",selectedTree.latitude);
            params.putDouble("lon",selectedTree.longitude);

            it.putExtras(params);
            viewPager.setCurrentItem(1);
        }
        else{
            Toast.makeText(view.getContext(),"Clique e Segure para Marcar a Arvore",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Location Manager
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        //Testa se o aparelho tem GPS
        PackageManager packageManager = getActivity().getPackageManager();
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


    @Override
    public void onMapLongClick(LatLng point) {
        if(mMap!= null) {
            if(newTree == null) {
                newTree = mMap.addMarker(new MarkerOptions()
                        .position(point).zIndex(1).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon)));
                setEditText(point);
            }else{
                newTree.setPosition(point);
                setEditText(point);
            }
            setAddress(point);
        }

    }
    public void setEditText(LatLng latLng){
        EditText latitude_field = view.findViewById(R.id.edit_text_lat);
        EditText longitude_field = view.findViewById(R.id.edit_text_long);
        latitude_field.setText((String.valueOf(latLng.latitude)));
        longitude_field.setText((String.valueOf(latLng.longitude)));



    }

    @SuppressLint("MissingPermission")
    public LatLng getLocation(String provider)
    {
        if(provider != null) {
            lm.requestSingleUpdate(provider, this, null);
            Location l = lm.getLastKnownLocation(provider);
            if (l != null){
                return new LatLng(l.getLatitude(), l.getLongitude());
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
            currentLocation = vicosa;//getLocation(provider);
            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);
        }else{
            currentLocation = vicosa;//getLocation(provider);
            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);

        }
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {


            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub
                setEditText(marker.getPosition());
                setAddress(marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                setEditText(marker.getPosition());
            }
        });
    }
    private String getAddress(LatLng pos) {
        StringBuilder result = new StringBuilder();
        String strAddress = null;
        try {
            geocoder = new Geocoder(view.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(pos.latitude,pos.longitude,1);
            if (addresses.size() > 0) {

                Address address = addresses.get(0);
                StringBuffer str = new StringBuffer();
                str.append(address.getThoroughfare() + ",");
                str.append(address.getSubThoroughfare()+ ",");
                str.append(address.getLocality()+ " - ");
                str.append(address.getAdminArea()+ ",");
                str.append(address.getCountryName());


                strAddress = str.toString();


            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return strAddress;
    }
    public void setAddress(LatLng pos){
        String address = getAddress(pos);

        textView.setText(address);
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
    public void onStart() {
        super.onStart();


        //Obtem melhor provedor habilitado com o critério estabelecido


        if ( provider == null ){
            Log.e( "PROVEDOR", "Nenhum provedor encontrado!" );
        }else{
            Log.i( "PROVEDOR", "Está sendo utilizado o provedor: " + provider );

            //Obtem atualizações de posição
             lm.requestSingleUpdate(provider,this,null );
        }
    }

    @Override
    public void onDestroy() {
        //interrompe o Location Manager
        lm.removeUpdates(this);

        Log.w("PROVEDOR","Provedor " + provider + " parado!");
        super.onDestroy();
    }
    @SuppressLint("MissingPermission")
    public void myLocation(View v){
        currentLocation = vicosa;//getLocation(provider);
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
