package com.example.akarsh.finalterm.adapters.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akarsh.finalterm.R;
import com.example.akarsh.finalterm.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<ItemRecycleViewAdapter.ViewHolder> {

    private ArrayList<Item> items;
    private int layoutId;
    private LayoutInflater inflater;
    private Context mContext;

    //TODO: Remove this is OnClickITemListener is not required
    private RecyclerOnItemClickListener mItemClickListener;
    //Method to initialize ItemCLickListner
    public void SetOnItemClickListener(final RecyclerOnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /*
    TODO: Change RVitem to required class
     */
    public ItemRecycleViewAdapter(Context mContext, ArrayList<Item> items, int layoutId) {
        this.items= items;
        this.layoutId=layoutId;
        inflater=LayoutInflater.from(mContext);
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: Change .activity_main to appropriate
        View contactView = inflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);

        TextView name = holder.textViewName;
        TextView price =holder.textViewPrice;
        ImageView rating = holder.imageViewRating;
        ImageView delete= holder.imageViewDelete;

       /* name.setText(item.getName());
        price.setText("Price USD:"+item.getPrice());
        Picasso.with(mContext).load(item.getImageSrcLarge()).centerCrop().resize(300,300).into(rating);
*/

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewPrice;
        ImageView imageViewRating;
        ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);

           /* textViewName = (TextView) itemView.findViewById(R.id.textViewNameRV);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPriceRV);
            imageViewRating = (ImageView) itemView.findViewById(R.id.imageViewRAtingsRV);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDeleteRV);*/

        }
    }
}