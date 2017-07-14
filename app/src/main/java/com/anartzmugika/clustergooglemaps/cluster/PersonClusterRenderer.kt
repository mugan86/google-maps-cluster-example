package com.anartzmugika.clustergooglemaps.cluster

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView

import com.anartzmugika.clustergooglemaps.Config
import com.anartzmugika.clustergooglemaps.R
import com.anartzmugika.clustergooglemaps.model.Person
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

/***********************************
 * Created by anartzmugika on 28/2/16.
 */
class PersonClusterRenderer(context: Context, map: GoogleMap,
                            clusterManager: ClusterManager<Person>, private val activity: Activity) : DefaultClusterRenderer<Person>(context, map, clusterManager) {
    private val mIconGenerator: IconGenerator
    private val mClusterIconGenerator: IconGenerator
    private val mImageView: ImageView
    private val mDimension: Int

    init {


        val multiProfile = activity.layoutInflater.inflate(R.layout.custom_marker_layout, null)
        mIconGenerator = IconGenerator(activity)
        mClusterIconGenerator = IconGenerator(activity)
        mClusterIconGenerator.setContentView(multiProfile)

        mImageView = ImageView(activity)
        mDimension = activity.resources.getDimension(R.dimen.custom_profile_image).toInt()
        mImageView.layoutParams = ViewGroup.LayoutParams(mDimension, mDimension)
        val padding = activity.resources.getDimension(R.dimen.custom_profile_padding).toInt()
        mImageView.setPadding(padding, padding, padding, padding)
        mIconGenerator.setContentView(mImageView)
    }

    override fun onBeforeClusterItemRendered(item: Person?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        markerOptions!!.title(item!!.getName())
        markerOptions.snippet(item.position.toString())
        mImageView.setImageBitmap(Config.downloadImage(item.photo_url))
        //mImageView.setImageResource(item.profilePhoto); //Use with drawable
        val icon = mIconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.name)
    }

    override fun onClusterItemRendered(clusterItem: Person?, marker: Marker?) {
        super.onClusterItemRendered(clusterItem, marker)

        //here you have access to the marker itself
    }
}