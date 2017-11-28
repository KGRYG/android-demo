package com.karen.hideshowui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    public void toggleUI(View view) {
        String id = view.getResources().getResourceEntryName(view.getId());
        textView.setVisibility(id.equals("show") ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
    }
}
