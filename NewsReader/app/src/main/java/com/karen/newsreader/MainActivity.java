package com.karen.newsreader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> titles;
    List<String> content;
    ArrayAdapter arrayAdapter;
    String result;
    private static final int NUMBER_OF_NEWS = 20;
    RestTemplate restTemplate;
    SQLiteDatabase articlesDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        updateListView();
        DownloadTask task = new DownloadTask();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra("content", content.get(position));
                startActivity(intent);
            }
        });

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
        content = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);

        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY AUTOINCREMENT, articleId INTEGER, title VARCHAR, content VARCHAR)");
    }

    public void updateListView() {
        Cursor c = articlesDB.rawQuery("SELECT * FROM articles", null);

        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");

        if (c.moveToFirst()) {
            titles.clear();
            content.clear();

            do {
                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));


            } while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            List<Integer> topArticleIds = restTemplate.getForObject(urls[0], List.class);

            articlesDB.execSQL("DELETE FROM articles");

            for (int i = 0; i < NUMBER_OF_NEWS; i++) {
                Article article = restTemplate.getForObject(
                        "https://hacker-news.firebaseio.com/v0/item/"+ topArticleIds.get(i) + ".json?print=pretty",
                        Article.class
                );

                if (article.getUrl() != null) {
                    String contentLocal = restTemplate.getForObject(article.getUrl(), String.class);

                    ContentValues insertValues = new ContentValues();
                    insertValues.put("articleId", article.getArticleId());
                    insertValues.put("title", article.getTitle());
                    insertValues.put("content", contentLocal);

                    articlesDB.insert("articles", null, insertValues);
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateListView();

        }
    }
}
