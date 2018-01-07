package com.example.akarsh.finalterm.model;

import java.io.Serializable;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class Place implements Serializable {

    private String placeId;
    private String description;

    public Place(String placeId, String description) {
        this.placeId = placeId;
        this.description = description;
    }
    public Place()
    {

    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
