package com.example.sarveshrawat.finalterm;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getPlacesAutoComplete extends AsyncTask<String,Void,List<Trip>> {


    private final OkHttpClient client = new OkHttpClient();
    private List<Trip> tripList = new ArrayList<>();
    IGetPlacesAutoComplete iGetPlacesAutoComplete;

    public getPlacesAutoComplete(IGetPlacesAutoComplete iGetPlacesAutoComplete) {
        this.iGetPlacesAutoComplete = iGetPlacesAutoComplete;
    }

    @Override
    protected List<Trip> doInBackground(String... strings) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/autocomplete/json").newBuilder();
        urlBuilder.addQueryParameter("key", "AIzaSyCJrZ5tVfQKtLjrsvDP0COHPbjN_Uv8Cwo");
        urlBuilder.addQueryParameter("types", "(cities)");
        urlBuilder.addQueryParameter("input", strings[0]);
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
                JSONArray messagesJsonArray = rootJsonObject.getJSONArray("predictions");

                for (int i = 0; i < messagesJsonArray.length(); i++) {
                    JSONObject message = messagesJsonArray.getJSONObject(i);
                    String description = message.getString("description");
                    String place_id = message.getString("place_id");
                    Trip tr = new Trip();
                    tr.setDescription(description);
                    tr.setPalce_id(place_id);
                    tripList.add(tr);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return tripList;
    }

    @Override
    protected void onPostExecute(List<Trip> tripAsynctaskModels) {
        super.onPostExecute(tripAsynctaskModels);
        iGetPlacesAutoComplete.getDescriptionPlaceId(tripList);
    }

    interface IGetPlacesAutoComplete {

        void getDescriptionPlaceId(List<Trip> tripList);
    }
}
