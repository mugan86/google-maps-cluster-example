package com.anartzmugika.clustergooglemaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.anartzmugika.clustergooglemaps.model.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 'InputStream' bidez sortu 'Bitmap' bueltatzeko 'doInBackground' metodora
    public static Bitmap downloadImage(String url) {

        //Fresh Garbage Collector
        System.gc();

        //Default sample size
        int sample_size = 1;

        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inScaled=true;
        bmOptions.inDensity= 160;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;


        boolean imageSuccessFullyLoad = true;
        boolean image_width_small_than_screen_width = false;
        do
        {
            bmOptions.inSampleSize = sample_size; //Default sample_size = 1

            //url = ConstantValues.URL_LOCALHOST + url.substring(1);
            System.out.println("URL Image: "+ url);

            try {
                stream = getHttpConnection(url);

                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
                System.out.println("Downloaded image");
                //stream.close();

                //Restart
                imageSuccessFullyLoad = true;
                image_width_small_than_screen_width = true;

            }
            catch (OutOfMemoryError oom)
            {
                System.out.println ("Out Of Memory Error..");
                sample_size++;

                System.out.println("Sample size: "+ sample_size);
                imageSuccessFullyLoad = false;
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            catch (Exception eu)
            {
                System.out.println ("Exception");
            }
            System.out.println("Irudia ondo kargatua? "
                    +imageSuccessFullyLoad
                    + "\nTamaina egokitua?"+ image_width_small_than_screen_width+"\n"+
                    "SampleSize: "+sample_size);
        }
        while (!imageSuccessFullyLoad && image_width_small_than_screen_width);

        System.out.println ("Irudiari tamaina zehazten...");


        return bitmap;
    }

    private static InputStream getHttpConnection(String urlString)
            throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                stream = httpConnection.getInputStream();
            }
        }
        catch (SocketTimeoutException e)
        {
            Log.e("SocketTimeoutException", "SocketTimeoutException");
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return stream;
    }


}
