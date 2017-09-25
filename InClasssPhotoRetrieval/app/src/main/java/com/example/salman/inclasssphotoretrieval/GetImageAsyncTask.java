package com.example.salman.inclasssphotoretrieval;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap>{

    MainActivity mainActivity;
    Bitmap image;
    ProgressDialog myPd_ring;
    public GetImageAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        myPd_ring = new ProgressDialog(mainActivity);
        myPd_ring.setMessage("Loading Directory...");
        myPd_ring.show();
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        HttpURLConnection con;
        try {
            URL url = new URL(params[0]);
            con = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null){
            mainActivity.setupData(bitmap);
        }
        myPd_ring.dismiss();
        super.onPostExecute(bitmap);
    }

     public interface IData{
         void setupData(Bitmap image);
    }
}
