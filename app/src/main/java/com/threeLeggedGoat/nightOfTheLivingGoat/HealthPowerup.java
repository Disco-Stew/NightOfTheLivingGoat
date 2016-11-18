package com.threeLeggedGoat.nightOfTheLivingGoat;

/**
 * Created by Stuart on 14/03/2016.
 */

//Stuart
//not implemented in the game

public class HealthPowerup extends PowerupObject{

    final int healthBonus = 50;

    public HealthPowerup(int screenX, int screenY, Viewport viewport, PlayerObject player) {
        super(screenX, screenY, viewport, player);
    }
    @Override
    public void activate(PlayerObject player){
        player.updateHealth(player.getCurrentHealth()+50);
        if (player.getCurrentHealth()>player.getMaxHealth()){
            player.updateHealth(player.getMaxHealth());
        }
    }

    @Override
    public void deactivate(PlayerObject player){
        this.setForDestroy();
    }
}
