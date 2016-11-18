package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//Joystick View class is largely based off this website's code https://web.archive.org/web/20111128000201/http://www.java2s.com/Open-Source/Android/Widget/mobile-anarchy-widgets/com/MobileAnarchy/Android/Widgets/Joystick/JoystickView.java.htm
//Its not the exact same however and has been modified.

public class JoystickView extends View {


    // variables for the joystickview class
    // paint variables for drawing to canvas
    private final String TAG = "JoystickView";
    private Paint circlePaint;
    private Paint handlePaint;
    private double touchX, touchY;
    private int innerPadding;
    private int handleRadius;
    private int handleInnerBoundaries;
    private JoystickMovedListener listener;
    private int sensitivity;
    private int radius;
    private int circleAlpha = 125;
    private int circleRed = 115;
    private int circleBlue = 0;
    private int circleGreen = 0;
    private int handleAlpha = 195;
    private int handleRed = 115;
    private int handleBlue = 0;
    private int handleGreen = 0;
    private float circleShadowRadius = 7.5f;
    private float circleShadowX = 0;
    private float circleShadowY = 0;
    private float handleShadowRadius = 7.5f;
    private float handleShadowX = 0;
    private float handleShadowY = 0;
    private float strokeWidth = 1;

    // Actual working constructor. Takes context and an integer for radius
    public JoystickView(Context context,int radius){
        super(context);
        initJoystickView();
        this.radius = radius;
    }

    // Initialises the joystickview and uses the paint to set attributes
    // for drawing the sticks.
    private void initJoystickView() {
        setFocusable(true);

        //Paints the circle to the canvas
        //uses argb to define colour and alpha levels
        //alpha governs transparency/opacity
        //adds a small shadowlayer to improve visuals slightly
        //adds anti-aliasing
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.argb(circleAlpha, circleRed, circleGreen, circleBlue));
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setShadowLayer(circleShadowRadius,circleShadowX,circleShadowY,Color.BLACK);
        circlePaint.setAntiAlias(true);

        //paints the handle
        //uses rgba for colour
        //alpha covers the transparency/opacity
        //adds a small shadowlayer to improve visuals slightly.
        //adds anti-aliasing
        handlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handlePaint.setColor(Color.argb(handleAlpha, handleRed, handleGreen,handleBlue ));
        handlePaint.setStrokeWidth(strokeWidth);
        handlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        handlePaint.setShadowLayer(handleShadowRadius, handleShadowX, handleShadowY, Color.BLACK);
        handlePaint.setAntiAlias(true);

        //Modifiers for padding and sensitivity of the joystick
        innerPadding = 10;
        sensitivity = 10;
    }


    // Adds a motionlistener to the joystick to allow it to check for movement.
    public void setOnJoystickMovedListener(JoystickMovedListener listener) {
        this.listener = listener;
    }



    // measures the radius for the circle
    // takes width and height of the area for the circle to be drawn in
    // also calculates handle radius it needs to be smaller
    // governs the boundaries of the handle as well
    // due to the handle moving it needs to be reined in.
    // calls the measure method on the parameters to calculate the width
    // and the height.
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Here we make sure that we have a perfect circle
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        int d = radius*2;


        handleRadius = (int) (d * 0.275);
        handleInnerBoundaries = handleRadius;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    //Uses methods from the View superclass to get the mode
    //and the size of the passed integer.
    //returns the integer to the onMeasure method where
    //it is used to provide the measured width and measured
    // height.
    private int measure(int measureSpec) {
        int result;
        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    //Takes in a canvas to draw the circles
    //Uses View super class methods get measuredWidth
    //and get MeasuredHeight
    //when finished drawing the circles to the canvas
    //saves it to the canvas

    @Override
    public void onDraw(Canvas canvas) {

        int px = getMeasuredWidth() / 2;
        int py = getMeasuredHeight() / 2;

        // Draw the background
        canvas.drawCircle(px, py, radius - innerPadding, circlePaint);
        // Draw the handle
        canvas.drawCircle((int) touchX + px, (int) touchY + py,
                handleRadius, handlePaint);
    }

    //This method deals with the motion of the joystick
    //It passes in the motionevent. Motionevent is then used
    //to govern the movement of the character rendered on screen
    //uses the super class view methods get measuredWidth and
    //getMeasuredHeight. Generates a log as well which is useful for
    //debugging and ensure its working as intended not necessary for
    //final iteration as log not visible on android devices.
    //It also passess the variables to the OnMoved method
    //which is part of the listener JoystickMovedListener class


    public boolean onTouchEvent(MotionEvent event) {
        int actionType = event.getAction();


        if (actionType == MotionEvent.ACTION_MOVE) {
            int px = getMeasuredWidth() / 2;
            int py = getMeasuredHeight() / 2;

            int rad = radius - handleInnerBoundaries;

            touchX = (event.getX() - px);
            touchX = Math.max(Math.min(touchX, rad), -rad);

            touchY = (event.getY() - py);
            touchY = Math.max(Math.min(touchY, rad), -rad);

            // Coordinates
            Log.d(TAG, "X:" + touchX + "|Y:" + touchY);
            Log.d(TAG, "X:" + event.getX() + "|Y:" + event.getY());

            // Pressure
            if (listener != null) {
                listener.OnMoved((int) (touchX / rad * sensitivity),
                        (int) (touchY / rad * sensitivity));
            }

            invalidate();
        } else if (actionType == MotionEvent.ACTION_UP) {
            returnHandleToCenter();
            Log.d(TAG, "X:" + touchX + "|Y:" + touchY);
            Log.d(TAG, "X:" + event.getX() + "|Y:" + event.getY());
        } else if(actionType == MotionEvent.ACTION_CANCEL) {
            returnHandleToCenter();
            Log.d(TAG, "X:" + touchX + "|Y:" + touchY);
            Log.d(TAG, "X:" + event.getX() + "|Y:" + event.getY());
        }
        return true;
    }


    //private method is called to return the handle of the joystick to the centre
    //this is to stop the joystick from moving to one area of the screen and not returning
    //called by the previous method onTouchevent when their is no contact with the screen.

    private void returnHandleToCenter() {

        Handler handler = new Handler();
        int numberOfFrames = 5;
        final double intervalsX = (0 - touchX) / numberOfFrames;
        final double intervalsY = (0 - touchY) / numberOfFrames;

        for (int i = 0; i < numberOfFrames; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    touchX += intervalsX;
                    touchY += intervalsY;
                    invalidate();
                }
            }, i * 40);
        }

        if (listener != null) {
            listener.OnReleased();
        }
    }
}
