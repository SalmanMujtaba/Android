package com.example.salman.inclass04_02;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GeneratedPasswords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_passwords);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] thread = getIntent().getExtras().getStringArray("thread");
        String[] async = getIntent().getExtras().getStringArray("async");

        getSupportActionBar().setTitle("Generated Passwords");

        LinearLayout linearLayout1=(LinearLayout) findViewById(R.id.linearLayoutThread);
        LinearLayout linearLayout2=(LinearLayout) findViewById(R.id.linearLayoutAsyncTask);
        (findViewById(R.id.buttonFinish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for(int i=0;i<thread.length;i++){
            TextView textViewShowByThread=new TextView(this);
            textViewShowByThread.setText(thread[i]);
            linearLayout1.addView(textViewShowByThread);
        }
        for(int i=0;i< async.length;i++){
            TextView textViewShowByAsync=new TextView(this);
            textViewShowByAsync.setText(async[i]);
            linearLayout2.addView(textViewShowByAsync);
        }
    }
}
