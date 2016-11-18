package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

//ROBERT/AREK

public class GameEngine extends AppCompatActivity {


    //Initialising variables
    private BroadcastReceiver finReceiver;

    private GameView gameView;

    private LinearLayout.LayoutParams pause = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout linearLayout;
    private FrameLayout frameLayout;

    private Button pauseButton;
    private View.OnClickListener pauseClick;

    private int pauseButtonWidth = 100;
    private int pauseButtonHeight = 100;

    private int pauseButtonMarginLeft = 25;
    private int pauseButtonMarginTop = 175;
    private int pauseButtonMarginRight = 0;
    private int pauseButtonMarginBottom = 0;

    //Creates the gameView,layouts,button and typeface.ROBERT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets up layouts
        frameLayout = new FrameLayout(this);
        linearLayout = new LinearLayout(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Bloodthirsty.ttf");
        gameView = new GameView(this,typeface,this);

        //Calls the setup button method
        setUpPauseButton();

        frameLayout.addView(gameView);
        frameLayout.addView(linearLayout);

        setContentView(frameLayout);

	//required to terminate activity from main menu
        finReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("finish")) {
                    finish();
                }
            }
        };
    }

    //This method executes when the game is resumed. AREK/ROBERT
    @Override
    public void onResume()
    {
        super.onResume();
        //calls the gameView's method resume which resumes the game.
        if (!gameView.isPauseScreenShowing()) {
            gameView.resume();
        }
        registerReceiver(finReceiver, new IntentFilter("finish"));
    }

    //This method deals with pausing the game. AREK/ROBERT
    @Override
    public void onPause()
    {
        super.onPause();
        unregisterReceiver(finReceiver);
        //Calls gameView class executes the pause method.
        gameView.pause();
    }

    //Deals with button backPressed. ROBERT.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        gameView.pause();
    }

    //Sets up the buttons parameters and adds it to the layout. ROBERT
    private void setUpPauseButton(){

        pauseButton = new Button(this);
        pauseButton.setTextColor(Color.WHITE);
        pauseButton.setBackgroundResource(R.drawable.pause);

        //Sets up the listener that listens for when the user clicks the button
        pauseClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.pause();
            }
        };

        pauseButton.setOnClickListener(pauseClick);

        pause.setMargins(gameView.scaler.scaledX(pauseButtonMarginLeft), gameView.scaler.scaledY(pauseButtonMarginTop),gameView.scaler.scaledX(pauseButtonMarginRight),gameView.scaler.scaledY(pauseButtonMarginBottom));

        pause.width = pauseButtonWidth;
        pause.height = pauseButtonHeight;

        pauseButton.setLayoutParams(pause);

        linearLayout.addView(pauseButton);

    }
}
