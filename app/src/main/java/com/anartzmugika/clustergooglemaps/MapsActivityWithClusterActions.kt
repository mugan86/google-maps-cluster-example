package com.anartzmugika.clustergooglemaps

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast

import com.anartzmugika.clustergooglemaps.cluster.PersonClusterRenderer
import com.anartzmugika.clustergooglemaps.model.MapModel
import com.anartzmugika.clustergooglemaps.model.Person
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager

import java.util.ArrayList
import java.util.Random

class MapsActivityWithClusterActions : FragmentActivity(), OnMapReadyCallback, ClusterManager.OnClusterClickListener<Person>, ClusterManager.OnClusterInfoWindowClickListener<Person>, ClusterManager.OnClusterItemClickListener<Person>, ClusterManager.OnClusterItemInfoWindowClickListener<Person> {

    private var mClusterManager: ClusterManager<Person>? = null
    private val mRandom = Random(1984)
    private var person_list: ArrayList<Person>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    override fun onMapReady(googleMap: GoogleMap) {
        val mapModel = MapModel(googleMap, true, true)

        addItems()
        //Cluster Manage with click actions in cluster and inside cluster markers :)

        val center_location = Config.getMapCenterPosition(1, person_list)

        val center_point = Location("center_point")
        center_point.latitude = center_location.latitude
        center_point.longitude = center_location.longitude

        var max_distance = 0f
        // val zoom = 15
        for (person in person_list!!) {

            println(person.position)
            //mountain_list.add(mountain);
            val location_mountain = Location("race_location")

            location_mountain.latitude = person.position.latitude
            location_mountain.longitude = person.position.longitude
            val distance = center_point.distanceTo(location_mountain)
            if (distance > max_distance) {
                max_distance = distance
            }
        }


        mapModel.moveCamera(center_location, max_distance)
        mClusterManager = ClusterManager<Person>(this, mapModel.googleMap)
        mClusterManager!!.setRenderer(PersonClusterRenderer(this, mapModel.googleMap, mClusterManager!!, this@MapsActivityWithClusterActions))
        // mapModel.googleMap.setOnCameraChangeListener(mClusterManager)
        mapModel.googleMap.setOnMarkerClickListener(mClusterManager)
        mapModel.googleMap.setOnInfoWindowClickListener(mClusterManager)
        mClusterManager!!.setOnClusterClickListener(this)
        mClusterManager!!.setOnClusterInfoWindowClickListener(this)
        mClusterManager!!.setOnClusterItemClickListener(this)
        mClusterManager!!.setOnClusterItemInfoWindowClickListener(this)
        for (person in person_list!!) {
            mClusterManager!!.addItem(person)
        }

        mClusterManager!!.cluster()

        mapModel.googleMap.setOnInfoWindowClickListener { arg0 ->
            for (person in person_list!!) {
                if (person.name == arg0.title) {

                    val uri = "http://maps.google.com/maps?daddr=" + arg0.position.latitude + "," +
                            arg0.position.longitude
                    println("Google maps url: " + uri)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
                    startActivity(intent)
                }
            }
        }
    }

    override fun onClusterClick(cluster: Cluster<Person>): Boolean {
        val firstName = cluster.items.iterator().next().name
        Toast.makeText(this, cluster.size.toString() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onClusterInfoWindowClick(cluster: Cluster<Person>) {

    }

    override fun onClusterItemClick(person: Person): Boolean {
        //Create select marker info window to show this select info
        Toast.makeText(applicationContext, person.name, Toast.LENGTH_LONG).show()

        return false
    }

    override fun onClusterItemInfoWindowClick(person: Person) {

    }

    private fun addItems() {

        person_list = ArrayList<Person>()
        person_list!!.add(Person(position(), "Walter", R.drawable.walter, "http://static.panoramio.com/photos/original/101779498.jpg"))
        person_list!!.add(Person(position(), "Gran", R.drawable.gran, "http://mw2.google.com/mw-panoramio/photos/medium/14449320.jpg"))
        person_list!!.add(Person(position(), "Ruth", R.drawable.ruth, "http://mw2.google.com/mw-panoramio/photos/medium/27049250.jpg"))
        person_list!!.add(Person(position(), "Stefan", R.drawable.stefan, "http://mw2.google.com/mw-panoramio/photos/medium/20450379.jpg"))
        person_list!!.add(Person(position(), "Mechanic", R.drawable.mechanic, "http://mw2.google.com/mw-panoramio/photos/medium/8636065.jpg"))
        person_list!!.add(Person(position(), "Yeats", R.drawable.yeats, "http://mw2.google.com/mw-panoramio/photos/medium/8636188.jpg"))
        person_list!!.add(Person(position(), "John", R.drawable.john, "http://mw2.google.com/mw-panoramio/photos/medium/90076639.jpg"))
        person_list!!.add(Person(position(), "Trevor the Turtle", R.drawable.turtle, "http://mw2.google.com/mw-panoramio/photos/medium/89815173.jpg"))
        person_list!!.add(Person(position(), "Teach", R.drawable.teacher, "http://mw2.google.com/mw-panoramio/photos/medium/90080431.jpg"))
    }

    private fun position(): LatLng {
        return LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683))
    }

    private fun random(min: Double, max: Double): Double {
        return mRandom.nextDouble() * (max - min) + min
    }

}
