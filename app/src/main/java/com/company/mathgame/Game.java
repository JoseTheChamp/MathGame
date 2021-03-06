package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.sax.StartElementListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    private char operation;
    TextView textViewQuestion;
    TextView textViewScore;
    TextView textViewLives;
    TextView textViewTime;
    Button buttonSubmit;
    Button buttonNextQuestion;
    EditText editTextAnswer;
    TextView textViewHintDiv;

    CountDownTimer timer;
    //Boolean timerRunning;
    long timeLeft = 30000;
    Random random = new Random();

    //guessing variables
    int number1;
    int number2;
    int resultCorrect;
    int resultAnswer;
    int lives = 3;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewLives = findViewById(R.id.lives);
        textViewScore = findViewById(R.id.score);
        textViewTime = findViewById(R.id.time);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonNextQuestion = findViewById(R.id.buttonNextQuestion);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        textViewHintDiv = findViewById(R.id.textViewHint);

        Intent intent = getIntent();
        operation =  intent.getCharExtra("operation", ' ');

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            endRound();
            }
        });
        buttonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            nextRound();
            }
        });

        if (operation == '/'){
            textViewHintDiv.setVisibility(View.VISIBLE);
        }

        nextRound();
    }

    private void  startTimer(){
        timer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateText();
            }

            @Override
            public void onFinish() {
                //timerRunning = false;
                timeLeft = 0;
                pauseTimer();
                updateText();
                endGame();
            }
        }.start();
        //timerRunning = true;
    }

    private void pauseTimer(){
        timer.cancel();
        //timerRunning=false;
    }

    private void updateText(){
        int second = (int)(timeLeft/1000)%60;
        String seconds = String.format(Locale.getDefault(),"%02d",second);
        textViewTime.setText(seconds);
    }

    private void endRound(){
        buttonNextQuestion.setClickable(true);
        buttonSubmit.setClickable(false);
        pauseTimer();
        if (TextUtils.isEmpty(editTextAnswer.getText().toString())){
            editTextAnswer.setText(0+"");
        }
        resultAnswer = Integer.parseInt(editTextAnswer.getText().toString());
        if (resultCorrect == resultAnswer){
            score++;
            textViewScore.setText(score +"");
            textViewQuestion.setText(getResources().getString(R.string.correct) + " " + number1 + " " + operation + " " + number2 + " = " + resultCorrect);
        }else{
            lives--;
            textViewLives.setText(lives +"");
            textViewQuestion.setText(getResources().getString(R.string.wrong) + " "+ number1 + " " + operation + " " + number2 + " = " + resultCorrect);
            if (lives == 0){
                endGame();
            }
        }
    }

    private void endGame(){
        Log.d("Manual", "endGame: KonecKola");
        manageHighScore();

        Intent intent = new Intent(Game.this,end_game.class);
        intent.putExtra("score",score);
        intent.putExtra("operation",operation);
        startActivity(intent);
        finish();
    }

    private void manageHighScore(){
        SharedPreferences sp = getSharedPreferences("highScores",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int highScore;

        switch (operation){
            case '+':
                Log.d("Manual", "manageHighScore: beforecommit  " + sp.getInt("highScoreAdd",0));
                highScore = sp.getInt("highScoreAdd",0);
                if (score>highScore){
                    editor.putInt("highScoreAdd",score);
                }
                break;
            case '-':
                highScore = sp.getInt("highScoreSub",0);
                if (score>highScore){
                    editor.putInt("highScoreSub",score);
                }
                break;
            case '*':
                highScore = sp.getInt("highScoreMul",0);
                if (score>highScore){
                    editor.putInt("highScoreMul",score);
                }
                break;
            case '/':
                highScore = sp.getInt("highScoreDiv",0);
                if (score>highScore){
                    editor.putInt("highScoreDiv",score);
                }
                break;
        }
        editor.commit();
        Log.d("Manual", "manageHighScore: after commit   " + sp.getInt("highScoreAdd",0));
    }

    private void nextRound(){
        buttonNextQuestion.setClickable(false);
        buttonSubmit.setClickable(true);
        editTextAnswer.setText("");
        editTextAnswer.requestFocus();
        boolean notNiceAnswer = false;
        do{
            ;
            switch (operation){
                case '+':
                    number1 = random.nextInt(100);
                    number2 = random.nextInt(100);
                    resultCorrect = number1 + number2;
                    break;
                case '-':
                    number1 = random.nextInt(100);
                    number2 = random.nextInt(number1);
                    resultCorrect = number1 - number2;
                    if (resultCorrect < 0){
                        notNiceAnswer = true;
                    }
                    break;
                case '*':
                    number1 = random.nextInt(20);
                    number2 = random.nextInt(20);
                    resultCorrect = number1 * number2;
                    break;
                case '/':
                    number1 = random.nextInt(99)+1;
                    number2 = random.nextInt(number1)+1;
                    resultCorrect = number1 / number2;
                    /*if (number1%number2 != 0){
                        notNiceAnswer = true;
                    }*/
                    break;
                default:
                    Log.e("What", "nextRound: not supported operation");
                    break;
            }
        } while (notNiceAnswer);
        textViewQuestion.setText(number1 + " " + operation + " " + number2 + " = ?");
        startTimer();
    }


}