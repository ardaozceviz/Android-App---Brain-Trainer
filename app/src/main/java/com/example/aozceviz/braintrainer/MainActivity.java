package com.example.aozceviz.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout gameLayout;
    LinearLayout gameOverLayout;

    Button startButton;
    Button button0, button1, button2, button3, playAgainButton;

    TextView sumTextView;
    TextView resultTextView;
    TextView scoreTextView;
    TextView timeTextView;
    TextView gameOverScoreTextView;

    Enumeration answers;
    String answerKey;
    Hashtable<String, String> answersHashTable = new Hashtable<String, String>();

    int[] printButton = new int[4];
    int printButtonCounter = 0;
    int locationOfCorrectAnswer = 0;
    int incorrectAnswer = 0;
    int score = 0;
    int numberOfQuestions = 0;

    public void generateQuestion () {

        answersHashTable.clear();
        printButtonCounter = 0;

        for (int x = 0; x < 4; x++){
            printButton[x] = 0;
        }

        Random random = new Random();

        int a = random.nextInt(21); // Between 0 - 20
        int b = random.nextInt(21);
        locationOfCorrectAnswer = random.nextInt(4); // Between 0 - 3

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answersHashTable.put(String.valueOf(a+b), "correct");
            } else {
                incorrectAnswer = random.nextInt(41);
                if (incorrectAnswer != a + b){
                    answersHashTable.put(String.valueOf(incorrectAnswer), "incorrect");
                } else {
                    i--;
                }
            }
        }

        answers = answersHashTable.keys();

        while(answers.hasMoreElements()) {
            answerKey = (String) answers.nextElement();
            printButton[printButtonCounter] = Integer.parseInt(answerKey);
            printButtonCounter++;
            //System.out.println("Key: " +answerKey+ " & Value: " + answers.get(answerKey));
        }

        button0.setText(Integer.toString(printButton[0]));
        button1.setText(Integer.toString(printButton[1]));
        button2.setText(Integer.toString(printButton[2]));
        button3.setText(Integer.toString(printButton[3]));

    }

    public void playAgain (View view) {
        score = 0;
        numberOfQuestions = 0;
        locationOfCorrectAnswer = 0;
        incorrectAnswer = 0;

        timeTextView.setText("30s");
        scoreTextView.setText("0 / 0");
        gameOverScoreTextView.setText("");
        resultTextView.setText("");

        gameOverLayout.setVisibility(LinearLayout.INVISIBLE);
        gameLayout.setVisibility(ConstraintLayout.VISIBLE);

        generateQuestion();

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //timeTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                timeTextView.setText(String.format("%02ds", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timeTextView.setText("0s");
                gameOverScoreTextView.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
                gameLayout.setVisibility(ConstraintLayout.INVISIBLE);
                gameOverLayout.setVisibility(LinearLayout.VISIBLE);
            }
        }.start();
    }

    public void chooseAnswer (View view) {
        Log.i("Log: ", String.valueOf(answersHashTable.get(Integer.toString(printButton[Integer.parseInt((String.valueOf(view.getTag())))]))));
        Log.i("Button: ", Integer.toString(printButton[Integer.parseInt((String.valueOf(view.getTag())))]));
        if(String.valueOf(answersHashTable.get(Integer.toString(printButton[Integer.parseInt((String.valueOf(view.getTag())))]))).equals("correct")){
            score++;
            numberOfQuestions++;
            resultTextView.setText("Correct!");
            Log.i("Correct","correct");
        } else {
            numberOfQuestions++;
            resultTextView.setText("Wrong.");
        }
        scoreTextView.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void start (View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(ConstraintLayout.VISIBLE);
        playAgain(playAgainButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameOverLayout = (LinearLayout) findViewById(R.id.gameOverLayout);
        gameLayout = (ConstraintLayout) findViewById(R.id.gameLayout);

        startButton = (Button) findViewById(R.id.startButton);

        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        gameOverScoreTextView = (TextView) findViewById(R.id.gameOverScoreTextView);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        playAgainButton = (Button) findViewById(R.id.playAgainButton);

    }
}
