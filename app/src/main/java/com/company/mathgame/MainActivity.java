package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button addition;
    Button subtraction;
    Button multi;
    Button division;

    TextView textViewAdd;
    TextView textViewSub;
    TextView textViewMul;
    TextView textViewDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addition = findViewById(R.id.buttonAddition);
        subtraction = findViewById(R.id.buttonSubtraction);
        multi = findViewById(R.id.buttonMultiplication);
        division = findViewById(R.id.buttonDivision);

        textViewAdd = findViewById(R.id.textViewAdditionHigh);
        textViewSub = findViewById(R.id.textViewSubtractionHigh);
        textViewMul = findViewById(R.id.textViewMultiplicationHigh);
        textViewDiv = findViewById(R.id.textViewDivisionHigh);

        loadHighScores();

        addition.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("operation",'+');
                startActivity(intent);
                finish();
            }
        });
        subtraction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("operation",'-');
                startActivity(intent);
                finish();
            }
        });
        multi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("operation",'*');
                startActivity(intent);
                finish();
            }
        });
        division.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("operation",'/');
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadHighScores(){
        SharedPreferences sp = getSharedPreferences("highScores",MODE_PRIVATE);
        String prompt = getResources().getString(R.string.ma_highscore);
        textViewAdd.setText(prompt + " " + sp.getInt("highScoreAdd",0));
        textViewSub.setText(prompt + " " + sp.getInt("highScoreSub",0));
        textViewMul.setText(prompt + " " + sp.getInt("highScoreMul",0));
        textViewDiv.setText(prompt + " " + sp.getInt("highScoreDiv",0));
    }
}