package com.example.salman.inclassnewsapplication;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.salman.inclassnewsapplication.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    int index;
    String selected;
    private ActivityMainBinding binding;
    int arrayListLength;
    ArrayList<News> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arrayListLength = 0;
        index = 0;
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Code for spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.source, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        //Disable buttons
        buttonDisable();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected=parent.getItemAtPosition(position).toString();
    }
    public void lastNews(View view) throws ParseException {
        index = arrayListLength-1;
        setNews(news.get(index));
        callGetImageAsyncTask(news.get(index).getUrlToImage());
    }
    public void firstNews(View view) throws ParseException {
        index = 0;
        setNews(news.get(index));
        callGetImageAsyncTask(news.get(index).getUrlToImage());
    }
    public void nextNews(View view) throws ParseException {
        try {
            if (index >= 0 && index < arrayListLength) {
                index++;
                setNews(news.get(index));
                callGetImageAsyncTask(news.get(index).getUrlToImage());
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            index=0;
            setNews(news.get(index));
            callGetImageAsyncTask(news.get(index).getUrlToImage());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void prevNews(View view) throws ParseException {
        try {
            if (index >= 0 && (index < arrayListLength)) {
                index--;
                setNews(news.get(index));
                callGetImageAsyncTask(news.get(index).getUrlToImage());
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            index= arrayListLength-1;
            setNews(news.get(index));
            callGetImageAsyncTask(news.get(index).getUrlToImage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getNews(View view){
        if(!selected.equals("Select")){
            if(isConnectedOnline()){
                Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_LONG).show();
                RequestParams params = new RequestParams("GET", "https://newsapi.org/v1/articles");
                params.addParams("apiKey","fc1354f8b0274ed6bb4e14b245800abe");
                params.addParams("source",selected.toLowerCase());
                new GetData().execute(params);
            }
            else
            {
                Toast.makeText(MainActivity.this, "Not Connected",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please select ann option first",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    public void setupData(Bitmap bitmap) {
        binding.imageView.setImageBitmap(bitmap);
    }

    public void finish(View view) {
        finish();
    }

    public void callGetImageAsyncTask(String link)
    {
        new GetImageAsyncTask(MainActivity.this).execute(link);
    }

    public void setNews(News newsObject) throws ParseException {


        binding.textViewNewsDate.setText("Published AT: "+getDate(newsObject.getPublishedAt()));
        binding.textViewNewsD.setText("Description: "+newsObject.getDescription());
        binding.textViewNewsAuthor.setText("Author: "+newsObject.getAuthor());
        binding.textViewNewsTitle.setText("News Title: "+newsObject.getTitle());
    }

    public String getDate(String dateString) throws ParseException {
        String format = "yyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        Date date = dateFormat.parse(dateString);
        return dateFormat.format(date);

    }

    public void buttonEnable()
    {
        binding.imageButtonF.setEnabled(true);
        binding.imageButtonL.setEnabled(true);
        binding.imageButtonN.setEnabled(true);
        binding.imageButtonP.setEnabled(true);
    }

    public void buttonDisable()
    {
        binding.imageButtonF.setEnabled(false);
        binding.imageButtonL.setEnabled(false);
        binding.imageButtonN.setEnabled(false);
        binding.imageButtonP.setEnabled(false);
    }

    private class GetData extends AsyncTask<RequestParams, Void, ArrayList<News>> {

        BufferedReader reader = null;

        @Override
        protected ArrayList<News> doInBackground(RequestParams... params) {
            try {
                HttpURLConnection con = params[0].setupConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String kaalu = sb.toString();
                    sb.append(line);
                }
                return NewsUtil.NewsJSONParser.parseNews(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
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
        protected void onPostExecute(ArrayList<News> result) {
            super.onPostExecute(result);
            arrayListLength = result.size();
            news = result;


            if (result != null) {
                buttonEnable();
                try {
                    setNews(result.get(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                callGetImageAsyncTask(result.get(0).getUrlToImage());
            }
        }
    }
}
