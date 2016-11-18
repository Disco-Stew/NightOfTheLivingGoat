package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RectangularProjectile extends ProjectileObject {

    public RectangularProjectile(int positionX, int positionY, int walkSpeedPerSecond, int walkSpeedDivider,
                            int maxHealth, int currentHealth, int damage, boolean enemyProjectile, Assets assets, Facing facing,
                                 Viewport viewport,PlayerObject player) {
        super(positionX,positionY,walkSpeedPerSecond,walkSpeedDivider,maxHealth,currentHealth,
                damage,enemyProjectile,assets,facing,viewport,player);

        calcPositionX();
        calcPositionY();
    }

    @Override
    public void draw(Canvas canvas,Paint paint) {
        switch (getFacing()) {
            case RIGHT:
                canvas.drawBitmap(assets.projectileRight, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case LEFT:
                canvas.drawBitmap(assets.projectileLeft, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case UP:
                canvas.drawBitmap(assets.projectileUp, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case DOWN:
                canvas.drawBitmap(assets.projectileDown, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case UPRIGHT:
                canvas.drawBitmap(assets.projectileUpRight, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case DOWNRIGHT:
                canvas.drawBitmap(assets.projectileDownRight, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case DOWNLEFT:
                canvas.drawBitmap(assets.projectileDownLeft, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
            case UPLEFT:
                canvas.drawBitmap(assets.projectileUpLeft, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
                break;
        }
    }

    @Override
    public void setSpriteBounds(){
        switch (getFacing()){
            case RIGHT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileRight.getWidth(), viewport.objectDrawY(this,player) + assets.projectileRight.getHeight());
                break;
            case LEFT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileLeft.getWidth(), viewport.objectDrawY(this,player) + assets.projectileLeft.getHeight());
                break;
            case UP:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileUp.getWidth(), viewport.objectDrawY(this,player) + assets.projectileUp.getHeight());
                break;
            case DOWN:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileDown.getWidth(), viewport.objectDrawY(this,player) + assets.projectileDown.getHeight());
                break;
            case UPRIGHT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileUpRight.getWidth(), viewport.objectDrawY(this,player) + assets.projectileUpRight.getHeight());
                break;
            case DOWNRIGHT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileDownRight.getWidth(), viewport.objectDrawY(this,player) + assets.projectileDownRight.getHeight());
                break;
            case DOWNLEFT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileDownLeft.getWidth(), viewport.objectDrawY(this,player) + assets.projectileDownLeft.getHeight());
                break;
            case UPLEFT:
                getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + assets.projectileUpLeft.getWidth(), viewport.objectDrawY(this,player) + assets.projectileUpLeft.getHeight());
                break;
        }
    }

    public void calcPositionX(){
        switch(getFacing()){
            case RIGHT:
                positionX += 85;
                break;
            case LEFT:
                positionX -= 5;
                break;
            case UP:
                positionX += 78;
                break;
            case DOWN:
                positionX += 26;
                break;
            case UPRIGHT:
                positionX += 85;
                break;
            case DOWNRIGHT:
                positionX += 55;
                break;
            case DOWNLEFT:
                positionX -= 5;
                break;
            case UPLEFT:
                positionX += 30;
                break;
        }
    }

    public void calcPositionY(){
        switch(getFacing()){
            case RIGHT:
                positionY += 75;
                break;
            case LEFT:
                positionY += 25;
                break;
            case UP:
                positionY -= 10;
                break;
            case DOWN:
                positionY += 80;
                break;
            case UPRIGHT:
                positionY += 30;
                break;
            case DOWNRIGHT:
                positionY += 80;
                break;
            case DOWNLEFT:
                positionY += 45;
                break;
            case UPLEFT:
                positionY -= 5;
                break;
        }
    }
}