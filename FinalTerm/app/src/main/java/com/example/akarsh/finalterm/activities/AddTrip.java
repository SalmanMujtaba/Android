package com.example.akarsh.finalterm.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.akarsh.finalterm.R;
import com.example.akarsh.finalterm.adapters.listview.PlaceListViewAdapter;
import com.example.akarsh.finalterm.asynctasks.PlaceAsyncTask;
import com.example.akarsh.finalterm.model.Item;
import com.example.akarsh.finalterm.model.Place;
import com.example.akarsh.finalterm.model.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTrip extends AppCompatActivity implements PlaceAsyncTask.IData {


    EditText editTextTripName;
    EditText editTextCityName;
    Button buttonSearch ;
    Button buttonAddTrip ;
    ListView listViewPlaces ;
    PlaceListViewAdapter placeListViewAdapter;
    ArrayList<Place> places;
    Place selectedPlace ;

    FirebaseDatabase mDatabase;
    DatabaseReference mTripRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        editTextCityName = (EditText) findViewById(R.id.editTextCityName);
        editTextTripName = (EditText) findViewById(R.id.editTextTripName);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonAddTrip = (Button) findViewById(R.id.buttonAddtrip);
        listViewPlaces = (ListView) findViewById(R.id.listViewSearchPlaces);
        mDatabase = FirebaseDatabase.getInstance();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCityName.getText().length() == 0)
                {
                    editTextCityName.setError("Please enter city name");
                    return;
                }
                new PlaceAsyncTask(AddTrip.this).execute(editTextCityName.getText().toString());
            }
        });

        listViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlace = places.get(position);
                view.setBackgroundColor(Color.GREEN);
            }
        });

        buttonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Place> placeSelected = new ArrayList<Place>();
                placeSelected.add(selectedPlace);
                Trip trip = new Trip(editTextTripName.getText().toString(),editTextCityName.getText().toString(),placeSelected);
                mDatabase.getReference().child("trips").push().setValue(trip);
                finish();
            }
        });
    }


    @Override
    public void setupData(ArrayList<Place> appItems) {
        //Log.d("size",String.valueOf(appItems.size()));
        this.places = appItems;
        placeListViewAdapter = new PlaceListViewAdapter(AddTrip.this,R.layout.item_row_trips,places);
        placeListViewAdapter.setNotifyOnChange(true);
        listViewPlaces.setAdapter(placeListViewAdapter);
    }
}
