package com.example.matchpairsmemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btplay, btGameRanking, btYourRecords, btClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btplay = findViewById(R.id.btPlay);
        btplay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GamePlayActivity.class);
            startActivity(intent);
        });

        btGameRanking = findViewById(R.id.btGameRanking);
        btGameRanking.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameRankingActivity.class);
            startActivity(intent);
        });

        btYourRecords = findViewById(R.id.btYourRecords);
        btYourRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, YourRecordsActivity.class);
            startActivity(intent);
        });

        btClose = findViewById(R.id.btClose);
        btClose.setOnClickListener(v -> {
            finish();
        });
    }
}