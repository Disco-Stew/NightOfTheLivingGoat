package com.threeLeggedGoat.nightOfTheLivingGoat;

/**
 * Created by Stuart on 07/03/2016.
 */

//Stuart
//not implemented in the game

public class RiflePowerup extends PowerupObject {

    final int FIRERATE = 125; //delay between bullets in ms

    public RiflePowerup(int screenX, int screenY, Viewport viewport, PlayerObject player){
        super(screenX, screenY, viewport, player);
    }

    @Override
    public void activate(PlayerObject player){
        player.fireRate = FIRERATE;
        player.riflePowerup = true;
        setIsActive(true);
    }

    @Override
    public void deactivate(PlayerObject player){
        player.fireRate = 250;
        setIsActive(false);
        this.setForDestroy();
    }
}
