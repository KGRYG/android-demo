package com.karen.eggtimmer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView timer;
    private MediaPlayer mediaPlayer;
    private Button button;
    private boolean counterIsActive;
    private CountDownTimer countDownTimer;


    public void startTimer(View view) {
        if (!counterIsActive) {

            counterIsActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();

                    resetCounter();
                }
            }.start();
        } else {
            resetCounter();
        }

    }

    private void resetCounter() {
        counterIsActive = false;
        timer.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("GO!");
        seekBar.setEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.goButton);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        timer = findViewById(R.id.timer);
        timer.setText("0:30");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mediaPlayer = MediaPlayer.create(this, R.raw.horn);
    }

    private void updateTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timer.setText(Integer.toString(minutes) + ":" + secondString);
    }


}
