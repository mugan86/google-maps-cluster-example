package com.anartzmugika.clustergooglemaps.cluster;

import android.content.Context;

import com.anartzmugika.clustergooglemaps.model.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/***********************************
 * Created by anartzmugika on 28/2/16.
 */
public class PersonClusterRenderer extends DefaultClusterRenderer<Person> {

    public PersonClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<Person> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        markerOptions.title(item.getName());
        markerOptions.snippet(item.getPosition().toString());
    }

    @Override
    protected void onClusterItemRendered(Person clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }
}