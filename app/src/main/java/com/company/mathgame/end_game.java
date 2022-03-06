package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class end_game extends AppCompatActivity {

    Button buttonExit;
    Button buttonAgain;
    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        buttonAgain = findViewById(R.id.buttonPlayAgain);
        buttonExit = findViewById(R.id.buttonExit);
        textViewScore = findViewById(R.id.textViewScore);

        textViewScore.setText("Score: " + getIntent().getIntExtra("Score",0)+"");
        char operation = getIntent().getCharExtra("Operation",' ');
        switch (operation){
            case '+':
                textViewScore.setText("Addition");
                break;
            case '-':
                textViewScore.setText("Subtraction");
                break;
            case '*':
                textViewScore.setText("Multiplication");
                break;
            case '/':
                textViewScore.setText("Division");
                break;
        }
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(end_game.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}