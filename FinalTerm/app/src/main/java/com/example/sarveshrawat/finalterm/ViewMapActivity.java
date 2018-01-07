package com.example.sarveshrawat.finalterm;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_ID = 100;
    LocationManager location;
    List<Places> locationMap;
    Trip tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tr = (Trip)getIntent().getExtras().getSerializable("trip");
        locationMap = tr.getChildList();
        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setTitle("Trip Manager");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    PolylineOptions polyLineOptions = new PolylineOptions();
    Polyline polyline;
    List<Marker> marker = new ArrayList<>();
    LocationListener mLocListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!location.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertDialog();
        }
        else{

            LatLng currentLocation = new LatLng(tr.getLocation().get("lat"),tr.getLocation().get("lng"));
            marker.add(mMap.addMarker(new MarkerOptions().position(currentLocation).title(tr.getDescription())));
            Map<String,Double> temp=new LinkedHashMap<>();
            if(locationMap!=null){
                for(Places p:locationMap){
                    temp = p.getLocation();
                    marker.add(mMap.addMarker(new MarkerOptions().position(new LatLng(temp.get("lat"),temp.get("lng"))).title(p.getName())));
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            repositionCamera();
        }

    }
    public void repositionCamera(){
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(getBounds() , width, height, padding);
        mMap.animateCamera(cu);
    }

    public LatLngBounds getBounds(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker m : marker) {
            builder.include(m.getPosition());
        }
        return builder.build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_ID:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, mLocListener);
                    }
                    return;

                } else {
                    Toast.makeText(this,"Location services not enabled. App wont work. Enable in settings!",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    private void alertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Location service")
                .setMessage("Would you like to enable GPS now?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
