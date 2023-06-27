package com.example.matchpairsmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btplay;
    Button btGameRanking;
    Button btYourRecords;
    Button btClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btplay = findViewById(R.id.btPlay);
        btGameRanking = findViewById(R.id.btGameRanking);
        btYourRecords = findViewById(R.id.btYourRecords);
        btClose = findViewById(R.id.btClose);
    }

    public void gamePlay(View view){
        Intent i = new Intent(this, GamePlayActivity.class);
        startActivity(i);
    }

    public void checkRanking(View view){
        Intent i = new Intent(this, GameRankingActivity.class);
        startActivity(i);
    }

    public void checkYourRecords(View view){
        Intent i = new Intent(this, YourRecordsActivity.class);
        startActivity(i);
    }
}