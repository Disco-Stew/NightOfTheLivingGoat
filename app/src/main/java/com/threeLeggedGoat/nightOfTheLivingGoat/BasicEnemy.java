package com.threeLeggedGoat.nightOfTheLivingGoat;
//Author Adam
public class BasicEnemy extends EnemyObject {

    public BasicEnemy(Assets assets, PlayerObject player, int moveToPlayerCenterX, int moveToPlayerCenterY, int health, int damage, Viewport viewport, Scaler scaler) {
        super(assets, player, moveToPlayerCenterX, moveToPlayerCenterY, health, damage, viewport, scaler);
    }

    //Author Adam
    @Override
    public void loadAnimations()
    {
        zombieWalk=new Animation(1, assets.basicEnemyWalk);
    }

}
