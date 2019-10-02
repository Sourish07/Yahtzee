package com.example.yahtzee;

import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton[] dice = new ImageButton[5];

    //Functionality to be added later...
    private Bundle bundle = new Bundle();
    private String[] diceStrings = new String[5];

    private Button roll;
    private Button reset;
    private TextView userMessage;
    private int TOTAL_NUM_OF_ROLLS = 3;
    private int numOfRollsLeft = TOTAL_NUM_OF_ROLLS;

    //Matrix for the invert colors filter
    private final float[] NEGATIVE = {
            -1.0f,     0,     0,    0, 255, // red
            0, -1.0f,     0,    0, 255, // green
            0,     0, -1.0f,    0, 255, // blue
            0,     0,     0, 1.0f,   0  // alpha
    };

    private ColorMatrixColorFilter invert = new ColorMatrixColorFilter(NEGATIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dice[0] = findViewById(R.id.die1);
        dice[1] = findViewById(R.id.die2);
        dice[2] = findViewById(R.id.die3);
        dice[3] = findViewById(R.id.die4);
        dice[4] = findViewById(R.id.die5);

        roll = findViewById(R.id.roll);
        reset = findViewById(R.id.reset);
        userMessage = findViewById(R.id.numOfRollsLeft);

        for (int i = 0; i < diceStrings.length; i++){
            diceStrings[i] = "dice" + i;
        }

        //On Click Listener for the roll button
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If all the dice are locked, then it won't waste a roll
                if (dice[0].getColorFilter() != null &&
                dice[1].getColorFilter() != null &&
                dice[2].getColorFilter() != null &&
                dice[3].getColorFilter() != null &&
                dice[4].getColorFilter() != null)
                    return;
                //Goes through the five dice and changes the image (representing a new roll
                for (int i = 0; i < dice.length; i++) {
                    rollDie(dice[i]);
                }
                numOfRollsLeft--;
                if (numOfRollsLeft <= 0) {
                    userMessage.setText("No rolls left!");
                    return;
                }
                // If there are more rolls left, it changes the message for the user, taking plural into account
                userMessage.setText(numOfRollsLeft + " roll");
                if (numOfRollsLeft != 1)
                    userMessage.append("s");
                userMessage.append(" left!");
            }
        });

        //On Click Listener for the reset button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goes through each dice and clears teh image filter and sets it to the blank die image
                for (int i = 0; i < dice.length; i++) {
                    dice[i].setImageResource(R.drawable.d6_zero);
                    dice[i].setColorFilter(null);
                }
                //Resets the number of rolls
                numOfRollsLeft = TOTAL_NUM_OF_ROLLS;
                userMessage.setText(numOfRollsLeft + " rolls left!");
            }
        });

        //On Click Listener for the dice buttons
        View.OnClickListener lock = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user hasn't rolled the dice even once, then it doesn't do anything on a click
                if (numOfRollsLeft == TOTAL_NUM_OF_ROLLS)
                    return;
                //Takes the imageButton and if its filter isn't inverted, it inverts it, signifying that the user has locked it
                //And if it was already inverted, then it makes it normal
                ImageButton imageButton = (ImageButton) v;
                if (imageButton.getColorFilter() == null)
                    imageButton.setColorFilter(invert);
                else
                    imageButton.clearColorFilter();
            }
        };

        //Assigns the On Click Listener to each of the Dice Buttons
        for (ImageButton imageButton : dice) {
            imageButton.setOnClickListener(lock);
        }
    }

    //Functionality to be added later...
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Functionality to be added later...
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Takes the imageButton object passed to it and changes the image to a randomly selected one
    private void rollDie(ImageButton imageButton) {
        //Checks to see if there are rolls still left and if the die isn't locked
        if (numOfRollsLeft <= 0 || imageButton.getColorFilter() != null)
            return;

        int rand = (int) (Math.random() * 6);
        switch (rand) {
            case 0:
                imageButton.setImageResource(R.drawable.d6_one);
                break;
            case 1:
                imageButton.setImageResource(R.drawable.d6_two);
                break;
            case 2:
                imageButton.setImageResource(R.drawable.d6_three);
                break;
            case 3:
                imageButton.setImageResource(R.drawable.d6_four);
                break;
            case 4:
                imageButton.setImageResource(R.drawable.d6_five);
                break;
            case 5:
                imageButton.setImageResource(R.drawable.d6_six);
        }
    }
}
