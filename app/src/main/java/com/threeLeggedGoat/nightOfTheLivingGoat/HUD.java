package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;

/**
 * Created by robert on 08/12/15.
 */
public class HUD {

    //A scaler object,typeface(custom font) and 2 paths for the 2 pentagrams
    private Scaler scaler;
    private Path pentagramPath1;
    private Path pentagramPath2;
    Typeface typeface;

    //X co-ordinates for the first pentagrams' X points
    private int pentagram1Point1X;
    private int pentagram1Point2X;
    private int pentagram1Point3X;
    private int pentagram1Point4X;
    private int pentagram1Point5X;
    private int pentagram1Point6X;

    //Y co-ordinates for the first pentagrams' Y points
    private int pentagram1Point1Y;
    private int pentagram1Point2Y;
    private int pentagram1Point3Y;
    private int pentagram1Point4Y;
    private int pentagram1Point5Y;
    private int pentagram1Point6Y;

    //X co-ordinates for the second pentagrams' X points
    private int pentagram2Point1X;
    private int pentagram2Point2X;
    private int pentagram2Point3X;
    private int pentagram2Point4X;
    private int pentagram2Point5X;
    private int pentagram2Point6X;

    //Y co-ordinates for the second pentagrams' Y points
    private int pentagram2Point1Y;
    private int pentagram2Point2Y;
    private int pentagram2Point3Y;
    private int pentagram2Point4Y;
    private int pentagram2Point5Y;
    private int pentagram2Point6Y;

    //two dimensional arrays to hold the values for the pentagrams used for the paths
    private int[][] pentagram1Array ;
    private int[][] pentagram2Array ;

    //Paints for the text and pentagrams
    private Paint paint;
    private Paint outlinePaint;
    private Paint pentagramPainter;

    //Text for the HUD. Never changes.
    private final String LIVES = "Lives: ";
    private final String SCORE = "Score: ";
    private final String WAVES = "Waves: ";

    //Variables for lives score and the wave
    int numberOfLives;
    int currentScore;
    int currentWaves;

    //ARGB values for paint
    private int alpha = 200;
    private int red = 135;
    private int green = 0;
    private int blue = 0;

    //ARGB values for outlinepaint
    private int outlineAlpha = 255;
    private int outlineRed = 0;
    private int outlineGreen = 0;
    private int outlineBlue = 0;
    private int outlineStrokeWidth = 3;

    //ARGB values for pentagram paint
    private int pentagramAlpha = 255;
    private int pentagramRed = 0;
    private int pentagramGreen = 0;
    private int pentagramBlue = 0;

    //Stroke width for pentagram
    private int pentagramOutlineStrokeWidth = 5;

    //Variables for shadowlayer of the pentagram. Also ARGB values for colour of shadow
    private int pentagramShadowAlpha = 255;
    private int pentagramShadowRed = 75;
    private int pentagramShadowBlue = 0;
    private int pentagramShadowGreen =0;
    private int pentagramShadowX = 0;
    private int pentagramShadowY = 0;
    private float pentagramShadowRadius = 8;

    //Radius for Pentagram's outer circles
    private int pentagramCircle1Radius;
    private int pentagramCircle2Radius;
    private int pentagramCircle3Radius;
    private int pentagramCircle4Radius;

    //X position of the pentagrams circles
    private int pentagramCircle1X;
    private int pentagramCircle2X;
    private int pentagramCircle3X;
    private int pentagramCircle4X;

    //Y position of the pentagrams circles
    private int pentagramCircle1Y;
    private int pentagramCircle2Y;
    private int pentagramCircle3Y;
    private int pentagramCircle4Y;

    //Positions of the text on screen for lives,waves and score
    private int livesX = 25;
    private int livesY = 125;
    private int scoreX = 575;
    private int scoreY = 125;
    private int wavesX = 1275;
    private int wavesY = 125;
    private int textSize = 125;




