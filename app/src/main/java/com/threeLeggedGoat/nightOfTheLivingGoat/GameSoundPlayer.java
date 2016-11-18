package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;

/**
 * Created by arase on 29/02/2016.
 */
public class GameSoundPlayer extends SoundPlayer {

    private SoundPool sp;
    private final int maxStreams = 15;
    private int background,shot,steps,kill;
    private float lastFootstep;
    private Context context;

    public GameSoundPlayer(Context context){
        super(context);
        this.context = context;
        lastFootstep = 0;

        resume();

    }

    public void shoot(){sp.play(shot, getCurrentVolume(), getCurrentVolume(), 1, 0, 1);}

    public void walk(){

        //timer ensures the sound isn't continuously played
        if (SystemClock.uptimeMillis() - lastFootstep >= 288){
            sp.play(steps,getCurrentVolume()/2,getCurrentVolume()/2,2,0,1);
            lastFootstep = SystemClock.uptimeMillis();
        }
    }
    public void kill(){
        sp.play(kill,getCurrentVolume()/2,getCurrentVolume()/2,2,0,1);
    }

    public void stop(){
        sp.release();
    }

    //initializes all sounds again after game was paused/first created
    public void resume() {
        sp = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC,0);
        background = sp.load(context,R.raw.background,1);
        shot = sp.load(context,R.raw.shot,2);
        steps = sp.load(context,R.raw.foot,2);
        kill = sp.load(context,R.raw.boom,2);

        //starts playing background music once all sounds loaded
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sp.play(background,getCurrentVolume(),getCurrentVolume(),5,-1,1f);
            }
        });
    }
}
