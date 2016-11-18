package com.threeLeggedGoat.nightOfTheLivingGoat;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public  abstract class  GameObject {

    int positionX;
    int positionY;
    int currentHealth;
    int maxHealth;
    int walkSpeedPerSecond;
    int walkSpeedDivider;
    boolean forDestroy;

    public int getPositionX() {
        return positionX;
    }

    public void updatePositionX(int positionX) {
        this.positionX += positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void updatePositionY(int positionY) {
        this.positionY += positionY;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void updateHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getWalkSpeedPerSecond() {
        return walkSpeedPerSecond;
    }

    public int getWalkSpeedDivider() {
        return walkSpeedDivider;
    }

    public void setWalkSpeedDivider(int walkSpeedDivider) {
        this.walkSpeedDivider = walkSpeedDivider;
    }

    public void setForDestroy(){
        forDestroy = true;
    }

    abstract void draw(Canvas canvas, Paint paint);

    abstract void update();

}