package com.example.sarveshrawat.finalterm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesRecyclerViewHolder> {

    private ArrayList<Places> placeList;

    private Context context;
    private IPlaceAdapter iPlaceAdapter;

    public PlacesAdapter(ArrayList<Places> tripist,Context context, IPlaceAdapter iPlaceAdapter) {
        this.placeList = tripist;
        this.context = context;
        this.iPlaceAdapter = iPlaceAdapter;
    }

    @Override
    public PlacesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.placesrow, parent, false);
        PlacesRecyclerViewHolder placesRecyclerViewHolder = new PlacesRecyclerViewHolder(view);
        return placesRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final PlacesRecyclerViewHolder holder, final int position) {
        final Places place = placeList.get(position);
       holder.textViewPlace.setText(place.getName());
       holder.imageButtonPlaceAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               iPlaceAdapter.addToTrip(place);
           }
       });
        Picasso.with(context).load(place.getIcon()).into(holder.imageViewIconPlace);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlacesRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView  imageViewIconPlace;
        private ImageButton imageButtonPlaceAdd;
        private TextView textViewPlace;

        public PlacesRecyclerViewHolder(View itemView) {
            super(itemView);

            imageViewIconPlace = itemView.findViewById(R.id.imageViewIconPlace);
            imageButtonPlaceAdd = itemView.findViewById(R.id.imageButtonPlaceAdd);
            textViewPlace =  itemView.findViewById(R.id.textViewPlace);
        }
    }

    interface IPlaceAdapter {
       void addToTrip(Places place);
    }
}

