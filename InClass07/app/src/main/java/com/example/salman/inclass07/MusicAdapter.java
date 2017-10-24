package com.example.salman.inclass07;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicRecyclerViewHolder> {

    ArrayList<Music> MusicArrayList;
    Context context;
    IMusicAdapter iMusicAdapter;
    List<Music> favouriteList= new ArrayList<>();
//    boolean switchState = false;

    public MusicAdapter(ArrayList<Music> MusicArrayList, Context context, IMusicAdapter iMusicAdapter) {
        this.MusicArrayList = MusicArrayList;
        this.context = context;
        this.iMusicAdapter=iMusicAdapter;
    }

    @Override
    public MusicRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_row,parent,false);
        MusicRecyclerViewHolder musicRecyclerViewHolder = new MusicRecyclerViewHolder(view);
        return musicRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final MusicRecyclerViewHolder holder, final int position) {
        final Music music = MusicArrayList.get(position);
        if(!music.getIcon().isEmpty()){
            Picasso.with(context).load(music.getIcon()).into(holder.imageViewIcon);
        }

        holder.textViewName.setText(music.getName());
        holder.textViewPrice.setText(music.getPrice());

        Picasso.with(context).load(music.getIcon()).into(holder.imageViewIcon);


        iMusicAdapter.manageSwitch();

        Float price = Float.parseFloat(music.getPrice().substring(1,music.getPrice().length()));

        if(price>=0 && price<1.99f){
            holder.imgViewDollar.setImageResource(R.drawable.price_low);
        }
        else if(price>=2f && price<5.99f){
            holder.imgViewDollar.setImageResource(R.drawable.price_medium);
        }
        else{
            holder.imgViewDollar.setImageResource(R.drawable.price_high);

        }

////        holder.status.setText("Ascending");
//
//        holder.sw.setChecked(iMusicAdapter.getSwitchedState());
//
//        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked){
//                    iMusicAdapter.setSwitchedState(true);
//                    iMusicAdapter.refreshList("asc");
//                    //
//                }
//                else{
////                    holder.status.setText("Descending");
//                    iMusicAdapter.setSwitchedState(false);
//
//                    iMusicAdapter.refreshList("desc");
//                }
//            }
//
//
//        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
              iMusicAdapter.removeDataFromFirstList(music);
              iMusicAdapter.refreshUpperList();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return MusicArrayList.size();
    }


    public static  class MusicRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewIcon;
        TextView textViewName;
        ImageView imgViewDollar;
        TextView textViewPrice;


        public MusicRecyclerViewHolder(View itemView) {
            super(itemView);

            imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            imgViewDollar = (ImageView) itemView.findViewById(R.id.imageViewDollar);
            textViewName = (TextView) itemView.findViewById(R.id.textViewTitle);
        }
    }

    interface IMusicAdapter
    {
        void refreshList(String asc);
        void removeDataFromFirstList(Music music);
        void refreshUpperList();
        boolean getSwitchedState();
        void manageSwitch();
        void  setSwitchedState(boolean set);

    }
}
