package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by arase on 29/02/2016.
 */
public class MenuPlayer extends SoundPlayer {

    private MediaPlayer mp;

    //required because android is too slow
    private boolean stopped;

    public MenuPlayer(Context context){
        super(context);
        stopped = false;

        //sets up media player for playing background music
        mp = MediaPlayer.create(context,R.raw.menu_back);
        mp.setVolume(getCurrentVolume(),getCurrentVolume());
        mp.start();

    }

    //pauses background music
    public void pause(){
        mp.pause();
    }

    public void stop(){
        mp.stop();
        stopped = true;
    }

    public void play(){
        if (stopped) {
            try {
                mp.prepare();
                mp.start();
            } catch (IOException f) {
                System.out.print("broken!!!");
            }
        }else{
            mp.start();
        }
    }

    //closes the sound stream
    public void annihilate(){
        mp.release();
    }
}
