package com.example.cacau2.ecovoicetest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.qap.ctimelineview.TimelineRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Base.BaseMenuActivity;

public class MenuMap extends BaseMenuActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener {

    private static Location location;
    public static ArrayList<Tree> arrayTrees = new ArrayList<Tree>();
    SessionManagement session;
    private ClusterManager<Tree_item> mClusterManager;

    private LatLng vicosa = new LatLng(-20.752946, -42.879097);
    private LatLng google = new LatLng(37.25194, -122.084017);
    private Marker currentMarker;
    private Marker myPos;
    private Marker marker;
    private GoogleMap map;
    private SlidingUpPanelLayout slidingLayout;
    private LinearLayout small_info;
    private LatLng currentLocation;
    private int z = 18;
    private float anchor = 0.3125f;
    private LocationManager lm;
    private Criteria criteria;
    private String provider;

    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

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


        comments1.add(new Comment_data("Igor Oliveira", "Teste", "1", 0));
        comments1.add(new Comment_data("Igor Oliveira", "Teste", "1", 0));

        comments2.add(new Comment_data("Igor Oliveira", "Teste", "2", 1));
        comments3.add(new Comment_data("Igor Oliveira", "Teste", "3", 2));
        comments4.add(new Comment_data("Igor Oliveira", "Teste", "4", 3));
        comments5.add(new Comment_data("Igor Oliveira", "Teste", "5", 4));

        //comments.add(new Comment_data("Igor Oliveira","Teste", "6",5));

        data.add(new Event_data("Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman  Batman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal " +
                "vendetta against Superman Batman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman" +
                " vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction of Metropolis, Batman embarks on a personal vendetta against SupermanBatman vs Superman, Following the destruction " +
                "of Metropolis, Batman embarks on a personal vendetta against Superman", R.drawable.ic_launcher_background, 1, "igor", comments));
        data.add(new Event_data("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics ", R.drawable.ic_launcher_background, 2, "igor", comments1));
        data.add(new Event_data("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.  ", R.drawable.ic_launcher_background, 3, "igor", comments2));
        data.add(new Event_data("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas", R.drawable.ic_launcher_background, 4, "igor", comments3));
        data.add(new Event_data("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth. ", R.drawable.ic_launcher_background, 5, "igor", comments4));
        data.add(new Event_data("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass ", R.drawable.ic_launcher_background, 6, "igor", comments5));

        return data;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        super.onCreate(savedInstanceState);


        List<Event_data> data = fill_with_data();


        setContentView(R.layout.activity_menu_map);


        //Create a login session manager
        session = new SessionManagement(getApplicationContext());


        //Get user data from session
        HashMap<String, String> current_user = session.getUserDetails();
        String user_name = current_user.get(SessionManagement.KEY_NAME);
        String user_email = current_user.get(SessionManagement.KEY_EMAIL);

        // *****************************************************************************************

        this.setUpLayout();
        currentMarker = null;
        marker = null;

