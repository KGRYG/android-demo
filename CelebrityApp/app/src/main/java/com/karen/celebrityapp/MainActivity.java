package com.karen.celebrityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    List<String> celebURLs = new ArrayList<>();
    List<String> celebNames = new ArrayList<>();
    int chosenCeleb = 0;
    ImageView celebView;
    int locationOfCorrectAnswer = 0;
    String [] answers = new String[4];
    GridLayout buttonGrid;

    public void celebChosen(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong! It was " + celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }

        try {
            generateCelebs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        celebView = findViewById(R.id.imageView);
        buttonGrid = findViewById(R.id.buttonGrid);

        DownloadTask task = new DownloadTask();
        String result;

        try {
            result = task.execute("http://posh24.se/kandisar").get();
            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebURLs.add(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

            generateCelebs();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void generateCelebs() throws InterruptedException, ExecutionException {
        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());

        ImageDownloader imageTask = new ImageDownloader();
        Bitmap celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();
        celebView.setImageBitmap(celebImage);

        locationOfCorrectAnswer = random.nextInt(4);

        int incorrectAnswerLocation;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers[i] = celebNames.get(chosenCeleb);
            } else {
                incorrectAnswerLocation = random.nextInt(celebURLs.size());

                while (incorrectAnswerLocation == chosenCeleb) {
                    incorrectAnswerLocation = random.nextInt(celebURLs.size());
                }

                answers[i] = celebNames.get(incorrectAnswerLocation);
            }
        }

        for (int i = 0; i < 4; i++) {
            Button button = (Button) buttonGrid.getChildAt(i);
            button.setText(answers[i]);
        }
    }

    public static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            BufferedReader in = null;
            try {
                URL url = new URL(urls[0]);
                in = new BufferedReader(new InputStreamReader(url.openStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }

                return result.toString();

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
    }

    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);

                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
