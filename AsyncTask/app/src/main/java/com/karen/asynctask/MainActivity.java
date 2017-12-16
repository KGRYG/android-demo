package com.karen.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: starting AsyncTask");

        DownloaderTask downloaderTask = new DownloaderTask();
        downloaderTask.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

        Log.i(TAG, "onCreate: done");

    }

    public class DownloaderTask extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloaderTask";

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Log.i(TAG, s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: starts with :" + strings[0]);
            String rssFeed = donwnloadXml(strings[0]);

            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }

            return rssFeed;
        }
    }

    private String donwnloadXml(String string) {
        StringBuilder xmlResult = new StringBuilder();

        try {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int charsRead;
            char[] inputBuffer = new char[500];

            while (true) {
                charsRead = reader.read(inputBuffer);
                if (charsRead < 0) {
                    break;
                } else {
                    xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }
            }

            reader.close();
            return xmlResult.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
