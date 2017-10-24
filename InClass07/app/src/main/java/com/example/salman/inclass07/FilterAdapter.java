package com.example.salman.inclass07;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterRecyclerViewHolder> {
    ArrayList<Music> mData;
    Context mContext;
    IFilterAdapter iFilterAdapter;

    public FilterAdapter(ArrayList<Music> mData, Context mContext, IFilterAdapter iFilterAdapter) {
        this.mData = mData;
        this.mContext = mContext;
        this.iFilterAdapter = iFilterAdapter;
    }

    @Override
    public FilterRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_item, parent, false);
        FilterRecyclerViewHolder viewHolder = new FilterRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilterRecyclerViewHolder holder, int position) {
        final Music music = mData.get(position);
        holder.musicName.setText(music.getName());
        holder.musicPrice.setText(music.getPrice());

        Float price = Float.parseFloat(music.getPrice().substring(1,music.getPrice().length()));

        if(price>=0 && price<1.99f){
            holder.priceIcon.setImageResource(R.drawable.price_low);
        }
        else if(price>=2f && price<5.99f){
            holder.priceIcon.setImageResource(R.drawable.price_medium);
        }
        else{
            holder.priceIcon.setImageResource(R.drawable.price_high);

        }
        holder.itemView.findViewById(R.id.trash_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iFilterAdapter.removeData(music);
                iFilterAdapter.refreshBelowList();
            }
        });

        Picasso.with(mContext).load(music.getBigIcon()).into(holder.bigThumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FilterRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView musicName, musicPrice;
        ImageView bigThumbnail, priceIcon;
        Music music;

        public FilterRecyclerViewHolder(View itemView){
            super(itemView);
            musicName = (TextView) itemView.findViewById(R.id.filter_nameTexView);
            musicPrice = (TextView) itemView.findViewById(R.id.filter_priceTextView);
            bigThumbnail = (ImageView) itemView.findViewById(R.id.filterBig_imageView);
            priceIcon = (ImageView) itemView.findViewById(R.id.filter_priceImage);

        }
    }

    interface IFilterAdapter
    {
        void removeData(Music note);
        void refreshBelowList();

    }
}
