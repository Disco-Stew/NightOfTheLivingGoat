package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by arase on 01/03/2016.
 */
public class PauseScreen extends Dialog implements android.view.View.OnClickListener {

    private Activity activity;
    private Button resume,restart,quit;
    private boolean playing;
    private boolean displayed;

    public PauseScreen(Activity a, boolean isGameOver){
        super(a);
        activity = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pause_menu);

        playing=true;

        //initializes buttons to the xml ones
        resume = (Button)findViewById(R.id.resume);
        restart = (Button)findViewById(R.id.restart);
        quit = (Button)findViewById(R.id.exit);

        //sets the buttons click method to one in this class
        resume.setOnClickListener(this);
        restart.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.resume){//resume game

            playing = true;
            dismiss();
        }else if(v.getId()==R.id.restart){//restart game
            dismiss();
            playing=false;
            activity.recreate();

        }else if(v.getId()==R.id.exit){//exit to main menu
            playing = false;
            activity.finish();
        }
    }

    public boolean isPlaying(){
        return playing;
    }


}
