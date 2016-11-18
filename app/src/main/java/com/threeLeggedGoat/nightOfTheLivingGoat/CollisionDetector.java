package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Stuart on 04/03/2016.
 */
public class CollisionDetector {

    //Stuart
    public void checkCollisions(ArrayList<EnemyObject> enemies, ArrayList<ProjectileObject> projectiles, ArrayList<PowerupObject> powerups, PlayerObject player, GameSoundPlayer soundPlayer){
        checkEnemiesHit(enemies, projectiles, soundPlayer);
        checkPlayerHitByEnemy(enemies, player);
        checkPowerupsHit(powerups, player);
        checkPlayerHitByEnemyProjectile(projectiles, player);
    }

    //Stuart
    private void checkEnemiesHit(ArrayList<EnemyObject> enemies, ArrayList<ProjectileObject> projectiles, GameSoundPlayer soundPlayer){
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < projectiles.size(); j++) {
                if (!projectiles.get(j).isEnemyProjectile()) {
                    if (enemies.get(i).getSpriteBounds().intersect(projectiles.get(j).getSpriteBounds())) {
                        enemies.get(i).damageEnemy(projectiles.get(j).getDamage());
                        projectiles.get(j).setForDestroy();
                        soundPlayer.kill();
                    }
                }
            }
        }
    }

    //Stuart
    private void checkPlayerHitByEnemy(ArrayList<EnemyObject> enemies, PlayerObject player){
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getSpriteBounds().intersect(player.getSpriteBounds())) {
                //if (isSpriteCollision(player.getSpriteBounds(), enemies.get(i).getSpriteBounds(), player.getFrame(), enemies.get(i).getFrame())){
                    player.updateHealth(player.getCurrentHealth() - enemies.get(i).getDamage());
                    if (player.getCurrentHealth() < 0) player.updateHealth(0);
                    enemies.get(i).setTouchingPlayer(true);
                //}
            }else{
                enemies.get(i).setTouchingPlayer(false);
            }
        }
    }

    //Adam
    private void checkPlayerHitByEnemyProjectile(ArrayList<ProjectileObject> projectiles, PlayerObject player){
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isEnemyProjectile()) {
                if (projectiles.get(i).getSpriteBounds().intersect(player.getSpriteBounds())) {
                    player.updateHealth(player.getCurrentHealth() - projectiles.get(i).getDamage());
                    projectiles.get(i).setForDestroy();
                }
            }
        }
    }

    //Stuart
    private void checkPowerupsHit(ArrayList<PowerupObject> powerups, PlayerObject player) {
        if (powerups.size() > 0) {
            for (int i = 0; i < powerups.size(); i++) {
                if (powerups.get(i).getSpriteBounds().intersect(player.getSpriteBounds())) {
                    powerups.get(i).activate(player);
                }
            }
        }
    }

    //Stuart
    // not implemented in the game

//    //based on code from techrepublic.com
//    private boolean  isSpriteCollision(Rect sprite1, Rect sprite2, Bitmap bm1, Bitmap bm2){ //checks pixel-wise whether the sprites are colliding
//        if (sprite1.left<=0 && sprite2.left<=0 && sprite1.top<=0 && sprite2.top<=0) return false;
//        Rect r3 = new Rect(sprite1);
//        for (int i = sprite1.left; i<sprite1.right; i++) {
//            for (int j = sprite1.top; j<sprite1.bottom; j++) {
//                if (bm1.getPixel(i-r3.left, j-r3.top)!= Color.TRANSPARENT) {
//                    if (bm2.getPixel(i-sprite2.left, j-sprite2.top) != Color.TRANSPARENT) {
////                        lastCollision = new Point(sprite2.x +
////                                i-r2.left, sprite2.y + j-r2.top);
//                        return true;
//
//                    }else{
//
//                    }
//
//                }
//
//            }
//
//        }
////        lastCollision = new Point(-1,-1);
//        return false;
//    }

}
