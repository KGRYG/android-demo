package com.karen.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private RelativeLayout relativeLayout;
    private TextView question;
    private TextView scoreView;
    private int score = 0;
    int numberOfQuestion = 0;
    private TextView message;
    private TextView countdownText;
    private Button goButton;
    private Button playAgainButton;
    private GridLayout buttonGrid;
    private final static int MIN = 1;
    private final static int MAX = 20;
    List<Integer> answerList = new ArrayList<>();
    int locationOfCorrectAnswer;

    public void playAgain(View view) {
        score = 0;
        numberOfQuestion = 0;
        countdownText.setText("0:30s");
        scoreView.setText("0/0");
        message.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        toggleEnableButtons(true);
        generateQuestions();
        beginCountDown();
    }

    private void toggleEnableButtons(boolean enabled) {
        for (int i = 0; i < 4; i++) {
            Button button = (Button) buttonGrid.getChildAt(i);
            button.setEnabled(enabled);
        }
    }


    public void startGame(View view) {
        goButton.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        generateQuestions();
        beginCountDown();

    }

    private void beginCountDown() {
        countDownTimer = new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String secondString = Integer.toString(seconds);

                if (seconds < 10) {
                    secondString = "0" + seconds;
                }
                countdownText.setText(secondString + "s");
            }

            @Override
            public void onFinish() {
                toggleEnableButtons(false);
                countdownText.setText("00s");
                message.setText("Your score: " + scoreView.getText());
                playAgainButton.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void buttonTapped(View view) {

        if (view.getTag().toString().equals(""+locationOfCorrectAnswer)) {
            score++;
            message.setText("Correct!");
        } else {
            message.setText("Wrong!");
        }

        numberOfQuestion++;
        scoreView.setText(numberOfQuestion + "/" + score);
        generateQuestions();

    }

    public void generateQuestions () {
        int randomNum1 = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        int randomNum2 = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        int correctAnswer = randomNum1 + randomNum2;
        question.setText(randomNum1 + " + " + randomNum2);

        locationOfCorrectAnswer = ThreadLocalRandom.current().nextInt(0, 4);

        answerList.clear();
        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answerList.add(correctAnswer);
            } else {

                incorrectAnswer = ThreadLocalRandom.current().nextInt(MIN, 41);

                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = ThreadLocalRandom.current().nextInt(MIN, 41);
                }

                answerList.add(incorrectAnswer);
            }
        }

        for (int i = 0; i < 4; i++) {
            Button button = (Button) buttonGrid.getChildAt(i);
            button.setText(answerList.get(i)+"");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        question = findViewById(R.id.question);
        scoreView = findViewById(R.id.score);
        message = findViewById(R.id.messageText);
        goButton = findViewById(R.id.goButton);
        countdownText = findViewById(R.id.countdownText);
        buttonGrid = findViewById(R.id.answers);
        playAgainButton = findViewById(R.id.playAgainId);
        relativeLayout = findViewById(R.id.nestedRelLayout);

    }
}
