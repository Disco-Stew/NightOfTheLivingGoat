package com.threeLeggedGoat.nightOfTheLivingGoat;

import java.util.ArrayList;

//Author Adam
public class Boss extends EnemyObject {
    private int updates;
    private boolean alternateFiringMode = true;
    ArrayList<ProjectileObject> projectiles;
    PlayerObject player;

    private final int FIRING_DELAY = 60;
    private final int PROJECTILE_DAMAGE = 10;

    public Boss(Assets assets, PlayerObject player, ArrayList<ProjectileObject> projectiles, int moveToPlayerCenterX, int moveToPlayerCenterY, int health, int damage, Viewport viewport, Scaler scaler) {
        super(assets, player, moveToPlayerCenterX, moveToPlayerCenterY, health, damage, viewport, scaler);

        this.projectiles = projectiles;
        this.player= player;
        walkSpeedDivider *= 2;
        maxHealth *= 4;
        currentHealth = maxHealth;
        foregroundPaint.setARGB(255,255,255,255);
        points *=2;
    }

    //Alternates between firing projectiles in all straight directions and all diagonal directions
    //Author Adam
    @Override
    public void update(){
        super.update();
        updates++;

        if (updates == FIRING_DELAY) {
            if (alternateFiringMode){
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.UP,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWN,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.LEFT,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.RIGHT,viewport,player));
            } else {
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.UPLEFT,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.UPRIGHT,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWNRIGHT,viewport,player));
                projectiles.add(new CircularProjectile(getPositionX() + (zombieWalk.getImage(0).getWidth()/2), getPositionY() + (zombieWalk.getImage(0).getHeight()/2),
                        500, walkSpeedDivider*2, 100, 100, PROJECTILE_DAMAGE, true, assets, Facing.DOWNLEFT,viewport,player));

            }
            updates =0;
            alternateFiringMode = !alternateFiringMode;
        }
    }

    //Author Shaun
    @Override
    void loadAnimations()
    {
        zombieWalk = new Animation(3, assets.bossEnemyWalk);
    }
}
