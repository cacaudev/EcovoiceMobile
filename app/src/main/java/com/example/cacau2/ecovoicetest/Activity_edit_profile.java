package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.IOException;

public class Activity_edit_profile extends AppCompatActivity  {
    String dir;
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PICTURE = 10;
    private GoogleMap mMap;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    private LatLng currentLocation;
    private Marker myPos;
    private LatLng vicosa = new LatLng(-20.752946, -42.879097);
    int z = 17;
    private MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_personal_info);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN  |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        Button btn = (Button) findViewById(R.id.btn_edit_pic);
        registerForContextMenu(btn);
        btn.setOnCreateContextMenuListener(this);



       /* //Location Manager
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

        mMapView = (MapView) findViewById(R.id.map_view_edit_profile);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.header_menu_pic);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile,menu);
    }
    public void tirarFoto(){
        // cria arquivo JPG dentro da pasta IntentCamera.
        // Nome do arquivo contem a hora do sistema no nome.
        String file = "IMG_" + System.currentTimeMillis() + ".jpg";

        File newfile = new File(dir+file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {}

        Uri outputFileUri = Uri.fromFile(newfile);
        Log.i("EcoVoiceFoto","URI: "+ outputFileUri);

        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Configuração de qualidade da imagem
        it.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 640*640);

        //Configuração de persistencia em memoria externa
        //COMENTAR PARA onActivityResult receber uma intent não nula
        //it.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(it, TAKE_PICTURE);
    }

    public void abreGaleria(){
        //Pega conteudo do tipo Imagem pelo visualizador de Documentos embutido
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        //Pega conteudo do tipo Imagem pelo aplicativo da Galeria Preferido
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        //Cria a escolha para o usuario
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {

        switch (item.getItemId()) {
            case R.id.take_pic:

                tirarFoto();
                return true;
            case R.id.open_galerie:

                abreGaleria();
                return true;
            case R.id.delete_pic:
                //delete(item);
                return true;
            default:
                return false;
        }
    }

/*
    @Override
    public void onLocationChanged(Location location) {

    }
    @SuppressLint("MissingPermission")
    public LatLng getLocation(String provider)
    {
        if(provider != null) {
            lm.requestSingleUpdate(provider, (android.location.LocationListener) this, null);
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
        if(provider != null) {
            currentLocation = vicosa;//getLocation(provider);
            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);
        }else{
            currentLocation = vicosa;//getLocation(provider);
            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);

        }


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
    public void request_location(View view){
            currentLocation = vicosa;//getLocation(provider);
            Log.i("BUTTON", "Requested location");

            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);
    }*/

}
