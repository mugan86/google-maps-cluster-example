package com.anartzmugika.clustergooglemaps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

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
}
