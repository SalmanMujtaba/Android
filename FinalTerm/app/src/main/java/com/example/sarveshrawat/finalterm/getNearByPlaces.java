package com.example.sarveshrawat.finalterm;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sarveshrawat on 12/9/17.
 */

public class getNearByPlaces extends AsyncTask<Void,Void,List<Places>> {

    private final OkHttpClient client = new OkHttpClient();
    Map<String,Double> location;
    private List<Places> placeList = new ArrayList<>();
    IgetNearbyPlaces igetNearbyPlaces;
    Places places;
    String latitude;
    String longitude;

    public getNearByPlaces(IgetNearbyPlaces igetNearbyPlaces, String latitude, String longitude) {
        this.igetNearbyPlaces = igetNearbyPlaces;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected List<Places> doInBackground(Void...voids) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json").newBuilder();
        urlBuilder.addQueryParameter("key", "AIzaSyCJrZ5tVfQKtLjrsvDP0COHPbjN_Uv8Cwo");
        urlBuilder.addQueryParameter("location", latitude+","+longitude);
        urlBuilder.addQueryParameter("radius", "1000");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                JSONObject rootJsonObject = new JSONObject(response.body().string());
                JSONArray messagesJsonArray = rootJsonObject.getJSONArray("results");

                for (int i = 0; i < messagesJsonArray.length(); i++) {
                    JSONObject message = messagesJsonArray.getJSONObject(i);
                    JSONObject loc = message.getJSONObject("geometry").getJSONObject("location");
                    location = new HashMap<>();
                    location.put("lat",Double.parseDouble(loc.getString("lat")));
                    location.put("lng",Double.parseDouble(loc.getString("lng")));
                    String id = message.getString("id");
                    String name = message.getString("name");
                    String icon = message.getString("icon");
                    places = new Places();
                    places.setName(name);
                    places.setLocation(location);
                    places.setIcon(icon);
                    places.setId(id);
                    placeList.add(places);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return placeList;

    }

    @Override
    protected void onPostExecute(List<Places> placeList) {
        super.onPostExecute(placeList);
        igetNearbyPlaces.getNearByPlacesList(placeList);
    }


    interface IgetNearbyPlaces {
        void getNearByPlacesList(List<Places> placeList);
    }
}



