package com.example.sarveshrawat.finalterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.squareup.picasso.Picasso;


public class PlacesViewHolder extends ChildViewHolder {

    ImageView imageViewIcon;
    TextView textViewName;
    ImageButton imageButtonDelete;
    Context context1;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     * @param context
     */
    public PlacesViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        context1 = context;
        textViewName = itemView.findViewById(R.id.textViewPlace);
        imageButtonDelete = itemView.findViewById(R.id.imageButtonPlaceAdd);
        imageViewIcon = itemView.findViewById(R.id.imageViewIconPlace);
    }

    public void bind(final Places place) {
        textViewName.setText(place.getName());
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context1;
                mainActivity.placesHolderDelete(place, getParentAdapterPosition(), getChildAdapterPosition());
            }
        });
        Picasso.with(context1).load(place.getIcon()).into(imageViewIcon);
    }

}
