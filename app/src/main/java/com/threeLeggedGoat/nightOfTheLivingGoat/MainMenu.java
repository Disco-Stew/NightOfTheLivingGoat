package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewSwitcher;

/**
 * Created by arase on 19/02/2016.
 */
public class MainMenu extends AppCompatActivity{

    private MenuPlayer sound;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //initializes the sound
        sound = new MenuPlayer(this);

        //sets layout to splash screen first and then menu is displayed

        setContentView(R.layout.splash);
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished){

            }
            @Override
            public void onFinish(){
                setContentView(R.layout.menu);
            }
        }.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        sound.pause();

    }

    @Override
    public void onStop(){
        super.onStop();
        sound.stop();
    }

    //closes all sound streams
    @Override
    public void onDestroy(){
        super.onDestroy();
        sound.annihilate();
    }

    @Override
    public void onResume(){
        super.onResume();
        sound.play();
    }

    //runs the game activity
    public void startGame(View view){
        Intent game = new Intent(this,GameEngine.class);
        startActivity(game);
    }

    public void quit(View view){
        quitDialog();
    }

    //overrides method so user cant just back out of app by pressing the back button
    @Override
    public void onBackPressed(){
        quitDialog();
    }

    private void quitDialog(){
        //creates new dialog to ensure that the user definietly want to quit
        new AlertDialog.Builder(this)
                .setTitle("Quit?")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener(){

                    //if user clicks yes
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //sends broadcast to finish game activity
                        Intent endGame = new Intent("finish");
                        sendBroadcast(endGame);

                        //finishes the menu activity
                        finish();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
