package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;


import java.util.ArrayList;

public class Handler {
    int walkSpeedDivider = 50;

    PlayerObject player;
    GameSoundPlayer soundPlayer;
    CollisionDetector collisionDetector;

    //ArrayLists allow easy tracking of multiple instances of similar object types, as well as removal and subsequent garbage collection
    private ArrayList<EnemyObject> enemies;
    private ArrayList<ProjectileObject> projectiles;
    private ArrayList<PowerupObject> powerups;

    Assets assets;

    Viewport viewport;

    private float lastBulletFired = 0;
    private int bulletDamage = 5;

    private HUD hud;
    private Waves waves;
    private Scaler scaler;

    public Handler(PlayerObject player,Assets assets,GameSoundPlayer sounds, Viewport viewport, HUD hud, Scaler scaler)
    {
        this.player=player;
        this.assets=assets;
	    this.hud = hud;
        this.viewport = viewport;
        this.scaler = scaler;

        enemies=new ArrayList<>(1);
        projectiles=new ArrayList<>(1);
        powerups=new ArrayList<>(1);
        soundPlayer=sounds;

        collisionDetector = new CollisionDetector();
        waves = new Waves(enemies, projectiles, assets, player, viewport, scaler);
    }
    int i = 1;

    public void update() { //updates all GameObjects and HUD
        player.update();

        if (player.getCurrentHealth() == 0) {
            if (player.getLives() > 0) {
                player.setLives(player.getLives() - 1);
                player.updateHealth(player.getMaxHealth());
                player.resetPosition();
                enemies.clear();
                projectiles.clear();
                waves.resetWave();
            }
        }

        //plays footsteps soundFX when player is moving
        if(player.direction!=MoveDirection.NOTMOVING){
            soundPlayer.walk();
        }

        if (SystemClock.uptimeMillis() - lastBulletFired >= player.fireRate && player.firing) {
            playerCreateProjectile(new RectangularProjectile(player.getPositionX(), player.getPositionY(), 500, walkSpeedDivider, 100, 100, bulletDamage, false, assets, player.bodyFacing,viewport,player));
            lastBulletFired = SystemClock.uptimeMillis();
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        collisionDetector.checkCollisions(enemies, projectiles, powerups, player, soundPlayer);

        hud.setNumberOfLives(player.lives);
        hud.setWaves(waves.getWaveCount());
        hud.setScore(player.getScore());

        waves.update();


        for (ProjectileObject x: projectiles) {
            if(!viewport.onScreen(x,player)){
                x.setForDestroy();
            }
        }
        searchAndDestroy();
    }

    public void draw(Canvas canvas, Paint paint) { //Draws all GameObjects and HUD


        canvas.drawBitmap(assets.map,viewport.viewportX(player.getPositionX()),viewport.viewportY(player.getPositionY()), paint);

        player.draw(canvas, paint);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas, paint);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(canvas, paint);
        }

        for (int i = 0; i < powerups.size(); i++) {
            powerups.get(i).draw(canvas, paint);
        }

        hud.draw(canvas);

    }

    public void playerCreateProjectile(ProjectileObject projectile)//creates a projectile
    {
        projectiles.add(projectile);
        soundPlayer.shoot();
    }


    public void createPowerup(PowerupObject powerup) {
        powerups.add(powerup);
    }

    private void searchAndDestroy() { //traverses the ArrayLists and removes those marked for destruction

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isForDestroy()) enemies.remove(i);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).forDestroy) projectiles.remove(i);
        }

        for (int i = 0; i < powerups.size(); i++) {
            if (powerups.get(i).forDestroy) powerups.remove(i);
        }
    }
}

