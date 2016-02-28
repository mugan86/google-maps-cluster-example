package com.anartzmugika.clustergooglemaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.anartzmugika.clustergooglemaps.cluster.PersonClusterRenderer;
import com.anartzmugika.clustergooglemaps.model.Person;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivityWithClusterActions extends FragmentActivity
                                            implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<Person>,
                                                        ClusterManager.OnClusterInfoWindowClickListener<Person>,
                                                        ClusterManager.OnClusterItemClickListener<Person>,
                                                        ClusterManager.OnClusterItemInfoWindowClickListener<Person> {

    private GoogleMap mMap;
    private ClusterManager<Person> mClusterManager;
    private Random mRandom = new Random(1984);
    private ArrayList<Person> person_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        //Cluster Manage with click actions in cluster and inside cluster markers :)

        addItems();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Config.getMapCenterPosition(1, person_list), 5));
        mClusterManager = new ClusterManager<>(this, mMap);
        mClusterManager.setRenderer(new PersonClusterRenderer(this, mMap ,mClusterManager));
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        for(Person person:person_list)
        {
            mClusterManager.addItem(person);
        }

        mClusterManager.cluster();

        Config.getGoogleMapUISettings(mMap, true, true);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker arg0) {

                for (Person person : person_list) {
                    if (person.name.equals(arg0.getTitle())) {

                        String uri = "http://maps.google.com/maps?daddr="+arg0.getPosition().latitude+","+
                                arg0.getPosition().longitude;
                        System.out.println("Google maps url: "+uri);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onClusterClick(Cluster<Person> cluster) {
        String firstName = cluster.getItems().iterator().next().name;
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Person> cluster) {

    }

    @Override
    public boolean onClusterItemClick(Person person) {
        //Create select marker info window to show this select info
        Toast.makeText(getApplicationContext(), person.name, Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Person person) {

    }

    private void addItems() {

        person_list = new ArrayList<>();
        person_list.add(new Person(position(), "Walter", R.drawable.walter));
        person_list.add(new Person(position(), "Gran", R.drawable.gran));
        person_list.add(new Person(position(), "Ruth", R.drawable.ruth));
        person_list.add(new Person(position(), "Stefan", R.drawable.stefan));
        person_list.add(new Person(position(), "Mechanic", R.drawable.mechanic));
        person_list.add(new Person(position(), "Yeats", R.drawable.yeats));
        person_list.add(new Person(position(), "John", R.drawable.john));
        person_list.add(new Person(position(), "Trevor the Turtle", R.drawable.turtle));
        person_list.add(new Person(position(), "Teach", R.drawable.teacher));

    }

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

}
