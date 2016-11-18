package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

//Author Adam
public class CircularProjectile extends ProjectileObject {
    Bitmap image;


    public CircularProjectile(int positionX, int positionY, int walkSpeedPerSecond, int walkSpeedDivider,
                              int maxHealth, int currentHealth, int damage, boolean enemyProjectile, Assets assets, Facing facing,
                              Viewport viewport, PlayerObject player) {
        super(positionX, positionY, walkSpeedPerSecond, walkSpeedDivider, maxHealth, currentHealth, damage, enemyProjectile, assets, facing,viewport,player);
        this.image = assets.projectileGeneric;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Author Adam
    //Circular projectiles have the same appearance no matter their facing value
    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(viewport.onScreen(this,player)) {
            canvas.drawBitmap(image, viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), paint);
        }
    }

    //Author Adam
    @Override
    public void setSpriteBounds(){
        getSpriteBounds().set(viewport.objectDrawX(this,player), viewport.objectDrawY(this,player), viewport.objectDrawX(this,player) + getSpriteBounds().width(), viewport.objectDrawY(this,player) + getSpriteBounds().height());
    }
}
