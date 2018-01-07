package com.example.akarsh.finalterm.jsonparser;

import com.example.akarsh.finalterm.model.Item;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class ItemParser {

    public static ArrayList<com.example.akarsh.finalterm.model.Place> parseItem (String rootJson) throws JSONException {
        ArrayList<com.example.akarsh.finalterm.model.Place> appItemsList = new ArrayList<com.example.akarsh.finalterm.model.Place>();

        JSONObject root = new JSONObject(rootJson);

        //Change root element to actual root element in service
        if (root.has("predictions")) {

            JSONArray jsonArray = root.getJSONArray("predictions");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                com.example.akarsh.finalterm.model.Place place = new com.example.akarsh.finalterm.model.Place();
                String placeId = object.getString("place_id");
                String description = object.getString("description");
                place.setPlaceId(placeId);
                place.setDescription(description);
                appItemsList.add(place);
            }
        }
        return appItemsList;
    }

    public static LatLng parsePlace(String rootJson) throws JSONException
    {
        JSONObject root = new JSONObject(rootJson);
        LatLng latLng = null ;
        //Change root element to actual root element in service
        if (root.has("predictions")) {


        }
        return latLng;
    }

}
