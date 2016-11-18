package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Robert on 14/11/2015.
 */
public class Scaler {
    //2d game so should only have 2 co-ordinates
    private final int ASSUMEDSCREENSIZEX = 1794;
    private final int ASSSUMEDSCREENSIZEY = 1080;
    private int actualScreenX;
    private int actualScreenY;
    //Incase of weird resoultions
    private float scalerX;
    private float scalerY;
    private WindowManager wm;
    private Display display;
    private Point point;

    //Scaler Object
    public Scaler(Context context){

        wm = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        actualScreenX = point.x;
        actualScreenY = point.y;

    }
    //Calculate ratio
    public void scaler(){
        //cast ints to floats for calculation only
        scalerX = (float)actualScreenX/(float) ASSUMEDSCREENSIZEX;
        scalerY = (float)actualScreenY/(float) ASSSUMEDSCREENSIZEY;
    }

    //in case of odd resolutions best to use two separate methods.
    //scaleX
    public int scaledX (int xPosition){
        int result;
        result = (int)(scalerX*xPosition);
        return result;
    }

    //scaleY
    public int scaledY(int yPosition){
        int result;
        result = (int)(scalerY*yPosition);
        return result;
    }
    
    //Useful method for retrieving the width of the resolution of the device.
    public int getDisplayX(){
        return actualScreenX;
    }
    //Useful method for retrieving the height of the resolution of the device.
    public int getDisplayY(){
        return actualScreenY;
    }

}
