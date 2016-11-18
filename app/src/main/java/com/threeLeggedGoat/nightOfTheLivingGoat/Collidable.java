package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Rect;

/**
 * Created by Stuart on 10/02/2016.
 */
public interface Collidable {

    void checkBounds();
    Rect getSpriteBounds();

}

