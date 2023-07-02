package com.example.matchpairsmemorygame;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity {

    List<ImageButton> imageButtons;
    List<Integer> imageResources;
    TextView tvGameMoves;
    int card = 1;
    int moves = 0;
    int firstIndex = -1;
    int secondIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvGameMoves = findViewById(R.id.tvMoves);
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
                    endGame();
                }
            }, 1000);
        } else {
            imageButtons.get(firstIndex).postDelayed(() -> {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    animateCardFlip(firstButton, R.drawable.card_back);
                    animateCardFlip(secondButton, R.drawable.card_back);
                    firstButton.setClickable(true);
                    secondButton.setClickable(true);
                }, 1000);
            }, 1000);
        }

        firstIndex = -1;
        secondIndex = -1;
    }

    private void animateCardFlip(View view, int cardResource) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
        animator.setDuration(500);
        animator.start();
        view.postDelayed(() -> ((ImageView) view).setImageResource(cardResource), 500);
    }

    private void updateGameMoves() {
        tvGameMoves.setText(getString(R.string.moves_count, moves));
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
    }
}
