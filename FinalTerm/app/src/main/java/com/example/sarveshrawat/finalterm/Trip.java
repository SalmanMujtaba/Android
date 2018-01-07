package com.example.sarveshrawat.finalterm;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trip implements Parent<Places>, Serializable {
    String name;
    String description;
    String palce_id;
    Map<String,Places> placesList;

    String id;

    Map<String, Double> location;

    public Map<String, Places> getPlacesList() {
        return placesList;
    }

    public void setPlacesList(Map<String, Places> placesList) {
        this.placesList = placesList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPalce_id(String palce_id) {
        this.palce_id = palce_id;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPalce_id() {
        return palce_id;
    }

    public Map<String, Double> getLocation() {
        return location;
    }

    @Exclude
    @Override
    public List<Places> getChildList() {
        if(placesList==null){
            return new ArrayList<Places>();
        }
        List<Places> t = new ArrayList<>(placesList.values());
        return t;
    }

    @Exclude
    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }
}
