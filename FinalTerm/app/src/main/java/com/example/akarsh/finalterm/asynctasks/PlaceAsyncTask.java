package com.example.akarsh.finalterm.asynctasks;

import android.os.AsyncTask;

import com.example.akarsh.finalterm.jsonparser.ItemParser;
import com.example.akarsh.finalterm.model.*;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import com.example.akarsh.finalterm.*;
import com.google.android.gms.location.places.Place;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class PlaceAsyncTask extends AsyncTask<String,Void,ArrayList<com.example.akarsh.finalterm.model.Place>> {

    String mapsApiUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyBp_IrbW-Lk3FY5Nbl0okuImLZWZ-OpYr0&types=(cities)&input=";
    private IData activity;

    public PlaceAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<com.example.akarsh.finalterm.model.Place> doInBackground(String... params) {
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

                return ItemParser.parseItem(sb.toString());
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
    protected void onPostExecute(ArrayList<com.example.akarsh.finalterm.model.Place> items) {
        super.onPostExecute(items);
        activity.setupData(items);
    }

    public interface IData
    {
        void setupData(ArrayList<com.example.akarsh.finalterm.model.Place> appItems);
    }
}
