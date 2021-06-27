package com.example.timerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer media;
    SeekBar seekBar;
    Button button;
    TextView text;
    Runnable runnable;
    Handler handler;
    int time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.textView);
        media = MediaPlayer.create(this,R.raw.coffee_peetey_peetey);
        seekBar.setMax(3600);
        onButtonStart();

    }

    private void onButtonStart(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(time>0) {
                    time--;
                    progressSeekBar(time);
                    handler.postDelayed(this,1000);
                }
                else if(time==0){
                    button.setText("START");
                    seekBar.setEnabled(true);
                    play();
                }
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressSeekBar(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onClick(View view){
        if(seekBar.isEnabled()) {
            if(media.isPlaying()) {
                stop();
            }
            time = seekBar.getProgress();
            runnable.run();
            seekBar.setEnabled(false);
            button.setText("STOP");
        }
        else {
            button.setText("START");
            seekBar.setEnabled(true);
            stop();
            seekBar.setProgress(time);
            time = -1;
        }
    }

    private void play(){

        media.start();

    }

    private void stop(){

        media.stop();
        media = MediaPlayer.create(this,R.raw.coffee_peetey_peetey);

    }

    private void progressSeekBar(int i){
        String print = checkLength(i/60) + ":" + checkLength(i%60);
        text.setText(print);
    }

    private String checkLength(int i){
        String len = Integer.toString(i);
        if(len.length() == 1){
            len = "0"+len;
        }
        return len;
    }

}