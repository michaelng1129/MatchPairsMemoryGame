package com.example.matchpairsmemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btPlay, btGameRanking, btYourRecords, btClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btPlay = findViewById(R.id.btPlay);
        btGameRanking = findViewById(R.id.btGameRanking);
        btYourRecords = findViewById(R.id.btYourRecords);
        btClose = findViewById(R.id.btClose);

        // Set click listeners for buttons
        btPlay.setOnClickListener(v -> {
            // Start the GamePlayActivity
            Intent intent = new Intent(MainActivity.this, GamePlayActivity.class);
            startActivity(intent);
        });

        btGameRanking.setOnClickListener(v -> {
            // Start the GameRankingActivity
            Intent intent = new Intent(MainActivity.this, GameRankingActivity.class);
            startActivity(intent);
        });

        btYourRecords.setOnClickListener(v -> {
            // Start the YourRecordsActivity
            Intent intent = new Intent(MainActivity.this, YourRecordsActivity.class);
            startActivity(intent);
        });

        btClose.setOnClickListener(v -> {
            // Close the app
            finishAffinity();
        });
    }
}
