package com.example.matchpairsmemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    Button btPlayAgain, btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Initialize views and buttons
        TextView resultTextView = findViewById(R.id.tvScore);
        btPlayAgain = findViewById(R.id.btPlayAgain);
        btBack = findViewById(R.id.btBack);

        // Set click listener for the "Play Again" button
        btPlayAgain.setOnClickListener(v -> {
            // Start the GamePlayActivity
            Intent intent = new Intent(ScoreActivity.this, GamePlayActivity.class);
            startActivity(intent);
        });

        // Set click listener for the "Back" button
        btBack.setOnClickListener(v -> {
            // Start the MainActivity
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Receive the score data from the previous activity
        Intent receiveAttempt = getIntent();
        int score = receiveAttempt.getIntExtra("score", 0);

        // Set the score value to the resultTextView
        resultTextView.setText(String.valueOf(score));
    }
}
