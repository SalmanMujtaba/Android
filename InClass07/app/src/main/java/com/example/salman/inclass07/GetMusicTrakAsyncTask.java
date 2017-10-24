package com.example.salman.inclass07;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetMusicTrakAsyncTask extends AsyncTask<String, Void, ArrayList<Music>> {

    IData activity;

    public GetMusicTrakAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Music> doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                    return MusicUtil.MusicJSONParser.parseMusic(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Music> musicTracks) {
        super.onPostExecute(musicTracks);
        activity.enableView();
        activity.updateData(musicTracks);
    }

    static public interface IData{
        void updateData(ArrayList<Music> musicTracks);
        void enableView();
    }
}
