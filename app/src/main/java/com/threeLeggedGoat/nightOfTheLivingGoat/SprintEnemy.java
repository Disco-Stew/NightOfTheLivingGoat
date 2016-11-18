package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Rect;
import java.util.ArrayList;

//References:
//http://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
//http://math.stackexchange.com/questions/274712/calculate-on-which-side-of-straign-line-is-dot-located
//http://www.gamefromscratch.com/post/2012/11/24/GameDev-math-recipes-Rotating-one-point-around-another-point.aspx


//Author Adam
public class SprintEnemy extends EnemyObject {

    private int projectileX;
    private int projectileY;

    private int lineAX;
    private int lineAY;
    private int lineBX;
    private int lineBY;
    private double degrees =0;
    private boolean leftOfLine;

    private final int DODGE_RADIUS;
    private final int SQUARED_DODGE_RADIUS;
    private final int DODGE_DISTANCE;
    private final double DEGREES_RADIANS_180 = Math.toRadians(180);

    private int dodgeCount;
    private int counter =0;
    private boolean dodging = false;

    private ArrayList<ProjectileObject> projectiles;

    public SprintEnemy(Assets assets, PlayerObject player, ArrayList<ProjectileObject> projectiles, int moveToPlayerCenterX, int moveToPlayerCenterY, int health, int damage, Viewport viewport, Scaler scaler) {
        super(assets, player, moveToPlayerCenterX, moveToPlayerCenterY, health, damage, viewport, scaler);

        this.projectiles = projectiles;
        walkSpeedDivider /=1.25;
        maxHealth /=2;
        currentHealth = maxHealth;
        foregroundPaint.setARGB(255,0,255,0);
        points *= 2;
        dodgeCount=1;
        DODGE_RADIUS = 150;
        SQUARED_DODGE_RADIUS = (int) Math.pow(DODGE_RADIUS,2);
        DODGE_DISTANCE = 100;
    }

    //Determines if the enemy should dodge or continue pathing towards the player
    //Author Adam
    @Override
    public void update(){
        //sets dodging to true if their is a projectile in range of the enemy
        // and has dodges left
        if (dodgeCount>0) {
            while (!dodging && counter < projectiles.size()){
                if (!projectiles.get(counter).isEnemyProjectile()) {
                    dodging = insideDodgeRange(projectiles.get(counter).getSpriteBounds(), projectiles.get(counter).getFacing());
                }
                counter++;
            }
        }

        if (dodging){
            dodgeDirection(projectiles.get(counter - 1).getFacing());

            spriteBounds.set(viewport.objectDrawX(this,player), viewport.objectDrawY(this, player), viewport.objectDrawX(this,player) + zombieWalk.getImage(0).getWidth(),
                    viewport.objectDrawY(this, player) + zombieWalk.getImage(0).getHeight());

            dodging = false;
            dodgeCount--;
        } else {
            super.update();
        }
        counter =0;
    }

    //Shaun
    @Override
    public void loadAnimations()
    {
        zombieWalk=new Animation(1,assets.sprintEnemyWalk);
    }

    //Determines if a projectile is within dodging range
    //Author Adam
    private boolean insideDodgeRange(Rect r, Facing facing) {
        //Finds the front of the projectile if it was facing up
        projectileX = r.centerX();
        projectileY = r.centerY() + r.width()/2;

        //finds the degrees needed to rotate the projectile co-ordinates
        //to make them match the projectiles Facing value
        switch (facing) {
            case UP:
                degrees =0;
                break;
            case UPRIGHT:
                degrees =45;
                break;
            case RIGHT:
                degrees =90;
                break;
            case DOWNRIGHT:
                degrees =135;
                break;
            case DOWN:
                degrees =180;
                break;
            case DOWNLEFT:
                degrees =225;
                break;
            case LEFT:
                degrees =270;
                break;
            case UPLEFT:
                degrees =315;
                break;
            default:
                break;
        }

        //Rotates the projectile co-ordinates by degrees and about the projectile center
       if (degrees != 0) {
            degrees = Math.toRadians(degrees);
            projectileX = (int) (r.centerX() + Math.cos(degrees) * (projectileX - r.centerX()) - Math.sin(degrees) * (projectileY - r.centerY()));
            projectileY = (int) (r.centerY() + Math.sin(degrees) * (projectileX - r.centerX()) + Math.cos(degrees) * (projectileY - r.centerY()));
        }
        //Returns true if the projectile co-ordinates are within dodge range of the enemy
        return ((Math.pow(projectileX - spriteBounds.centerX(),2) + Math.pow(projectileY - spriteBounds.centerY(),2)) < SQUARED_DODGE_RADIUS);
    }

