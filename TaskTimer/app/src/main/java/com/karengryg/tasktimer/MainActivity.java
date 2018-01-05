package com.karengryg.tasktimer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//
//        String[] projection = {
//                Columns._ID,
//                Columns.TASKS_NAME,
//                Columns.TASKS_DESCRIPTION,
//                Columns.TASKS_SORTORDER
//        };
//
//        ContentResolver contentResolver = getContentResolver();
//
//        ContentValues values = new ContentValues();
//
//        values.put(Columns.TASKS_NAME, "Content provider");
//        values.put(Columns.TASKS_DESCRIPTION, "Record content provider video");
//        int count = contentResolver.update(TasksContract.buildTaskUri(1), values, null, null);

//        values.put(Columns.TASKS_NAME, "New Task 1");
//        values.put(Columns.TASKS_DESCRIPTION, "Description 1");
//        values.put(Columns.TASKS_SORTORDER, 2);
//        Uri uri = contentResolver.insert(TasksContract.CONTENT_URI, values);




//        Cursor cursor = contentResolver.query(TasksContract.CONTENT_URI, projection, null, null, Columns.TASKS_NAME);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    Log.d(TAG, "onCreate:  " + cursor.getColumnName(i) + " : " + cursor.getString(i));
//                }
//            }
//            cursor.close();
//        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
