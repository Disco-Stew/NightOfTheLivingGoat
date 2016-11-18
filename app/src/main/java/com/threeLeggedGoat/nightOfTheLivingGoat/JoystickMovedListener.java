package com.threeLeggedGoat.nightOfTheLivingGoat;

/**
 * Created by Stuart on 24/10/2015.
 */
public interface JoystickMovedListener {

        void OnMoved(int pan, int tilt);

        void OnReleased();
    }