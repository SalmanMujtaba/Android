package com.example.sarveshrawat.finalterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlacesListActivity extends AppCompatActivity implements PlacesAdapter.IPlaceAdapter,getNearByPlaces.IgetNearbyPlaces
{

    FirebaseDatabase fb;
    PlacesAdapter placesAdapter;
    List<Places> placesList;
    Trip trip;
    DatabaseReference mDatabase;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        //trip coming from the Main activity
        trip = (Trip)getIntent().getExtras().getSerializable("trip");

        fb = FirebaseDatabase.getInstance();
        placesList = new ArrayList<>();
        new getNearByPlaces(PlacesListActivity.this,String.valueOf(trip.getLocation().get("lat")),String.valueOf(trip.getLocation().get("lng")))
                .execute();
    }

    @Override
    public void addToTrip(Places place) {
        if(trip.getPlacesList()==null){
            mDatabase = fb.getReference("trips").child(trip.getId()).child("placesList");
            String key = mDatabase.push().getKey();
            place.setId(key);
            fb.getReference("trips").child(trip.getId()).child("placesList").child(key).setValue(place);
        }
        else {
            fb.getReference("trips").child(trip.getId()).child("placesList").child(place.getId()).setValue(place);
        }
        finishTheActivity();
    }

    public void setAdapterAndNotify(){
        placesAdapter = new PlacesAdapter((ArrayList<Places>) placesList, this, this);
        RecyclerView recyclerViewCourse = (findViewById(R.id.recyclerViewPlaces));
        recyclerViewCourse.setAdapter(placesAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCourse.setLayoutManager(linearLayoutManager);
        placesAdapter.notifyDataSetChanged();
    }

    void finishTheActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getNearByPlacesList(List<Places> placeList) {
        placesList = placeList;
        setAdapterAndNotify();
    }
}
