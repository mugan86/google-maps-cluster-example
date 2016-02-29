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

    public static int getUseZoomInMap(float max_distance)
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
