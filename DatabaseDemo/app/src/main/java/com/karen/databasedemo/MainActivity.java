package com.karen.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor c = null;
        try {
            SQLiteDatabase myDataBase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            myDataBase.delete("users", null, null);
            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS newUsers1 (name VARCHAR(255), age INTEGER (3), id INTEGER PRIMARY KEY)");

            myDataBase.execSQL("INSERT INTO newUsers1(name, age) VALUES ('Arianna', 3)");
            myDataBase.execSQL("INSERT INTO newUsers1(name, age) VALUES ('Olga', 30)");

            c = myDataBase.rawQuery("SELECT * FROM newUsers1", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();

            while (c != null) {
                Log.i("Name: ", c.getString(nameIndex));
                Log.i("Age: ", c.getString(ageIndex));
                Log.i("Id: ", c.getString(idIndex));
                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }



//        try {
//
//            SQLiteDatabase events = this.openOrCreateDatabase("Events", MODE_PRIVATE, null);
//            events.execSQL("CREATE TABLE IF NOT EXISTS events (event VARCHAR(255), year INT(4))");
//            events.execSQL("INSERT INTO events(event, year) VALUES ('End of WW2', 1945)");
//            events.execSQL("INSERT INTO events(event, year) VALUES ('Start of WW2', 1941)");
//
//            c = events.rawQuery("SELECT * FROM events", null);
//            int eventIndex = c.getColumnIndex("event");
//            int yearIndex = c.getColumnIndex("year");
//
//            c.moveToFirst();
//
//            while (c != null) {
//                Log.i("Event: ", c.getString(eventIndex));
//                Log.i("Year: ", c.getString(yearIndex));
//                c.moveToNext();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }


    }
}
