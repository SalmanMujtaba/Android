package com.example.salman.inclassnewsapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsUtil {
    static public class NewsJSONParser {
        static ArrayList<News> parseNews(String in) throws JSONException{
            ArrayList<News> newsArrayList = new ArrayList<>();

            JSONObject root = new JSONObject(in);
            JSONArray newsJSONArray = root.getJSONArray("articles");

            for(int i=0;i<newsJSONArray.length();i++){
                JSONObject newsJSONObject = newsJSONArray.getJSONObject(i);
                News news = new News();
                news.setAuthor(newsJSONObject.getString("author"));
                news.setDescription(newsJSONObject.getString("description"));
                news.setUrlToImage(newsJSONObject.getString("urlToImage"));
                news.setTitle(newsJSONObject.getString("title"));
                news.setPublishedAt(newsJSONObject.getString("publishedAt"));
                newsArrayList.add(news);
            }
            return newsArrayList;
        }
    }
}
