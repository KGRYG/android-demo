package com.karen.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.myListView);

        final List<String> myFamily = new ArrayList<>();
        myFamily.add("Karen");
        myFamily.add("Olga");
        myFamily.add("Ashot");
        myFamily.add("Arianna");
        myFamily.add("Gaya");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFamily);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Person name: " , myFamily.get(position));
            }
        });
    }
}
