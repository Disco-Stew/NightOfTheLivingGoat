package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

/**
 * Created by Stuart on 04/03/2016.
 */

//not implemented in the game

public abstract class PowerupObject extends GameObject implements Collidable{

    private final int BUFFER_WIDTH = 50;

    private Rect spriteBounds;
    boolean forDestroy;
    private int screenX;
    private int screenY;
    private int duration;
    private int bufferX;
    private int bufferY;
    private Viewport viewport;
    private PlayerObject player;
    private boolean isActive;


    public PowerupObject(int screenX, int screenY, Viewport viewport, PlayerObject player){
        this.screenX = screenX;
        this.screenY = screenY;
        forDestroy = false;
        this.viewport = viewport;
        this.player = player;
        bufferX = (screenX / 2);
        bufferY = (screenY / 2);
        generateRandomStartingPosition((viewport.getCamX() + bufferX), (viewport.getCamY() + bufferY), (bufferX + BUFFER_WIDTH), (bufferY + BUFFER_WIDTH));
        spriteBounds = new Rect(positionX, positionY, positionX + 50, positionY + 50);
    }

    public void activate(PlayerObject player){

    }
    public void deactivate(PlayerObject player){

    }

    private void generateRandomStartingPosition(float screenCenterX, float screenCenterY, float bufferX, float bufferY){
        Random random = new Random();

        //Randomly positions enemy outside the screen
        switch (random.nextInt(4)) {
            case 0:
                //Moves enemy outside one side of the screen
                this.positionX = (int) (screenCenterX + bufferX);

                //Positions enemy at random point on that side
                randomisePositionY(random);
                break;
            case 1:
                this.positionX = (int) (screenCenterX - bufferX);
                randomisePositionY(random);
                break;
            case 2:
                this.positionY = (int) (screenCenterY + bufferY);
                randomisePositionX(random);
                break;
            case 3:
                this.positionY = (int) (screenCenterY - bufferY);
                randomisePositionX(random);
                break;
        }
    }

    private void randomisePositionX(Random random) {
        this.positionX += random.nextInt(screenX+(BUFFER_WIDTH*2)+1);
    }

    private void randomisePositionY(Random random) {
        this.positionY += random.nextInt(screenY+(BUFFER_WIDTH*2)+1);
    }
    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (viewport.onScreen(this, player)) {
            int x = viewport.objectDrawX(this, player);
            int y = viewport.objectDrawY(this,player);
            Rect box = new Rect(x, y, x+50, y-50);
            canvas.drawRect(box, paint);
        }
    }
    public void update() {}

    public void setForDestroy(){
        this.forDestroy = true;
    }

    @Override
    public void checkBounds() {}

    @Override
    public Rect getSpriteBounds() {return spriteBounds;}

    public Boolean getIsActive(){return isActive;}

    public void setIsActive(Boolean active){isActive = active;}
}

