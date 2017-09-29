package com.example.salman.inclassnewsapplication;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RequestParams {
    String  method, baseURL;
    HashMap<String, String> params = new HashMap<String, String>();

    public RequestParams(String method, String baseURL) {
        this.method = method;
        this.baseURL = baseURL;
    }

    public void addParams(String key, String value){
        params.put(key, value);
    }

    public String getEncodedParams() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for(String key:params.keySet()){
            String value = URLEncoder.encode(params.get(key), "UTF-8");
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(key+"="+value);
        }
        return sb.toString();
    }

    public String getEncodedURL() throws UnsupportedEncodingException {

        return this.baseURL+"?"+getEncodedParams();

    }

    public HttpURLConnection setupConnection() throws IOException {
        if(method.equals("GET")){
            URL url = new URL(getEncodedURL());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            return con;
        }
        return null;
    }
}
