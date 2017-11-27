package com.karen.listviewfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.friendsListView);

        final List<String> friendList = new ArrayList<>(Arrays.asList("Olga", "Dima", "Kopa", "Kolian", "Andrei"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friendList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), friendList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
