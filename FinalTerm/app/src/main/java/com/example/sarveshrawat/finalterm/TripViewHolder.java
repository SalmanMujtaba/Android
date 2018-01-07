package com.example.sarveshrawat.finalterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

public class TripViewHolder extends ParentViewHolder {
    private TextView tripname;
    private TextView description;
    private ImageButton map;
    private ImageButton add;
    MainActivity mainActivity;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     * @param context

     */

    public TripViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mainActivity = (MainActivity) context;
        tripname = itemView.findViewById(R.id.textViewTripName);
        description = itemView.findViewById(R.id.textViewDescription);
        map = itemView.findViewById(R.id.imageButtonMap);
        add = itemView.findViewById(R.id.imageButtonAdd);
    }

    public void bind(final Trip trip) {
        tripname.setText(trip.getName());
        description.setText(trip.getDescription());
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mainActivity.sendToMap(trip);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mainActivity.sendControlToPlaces(trip);
            }
        });
    }
}
