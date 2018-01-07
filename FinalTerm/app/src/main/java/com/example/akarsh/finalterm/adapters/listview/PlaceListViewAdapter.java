package com.example.akarsh.finalterm.adapters.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akarsh.finalterm.R;
import com.example.akarsh.finalterm.model.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class PlaceListViewAdapter extends ArrayAdapter<Place> {

    private Context mContext;
    private List<Place> placeList;
    private  int rowResId;


    public PlaceListViewAdapter(Context context, int resource, List<Place> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.rowResId = resource;
        this.placeList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.rowResId,parent,false);

        }
        Log.d("Demo","Inside ConvertView");
        Place place = placeList.get(position);
        String description = place.getDescription();
        ((TextView)convertView.findViewById(R.id.textViewPlace)).setText(description);
        return convertView;
    }
}