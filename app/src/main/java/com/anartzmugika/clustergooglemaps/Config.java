package com.anartzmugika.clustergooglemaps;

import com.anartzmugika.clustergooglemaps.model.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/***************************************************************
 * Created by anartzmugika on 26/2/16.
 **************************************************************/
public class Config {

    public static UiSettings getGoogleMapUISettings (GoogleMap GoogleMap, boolean control_zoom, boolean gestures)
    {
        UiSettings uiSettings = GoogleMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        //if (control_zoom)
        uiSettings.setZoomControlsEnabled(control_zoom);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setAllGesturesEnabled(gestures);
        //uiSettings.setMapToolbarEnabled(true);
        return uiSettings;
    }

    public static LatLng getMapCenterPosition(int type, ArrayList<Person> person_data)
    {
        //To do this, first calculate the bounds of all the markers like so:
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Person marker : person_data) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        return bounds.getCenter();
    }
}
