package com.example.salman.inclass08;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.salman.inclass08.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

//    Fetch data from database
//    List<Music> fetchdata(){
//        musicList2 = new ArrayList<>();
//        musicList2 = dm.getAll();
//        return musicList2;
//    }
}
