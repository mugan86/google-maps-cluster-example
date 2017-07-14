package com.anartzmugika.clustergooglemaps

/**
 * Created by anartzmugika on 26/2/16.
 */
import com.anartzmugika.clustergooglemaps.model.MyItem

import java.io.InputStream
import java.util.ArrayList
import java.util.Scanner

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MyItemReader {

    @Throws(JSONException::class)
    fun read(inputStream: InputStream): List<MyItem> {
        val items = ArrayList<MyItem>()
        val json = Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next()
        val array = JSONArray(json)
        for (i in 0..array.length() - 1) {
            val `object` = array.getJSONObject(i)
            val lat = `object`.getJSONObject("location").getDouble("lat")
            val lng = `object`.getJSONObject("location").getDouble("lng")
            items.add(MyItem(lat, lng))
        }
        return items
    }

    companion object {

        /*
     * This matches only once in whole input,
     * so Scanner.next returns whole InputStream as a String.
     * http://stackoverflow.com/a/5445161/2183804
     */
        private val REGEX_INPUT_BOUNDARY_BEGINNING = "\\A"
    }
}