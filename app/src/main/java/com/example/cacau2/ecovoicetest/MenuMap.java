package com.example.cacau2.ecovoicetest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.maps.android.clustering.ClusterManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.qap.ctimelineview.TimelineRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuMap extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,LocationListener {

    public static ArrayList<Tree> arrayTrees= new ArrayList<Tree>();
    SessionManagement session;
    private ClusterManager<Tree_item> mClusterManager;

    private LatLng vicosa = new LatLng(-20.752946, -42.879097);
    private LatLng google = new LatLng(37.25194,  -122.084017);
    private Marker currentMarker;
    private Marker myPos;
    private GoogleMap map;
    private SlidingUpPanelLayout slidingLayout;
    private LinearLayout small_info;
    private LatLng currentLocation;
    private int z = 18;
    private float anchor = 0.3125f;
    private LocationManager lm;
    private Criteria criteria;
    private String provider;

    private  ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

    private View tree_id;
    ProgressDialog mProgressDialog;


    private ArrayList<Comment_data> comment_list_data;
    Event_view_adapter adapter;
    public List<Event_data> fill_with_data() {

        List<Event_data> data = new ArrayList<>();
        List<Comment_data> comments = new ArrayList<>();
        List<Comment_data> comments1 = new ArrayList<>();
        List<Comment_data> comments2 = new ArrayList<>();
        List<Comment_data> comments3 = new ArrayList<>();
        List<Comment_data> comments4 = new ArrayList<>();
        List<Comment_data> comments5 = new ArrayList<>();


       comments1.add(new Comment_data("Igor Oliveira","Teste", "1",0));
        comments1.add(new Comment_data("Igor Oliveira","Teste", "1",0));

        comments2.add(new Comment_data("Igor Oliveira","Teste", "2",1));
        comments3.add(new Comment_data("Igor Oliveira","Teste", "3",2));
        comments4.add(new Comment_data("Igor Oliveira","Teste", "4",3));
        comments5.add(new Comment_data("Igor Oliveira","Teste", "5",4));

        //comments.add(new Comment_data("Igor Oliveira","Teste", "6",5));

        data.add(new Event_data("Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman  Batman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal " +
                "vendetta against Superman Batman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman" +
                " vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction " +
                "of Metropolis, Batman embarks on a personal vendetta against Superman", R.drawable.ic_launcher_background,1,"igor",comments));
        data.add(new Event_data("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics ", R.drawable.ic_launcher_background,2,"igor",comments1));
        data.add(new Event_data("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.  ", R.drawable.ic_launcher_background,3,"igor",comments2));
        data.add(new Event_data("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas", R.drawable.ic_launcher_background,4,"igor",comments3));
        data.add(new Event_data("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth. ", R.drawable.ic_launcher_background,5,"igor",comments4));
        data.add(new Event_data("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass ", R.drawable.ic_launcher_background,6,"igor",comments5));

        return data;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);


        List<Event_data> data = fill_with_data();





        setContentView(R.layout.activity_menu_map);

        // *****************************************************************************************

        //Create a login session manager
        session = new SessionManagement(getApplicationContext());


        Log.d("LOAD",this.arrayTrees.size()+ "  ARRAY ");

        //Get user data from session
        HashMap<String, String> current_user = session.getUserDetails();
        String user_name = current_user.get(SessionManagement.KEY_NAME);
        String user_email = current_user.get(SessionManagement.KEY_EMAIL);

        Toast.makeText(getApplicationContext(),
                "Logado(a) com " + user_name + " email: " + user_email ,Toast.LENGTH_SHORT).show();

        // *****************************************************************************************



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        currentMarker = null;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //maps

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.add_tree_map)).getMapAsync(this);


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
        //mClusterManager.addItem(new StringClusterItem("Marker #" + (i + 1), latLng));

        //mClusterManager.cluster();

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

        mClusterManager = new ClusterManager<Tree_item>(this, map);
       ///// map.setOnCameraMoveListener(mClusterManager);
        // map.setOnCameraMoveListener((GoogleMap.OnCameraMoveListener) mClusterManager);

        map.setOnMarkerClickListener(this);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if(provider != null) {
            currentLocation  = vicosa;//= getLocation(provider);
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


        LoadTrees loadTrees = new LoadTrees(session,findViewById(R.id.add_tree_map).getContext(),this.map);
        this.arrayTrees = loadTrees.getArrayTree();



    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new Event_view_adapter(fill_with_data(), getApplication());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingLayout.setAnchorPoint(anchor);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                small_info.setVisibility(View.VISIBLE);
            }
            else if (currentMarker != null) {
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

        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.cardview_light_background), PorterDuff.Mode.SRC_ATOP);
            }
        }
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
            session.logoutUser(session.getToken());
            finish();
            return true;
        } else if (id == R.id.add_tree) {
            it = new Intent(this,Activity_tab_add_tree.class);
            startActivity(it);
            return true;
        }else if(id == R.id.feedback_bar){
            it = new Intent(this,Activity_feedback.class);
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
            //TODO current user
            Intent it = new Intent(getBaseContext(),Activity_tab_profile.class);
            startActivity(it);
        } else if (id == R.id.nav_profile_edit) {
            Intent it = new Intent(getBaseContext(),Activity_edit_profile.class);
            startActivity(it);


        } else if (id == R.id.nav_feedback) {
            Intent it = new Intent(getBaseContext(),Activity_feedback.class);
            startActivity(it);

        } else if (id == R.id.nav_feed) {

        } else if (id == R.id.nav_trees) {
            //TODO all trees
            Intent intent = new Intent(getBaseContext(), ShowTreesTest.class);
            startActivity(intent);
        } else if (id == R.id.nav_species) {
            Intent intent = new Intent(getBaseContext(), Activity_list_species.class);
            startActivity(intent);
            //TODO all species
        } else if (id == R.id.nav_companies) {

        } else if (id == R.id.nav_users) {
            //TODO all users
            Intent intent = new Intent(getBaseContext(), ShowUsersScreen.class);
            startActivity(intent);
            //finish();
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
        currentLocation = vicosa;//getLocation(provider);
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
    public void newEvent(View v){
        Intent it;
        it = new Intent(this,Activity_detail.class);
        startActivity(it);

    }

}
