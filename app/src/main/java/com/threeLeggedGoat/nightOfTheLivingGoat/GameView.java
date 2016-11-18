package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//ROBERT/SHAUN/AREK/STUART

public class GameView extends SurfaceView implements Runnable {


    private int radius = 125;

    HUD hud;

    private JoystickView joystick;
    private JoystickView joystick2;

    private JoystickMovedListener joystickMovedListener;
    private JoystickMovedListener joystickMovedListener2;

    private long lastTime = System.nanoTime();

    private double numOfTicks = 60.0;
    private double ns = 1000000000/numOfTicks;
    private double dt = 0;
    int updates = 0;
    int frames = 0;


    Scaler scaler;

    //its a thread.
    Thread gameThread = null;

    //Used for the paint and draw in a thread.
    //Locks the drawing surface as OS can make draw calls i.e text notifications
    private SurfaceHolder ourHolder;

    //Game is running or not
    //Volatile allows it to be in and out of a thread
    volatile boolean playing;


    //Canvas and paint. Graphics.

    private Canvas canvas;
    private Paint paint;

    private Assets assets;
    private Viewport viewport;
    private GameSoundPlayer soundPlayer;
    Context context;
    private PauseScreen ps;


    // Defines walking speed in pixels
    private int walkSpeedPerSecond = 150;
    private int walkSpeedDivider = 50;

    private int initialLives = 3;
    private int initialWaves = 0;
    private int initialScore = 0;
    private int currentHealth = 100;
    private int maxHealth = 100;
    private int lives = 3;
    private int fireRate = 250; //delay between bullets in ms


    // Defines the start position for the character
    private int playerXPosition = 897;
    private int playerYPosition = 540;

    private Handler handler;

    private boolean paused;
    private boolean first;

    private Activity act;

    private Bitmap gameOverImage;
    private boolean gameOver;
    private int count;

    //Constructor for GameView. ROBERT
    public GameView(Context context,Typeface typeface, Activity currentAct) {

        // calls super class uses the context passed as parameter

        super(context);
        this.context=context;
        act = currentAct;

        first = true;
        paused = false;
        playing = true;

        scaler = new Scaler(context);
        scaler.scaler();
        joystick = new JoystickView(context, scaler.scaledX(radius));
        joystick2 = new JoystickView(context, scaler.scaledX(radius));
        playerXPosition = scaler.scaledX(playerXPosition);
        playerYPosition = scaler.scaledY(playerYPosition);
      
        ourHolder = getHolder();


        hud = new HUD(initialLives, initialScore,initialWaves,scaler,typeface);
        paint = new Paint();

        assets = new Assets(this.getResources());

        viewport = new Viewport(scaler,assets.map.getWidth(),assets.map.getHeight());
        soundPlayer = new GameSoundPlayer(context);

        gameOverImage=BitmapFactory.decodeResource(this.getResources(), R.drawable.gameover);
        gameOverImage=Bitmap.createScaledBitmap(gameOverImage,1200,180,true);
        handler= new Handler(new PlayerObject(playerXPosition,playerYPosition,walkSpeedPerSecond,walkSpeedDivider,maxHealth,currentHealth, assets, scaler.getDisplayX(), scaler.getDisplayY(), viewport, lives, fireRate),assets,soundPlayer,viewport,hud, scaler);


    }

    //runs the game includes update and draw methods and game loop
    //acquires time from system uses this to calculate fps
    //ROBERT/SHAUN
    @Override
    public void run() {
        while (playing) { //loop by Shaun
            long now = System.nanoTime();
            dt += (now - lastTime) / ns;
            lastTime = now;
            while (dt >= 1) {
                update();
                updates++;
                dt--;
            }
            draw();
            frames++;
        }
    }

