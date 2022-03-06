package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addition;
    Button subtraction;
    Button multi;
    Button division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addition = findViewById(R.id.buttonAddition);
        subtraction = findViewById(R.id.buttonSubtraction);
        multi = findViewById(R.id.buttonMultiplication);
        division = findViewById(R.id.buttonDivision);

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
}