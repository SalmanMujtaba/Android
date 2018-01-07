package com.example.sarveshrawat.finalterm;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class getPlaceLatLon extends AsyncTask<String,Void,Map<String,Double>> {

    private final OkHttpClient client = new OkHttpClient();
    Map<String,Double> location;
    private List<Trip> tripList = new ArrayList<>();
    IgetPlaceLatLon igetPlaceLatLon;

    public getPlaceLatLon(IgetPlaceLatLon igetPlaceLatLon) {
        this.igetPlaceLatLon = igetPlaceLatLon;
    }

    @Override
    protected Map<String,Double>  doInBackground(String... strings) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/details/json").newBuilder();
        urlBuilder.addQueryParameter("key", "AIzaSyCJrZ5tVfQKtLjrsvDP0COHPbjN_Uv8Cwo");
        urlBuilder.addQueryParameter("placeid", strings[0]);
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
                JSONObject json = rootJsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
                String lat = json.getString("lat");
                String lon = json.getString("lng");
                location = new HashMap<>();
                location.put("lat",Double.parseDouble(lat));
                location.put("lng",Double.parseDouble(lon));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    @Override
    protected void onPostExecute(Map<String,Double> location) {
        super.onPostExecute(location);
        igetPlaceLatLon.sendLatLon(location);
    }


    interface IgetPlaceLatLon {
        void sendLatLon(Map<String,Double>location);
    }
}


