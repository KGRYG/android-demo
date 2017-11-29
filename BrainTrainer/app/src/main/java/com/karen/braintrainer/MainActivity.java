package com.karen.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private TextView question;
    private TextView score;
    private TextView message;
    private TextView countdownText;
    private Button goButton;

    public void startGame(View view) {
        goButton.setVisibility(View.INVISIBLE);

        countDownTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        message = findViewById(R.id.messageText);
        goButton = findViewById(R.id.goButton);
        countdownText = findViewById(R.id.countdownText);
    }
}
