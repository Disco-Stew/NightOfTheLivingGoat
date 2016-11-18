package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.Context;
import android.media.AudioManager;


/**
 * Created by arase on 03/11/2015.
 */
public class SoundPlayer{
    private  AudioManager audioManager;

    public SoundPlayer(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    //returns the value of the current volume set on device by user
    public int getCurrentVolume(){
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }
}
