package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        pauseTimer();
        if (TextUtils.isEmpty(editTextAnswer.getText().toString())){
            editTextAnswer.setText(0+"");
        }
        resultAnswer = Integer.parseInt(editTextAnswer.getText().toString());
        if (resultCorrect == resultAnswer){
            score++;
            textViewScore.setText(score +"");
            textViewQuestion.setText("Correct: " + number1 + " " + operation + " " + number2 + " = " + resultCorrect);
        }else{
            lives--;
            textViewLives.setText(lives +"");
            textViewQuestion.setText("Wrong: " + number1 + " " + operation + " " + number2 + " = " + resultCorrect);
            if (lives == 0){
                endGame();
            }
        }
    }

    private void endGame(){
        Log.d("Manual", "endGame: KonecKola");
        Intent intent = new Intent(Game.this,end_game.class);
        intent.putExtra("Score",score);
        intent.putExtra("Operation",operation);
        startActivity(intent);
        finish();
    }

    private void nextRound(){
        buttonNextQuestion.setClickable(false);
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
                    number1 = random.nextInt(100);
                    number2 = random.nextInt(100);
                    resultCorrect = number1 / number2;
                    if (number1%number2 != 0){
                        notNiceAnswer = true;
                    }
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