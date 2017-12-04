package com.karen.memorableplacesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    static List<String> places = new ArrayList<>();
    static List<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.karen.memorableplacesapp", Context.MODE_PRIVATE);

        List<String> lats = new ArrayList<>();
        List<String> longs = new ArrayList<>();

        places.clear();
        lats.clear();
        longs.clear();
        locations.clear();

        try {
            places = (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String >())));
            lats = (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String >())));
            longs = (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String >())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!places.isEmpty() && !lats.isEmpty() && !longs.isEmpty()) {
            if (places.size() == lats.size() && lats.size() == longs.size()) {
                for (int i = 0; i < lats.size(); i++) {
                    locations.add(new LatLng(
                                    Double.parseDouble(lats.get(i)),
                                    Double.parseDouble(longs.get(i))
                            )
                    );
                }
            }
        } else {
            places.add("Add a new place...");
            locations.add(new LatLng(0,0));
        }

        arrayAdapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);

            }
        });
    }
}