    // this updates everything that requires an update. ROBERT/SHAUN
    public void update() {

        //calls handler update for updating all objects
        if(!paused&&!gameOver) {
            handler.update();
            if (handler.player.getLives() == 0){
                gameOver=true;
            }
        }

        if (gameOver)
        {
            count++;
            if (count>=180)
            {
                gameOver=false;
                act.finish();
            }
        }

    }

    //Draws to the screen.ROBERT/SHAUN
    public void draw() {

        if (ourHolder.getSurface().isValid()&&!paused) {
            // lock canvas for drawing
            // Makes the drawing surface the focus of the canvas object

            canvas = ourHolder.lockCanvas();

            //draws the backgrounds. uses rgb colours.
            //Color object is used and given the values as constructor to make specific colour.

            //calls handler draw method for drawing all GameObjects
            handler.draw(canvas, paint);

            //Draws the joysticks
            joystick.onMeasure(scaler.scaledX(350), scaler.scaledY(1700));
            joystick.draw(canvas);
            joystick2.onMeasure(scaler.scaledX(3250), scaler.scaledY(1700));
            joystick2.draw(canvas);


            if (gameOver)
            {
                canvas.drawARGB(255,0,0,0);
                canvas.drawBitmap(gameOverImage, 450, 400, paint);
            }

            //Draws to the screen
            //and then unlocks the drawing surface again
            //essentially undoes the lock canvas
            //allows you to dictate what happens then executes the steps.

            ourHolder.unlockCanvasAndPost(canvas);

        }
    }
    //Deals with pausing AREK/ROBERT.
    public void pause() {
        paused=true;
        soundPlayer.stop();
        ps = new PauseScreen(act, gameOver);
        ps.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                resume();
            }
        });

        //makes the background translucent
        ps.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(150,160,160,160)));
        ps.show();
    }

    //Resumes the game from pausing. AREK/ROBERT
    public void resume() {
        if (first){
            gameThread = new Thread((this));
            gameThread.start();
            first=false;
        }else if(ps.isPlaying()){
            paused =false;
            soundPlayer.resume();
        }else{
            playing = ps.isPlaying();
        }
    }
    //Deals with touches to the screen.ROBERT
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        //Governs joystick movement and adds joystick listener
        //Reacts to touch and triggers the boolean values based on the tilt,pan
        //it also sets all to false after stick is released causing movement to stop

            joystickMovedListener = new JoystickMovedListener() {
                @Override
                public void OnMoved(int pan, int tilt) { // Robert and Shaun
                    // Move up
                    if (pan >= -5 && pan <= 5 && tilt >= -50 && tilt < 0) {

                        handler.player.direction=MoveDirection.MOVEUP;

                        handler.player.legFacing=Facing.UP;
                    }
                    // Move right
                    else if (pan <= 50 && pan > 0 && tilt >= -5 && tilt <= 5) {

                        handler.player.direction=MoveDirection.MOVERIGHT;

                        handler.player.legFacing = Facing.RIGHT;
                    }
                    // Move left
                    else if (pan >= -50 && pan <0 && tilt >= -5 && tilt <=5) {

                        handler.player.direction=MoveDirection.MOVELEFT;

                        handler.player.legFacing = Facing.LEFT;
                    }

                        // Move Down
                    else if (pan >= -5 && pan <= 5 && tilt <= 50 && tilt > 0) {

                        handler.player.direction=MoveDirection.MOVEDOWN;

                        handler.player.legFacing = Facing.DOWN;
                    }

                    // Move left
                    else if (pan >= -50 && pan < 0 && tilt >= -5 && tilt <= 5) {

                        handler.player.direction=MoveDirection.MOVELEFT;

                        handler.player.legFacing = Facing.LEFT;
                    }

                    // Move DiagonalUpRight
                    else if (pan >= 6 && pan <= 50 && tilt >= -50 && tilt <= -6) {

                        handler.player.direction=MoveDirection.MOVEUPRIGHT;

                        handler.player.legFacing = Facing.UPRIGHT;
                    }
                    // Move Diagonal DownRight
                    else if (pan >= 6 && pan <= 50 && tilt >= 6 && tilt <= 50) {

                        handler.player.direction=MoveDirection.MOVEDOWNRIGHT;

                        handler.player.legFacing = Facing.DOWNRIGHT;
                    }
                    // Move DiagonalUpLeft
                    else if (pan >= -50 && pan <= -6 && tilt >= -50 && tilt <= -6) {

                        handler.player.direction=MoveDirection.MOVEUPLEFT;

                        handler.player.legFacing = Facing.UPLEFT;
                    }
                    //Move DiagonalDownLeft
                    else if (pan >= -50 && pan <= -6 && tilt >= 6 && tilt <= 50) {

                        handler.player.direction=MoveDirection.MOVEDOWNLEFT;

                        handler.player.legFacing = Facing.DOWNLEFT;
                    }

                }

                @Override
                public void OnReleased() {
                    handler.player.direction=MoveDirection.NOTMOVING;
                }

            };
            joystick.setOnJoystickMovedListener(joystickMovedListener);


        //Firing joystick the right hand side joystick. ROBERT/STUART
            joystickMovedListener2 = new JoystickMovedListener() { //Robert and Shaun
                @Override
                public void OnMoved(int pan, int tilt) {
                    // Face and shoot up
                    if (pan >= -5 && pan <= 5 && tilt >= -50 && tilt < 0) {
                        handler.player.bodyFacing = Facing.UP;
                        handler.player.firing = true;
                    }
                    // Face and shoot right
                    else if (pan <= 50 && pan > 0 && tilt >= -5 && tilt <= 5) {
                        handler.player.bodyFacing = Facing.RIGHT;
                        handler.player.firing = true;
                    }
                    // Face and shoot Down
                    else if (pan >= -5 && pan <= 5 && tilt <= 50 && tilt > 0) {
                        handler.player.bodyFacing = Facing.DOWN;
                        handler.player.firing = true;
                    }
                    // Face and shoot left
                    else if (pan >= -50 && pan < 0 && tilt >= -5 && tilt <= 5) {
                        handler.player.bodyFacing = Facing.LEFT;
                        handler.player.firing = true;
                    }
                    // Face and shoot DiagonalUpRight
                    else if (pan >= 6 && pan <= 50 && tilt >= -50 && tilt <= -6) {
                        handler.player.bodyFacing = Facing.UPRIGHT;
                        handler.player.firing = true;
                    }
                    // Face and shoot DownRight
                    else if (pan >= 6 && pan <= 50 && tilt >= 6 && tilt <= 50) {
                        handler.player.bodyFacing = Facing.DOWNRIGHT;
                        handler.player.firing = true;
                    }
                    // Face and shoot DiagonalUpLeft
                    else if (pan >= -50 && pan <= -6 && tilt >= -50 && tilt <= -6) {
                        handler.player.bodyFacing = Facing.UPLEFT;
                        handler.player.firing = true;
                    }
                    // Face and shoot DiagonalDownLeft
                    else if (pan >= -50 && pan <= -6 && tilt >= 6 && tilt <= 50) {
                        handler.player.bodyFacing = Facing.DOWNLEFT;
                        handler.player.firing = true;
                    }
                }

                @Override
                public void OnReleased() {
                    handler.player.firing = false;
                }

            };
            joystick2.setOnJoystickMovedListener(joystickMovedListener2);

         if (motionEvent.getX() <= scaler.scaledX(300) && motionEvent.getY() >= scaler.scaledY(600)) {
            joystick.onTouchEvent(motionEvent);
          }

        if (motionEvent.getX() >= scaler.scaledX(800) && motionEvent.getY() >= scaler.scaledY(600)) {
            joystick2.onTouchEvent(motionEvent);
      }
        return true;
    }

    //Is the pause screen showing or not. Adam
    public boolean isPauseScreenShowing(){
        if (ps == null){
            return false;
        }  else {
            return ps.isShowing();
        }
    }
}