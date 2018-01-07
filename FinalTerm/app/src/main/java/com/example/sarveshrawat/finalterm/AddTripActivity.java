package com.example.sarveshrawat.finalterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity implements AddTripAdapter.IAddTripAdapter,
                                                        getPlacesAutoComplete.IGetPlacesAutoComplete,
                                                        getPlaceLatLon.IgetPlaceLatLon {

    EditText editTextTripName;
    EditText editTextCity;
    Button buttonSearch;
    Button buttonAddTrip;
    List<Trip> tripList;
    AddTripAdapter addTripAdapter;
    Trip tripMain;
    FirebaseDatabase fb;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        editTextTripName = findViewById(R.id.editTextTripName);
        editTextCity = findViewById(R.id.editTextCity);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonAddTrip = findViewById(R.id.buttonAddTrip);
        tripList = new ArrayList<>();
        tripMain = new Trip();
        fb = FirebaseDatabase.getInstance();
        setTitle("Add Activity");
    }

    @Override
    public void getDescriptionPlaceId(List<Trip> tripListt) {
        tripList = tripListt;
        setAdapterAndNotify();
    }

    @Override
    public void sendLatLon(Map<String, Double> location) {
        //comes from asynctask. recycler view calls it
        tripMain.setLocation(location);
        tripMain.setPlacesList(null);
    }

    public void addTrip(View view) {
        String name = editTextTripName.getText().toString();
        String city = editTextCity.getText().toString();
        if(!((city.equals("") || name.equals("")))){
            tripMain.setName(name);
            tripMain.setDescription(city);
            String key = fb.getReference("trips").push().getKey();
            tripMain.setId(key);
            fb.getReference("trips").child(key).setValue(tripMain);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Please check all the inputs", Toast.LENGTH_SHORT).show();
        }
    }

    public void search(View view) {
        String city = editTextCity.getText().toString();
        new getPlacesAutoComplete(this).execute(city);
    }

    public void setAdapterAndNotify(){
        addTripAdapter = new AddTripAdapter((ArrayList<Trip>) tripList, this, this);
        RecyclerView recyclerViewCourse = ((RecyclerView) findViewById(R.id.recylerViewCreate));
        recyclerViewCourse.setAdapter(addTripAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCourse.setLayoutManager(linearLayoutManager);
        addTripAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEditPlaceCity(Trip trip) {
        editTextCity.setText(trip.getDescription());
        tripMain.setPalce_id(trip.getPalce_id());
        tripMain.setDescription(trip.getDescription());
        new getPlaceLatLon(this).execute(trip.getPalce_id());
    }
}
