package com.example.sarveshrawat.finalterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;


public class TripAdapter extends ExpandableRecyclerAdapter<Trip, Places, TripViewHolder, PlacesViewHolder>  {

    private LayoutInflater mInflater;
    Context context;

    public TripAdapter(Context context, @NonNull List<Trip> recipeList) {
        super(recipeList);
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TripViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = mInflater.inflate(R.layout.parentrow, parentViewGroup, false);
        return new TripViewHolder(recipeView, context);
    }

    @Override
    public PlacesViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView = mInflater.inflate(R.layout.childrow, childViewGroup, false);
        return new PlacesViewHolder(ingredientView, context);
    }

    @Override
    public void onBindParentViewHolder(@NonNull TripViewHolder recipeViewHolder, int parentPosition, @NonNull Trip recipe) {
        recipeViewHolder.bind(recipe);
    }

    @Override
    public void onBindChildViewHolder(@NonNull PlacesViewHolder ingredientViewHolder, int parentPosition, int childPosition, @NonNull Places ingredient) {
        ingredientViewHolder.bind(ingredient);
    }
}
