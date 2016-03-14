package com.anartzmugika.clustergooglemaps.cluster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anartzmugika.clustergooglemaps.R;
import com.anartzmugika.clustergooglemaps.model.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/***********************************
 * Created by anartzmugika on 28/2/16.
 */
public class PersonClusterRenderer extends DefaultClusterRenderer<Person> {
    private IconGenerator mIconGenerator;
    private IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final int mDimension;
    public PersonClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<Person> clusterManager, Activity activity) {
        super(context, map, clusterManager);


        View multiProfile = activity.getLayoutInflater().inflate(R.layout.custom_marker_layout, null);
        mIconGenerator = new IconGenerator(activity);
        mClusterIconGenerator = new IconGenerator(activity);
        mClusterIconGenerator.setContentView(multiProfile);

        mImageView = new ImageView(activity);
        mDimension = (int) activity.getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) activity.getResources().getDimension(R.dimen.custom_profile_padding);
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        markerOptions.title(item.getName());
        markerOptions.snippet(item.getPosition().toString());
        mImageView.setImageResource(item.profilePhoto);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.name);
    }

    @Override
    protected void onClusterItemRendered(Person clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }
}