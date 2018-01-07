package com.example.sarveshrawat.finalterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase fb;
    List<Trip> tripList;
    ImageButton imageButtonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb = FirebaseDatabase.getInstance();
        tripList = new ArrayList<>();
        imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fb.getReference("trips").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip tr = dataSnapshot.getValue(Trip.class);
                tripList.add(tr);
                setAdapterAndNotify();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                setAdapterAndNotify();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    void setAdapterAndNotify(){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewMain);
        TripAdapter adapter = new TripAdapter(this, tripList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }


    void sendControlToPlaces(Trip trip){
        Intent intent = new Intent(MainActivity.this,PlacesListActivity.class);
        intent.putExtra("trip",trip);
        startActivity(intent);
        finish();
    }

    void placesHolderDelete(Places place, int adapterPosition, int parentAdapterPosition) {
        fb = FirebaseDatabase.getInstance();
        fb.getReference("trips").child(tripList.get(adapterPosition).getId()).child("placesList").child(place.getId()).setValue(null);
        tripList.get(adapterPosition).getPlacesList().remove(place.getId());
        setAdapterAndNotify();
    }

    void sendToMap(Trip trip){
        Intent intent = new Intent(this,ViewMapActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    public void addTrip(View view) {
        Intent intent = new Intent(this,AddTripActivity.class);
        startActivity(intent);
        finish();
    }

}