    public HUD(int numberOfLives, int currentScore,int currentWaves,Scaler scaler,Typeface typeface){
        this.numberOfLives = numberOfLives;
        this.currentScore = currentScore;
        this.currentWaves = currentWaves;
        this.scaler = scaler;
        this.typeface = typeface;

        //Scaling all the points to adapt to various displays and initialising variables for 2D arrays
        pentagram1Point1X = scaler.scaledX(125);
        pentagram1Point2X = scaler.scaledX(175);
        pentagram1Point3X = scaler.scaledX(225);
        pentagram1Point4X = scaler.scaledX(100);
        pentagram1Point5X = scaler.scaledX(250);
        pentagram1Point6X = scaler.scaledX(125);

        pentagram1Point1Y = scaler.scaledY(925);
        pentagram1Point2Y = scaler.scaledY(765);
        pentagram1Point3Y = scaler.scaledY(925);
        pentagram1Point4Y = scaler.scaledY(810);
        pentagram1Point5Y = scaler.scaledY(810);
        pentagram1Point6Y = scaler.scaledY(925);

        pentagram2Point1X = scaler.scaledX(1575);
        pentagram2Point2X = scaler.scaledX(1625);
        pentagram2Point3X = scaler.scaledX(1675);
        pentagram2Point4X = scaler.scaledX(1550);
        pentagram2Point5X = scaler.scaledX(1700);
        pentagram2Point6X = scaler.scaledX(1575);

        pentagram2Point1Y = scaler.scaledY(925);
        pentagram2Point2Y = scaler.scaledY(765);
        pentagram2Point3Y = scaler.scaledY(925);
        pentagram2Point4Y = scaler.scaledY(810);
        pentagram2Point5Y = scaler.scaledY(810);
        pentagram2Point6Y = scaler.scaledY(925);

        pentagramCircle1X = scaler.scaledX(175);
        pentagramCircle2X= scaler.scaledX(175);
        pentagramCircle3X = scaler.scaledX(1625);
        pentagramCircle4X = scaler.scaledX(1625);

        pentagramCircle1Y =scaler.scaledY(850);
        pentagramCircle2Y= scaler.scaledY(850);
        pentagramCircle3Y =scaler.scaledY(850);
        pentagramCircle4Y =scaler.scaledY(850);

        pentagramCircle1Radius = scaler.scaledX(115);
        pentagramCircle2Radius = scaler.scaledX(105);
        pentagramCircle3Radius = scaler.scaledX(115);
        pentagramCircle4Radius = scaler.scaledX(105);
        
        //Adding the variables to the 2 dimensional arrays
        pentagram1Array = new int [][]
                {{pentagram1Point1X,pentagram1Point1Y},{pentagram1Point2X,pentagram1Point2Y},{pentagram1Point3X,pentagram1Point3Y},{pentagram1Point4X,pentagram1Point4Y},{pentagram1Point5X,pentagram1Point5Y},{pentagram1Point6X,pentagram1Point6Y}};
        pentagram2Array = new int [][]
                {{pentagram2Point1X,pentagram2Point1Y},{pentagram2Point2X,pentagram2Point2Y},{pentagram2Point3X,pentagram2Point3Y},{pentagram2Point4X,pentagram2Point4Y},{pentagram2Point5X,pentagram2Point5Y},{pentagram2Point6X,pentagram2Point6Y}};

        //Initialising the 3 paints and setting attributes
        paint = new Paint();
        paint.setARGB(alpha, red, green, blue);
        paint.setTextSize(scaler.scaledX(textSize));
        paint.setTypeface(typeface);
        paint.setStyle(Paint.Style.FILL);

        outlinePaint = new Paint();
        outlinePaint.setARGB(outlineAlpha, outlineRed, outlineGreen, outlineBlue);
        outlinePaint.setTypeface(typeface);
        outlinePaint.setTextSize(scaler.scaledX(textSize));
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(outlineStrokeWidth);

        pentagramPainter = new Paint();
        pentagramPainter.setAntiAlias(true);
        pentagramPainter.setShadowLayer(pentagramShadowRadius, pentagramShadowX, pentagramShadowY, Color.argb(pentagramShadowAlpha, pentagramShadowRed, pentagramShadowGreen, pentagramShadowBlue));
        pentagramPainter.setARGB(pentagramAlpha, pentagramRed, pentagramGreen, pentagramBlue);
        pentagramPainter.setStrokeWidth(pentagramOutlineStrokeWidth);
        pentagramPainter.setStyle(Paint.Style.STROKE);

    }