    //Determines which direction the enemy should dodge via a line drawn through the dodge radius center
    //Author Adam
    private void dodgeDirection(Facing facing) {
        //Positions point A above the enemy centre and on the dodge radius circumference
        lineAX = spriteBounds.centerX();
        lineAY = spriteBounds.centerY() - DODGE_RADIUS;

        //Rotates the starting point of the line to match the projectile's direction of approach
        switch (facing) {
            case UP:
            case DOWN:
                degrees =0;
                break;
            case UPRIGHT:
            case DOWNLEFT:
                degrees =45;
                break;
            case LEFT:
            case RIGHT:
                degrees =90;
                break;
            case UPLEFT:
            case DOWNRIGHT:
                degrees =135;
                break;
        }

        //Rotates point A about the dodge radius center based
        if (degrees != 0) {
            degrees = Math.toRadians(degrees);
            lineAX = (int) (spriteBounds.centerX() + Math.cos(degrees) * (lineAX - spriteBounds.centerX()) - Math.sin(degrees) * (lineAY - spriteBounds.centerY()));
            lineAY = (int) (spriteBounds.centerY() + Math.sin(degrees) * (lineAX - spriteBounds.centerX()) + Math.cos(degrees) * (lineAY - spriteBounds.centerY()));
        }

        //positions point B on the opposite side of the circular dodge range from point A
        lineBX = (int) (spriteBounds.centerX() + Math.cos(DEGREES_RADIANS_180) * (lineAX - spriteBounds.centerX()) - Math.sin(DEGREES_RADIANS_180) * (lineAY - spriteBounds.centerY()));
        lineBY = (int) (spriteBounds.centerY() + Math.sin(DEGREES_RADIANS_180) * (lineAX - spriteBounds.centerX()) + Math.cos(DEGREES_RADIANS_180) * (lineAY - spriteBounds.centerY()));

        //Determines what side of the line drawn between A and B the projectile front is on
        //from the POV of standing at A and looking at B
        leftOfLine = (lineBX-lineAX)*(projectileY-lineAY)-(projectileX-lineAX)*(lineBY-lineAY) <= 0;

        //dodges away from the half of the circle that the projectile is in
        switch (facing) {
            case UP:
            case DOWN:
                if (leftOfLine) {dodgeLeft();} else {dodgeRight();}
                break;
            case UPRIGHT:
            case DOWNLEFT:
                if (leftOfLine) {dodgeUpLeft();} else {dodgeDownRight();}
                break;
            case LEFT:
            case RIGHT:
                if (leftOfLine) {dodgeUp();} else {dodgeDown();}
                break;
            case UPLEFT:
            case DOWNRIGHT:
                if (leftOfLine) {dodgeUpRight();} else {dodgeDownLeft();}
                break;
        }
    }

    //Author Adam
    private void dodgeUp() {
        updatePositionY(-DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeUpRight() {
        updatePositionX(DODGE_DISTANCE);
        updatePositionY(-DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeRight() {
        updatePositionX(DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeDownRight() {
        updatePositionX(DODGE_DISTANCE);
        updatePositionY(DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeDown() {
        updatePositionY(DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeDownLeft() {
        updatePositionX(-DODGE_DISTANCE);
        updatePositionY(DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeLeft() {
        updatePositionX(-DODGE_DISTANCE);
    }

    //Author Adam
    private void dodgeUpLeft() {
        updatePositionX(-DODGE_DISTANCE);
        updatePositionY(-DODGE_DISTANCE);
    }
}
