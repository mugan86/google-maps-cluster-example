package com.anartzmugika.clustergooglemaps.model;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/***************************************************************************************************
 * Created by anartzmugika on 11/1/17.
 **************************************************************************************************/

public class MapModel {

    private GoogleMap googleMap;
    private boolean control_zoom, gestures;
    private com.google.android.gms.maps.UiSettings UiSettings;

    public MapModel(GoogleMap googleMap, boolean control_zoom, boolean gestures)
    {
        this.googleMap = googleMap;
        this.control_zoom = control_zoom;
        this.gestures = gestures;
        setUiSettings(control_zoom, gestures);
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public boolean isControl_zoom() {
        return control_zoom;
    }

    public void setControl_zoom(boolean control_zoom) {
        this.control_zoom = control_zoom;
    }

    public boolean isGestures() {
        return gestures;
    }

    public void setGestures(boolean gestures) {
        this.gestures = gestures;
    }


    private void setUiSettings (boolean control_zoom, boolean gestures)
    {
        com.google.android.gms.maps.UiSettings uiSettings = getGoogleMap().getUiSettings();
        uiSettings.setCompassEnabled(false);
        //if (control_zoom)
        uiSettings.setZoomControlsEnabled(control_zoom);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setAllGesturesEnabled(gestures);
        //uiSettings.setMapToolbarEnabled(true);
        this.UiSettings = uiSettings;
    }

    public void addMarker(LatLng location, String title)
    {
        getGoogleMap().addMarker(new MarkerOptions().position(location).title(title));
    }

    public void moveCamera(LatLng location)
    {
        getGoogleMap().moveCamera(CameraUpdateFactory.newLatLng(location));
    }


    //Center location with max distance center and apply zoom
    public void moveCamera(LatLng location, float max_distance)
    {
        getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(location, getUseZoomInMap(max_distance)));
    }

    /**********************************************************************************************
     *
     * @param type Define select map type
     *             1: Normal
     *             2: Hybrid
     *             3: Satellite
     *             4: Terrain
     */
    public void setMapType(int type)
    {
        if (type == 1) getGoogleMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (type == 2) getGoogleMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (type == 3) getGoogleMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (type == 4) getGoogleMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public LatLng getMapCenterPosition(int type, ArrayList<Person> person_data)
    {
        //To do this, first calculate the bounds of all the markers like so:
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Person marker : person_data) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        return bounds.getCenter();
    }

    public int getUseZoomInMap(float max_distance)
    {
        max_distance = max_distance / 1000;

        System.out.println("Max distance in kms: " + max_distance);
        if (max_distance < 34) {
            return 10;
        } else if (max_distance > 24 && max_distance < 48) {
            return 9;
        } else if (max_distance > 48 && max_distance < 128) {
            return 8;
        } else if (max_distance > 128 && max_distance < 400) {
            return 7;
        } else if (max_distance > 400 && max_distance < 600) {
            return 6;
        } else if (max_distance > 600 && max_distance < 1300) {
            return 5;
        } else if (max_distance > 1300 && max_distance < 2500) {
            return 4;
        } else if (max_distance > 2500 && max_distance < 4000) {
            return 3;
        }
        return 2;
    }



}
