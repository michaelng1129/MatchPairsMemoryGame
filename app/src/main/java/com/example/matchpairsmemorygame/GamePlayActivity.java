package com.example.matchpairsmemorygame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity {

    List<ImageButton> imageButtons;
    TextView tvMoves;
    Integer firstImage = null;
    Integer secondImage = null;
    int tvGameMoves = 0;


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

        // Set click listeners for all ImageButtons
        for (int i = 0; i < imageButtons.size(); i++) {
            final int index = i;
            imageButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClick(imageButtons.get(index), getImageResource(index));
                }
            });
        }
    }

    private int getImageResource(int index) {
        switch (index) {
            case 0:
            case 4:
                return R.drawable.contra;
            case 1:
            case 5:
                return R.drawable.ninja_gaiden;
            case 2:
            case 6:
                return R.drawable.super_dodge_ball;
            case 3:
            case 7:
                return R.drawable.super_mario;
            default:
                return 0;
        }
    }

    private void onImageClick(ImageButton imageButton, int imageId) {
        if (firstImage == null) {
            // First image selected
            firstImage = imageId;
            imageButton.setImageResource(imageId);
        } else if (secondImage == null) {
            // Second image selected
            secondImage = imageId;
            imageButton.setImageResource(imageId);
            tvGameMoves++;
            tvMoves.setText(getString(R.string.tvGameMoves));

            // Check if the images match
            if (firstImage.equals(secondImage)) {
                // Images match
                firstImage = null;
                secondImage = null;
            } else {
                // Images do not match
                for (ImageButton ib : imageButtons) {
                    //ib.setImageResource(R.drawable.question_mark);
                }
                firstImage = null;
                secondImage = null;
            }
        }
    }
}