        //maps

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.add_tree_map)).getMapAsync(this);


        small_info = (LinearLayout) findViewById(R.id.small_info);
        anchor = pxFromDp(this, small_info.getLayoutParams().height);


        Log.d("ANCOR", String.format("aaaaaaaaaa %f", anchor));
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);


        slidingLayout.setFadeOnClickListener(new SlidingUpPanelLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SLID", "CLICK");
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
        if (!hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.i("LOCATION", "Usando GPS");
        } else {
            Log.i("LOCATION", "Usando WI-FI ou dados");
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }
        provider = lm.getBestProvider(criteria, true);


        if (map != null) {
            arrayTrees.clear();

            LoadTrees loadTrees = new LoadTrees(session, findViewById(R.id.add_tree_map).getContext(), this.map);
            this.arrayTrees = loadTrees.getArrayTree();
            if (map != null) {
                mClusterManager.clearItems();
                for (Tree tree : this.arrayTrees) {

                    mClusterManager.addItem(new Tree_item(tree));
                }
            }
            mClusterManager.cluster();

        }

    }

    public void closePanel(View v) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

    }

    public void createMyLocationMarker(LatLng c) {
        if (myPos == null && c != null && map != null) {
            myPos = map.addMarker(new MarkerOptions()
                    .position(c).zIndex(-1));
            myPos.setTag("My location");
        } else if (myPos != null && c != null) {
            myPos.setPosition(c);
        }

    }

    public void updateCamera(LatLng target, int zoom) {
        if (map != null && target != null) {
            CameraUpdate c = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(target)
                            .zoom(zoom)
                            .build());
            map.animateCamera(c);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null) {
            map.clear();
            if (map != null) {
                arrayTrees.clear();

                LoadTrees loadTrees = new LoadTrees(session, findViewById(R.id.add_tree_map).getContext(), this.map);
                this.arrayTrees = loadTrees.getArrayTree();
                if (map != null) {
                    mClusterManager.clearItems();
                    for (Tree tree : this.arrayTrees) {

                        mClusterManager.addItem(new Tree_item(tree));
                    }
                }
                mClusterManager.cluster();

            }
        }

    }

    public void createMarker() {


        if (map != null) {
            for (Tree tree : arrayTrees) {
                mClusterManager.addItem(new Tree_item(tree));
            }
            mClusterManager.cluster();
            /*map.addMarker(new MarkerOptions()
                    .position(pos)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon)));*/
        }
    }

    public static int getActionBarParam(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public float pxFromDp(final Context context, final float layout) {
        TypedValue tv = new TypedValue();
        int actionBarHeigh = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeigh = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        Float anc = ((layout) / (context.getResources().getDisplayMetrics().heightPixels - getActionBarParam(context) - actionBarHeigh));
        return anc - (anc * 0.07f);

    }
    @SuppressLint("MissingPermission")
    private Location getDeviceLoc() {
        /*if (this.getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this.getContext(),  android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            this.getActivity().requestPermissions(new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},101);

        } else {*/
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location location1 = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location2 = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (location != null) {
            return location;
        } else if (location1 != null) {
            return location1;
        } else if (location2 != null) {
            return location2;
        } else {
            //  Toast.makeText(this, "Unable to trace your location", Toast.LENGTH_SHORT).show();
        }
        //}
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        mClusterManager = new ClusterManager<>(this, map);

        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);
        // mClusterManager.setRenderer();
        mClusterManager.setAnimation(true);


        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);

        if (provider != null) {
            location = getDeviceLoc();
            currentLocation = new LatLng(location.getLatitude(),location.getLongitude());

            updateCamera(currentLocation, z);
            createMyLocationMarker(currentLocation);
        }

        CustomClusterRenderer customClusterRenderer = new CustomClusterRenderer(this, map, mClusterManager);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mClusterManager.setOnClusterItemClickListener(
                tree_item -> {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                    adapter = new Event_view_adapter(fill_with_data(), getApplication());
                    recyclerView.setAdapter(adapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    marker = customClusterRenderer.getMarker(tree_item);
                    updateCamera(marker.getPosition(), z);
                    if (!marker.equals(myPos)) {
                        if (currentMarker == null) {
                            currentMarker = marker;

                            slidingLayout.setAnchorPoint(anchor);
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                            currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_selected));
                        } else if (currentMarker != null) {
                            currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                            currentMarker = marker;
                            slidingLayout.setAnchorPoint(anchor);
                            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                            currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_selected));
                        }
                    }

                    return true;
                });

        mClusterManager.setRenderer(customClusterRenderer);
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Tree_item>() {
            @Override
            public boolean onClusterClick(final Cluster<Tree_item> cluster) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        cluster.getPosition(), (float) Math.floor(map
                                .getCameraPosition().zoom + 1)), 500,
                        null);
                return true;
            }
        });
        map.setOnMarkerClickListener(mClusterManager);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));
                    currentMarker = null;
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    Log.i("MAPA", "CLICK");

                } else {
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    Log.i("MAPA", "CLICK");
                }
            }
        });


        LoadTrees loadTrees = new LoadTrees(session, findViewById(R.id.add_tree_map).getContext(), this.map);
        this.arrayTrees = loadTrees.getArrayTree();
        if (map != null) {
            mClusterManager.clearItems();
            for (Tree tree : this.arrayTrees) {
                mClusterManager.addItem(new Tree_item(tree));
            }
        }
        mClusterManager.cluster();

    }
    /*@Override
    protected void onClusterItemRendered(Tree_item clusterItem, Marker marker) {


    }*/



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (slidingLayout != null && slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
            if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingLayout.setAnchorPoint(anchor);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                small_info.setVisibility(View.VISIBLE);
            } else if (currentMarker != null) {
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


    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();

        //Obtem melhor provedor habilitado com o critério estabelecido
        provider = lm.getBestProvider(criteria, true);

        if (provider == null) {
            Log.e("PROVEDOR", "Nenhum provedor encontrado!");
        } else {
            Log.i("PROVEDOR", "Está sendo utilizado o provedor: " + provider);

            //Obtem atualizações de posição
            //lm.requestSingleUpdate(provider,this,null );
        }
        if (map != null) {
            this.arrayTrees.clear();
            mClusterManager.clearItems();

            LoadTrees loadTrees = new LoadTrees(session, findViewById(R.id.add_tree_map).getContext(), this.map);
            this.arrayTrees = loadTrees.getArrayTree();
            if (map != null) {
                mClusterManager.clearItems();
                for (Tree tree : this.arrayTrees) {
                    mClusterManager.addItem(new Tree_item(tree));
                }
            }
            mClusterManager.cluster();
        }
    }

    @SuppressLint("MissingPermission")
    public LatLng getLocation(String provider) {
        if (provider != null) {
            lm.requestSingleUpdate(provider, this, null);
            Log.i("BUTTON", "Requested location2");
            Location l = lm.getLastKnownLocation(provider);
            if (l != null) {
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

        Log.w("PROVEDOR", "Provedor " + provider + " parado!");
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    public void myLocation(View v) {
        location = getDeviceLoc();
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        updateCamera(currentLocation, z);
        createMyLocationMarker(currentLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
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

    public void newEvent(View v) {
        Intent it;
        it = new Intent(this, Activity_detail.class);
        startActivity(it);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
