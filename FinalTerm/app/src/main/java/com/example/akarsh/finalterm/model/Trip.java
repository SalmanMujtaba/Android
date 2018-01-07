package com.example.akarsh.finalterm.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class Trip implements Serializable {

    private String tripName ;
    private String cityName ;
    private ArrayList<Place> tripPlaces;

    public Trip(String tripName, ArrayList<Place> tripPlaces) {
        this.tripName = tripName;
        this.tripPlaces = tripPlaces;
    }

    public Trip(String tripName, String cityName, ArrayList<Place> tripPlaces) {
        this.tripName = tripName;
        this.cityName = cityName;
        this.tripPlaces = tripPlaces;
    }

    public Trip()
    {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public ArrayList<Place> getTripPlaces() {
        return tripPlaces;
    }

    public void setTripPlaces(ArrayList<Place> tripPlaces) {
        this.tripPlaces = tripPlaces;
    }
}
