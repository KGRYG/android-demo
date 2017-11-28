package com.karen.timestabledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.myListView);

        final SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(20);
        seekBar.setProgress(10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTable;

                if (progress < min) {
                    timesTable = min;
                    seekBar.setProgress(min);
                } else {
                    timesTable = progress;
                }

                generateTimesTable(timesTable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateTimesTable(10);

    }

    private void generateTimesTable(int timesTable) {

        List<String> timesList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            timesList.add(i* timesTable + "");
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, timesList);
        listView.setAdapter(adapter);
    }
}
