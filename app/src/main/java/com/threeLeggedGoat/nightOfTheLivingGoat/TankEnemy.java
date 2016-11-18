package com.threeLeggedGoat.nightOfTheLivingGoat;

import java.util.ArrayList;

//References:
//http://wikicode.wikidot.com/get-angle-of-line-between-two-points

//Author Adam
public class TankEnemy extends EnemyObject {
    private ArrayList<ProjectileObject> projectiles;
    private int updates;
    private final int FIRING_DELAY = 120;
    private final int PROJECTILE_DAMAGE = 5;

    private double angle;
    private double angleDifference;
    private int facingDirection;
    private double[] firingAngles = {0,45,90,135,180,225,270,315};
    
    public TankEnemy(Assets assets, PlayerObject player, ArrayList<ProjectileObject> projectiles, int moveToPlayerCenterX, int moveToPlayerCenterY, int health, int damage, Viewport viewport, Scaler scaler) {
        super(assets, player, moveToPlayerCenterX, moveToPlayerCenterY, health, damage, viewport, scaler);
        walkSpeedDivider *= 1.5;
        maxHealth *= 2;
        currentHealth = maxHealth;
        foregroundPaint.setARGB(255,0,0,255);
        points *= 2;
        this.projectiles = projectiles;
    }

    //Author Shaun
    void loadAnimations() {
        zombieWalk = new Animation(1, assets.tankEnemyWalk);
    }


    //Fires a bullet in the (closest available) direction of this enemy's destination
    @Override
    public void update() {
        super.update();
        updates++;

        if (updates == FIRING_DELAY) {
            //Returns how many degrees a horizontal line coming from the enemy centre
            //has to be rotated clockwise to reach the enemy's destination
            angle = Math.toDegrees(Math.atan2(getDestinationY() - getCenterY(), getDestinationX() - getCenterX()));

            if (angle <0){
                angle = 360 + angle;
            }

            angleDifference = 45;
            facingDirection =0;

            //Finds the Facing angle that closest matches the actual angle between enemy & player
            for (int i =0; i <  firingAngles.length; i++){
                if (doubleDifference(firingAngles[i], angle) < angleDifference){
                    angleDifference = doubleDifference(firingAngles[i], angle);
                    facingDirection = i;
                }
            }

            switch (facingDirection){
                case 0:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.RIGHT, viewport, player));
                    break;
                case 1:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWNRIGHT, viewport, player));
                    break;
                case 2:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWN, viewport, player));
                    break;
                case 3:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWNLEFT, viewport, player));
                    break;
                case 4:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.LEFT, viewport, player));
                    break;
                case 5:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.UPLEFT, viewport, player));
                    break;
                case 6:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2,
                            100, 100, PROJECTILE_DAMAGE, true, assets, Facing.UP, viewport, player));
                    break;
                case 7:
                    projectiles.add(new CircularProjectile(getCenterX(), getCenterY(), 500, walkSpeedDivider * 2, 100,
                            100, PROJECTILE_DAMAGE, true, assets, Facing.UPRIGHT, viewport, player));
                    break;
            }
            updates = 0;
        }
    }
    //Author Adam
    private double doubleDifference(double x, double y){
        if (x>y) return x-y; else return y-x;
    }
}