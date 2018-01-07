package com.example.akarsh.finalterm.asynctasks;

import android.os.AsyncTask;

import com.example.akarsh.finalterm.jsonparser.ItemParser;
import com.example.akarsh.finalterm.model.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class GetGeoCordinates extends AsyncTask<String,Void,LatLng> {


    String mapsApiUrl = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyBp_IrbW-Lk3FY5Nbl0okuImLZWZ-OpYr0&placeid=";
    private PlaceAsyncTask.IData activity;

    public GetGeoCordinates(PlaceAsyncTask.IData activity) {
        this.activity = activity;
    }


    @Override
    protected LatLng doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            String apiUrl = mapsApiUrl + params[0];
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                return ItemParser.parsePlace(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        super.onPostExecute(latLng);
    }

    public interface IData
    {
        void setupData(LatLng geoCode);
    }
}
