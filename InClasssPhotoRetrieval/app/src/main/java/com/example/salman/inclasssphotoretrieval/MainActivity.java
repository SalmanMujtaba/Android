package com.example.salman.inclasssphotoretrieval;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.salman.inclasssphotoretrieval.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity implements GetImageAsyncTask.IData{

    String[] choices= {"UNCC","Android", "Winter", "Aurora", "Wonders" };
    String[] links;
    ArrayList<String> arrayLinks;
    int length;
    ListIterator<String> lst;

    android.support.v7.app.AlertDialog.Builder alert;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        links = null;
        length = 1;
        arrayLinks=null;
        binding.imageButtonN.setEnabled(false);
        binding.imageButtonP.setEnabled(false);
    }

    public void chooseKeyword(View view) {
        links = null;
        arrayLinks=null;
        lst = null;

        CharSequence[] psw = new CharSequence[choices.length];
        for(int i =0;i<psw.length;i++){
            psw[i] = choices[i];
        }

        alert = new android.support.v7.app.AlertDialog.Builder(this);

        alert.setItems(psw, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.textViewKeyword.setText(choices[which]);
                if(isConnectedOnline()){
                    Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_LONG).show();
                    RequestParams params = new RequestParams("GET", "http://dev.theappsdr.com/apis/photos/index.php");
                    params.addParams("keyword",binding.textViewKeyword.getText().toString());
                    new GetData().execute(params);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not Connected",Toast.LENGTH_LONG).show();
                }
            }
        });
        alert.show();
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
    }
        return false;
    }

    @Override
    public void setupData(Bitmap image) {
        binding.imageView.setImageBitmap(image);
    }

    public void setNullImage(){
        binding.imageView.setImageResource(0);

    }
    public void nextImage(View view) {
        String nextLink;

        if(lst.hasNext()){
            nextLink = lst.next();
            Toast.makeText(MainActivity.this, nextLink,Toast.LENGTH_LONG).show();
            callGetImageAsyncTask(nextLink);
     }
        else{
            lst = arrayLinks.listIterator();
            nextLink = lst.next();
            Toast.makeText(MainActivity.this, nextLink,Toast.LENGTH_LONG).show();

            callGetImageAsyncTask(nextLink);
        }
    }

    public void prevImage(View view) {
        String previouLink;
        if(lst.hasPrevious()){
            previouLink = lst.previous();
            Toast.makeText(MainActivity.this, previouLink,Toast.LENGTH_LONG).show();
            callGetImageAsyncTask(previouLink);
        }
        else{
            lst = arrayLinks.listIterator(arrayLinks.size());
            previouLink = lst.previous();
            Toast.makeText(MainActivity.this, previouLink,Toast.LENGTH_LONG).show();
            callGetImageAsyncTask(previouLink);
        }
    }

    private class GetData extends AsyncTask<RequestParams, Void, String>{

    BufferedReader reader = null;
    @Override
    protected String doInBackground(RequestParams... params) {
        try {
            HttpURLConnection con = params[0].setupConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(reader!=null){
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
    protected void onPostExecute(String s) {
        links = s.split(";");
        if(links.length==1){
            setNullImage();
            Toast.makeText(MainActivity.this, "No images to display",Toast.LENGTH_LONG).show();
            return;
        }
        arrayLinks = new ArrayList<>();
        if(links.length>1){
            if(links.length==2){
                callGetImageAsyncTask(links[1]);
                binding.imageButtonN.setEnabled(false);
                binding.imageButtonP.setEnabled(false);
            }
            else
            {
                for(String link:links){
                    arrayLinks.add(link);
                }
                arrayLinks.remove(0);
                lst = arrayLinks.listIterator();
                callGetImageAsyncTask(arrayLinks.get(0));
                binding.imageButtonN.setEnabled(true);
                binding.imageButtonP.setEnabled(true);
            }
        }
        super.onPostExecute(s);
    }

    }
    public void callGetImageAsyncTask(String link)
    {
        new GetImageAsyncTask(MainActivity.this).execute(link);
    }
}
