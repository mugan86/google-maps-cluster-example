package com.anartzmugika.clustergooglemaps

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast

import com.anartzmugika.clustergooglemaps.model.MyItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager

import org.json.JSONException

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private val mMap: GoogleMap? = null

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
    override fun onMapReady(mMap: GoogleMap) {

        // Euskal Herriko erdigunea
        val latitude = 42.85197 //51.503186
        val longitude = -1.92296//-0.126446
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 6f))

        mClusterManager = ClusterManager<MyItem>(this, mMap)
        // mMap.setOnCameraCh(mClusterManager)

        Config.getGoogleMapUISettings(mMap, true, true)

        try {
            readItems()
        } catch (e: JSONException) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show()
        }


    }

    private var mClusterManager: ClusterManager<MyItem>? = null


    @Throws(JSONException::class)
    private fun readItems() {
        val inputStream = resources.openRawResource(R.raw.mendiak)
        val items = MyItemReader().read(inputStream)
        mClusterManager!!.addItems(items)
    }
}
