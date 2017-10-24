package com.example.salman.inclass07;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.salman.inclass07.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetMusicTrakAsyncTask.IData, MusicAdapter.IMusicAdapter, FilterAdapter.IFilterAdapter{

    private ActivityMainBinding binding;
    final static String BASE_URL = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    MusicAdapter musicListAdapter;
    LinearLayout linearLayout;

    boolean isBelowListEmpty;

    LinearLayoutManager mLayoutManager;
    DatabaseDataManager dm;

    FilterAdapter filterMusicListAdapter;


    LinearLayoutManager layoutManager;
    //musicList1
    List<Music> musicList1 = new ArrayList<>();
    //musicList2
    List<Music> musicList2;

    Boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dm = new DatabaseDataManager(this);
        switchState = true;
        isBelowListEmpty = true;

        musicList2 = fetchdata();
        if(musicList2.size()>0){
            isBelowListEmpty=false;
        }
        new GetMusicTrakAsyncTask(MainActivity.this).execute(BASE_URL);
        //        holder.status.setText("Ascending");
        manageSwitch();

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){
                    sortDescending();
                    setAdapterAndNotify((ArrayList<Music>) musicList1);
                    setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
                }
                else{
//                    sortAscending();
                    setAdapterAndNotify((ArrayList<Music>) musicList1);
                    setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
                }
            }
        });
    }

    @Override
    public void updateData(ArrayList<Music> musicTracks) {
        musicList1 = musicTracks;
        initialLoad();
    }

    @Override
    public void enableView() {

    }

    @Override
    public void refreshList(String asc) {

    }

    List<Music> fetchdata(){
        musicList2 = new ArrayList<>();
        musicList2 = dm.getAll();
        return musicList2;
    }

//    @Override
//    public void refreshList(String asc) {
//
//        if(asc.equals("ascending")){
//            Collections.sort(musicList1, Music.MusicComparatorAsc);
//        }
//        else
//        {
//            Collections.sort(musicList1, Music.MusicComparatorDesc);
//
//        }
//        setAdapterAndNotify((ArrayList<Music>) musicList1);
//    }


    @Override
    public void removeDataFromFirstList(Music music) {

        musicList1.remove(music);
        dm.save(music);
        musicList2 = fetchdata();
//        if(binding.myRecyclerView.getVisibility()==View.GONE){
//            linearLayout.setVisibility(View.GONE);
//            binding.myRecyclerView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void refreshUpperList() {
        sortAscending();
        setAdapterAndNotify((ArrayList<Music>) musicList1);
        setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
    }

    @Override
    public boolean getSwitchedState() {
        return switchState;
    }

    @Override
    public void manageSwitch() {
        binding.switch1.setChecked(getSwitchedState());
    }

    @Override
    public void setSwitchedState(boolean set) {
        switchState = set;
    }

    void sortDescending()
    {
        Collections.sort(musicList1, Music.MusicComparatorDesc);
        Collections.sort(musicList2, Music.MusicComparatorDesc);
    }

    void sortAscending(){
        Collections.sort(musicList1, Music.MusicComparatorAsc);
        Collections.sort(musicList2, Music.MusicComparatorAsc);
    }

    void setAdapterAndNotify(ArrayList<Music> musicList){

//        Collections.sort(musicList, Music.MusicComparatorDesc);
        musicListAdapter = new MusicAdapter((ArrayList<Music>) musicList, MainActivity.this, MainActivity.this);
        binding.recyclerViewMusic.setAdapter(musicListAdapter);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewMusic.setLayoutManager(layoutManager);
        musicListAdapter.notifyDataSetChanged();
    }

    void initialLoad(){
        if(isBelowListEmpty){
            Collections.sort(musicList1, Music.MusicComparatorAsc);
            setAdapterAndNotify((ArrayList<Music>) musicList1);
//            hideViews();
//            linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            updateLists();
            sortAscending();
            setAdapterAndNotify((ArrayList<Music>) musicList1);
            setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);

        }
    }

    void hideViews(){
        binding.myRecyclerView.setVisibility(View.GONE);

//        <LinearLayout
//        android:layout_width="match_parent"
//        android:layout_height="match_parent">
//
//            <TextView
//        android:id="@+id/abc"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:layout_gravity="center"
//        android:text="@string/filter"
//        android:textColor="@android:color/black" />
//        </LinearLayout>
//        linearLayout = new LinearLayout(this);
//        linearLayout.setVisibility(View.VISIBLE);
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        linearLayout.setId(20);
//        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lparams.gravity= Gravity.CENTER;
//
//        TextView tv=new TextView(this);
//        tv.setLayoutParams(lparams);
//        tv.setId(0);
//        tv.setText(R.string.noFiltered);
//        linearLayout.addView(tv);
//        setContentView(linearLayout);
    }
    void setAdapterAndNotifySecondRecyler(ArrayList<Music> musicList){
        Collections.sort(musicList, Music.MusicComparatorAsc);

        filterMusicListAdapter = new FilterAdapter(musicList, MainActivity.this, MainActivity.this);
            binding.myRecyclerView.setAdapter(filterMusicListAdapter);
            mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            binding.myRecyclerView.setLayoutManager(mLayoutManager);
            filterMusicListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeData(Music note) {
        dm.delete(note);

        musicList2 = fetchdata();
        if(musicList2.size()==0){
            isBelowListEmpty=true;
//            hideViews();
        }
        musicList1.add(note);
    }

    @Override
    public void refreshBelowList() {
        sortAscending();
        setAdapterAndNotify((ArrayList<Music>) musicList1);
        setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }

    void updateLists(){
        if(musicList2.size()>0){
            isBelowListEmpty = false;
            for(int i=0;i<=musicList2.size()-1;i++){
                Music musicTemp = musicList2.get(i);
                if(musicList1.contains(musicTemp)){
                    musicList1.remove(musicTemp);
                }
            }
        }
    }
}
