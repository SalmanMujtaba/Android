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
import java.util.List;

public class AddTripAdapter extends RecyclerView.Adapter<AddTripAdapter.AddTripRecyclerViewHolder> {

    private ArrayList<Trip> tripList;

    private Context context;
    private IAddTripAdapter iAddTripAdapter;

    public AddTripAdapter(List<Trip> tripList, Context context, IAddTripAdapter iTripAdapter) {
        this.tripList = (ArrayList<Trip>) tripList;
        this.context = context;
        this.iAddTripAdapter = iTripAdapter;
    }

    @Override
    public AddTripAdapter.AddTripRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.addtriprow, parent, false);
        AddTripRecyclerViewHolder placesRecyclerViewHolder = new AddTripRecyclerViewHolder(view);
        return placesRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final AddTripAdapter.AddTripRecyclerViewHolder holder, final int position) {
        final Trip trip = tripList.get(position);
        String desc = trip.getDescription();
        holder.textViewTripName.setText(desc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAddTripAdapter.setEditPlaceCity(trip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class AddTripRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTripName;

        public AddTripRecyclerViewHolder(View itemView) {
            super(itemView);

            textViewTripName = itemView.findViewById(R.id.textViewTripName);
        }
    }

    interface IAddTripAdapter {
        void setEditPlaceCity(Trip trip);
    }
}