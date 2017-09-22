package com.example.salman.inclass04_02;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.salman.inclass04_02.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    Handler handler;
    SeekBar se1, se2, se3, se4;
    Button b1, b2;
    private ProgressDialog progressDialog;
    int times = 0;

    Intent intent;
    String [] passwordsThread;
    String [] passwordsAsync;
    int step = 1;
    int max = 23;
    int max2 = 10;
    int lengthThread = 0;
    int countThread = 0;
    int lengthAsync = 0;
    int countAsync = 0;
    int commonProgress;
    int min = 8;
    int min2 = 1;

    private ActivityMainBinding binding;
    ExecutorService threadPool;

    @Override
    public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
        switch (bar.getId()) {

            case R.id.seekBarCount:
                countThread = min2 + (progress * step);
                binding.textViewCount.setText(String.valueOf(countThread));
                break;

            case R.id.seekBarLen:
                lengthThread = min + (progress * step);
                binding.textViewLen.setText(String.valueOf(lengthThread));
                break;

            case R.id.seekBarCountAsync:

                countAsync = min2 + (progress * step);
                binding.textViewAsyncCount.setText(String.valueOf(countAsync));
                break;

            case R.id.seekBarLenAsync:
                lengthAsync = min + (progress * step);
                binding.textViewLengthAsync.setText(String.valueOf(lengthAsync));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    class AsyncCalculate extends AsyncTask<Integer, Integer, String[]>{

        protected String[] doInBackground(Integer... params) {
            int length = params[0];
            int count = params[1];
            String[] passwords = new String[count];

            for (int index = 0; index < count; index++){
                passwords[index] = Util.getPassword(length);
                commonProgress = commonProgress+1;
                publishProgress(commonProgress);
            }
            return passwords;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            if(commonProgress==(countAsync+countThread))
            {
                progressDialog.dismiss();
            }
            passwordsAsync = strings;
            showPasswordsAsync();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }

    class Inner implements Runnable{
        static final int start = 0x00;
        static final int exec = 0x03;
        static final int end = 0x02;
        Bundle b = new Bundle();
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = start;
            handler.sendMessage(msg);
            String[] pass= new String[countThread];

            for(int i = 0; i<countThread; i++){
                pass[i]= Util.getPassword(lengthThread);
                msg = new Message();
                msg.what=exec;
                commonProgress = commonProgress+1;

                msg.obj=(commonProgress);
                handler.sendMessage(msg);
            }
            msg = new Message();
            msg.what=end;
            b.putStringArray("passThread", pass);
            msg.setData(b);
            handler.sendMessage(msg);        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        commonProgress = 0;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Progress");
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                progressDialog.setProgress(0);
                commonProgress = 0;
                intent = new Intent(MainActivity.this, GeneratedPasswords.class);
                intent.putExtra("thread", passwordsThread);
                intent.putExtra("async", passwordsAsync);
                startActivity(intent);
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what){
                    case Inner.start:
                        progressDialog.show();
                        progressDialog.setProgress(0);
                        break;
                    case Inner.exec:
                        progressDialog.setProgress((int)msg.obj);
                        break;
                    case Inner.end:
                        if(commonProgress == (countAsync+countThread)){
                            progressDialog.dismiss();
                        }
                        passwordsThread=msg.getData().getStringArray("passThread");
                        showPasswordsThread();
                        break;
                }
                return false;
            }
        });

        threadPool = Executors.newFixedThreadPool(2);

        b1 = (Button)findViewById(R.id.buttonThread);
        b2 = (Button) findViewById(R.id.buttonAsync);

        se1 = (SeekBar) findViewById(R.id.seekBarCount);
        se2 = (SeekBar) findViewById(R.id.seekBarLen);
        se3 = (SeekBar) findViewById(R.id.seekBarCountAsync);
        se4 = (SeekBar) findViewById(R.id.seekBarLenAsync);

        se1.setOnSeekBarChangeListener(this);
        se2.setOnSeekBarChangeListener(this);
        se3.setOnSeekBarChangeListener(this);
        se4.setOnSeekBarChangeListener(this);

        se2.setMax( (max - min) / step );
        se3.setMax( (max2 - min2) / step );
        se1.setMax( (max2 - min2) / step );
        se4.setMax( (max - min) / step );


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMax(countAsync+countThread);
                threadPool.execute(new Inner());
                new AsyncCalculate().execute(lengthAsync, countAsync);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AsyncCalculate().execute(lengthAsync, countAsync);
            }
        });
    }

    private void showPasswordsAsync() {
        CharSequence[] psw = new CharSequence[passwordsAsync.length];
        for (int i = 0; i < passwordsAsync.length; i++) {
            psw[i] = passwordsAsync[i];
        }
    }

    private void showPasswordsThread() {
        CharSequence[] psw = new CharSequence[passwordsThread.length];
        for(int i =0;i<passwordsThread.length;i++){
            psw[i] = passwordsThread[i];
        }
    }


}