//Salman Mujtaba 800969897
//Prerana Singh
//Ryan Mcpeck
//InClass07
//Group09

package com.example.salman.inclass07;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MusicUtil {
    static public class MusicJSONParser {
        static ArrayList<Music> parseMusic(String in) throws JSONException {
            ArrayList<Music> musicList;

                JSONObject musicObject;
                musicList = new ArrayList<>();
                Music music;
                JSONArray image;
                JSONObject imageObj;

                JSONObject root = new JSONObject(in);

                JSONArray musicJSONArray = root.getJSONObject("feed").getJSONArray("entry");

                for (int i = 0; i < musicJSONArray.length(); i++) {
                    musicObject = musicJSONArray.getJSONObject(i);
                    music = new Music();
                    music.setName(musicObject.getJSONObject("im:name").getString("label"));
                    String price = musicObject.getJSONObject("im:price").getString("label");
                    Double priceFloat = Double.parseDouble(price.substring(1,(price.length())));
                    music.setPrice(price);

                    image = musicObject.getJSONArray("im:image");
                    for (int j = 0; j < image.length(); j++) {
                        imageObj = image.getJSONObject(j);

                        if (imageObj.getJSONObject("attributes").getString("height").equals("53")) {
                            music.setIcon(imageObj.getString("label"));
                        }

                        if (imageObj.getJSONObject("attributes").getString("height").equals("100")) {
                            music.setBigIcon(imageObj.getString("label"));
                        }

                    }
                    musicList.add(music);
                }

                return musicList;
        }
    }
}
