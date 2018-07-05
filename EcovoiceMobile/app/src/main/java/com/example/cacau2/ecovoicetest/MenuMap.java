package com.example.cacau2.ecovoicetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MenuMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,LocationListener {

    SessionManagement session;

    private LatLng vicosa = new LatLng(-20.752946, -42.879097);
    private LatLng google = new LatLng(37.25194,  -122.084017);
    private Marker currentMarker;
    private Marker myPos;
    private GoogleMap map;
    private SlidingUpPanelLayout slidingLayout;
    private LinearLayout small_info;
    private LatLng currentLocation;
    private int z = 17;
    private float anchor = 0.3125f;
    private LocationManager lm;
    private Criteria criteria;
    private String provider;

    private  ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    public void timeLine(){
        // Create Timeline rows List


// Create new timeline row (Row Id)
        TimelineRow myRow = new TimelineRow(0);
        TimelineRow myRow2 = new TimelineRow(1);

// To set the row Date (optional)
        myRow2.setDate(new Date());
// To set the row Title (optional)
        myRow2.setTitle("Title");
// To set the row Description (optional)
        myRow2.setDescription("Description");
// To set the row bitmap image (optional)
        myRow2.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
// To set row Below Line Color (optional)
        myRow2.setBellowLineColor(Color.argb(255, 0, 0, 0));
// To set row Below Line Size in dp (optional)
        myRow2.setBellowLineSize(6);
// To set row Image Size in dp (optional)
        myRow2.setImageSize(40);
// To set background color of the row image (optional)
        myRow2.setBackgroundColor(Color.argb(255, 0, 0, 0));
// To set the Background Size of the row image in dp (optional)
        myRow2.setBackgroundSize(60);
// To set row Date text color (optional)
        myRow2.setDateColor(Color.argb(255, 0, 0, 0));
// To set row Title text color (optional)
        myRow2.setTitleColor(Color.argb(255, 0, 0, 0));
// To set row Description text color (optional)
        myRow2.setDescriptionColor(Color.argb(255, 0, 0, 0));


        // To set the row Date (optional)
        myRow.setDate(new Date());
// To set the row Title (optional)
        myRow.setTitle("Title");
// To set the row Description (optional)
        myRow.setDescription("Description");
// To set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_box_black_48dp));
// To set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
// To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(2);
// To set row Image Size in dp (optional)
        myRow.setImageSize(40);
// To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
// To set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(40);
// To set row Date text color (optional)
        myRow.setDateColor(Color.argb(255, 0, 0, 0));
// To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 0, 0, 0));
// To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_map);

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        //Get user data from session
        HashMap<String, String> current_user = session.getUserDetails();
        String user_email = current_user.get(SessionManagement.KEY_EMAIL);
        Toast.makeText(getApplicationContext(),"Logado(a) com " + user_email ,Toast.LENGTH_SHORT).show();



        timeLine();
        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                true);
        ListView myListView = (ListView) findViewById(R.id.time_line);
        myListView.setAdapter(myAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        currentMarker = null;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //maps

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SupportMapFragment mv = new SupportMapFragment();
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){

                }
            }
        }).start();*/

        small_info = (LinearLayout) findViewById(R.id.small_info);

        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);


        Log.d("IGOR", "onCreate:        "+ anchor);

        slidingLayout.setFadeOnClickListener(new SlidingUpPanelLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SLID","CLICK");
            }
        });
        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float v) {

                if (v <= 1 && v >= 0.9) {
                    if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.DRAGGING) {

                            slidingLayout.setAnchorPoint(anchor);
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        }
                    }
                }
                if (v <= 0.22 && v >= 0) {
                    if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.DRAGGING) {
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                        }
                    }
                }
                if (v >= 0.8) {
                    small_info.setVisibility(View.GONE);

                }

                if (v < 0.8)
                    small_info.setVisibility(View.VISIBLE);
               // Log.i("igor", "onPanelSlide, offset " + v);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("igor", "onPanelStateChanged " + previousState + "  " + newState);

            }

        });


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
        provider = lm.getBestProvider(criteria, true);


    }

    public void closePanel(View v){
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

    }
    public void createMyLocationMarker(LatLng c){
        if(myPos == null && c != null && map != null) {
            myPos = map.addMarker(new MarkerOptions()
                    .position(c).zIndex(-1));
            myPos.setTag("My location");
        }else if(myPos != null && c != null){
            myPos.setPosition(c);
        }

    }
    public void updateCamera(LatLng target,int zoom){
        if(map != null && target != null) {
            CameraUpdate c = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(target)
                            .zoom(zoom)
                            .build());
            map.animateCamera(c);
        }
    }

    public void createMarker(LatLng pos) {
        if(map != null) {
            map.addMarker(new MarkerOptions()
                    .position(pos)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon)));
        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMarkerClickListener(this);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if(provider != null) {
            currentLocation = getLocation(provider);
            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        createMarker(vicosa);
        createMarker(google);
        currentMarker = null;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
                    currentMarker = null;
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    Log.i("MAPA","CLICK");

                }else{
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    Log.i("MAPA","CLICK");
                }
            }
        });



    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!marker.equals(myPos)) {
            if (currentMarker == null && myPos != null) {
                currentMarker = marker;
                slidingLayout.setAnchorPoint(anchor);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_selected));
            } else if(currentMarker != null && myPos != null) {
                currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                currentMarker = null;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (slidingLayout != null && slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
            if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingLayout.setAnchorPoint(anchor);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                small_info.setVisibility(View.VISIBLE);
            }
            if (currentMarker != null) {
                currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
                currentMarker = null;
            }
        } else if (currentMarker != null) {
            currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
            currentMarker = null;
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent it;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_logout) {
            Log.v("BancoDeDados", "Foi deslogado");
            session.logoutUser();
            return true;
        } else if (id == R.id.add_tree) {
            it = new Intent(this,AddTree_Step1.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_profile_edit) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_feed) {

        } else if (id == R.id.nav_trees) {

        } else if (id == R.id.nav_species) {

        } else if (id == R.id.nav_companies) {

        } else if (id == R.id.nav_users) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            //lm.requestSingleUpdate(provider,this,null );
            }
        }

    @SuppressLint("MissingPermission")
    public LatLng getLocation(String provider)
    {
        if(provider != null) {
            lm.requestSingleUpdate(provider, this, null);
            Log.i("BUTTON", "Requested location2");
            Location l = lm.getLastKnownLocation(provider);
            if (l != null){
                LatLng pos = new LatLng(l.getLatitude(), l.getLongitude());
                return pos;
            }
        }
        return null;
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
            Log.i("BUTTON", "Requested location");

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

}
