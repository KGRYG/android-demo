package com.karen.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.karen.sharedpreferences", Context.MODE_PRIVATE);

        List<String> friends = new ArrayList<>(Arrays.asList("Monica", "Chandler"));
        try {
            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize((Serializable) friends)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> newFriendList = new ArrayList<>();
        try {
            newFriendList = (List<String>) ObjectSerializer.deserialize(
                    sharedPreferences.getString("friends",
                            ObjectSerializer.serialize(new ArrayList<String>()))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("newfriends", newFriendList.toString());

    }
}
