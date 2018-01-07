package com.example.akarsh.finalterm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akarsh.finalterm.R;
import com.example.akarsh.finalterm.asynctasks.GetGeoCordinates;
import com.example.akarsh.finalterm.model.Place;
import com.example.akarsh.finalterm.model.Trip;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements GetGeoCordinates.IData{

    ImageButton imageButtonAddTrip;
    RecyclerView recyclerViewTrips;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabaseRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButtonAddTrip = (ImageButton) findViewById(R.id.imageButtonAddtrip);
        recyclerViewTrips = (RecyclerView) findViewById(R.id.recyleViewTrips);
        recyclerViewTrips.setHasFixedSize(true);
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = firebaseDatabase.getReference("trips");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Trip,TripsViewHolder>(Trip.class,R.layout.trip_row_recycle_layout,TripsViewHolder.class,mDatabaseRef)
        {
            @Override
            protected void populateViewHolder(TripsViewHolder viewHolder, Trip model, final int position) {

                viewHolder.recycleTextViewName.setText(model.getTripName());
                viewHolder.recycleTextDescription.setText(model.getCityName());
                final String placeId = model.getTripPlaces().get(position).getPlaceId();
                viewHolder.imageButtonAddPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,AddTrip.class);
                        startActivity(intent);
                    }
                });

                viewHolder.imageButtonAddPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };

        recyclerViewTrips.setAdapter(firebaseRecyclerAdapter);
        imageButtonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTrip.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupData(LatLng geoCode) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    public static class TripsViewHolder extends RecyclerView.ViewHolder
    {
        TextView recycleTextViewName;
        TextView recycleTextDescription ;
        ImageButton imageButtonAddPlace;
        ImageButton imageButtonMap;
        public TripsViewHolder(View itemView) {
            super(itemView);
            recycleTextViewName = (TextView) itemView.findViewById(R.id.textViewRecyclerTripName);
            recycleTextDescription = (TextView) itemView.findViewById(R.id.textViewRecyclerDescription);
            imageButtonAddPlace = (ImageButton) itemView.findViewById(R.id.imageButtonRecycleAddTrip);
            imageButtonMap = (ImageButton) itemView.findViewById(R.id.imageButtonRecyclerMap);
        }
    }
}
