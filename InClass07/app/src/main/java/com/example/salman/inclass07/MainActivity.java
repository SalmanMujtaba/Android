//Salman Mujtaba 800969897
//Prerana Singh
//Ryan Mcpeck
//InClass07
//Group09

package com.example.salman.inclass07;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;


import com.example.salman.inclass07.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetMusicTrakAsyncTask.IData, MusicAdapter.IMusicAdapter, FilterAdapter.IFilterAdapter{

    private ActivityMainBinding binding;
    final static String BASE_URL = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    MusicAdapter musicListAdapter;
    boolean isBelowListEmpty;
    ProgressBar progressBar;
    LinearLayoutManager mLayoutManager;
    DatabaseDataManager dm;
    FilterAdapter filterMusicListAdapter;
    LinearLayoutManager layoutManager;
    List<Music> musicList1 = new ArrayList<>();
    List<Music> musicList2;
    Boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dm = new DatabaseDataManager(this);
        switchState = true;
        isBelowListEmpty = true;

        hideViews();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        musicList2 = fetchdata();
        if(musicList2.size()>0){
            isBelowListEmpty=false;
        }

        new GetMusicTrakAsyncTask(MainActivity.this).execute(BASE_URL);
        manageSwitch();
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    refreshList("ascending");
                }
                else{
                    refreshList("descending");
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
        //PROGRESS BAR
        binding.relativeLayout.setVisibility(View.GONE);

        binding.recyclerViewMusic.setVisibility(View.VISIBLE);
        binding.layoutAbove.setVisibility(View.VISIBLE);
        binding.textViewFiltered.setVisibility(View.VISIBLE);
        if(isBelowListEmpty){
            binding.textViewEmptyMessage.setVisibility(View.VISIBLE);
            binding.myRecyclerView.setVisibility(View.GONE);
            binding.textViewEmptyMessage.setText(R.string.empty);
        }
        else{
            binding.myRecyclerView.setVisibility(View.VISIBLE);
            binding.textViewEmptyMessage.setVisibility(View.GONE);
        }
    }

    public void refreshList(String order) {
        if(order.equals("ascending")){
            binding.switch1.setText(R.string.ascending);
            setSwitchedState(true);
            sortDescending();
        }
        else {
            binding.switch1.setText(R.string.descending);
            setSwitchedState(false);
            sortAscending();
        }
        setAdapterAndNotify((ArrayList<Music>) musicList1);
        setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
    }

    List<Music> fetchdata(){
        musicList2 = new ArrayList<>();
        musicList2 = dm.getAll();
        return musicList2;
    }

    @Override
    public void removeDataFromFirstList(Music music) {
        musicList1.remove(music);
        dm.save(music);
        musicList2 = fetchdata();
        if(isBelowListEmpty){
            isBelowListEmpty=false;
            binding.myRecyclerView.setVisibility(View.VISIBLE);
            binding.textViewEmptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshUpperList() {
        if(getSwitchedState()){
            sortDescending();
        }
        else{
            sortAscending();
        }
        setAdapterAndNotify((ArrayList<Music>) musicList1);
        setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
    }

    public boolean getSwitchedState() {
        return switchState;
    }

    public void manageSwitch() {
        binding.switch1.setText(R.string.ascending);
        binding.switch1.setChecked(getSwitchedState());
    }

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
        musicListAdapter = new MusicAdapter(musicList, MainActivity.this, MainActivity.this);
        binding.recyclerViewMusic.setAdapter(musicListAdapter);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewMusic.setLayoutManager(layoutManager);
        musicListAdapter.notifyDataSetChanged();
    }

    void initialLoad(){
        if(isBelowListEmpty){
            if(getSwitchedState()){
                Collections.sort(musicList1, Music.MusicComparatorDesc);
            }
            else{
                Collections.sort(musicList1, Music.MusicComparatorDesc);
            }
            setAdapterAndNotify((ArrayList<Music>) musicList1);
        }
        else{
            updateLists();

            if(getSwitchedState()){
                sortDescending();
            }
            else{
                sortAscending();
            }
            setAdapterAndNotify((ArrayList<Music>) musicList1);
            setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
        }
    }

    public void hideViews(){
        binding.myRecyclerView.setVisibility(View.INVISIBLE);
        binding.layoutAbove.setVisibility(View.INVISIBLE);
        binding.textViewFiltered.setVisibility(View.INVISIBLE);
        binding.recyclerViewMusic.setVisibility(View.INVISIBLE);
        binding.relativeLayout.setVisibility(View.VISIBLE);
    }

    void setAdapterAndNotifySecondRecyler(ArrayList<Music> musicList){
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
            binding.textViewEmptyMessage.setVisibility(View.VISIBLE);
            binding.myRecyclerView.setVisibility(View.GONE);
            binding.textViewEmptyMessage.setText(R.string.empty);
        }
        musicList1.add(note);
    }

    @Override
    public void refreshBelowList() {
        if(getSwitchedState()){
            sortDescending();
        }
        else{
            sortAscending();
        }
        setAdapterAndNotify((ArrayList<Music>) musicList1);
        setAdapterAndNotifySecondRecyler((ArrayList<Music>) musicList2);
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }

    void belowListEmpty(){
        if(isBelowListEmpty){

        }
        else{

        }
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

    public void fetchFromAPI(View view) {
        new GetMusicTrakAsyncTask(MainActivity.this).execute(BASE_URL);
    }
}
