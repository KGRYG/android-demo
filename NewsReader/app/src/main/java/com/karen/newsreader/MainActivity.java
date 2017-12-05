package com.karen.newsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> titles;
    ArrayAdapter arrayAdapter;
    String result;
    private static final int NUMBER_OF_NEWS = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        DownloadTask task = new DownloadTask();

        try {
            result = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        listView = findViewById(R.id.listView);
        titles = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();
            StringBuilder articles = new StringBuilder();
            BufferedReader in = null;
            try {
                URL url = new URL(urls[0]);
                in = new BufferedReader(new InputStreamReader(url.openStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }

                JSONArray jsonArray = new JSONArray(result.toString());


                int numberOfItems = NUMBER_OF_NEWS;

                if (jsonArray.length() < numberOfItems) {
                    numberOfItems = jsonArray.length();
                }


                for (int i = 0; i < numberOfItems; i++) {
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + jsonArray.get(i) + ".json?print=pretty");
                    in = new BufferedReader(new InputStreamReader(url.openStream()));

                    String articleInfo;

                    while ((articleInfo = in.readLine()) != null) {
                        articles.append(articleInfo);
                    }

                    JSONObject jsonObject = new JSONObject(articles.toString());

                    String articleTitle = jsonObject.getString("title");
                    String articleURL = jsonObject.getString("url");

                    Log.i("Info: ", articleTitle + " " + articleURL);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            try {
//
//                String message = "";
//
//                JSONObject jsonObject = new JSONObject(result);
//
//                String weatherInfo = jsonObject.getString("weather");
//
//                JSONArray arr = new JSONArray(weatherInfo);
//
//                for (int i = 0; i < arr.length(); i++) {
//
//                    JSONObject jsonPart = arr.getJSONObject(i);
//
//                    String main = "";
//                    String description = "";
//
//                    main = jsonPart.getString("main");
//                    description = jsonPart.getString("description");
//
//                    if (main != "" && description != "") {
//
//                        message += main + ": " + description + "\r\n";
//
//                    }
//
//                }
//
//                if (message != "") {
//
//                    resultTextView.setText(message);
//
//                } else {
//
//                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
//
//                }
//
//
//            } catch (JSONException e) {
//
//                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
//
//            }
//
//
//
//        }
    }
}
