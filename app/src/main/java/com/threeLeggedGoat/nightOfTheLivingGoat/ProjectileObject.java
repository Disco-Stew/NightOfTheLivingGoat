package com.threeLeggedGoat.nightOfTheLivingGoat;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public abstract class ProjectileObject extends GameObject implements  Collidable {

    public Assets assets;
    private Facing facing;

    private int damage;

    public Rect spriteBounds;

    private boolean enemyProjectile;

    PlayerObject player;
    Viewport viewport;

    public ProjectileObject(int positionX, int positionY, int walkSpeedPerSecond, int walkSpeedDivider,
                            int maxHealth, int currentHealth, int damage, boolean enemyProjectile, Assets assets, Facing facing,
                            Viewport viewport, PlayerObject player) {
        this.facing = facing;
        this.positionX = positionX;
        this.positionY = positionY;
        this.walkSpeedPerSecond = walkSpeedPerSecond;
        this.walkSpeedDivider = walkSpeedDivider;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.assets = assets;
        this.damage = damage;
        this.enemyProjectile = enemyProjectile;
        spriteBounds = new Rect();
        this.player = player;
        this.viewport = viewport;

        forDestroy = false;
    }

    public void update(){
        switch (facing){
            case RIGHT:
            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            break;
            case LEFT:
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
                break;
            case UP:
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            break;
            case DOWN:
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            break;
            case UPRIGHT:
            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            break;
            case DOWNRIGHT:
            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            break;
            case DOWNLEFT:
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            break;
            case UPLEFT:
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            break;
        }
        setSpriteBounds();
    }

    public abstract void setSpriteBounds();

    public Rect getSpriteBounds(){ return spriteBounds;}

    @Override
    public void checkBounds() {

    }

    public int getDamage() {
        return damage;
    }

    public boolean isEnemyProjectile(){
        return enemyProjectile;
    }

    public Facing getFacing() {
        return facing;
    }

}
