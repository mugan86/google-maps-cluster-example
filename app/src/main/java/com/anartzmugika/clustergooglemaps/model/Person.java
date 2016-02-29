package com.anartzmugika.clustergooglemaps.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/***************************************
 * Created by anartzmugika on 27/2/16.
 */
public class Person implements ClusterItem {
    public final String name;
    public final int profilePhoto;
    private LatLng mPosition;

    public Person(LatLng position, String name, int pictureResource) {
        this.name = name;
        profilePhoto = pictureResource;
        mPosition = position;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getName()
    {
        return this.name;
    }

    public void setmPosition(double lat, double lng)
    {
        mPosition = new LatLng(lat, lng);
    }

}
