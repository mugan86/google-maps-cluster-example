package com.anartzmugika.clustergooglemaps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.util.Log

import com.anartzmugika.clustergooglemaps.model.Person
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.ArrayList

/***************************************************************
 * Created by anartzmugika on 26/2/16.
 */
object Config {

    fun getGoogleMapUISettings(GoogleMap: GoogleMap, control_zoom: Boolean, gestures: Boolean): UiSettings {
        val uiSettings = GoogleMap.uiSettings
        uiSettings.isCompassEnabled = false
        //if (control_zoom)
        uiSettings.isZoomControlsEnabled = control_zoom
        uiSettings.isMyLocationButtonEnabled = false
        uiSettings.setAllGesturesEnabled(gestures)
        //uiSettings.setMapToolbarEnabled(true);
        return uiSettings
    }

    fun getMapCenterPosition(type: Int, person_data: ArrayList<Person>?): LatLng {
        //To do this, first calculate the bounds of all the markers like so:
        val builder = LatLngBounds.Builder()

        for (marker in person_data!!) {
            builder.include(marker.position)
        }

        val bounds = builder.build()
        return bounds.center
    }

    fun getUseZoomInMap(max_distance: Float): Int {
        var max_distance = max_distance
        max_distance = max_distance / 1000

        println("Max distance in kms: " + max_distance)
        if (max_distance < 34) {
            return 10
        } else if (max_distance > 24 && max_distance < 48) {
            return 9
        } else if (max_distance > 48 && max_distance < 128) {
            return 8
        } else if (max_distance > 128 && max_distance < 400) {
            return 7
        } else if (max_distance > 400 && max_distance < 600) {
            return 6
        } else if (max_distance > 600 && max_distance < 1300) {
            return 5
        } else if (max_distance > 1300 && max_distance < 2500) {
            return 4
        } else if (max_distance > 2500 && max_distance < 4000) {
            return 3
        }
        return 2
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val policy = StrictMode.ThreadPolicy.Builder()
                    .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val url = java.net.URL(src)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val myBitmap = BitmapFactory.decodeStream(input)
            return myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    // 'InputStream' bidez sortu 'Bitmap' bueltatzeko 'doInBackground' metodora
    fun downloadImage(url: String): Bitmap? {

        //Fresh Garbage Collector
        System.gc()

        //Default sample size
        var sample_size = 1

        var bitmap: Bitmap? = null
        var stream: InputStream? = null
        val bmOptions = BitmapFactory.Options()
        bmOptions.inScaled = true
        bmOptions.inDensity = 160
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565


        var imageSuccessFullyLoad = true
        var image_width_small_than_screen_width = false
        do {
            bmOptions.inSampleSize = sample_size //Default sample_size = 1

            //url = ConstantValues.URL_LOCALHOST + url.substring(1);
            println("URL Image: " + url)

            try {
                stream = getHttpConnection(url)

                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions)
                stream!!.close()
                println("Downloaded image")
                //stream.close();

                //Restart
                imageSuccessFullyLoad = true
                image_width_small_than_screen_width = true

            } catch (oom: OutOfMemoryError) {
                println("Out Of Memory Error..")
                sample_size++

                println("Sample size: " + sample_size)
                imageSuccessFullyLoad = false
            } catch (e1: IOException) {
                e1.printStackTrace()
            } catch (eu: Exception) {
                println("Exception")
            }

            println("Irudia ondo kargatua? "
                    + imageSuccessFullyLoad
                    + "\nTamaina egokitua?" + image_width_small_than_screen_width + "\n" +
                    "SampleSize: " + sample_size)
        } while (!imageSuccessFullyLoad && image_width_small_than_screen_width)

        println("Irudiari tamaina zehazten...")


        return bitmap
    }

    @Throws(IOException::class)
    private fun getHttpConnection(urlString: String): InputStream? {

        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var stream: InputStream? = null
        val url = URL(urlString)
        val connection = url.openConnection()

        try {
            val httpConnection = connection as HttpURLConnection
            httpConnection.requestMethod = "GET"
            httpConnection.connect()

            if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.inputStream
            }
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeoutException", "SocketTimeoutException")
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return stream
    }


}
