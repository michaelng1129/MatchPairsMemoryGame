package com.example.matchpairsmemorygame;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity {

    private List<ImageButton> imageButtons;
    private List<Integer> imageResources;
    TextView tvMoves;
    private int moves = 0;
    private int firstIndex = -1, secondIndex = -1;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvMoves = findViewById(R.id.tvMoves);
        imageButtons = new ArrayList<>();
        imageButtons.add(findViewById(R.id.ib1));
        imageButtons.add(findViewById(R.id.ib2));
        imageButtons.add(findViewById(R.id.ib3));
        imageButtons.add(findViewById(R.id.ib4));
        imageButtons.add(findViewById(R.id.ib5));
        imageButtons.add(findViewById(R.id.ib6));
        imageButtons.add(findViewById(R.id.ib7));
        imageButtons.add(findViewById(R.id.ib8));

        setGame();
    }

    private void setGame() {
        moves = 0;
        updateGameMoves();
        imageResources = Arrays.asList(
                R.drawable.contra, R.drawable.ninja_gaiden, R.drawable.super_dodge_ball, R.drawable.super_mario,
                R.drawable.contra, R.drawable.ninja_gaiden, R.drawable.super_dodge_ball, R.drawable.super_mario
        );
        Collections.shuffle(imageResources);

        for (ImageButton imageButton : imageButtons) {
            imageButton.setImageResource(R.drawable.card_back);
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setClickable(true);
            imageButton.setOnClickListener(this::cardClick);
        }
    }

    private void cardClick(View view) {
        int index = imageButtons.indexOf(view);

        if (firstIndex == -1) {
            firstIndex = index;
            animateCardFlip(view, imageResources.get(firstIndex));
            view.setClickable(false);
        } else if (secondIndex == -1 && index != firstIndex) {
            secondIndex = index;
            animateCardFlip(view, imageResources.get(secondIndex));
            view.setClickable(false);
            moves++;
            updateGameMoves();
            checkMatch();
        }
    }

    private void checkMatch() {
        ImageButton firstButton = imageButtons.get(firstIndex);
        ImageButton secondButton = imageButtons.get(secondIndex);

        if (imageResources.get(firstIndex).equals(imageResources.get(secondIndex))) {
            imageButtons.get(firstIndex).postDelayed(() -> {
                firstButton.setVisibility(View.INVISIBLE);
                secondButton.setVisibility(View.INVISIBLE);
                if (allMatched()) {
                    sendData();
                    endGame();
                }
            }, 1000);
        } else {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    animateCardFlip(firstButton, R.drawable.card_back);
                    animateCardFlip(secondButton, R.drawable.card_back);
                    firstButton.setClickable(true);
                    secondButton.setClickable(true);
                }, 1000);
        }

        firstIndex = -1;
        secondIndex = -1;
    }

    private void animateCardFlip(View view, int cardResource) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
        animator.setDuration(500);
        animator.start();
        view.postDelayed(() -> ((ImageView) view).setImageResource(cardResource), 250);
    }

    private void updateGameMoves() {
        tvMoves.setText(getString(R.string.moves_count, moves));
    }

    private boolean allMatched() {
        for (ImageButton imageButton : imageButtons) {
            if (imageButton.getVisibility() != View.INVISIBLE) {
                return false;
            }
        }
        return true;
    }

    private void endGame() {
        for (ImageButton imageButton : imageButtons) {
            imageButton.setClickable(false);
        }
        Intent sendAttemptIntent = new Intent(GamePlayActivity.this, ScoreActivity.class);
        sendAttemptIntent.putExtra("score", moves);
        startActivity(sendAttemptIntent);
    }

    private void sendData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String playDate = dateFormat.format(calendar.getTime());
        String playTime = timeFormat.format(calendar.getTime());
        int moves = this.moves;

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.matchpairsmemorygame/GamesLog", null, SQLiteDatabase.CREATE_IF_NECESSARY);

            String createTable = "CREATE TABLE IF NOT EXISTS GamesLog ("
                    + "gameID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "playDate TEXT,"
                    + "playTime TEXT,"
                    + "moves INTEGER"
                    + ");";
            db.execSQL(createTable);

            if (playDate != null && playTime != null && moves != 0) {
                String insertRecord = "INSERT INTO GamesLog (playDate, playTime, moves) VALUES ('" + playDate + "', '" + playTime + "', " + moves + ");";
                db.execSQL(insertRecord);
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