    //Draws the HUD
    public void draw(Canvas canvas) {
        drawPentagrams(canvas);
        drawLives(canvas);
        drawScore(canvas);
        drawWaves(canvas);
    }


    //Draws lives to the screen
    public void drawLives(Canvas canvas){
        canvas.drawText(LIVES + getNumberOfLives(), scaler.scaledX(livesX), scaler.scaledY(livesY), paint);
        canvas.drawText(LIVES + getNumberOfLives(), scaler.scaledX(livesX), scaler.scaledY(livesY), outlinePaint);
    }

    //Draws Score to the screen
    public void drawScore(Canvas canvas){
        canvas.drawText(SCORE + getCurrentScore(), scaler.scaledX(scoreX), scaler.scaledY(scoreY), paint);
        canvas.drawText(SCORE + getCurrentScore(), scaler.scaledX(scoreX), scaler.scaledY(scoreY), outlinePaint);
    }

    //Draws Waves to the screen
    public void drawWaves(Canvas canvas){
        canvas.drawText(WAVES + getWaves(),scaler.scaledX(wavesX),scaler.scaledY(wavesY),paint);
        canvas.drawText(WAVES + getWaves(), scaler.scaledX(wavesX), scaler.scaledY(wavesY), outlinePaint);
    }

    //Draws the Pentagrams to the screen
    public void drawPentagrams(Canvas canvas){

        //creates a new path and adds 2 circles and draws them.
        pentagramPath1 = new Path();
        pentagramPath1.addCircle(pentagramCircle1X, pentagramCircle1Y, pentagramCircle1Radius, Path.Direction.CW);
        canvas.drawPath(pentagramPath1, pentagramPainter);
        pentagramPath1.addCircle(pentagramCircle2X, pentagramCircle2Y, pentagramCircle2Radius, Path.Direction.CW);
        canvas.drawPath(pentagramPath1, pentagramPainter);

        //Moves the path to the start of the pentagram then goes through the 2 dimensional array to obtain points and then draws the pentagram.
        pentagramPath1.moveTo(pentagram1Array[0][0], pentagram1Array[0][1]);
        for(int x = 1; x < pentagram1Array.length; x++){
            pentagramPath1.lineTo(pentagram1Array[x][x - x], pentagram1Array[x][x - x + 1]);
        }
        canvas.drawPath(pentagramPath1, pentagramPainter);

        //creates a new path and adds 2 circles and draws them.
        pentagramPath2 = new Path();
        pentagramPath2.addCircle(pentagramCircle3X, pentagramCircle3Y, pentagramCircle3Radius, Path.Direction.CW);
        canvas.drawPath(pentagramPath2, pentagramPainter);
        pentagramPath2.addCircle(pentagramCircle4X, pentagramCircle4Y, pentagramCircle4Radius, Path.Direction.CW);
        canvas.drawPath(pentagramPath2, pentagramPainter);

        //Moves the path to the start of the pentagram then goes through the 2 dimensional array to obtain points and then draws the pentagram.
        pentagramPath2.moveTo(pentagram2Array[0][0], pentagram2Array[0][1]);
        for(int x = 1; x < pentagram2Array.length; x++){
            pentagramPath2.lineTo(pentagram2Array[x][x - x], pentagram2Array[x][x - x + 1]);
        }
        canvas.drawPath(pentagramPath2, pentagramPainter);

    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setScore(int score) {
        this.currentScore = score;
    }

    public int getWaves(){return currentWaves;}

    public void setWaves(int waves) {
        this.currentWaves = waves;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int lives) {
        this.numberOfLives = lives;
    }
}