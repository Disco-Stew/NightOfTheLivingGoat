package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

//this class includes boolean for rifle powerup, though the functionality was not quite achieved

public class PlayerObject extends GameObject implements  Collidable{

    Animation pistolWalkRight, pistolWalkLeft, pistolWalkUp, pistolWalkDown,
            pistolWalkDownLeft, pistolWalkDownRight, pistolWalkUpLeft, pistolWalkUpRight,

    rifleWalkRight, rifleWalkLeft, rifleWalkDown, rifleWalkUp, rifleWalkUpRight, rifleWalkUpLeft,
    rifleWalkDownRight, rifleWalkDownLeft,

    walkRightLegs, walkLeftLegs, walkUpLegs, walkDownLegs, walkDownLeftLegs,
            walkDownRightLegs, walkUpLeftLegs, walkUpRightLegs;

    Facing legFacing;
    Facing bodyFacing;
    int legXOffset;
    int legYOffset;

    int lives;
    private int score;

    private int redRecLeft;
    private int redRecTop;
    private int redRecRight;
    private int redRecBot;

    private int greenRecLeft;
    private int greenRecTop;
    private int greenRecRight;
    private int greenRecBot;

    private int blackRecLeft;
    private int blackRecTop;
    private int blackRecRight;
    private int blackRecBot;


    // tracks if moving or not
    MoveDirection direction;

    //tracks if player is at of the screen
    boolean atTopEdge = false;
    boolean atRightEdge = false;
    boolean atBottomEdge = false;
    boolean atLeftEdge = false;

    //tracks if player is firing bullets
    boolean firing;

    boolean riflePowerup;
    boolean forDestroy;
    int fireRate;  //bullet delay in ms

    //used for viewport
    private int screenX;
    private int screenY;
    private int drawPositionX;
    private int drawPositionY;
    Viewport viewport;

    private Rect spriteBounds;
    private Rect greenRec = new Rect();
    private Rect redRec = new Rect();
    private Rect blackRec = new Rect();

    private Paint greenPainter;
    private Paint redPainter;
    private Paint blackPainter;

    //Robert
    public PlayerObject(int positionX, int positionY, int walkSpeedPerSecond, int walkSpeedDivider,
                        int maxHealth, int currentHealth ,Assets assets,int screenX, int screenY, Viewport viewport,
                        int lives, int fireRate) {

        this.walkSpeedPerSecond = walkSpeedPerSecond;
        this.walkSpeedDivider = walkSpeedDivider;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.lives = lives;
        this.screenX = screenX;
        this.screenY = screenY;

        this.positionX = (screenX/2);
        this.positionY = (screenY/2);
        drawPositionX = this.positionX;
        drawPositionY = this.positionY;
        this.viewport = viewport;
        atTopEdge = false;
        atRightEdge = false;
        atBottomEdge = false;
        atLeftEdge = false;
        this.fireRate = fireRate;
        this.forDestroy = false;

        legFacing = Facing.RIGHT;
        bodyFacing = Facing.RIGHT;
        direction = MoveDirection.NOTMOVING;

        legXOffset = 4;
        legYOffset = 30;
        loadAnimations(assets);
        spriteBounds = new Rect(positionX, positionY, positionX + pistolWalkRight.getImage(0).getWidth(), positionY + pistolWalkRight.getImage(0).getHeight());

        //Sets green rectangle's paint
        greenPainter = new Paint();
        greenPainter.setARGB(255, 0, 175, 0);

        //Sets red rectangle's paint
        redPainter = new Paint();
        redPainter.setARGB(255, 175, 0, 0);

        //Sets black rectangle's paint
        blackPainter = new Paint();
        blackPainter.setARGB(255, 0, 0, 0);
        blackPainter.setStyle(Paint.Style.STROKE);
        blackPainter.setStrokeWidth(4);

    }

    private void updateDrawPos(boolean X, boolean Y) {
        if (direction==MoveDirection.MOVERIGHT) {
            if (X) {
                drawPositionX += (walkSpeedPerSecond / walkSpeedDivider);
                //  pistolWalkRight.runAnimation();
            }
        } else if (direction==MoveDirection.MOVELEFT) {
            if (X) {
                drawPositionX += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
            // pistolWalkLeft.runAnimation();
        } else if (direction==MoveDirection.MOVEUP) {
            if (Y) {
                drawPositionY += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
            // pistolWalkUp.runAnimation();
        } else if (direction==MoveDirection.MOVEDOWN) {
            if (Y) {
                drawPositionY += (walkSpeedPerSecond / walkSpeedDivider);
            }
            // pistolWalkDown.runAnimation();
        } else if (direction==MoveDirection.MOVEUPRIGHT) {
            if (X) {
                drawPositionX += (walkSpeedPerSecond / walkSpeedDivider);
            }
            if (Y) {
                drawPositionY += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
        } else if (direction==MoveDirection.MOVEDOWNRIGHT) {
            if (X) {
                drawPositionX += (walkSpeedPerSecond / walkSpeedDivider);
            }
            if (Y) {
                drawPositionY += (walkSpeedPerSecond / walkSpeedDivider);
            }
            // pistolWalkDownRight.runAnimation();
        } else if (direction==MoveDirection.MOVEDOWNLEFT) {
            if (X) {
                drawPositionX += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
            if (Y) {
                drawPositionY += (walkSpeedPerSecond / walkSpeedDivider);
            }
            // pistolWalkDownLeft.runAnimation();
        } else if (direction==MoveDirection.MOVEUPLEFT) {
            if (X) {
                drawPositionX += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
            if (Y) {
                drawPositionY += -1 * (walkSpeedPerSecond / walkSpeedDivider);
            }
            //  pistolWalkUpLeft.runAnimation();
        }
    }

    public void update () {
        if (viewport.inCorner(getPositionX(), getPositionY())) {
            updateDrawPos(true, true);
        } else if (viewport.edgeX(getPositionX(), getPositionY()) && !viewport.edgeY(getPositionX(), getPositionY())) {
            updateDrawPos(true, false);
        } else if (viewport.edgeY(getPositionX(), getPositionY()) && !viewport.edgeX(getPositionX(), getPositionY())) {
            updateDrawPos(false, true);
        }

        //Robert healthbar stuff.
        //Positions of black rectangle's 4 points
        blackRecLeft = drawPositionX - 1;
        blackRecTop = drawPositionY - 24;
        blackRecRight =  drawPositionX + maxHealth+1;
        blackRecBot =  drawPositionY - 6;

        //Positions of rectangle's 4 points
        redRecLeft = drawPositionX+currentHealth;
        redRecTop = drawPositionY-25;
        redRecRight = drawPositionX+maxHealth;
        redRecBot = drawPositionY - 5;

        //Positions of green Rectangle's 4 points
        greenRecLeft = drawPositionX;
        greenRecTop = drawPositionY-25;
        greenRecRight = drawPositionX+currentHealth;
        greenRecBot = drawPositionY - 5;

        //Sets the positions of the rectangles using the previous initialised integers
        greenRec.set(greenRecLeft, greenRecTop, greenRecRight, greenRecBot);
        redRec.set(redRecLeft, redRecTop, redRecRight, redRecBot);
        blackRec.set(blackRecLeft,blackRecTop,blackRecRight, blackRecBot);

        checkBounds();

        //if statements by Shaun, powerup toggles Stuart
        if (direction==MoveDirection.MOVERIGHT && !atRightEdge) {

            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            walkRightLegs.runAnimation();
            if(riflePowerup){
                rifleWalkRight.runAnimation();
            }else {
                pistolWalkRight.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkRight.getImage(0).getWidth(), drawPositionY + pistolWalkRight.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVELEFT && !atLeftEdge) {
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            walkLeftLegs.runAnimation();
            if(riflePowerup){
                rifleWalkLeft.runAnimation();
            }else {
                pistolWalkLeft.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkLeft.getImage(0).getWidth(), drawPositionY + pistolWalkLeft.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVEUP && !atTopEdge) {
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            walkUpLegs.runAnimation();
            if(riflePowerup){
                rifleWalkUp.runAnimation();
            }else {
                pistolWalkUp.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkUp.getImage(0).getWidth(), drawPositionY + pistolWalkUp.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVEDOWN && !atBottomEdge) {
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            walkDownLegs.runAnimation();
            if(riflePowerup){
                rifleWalkDown.runAnimation();
            }else {
                pistolWalkDown.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkDown.getImage(0).getWidth(), drawPositionY + pistolWalkDown.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVEUPRIGHT && !atTopEdge && !atRightEdge) {
            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            walkUpRightLegs.runAnimation();
            if(riflePowerup){
                rifleWalkUpRight.runAnimation();
            }else {
                pistolWalkUpRight.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkUpRight.getImage(0).getWidth(), drawPositionY + pistolWalkUpRight.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVEDOWNRIGHT && !atBottomEdge && !atRightEdge) {
            updatePositionX((walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            walkDownRightLegs.runAnimation();
            if(riflePowerup){
                rifleWalkDownRight.runAnimation();
            }else {
                pistolWalkDownRight.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkDownRight.getImage(0).getWidth(), drawPositionY + pistolWalkDownRight.getImage(0).getHeight());


        } else if (direction==MoveDirection.MOVEDOWNLEFT && !atBottomEdge && !atLeftEdge) {
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY((walkSpeedPerSecond / walkSpeedDivider));
            walkDownLeftLegs.runAnimation();
            pistolWalkDownLeft.runAnimation();
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkDownLeft.getImage(0).getWidth(), drawPositionY + pistolWalkDownLeft.getImage(0).getHeight());

        } else if (direction==MoveDirection.MOVEUPLEFT && !atTopEdge && !atLeftEdge) {
            updatePositionX(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            updatePositionY(-1 * (walkSpeedPerSecond / walkSpeedDivider));
            walkUpLeftLegs.runAnimation();
            if(riflePowerup){
                rifleWalkUpLeft.runAnimation();
            }else{
                pistolWalkUpLeft.runAnimation();
            }
            spriteBounds.set(drawPositionX, drawPositionY, drawPositionX + pistolWalkUpLeft.getImage(0).getWidth(), drawPositionY + pistolWalkUpLeft.getImage(0).getHeight());
        }
    }

    public void draw (Canvas canvas, Paint paint) {

        //canvas.drawRect(spriteBounds, paint);   //for testing purposes

        //Draws health bar. Robert
        canvas.drawRect(greenRec, greenPainter);
        canvas.drawRect(redRec, redPainter);
        canvas.drawRect(blackRec,blackPainter);

        if (direction != MoveDirection.NOTMOVING) { //Shaun

            if (direction == MoveDirection.MOVERIGHT) {
                walkRightLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVELEFT) {
                walkLeftLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEUP) {
                walkUpLegs.drawAnimation(canvas, drawPositionX + legXOffset + 5, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEDOWN) {
                walkDownLegs.drawAnimation(canvas, drawPositionX + legXOffset + 5, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEUPRIGHT) {
                walkUpRightLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEUPLEFT) {
                walkUpLeftLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEDOWNRIGHT) {
                walkDownRightLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);

            } else if (direction == MoveDirection.MOVEDOWNLEFT) {
                walkDownLeftLegs.drawAnimation(canvas, drawPositionX + legXOffset, drawPositionY + legYOffset, paint);
            }

            if (bodyFacing == Facing.RIGHT)
            {
                if(riflePowerup){
                    rifleWalkRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=4;
                legYOffset=30;
            }
            else if (bodyFacing == Facing.LEFT)
            {
                if(riflePowerup){
                    rifleWalkLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }

                legXOffset=15;
                legYOffset=30;
            }
            else if (bodyFacing == Facing.UP)
            {
                if(riflePowerup){
                    rifleWalkUp.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkUp.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }

                legXOffset=10;
                legYOffset=30;
            }
            else if (bodyFacing == Facing.DOWN)
            {
                if(riflePowerup){
                    rifleWalkDown.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkDown.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=4;
                legYOffset=20;
            }
            else if (bodyFacing == Facing.UPLEFT)
            {
                if(riflePowerup){
                    rifleWalkUpLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkUpLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=15;
                legYOffset=30;
            }
            else if (bodyFacing == Facing.UPRIGHT)
            {
                if(riflePowerup){
                    rifleWalkUpRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkUpRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=4;
                legYOffset=30;
            }

            else if (bodyFacing == Facing.DOWNRIGHT)
            {
                if(riflePowerup){
                    rifleWalkDownRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkDownRight.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=4;
                legYOffset=25;
            }

            else if (bodyFacing == Facing.DOWNLEFT)
            {
                if(riflePowerup){
                    rifleWalkDownLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }else {
                    pistolWalkDownLeft.drawAnimation(canvas, drawPositionX, drawPositionY, paint);
                }
                legXOffset=10;
                legYOffset=25;
            }

        }
        //determines resting frames for when not moving
        else { //Shaun

            if (legFacing == Facing.RIGHT)
            {
                canvas.drawBitmap(walkRightLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }
            else if (legFacing == Facing.LEFT)
            {
                canvas.drawBitmap(walkLeftLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }
            else if (legFacing == Facing.UP)
            {
                canvas.drawBitmap(walkUpLegs.getImage(0), drawPositionX +legXOffset+5, drawPositionY +legYOffset, paint);
            }
            else if (legFacing == Facing.DOWN)
            {
                canvas.drawBitmap(walkDownLegs.getImage(0), drawPositionX +legXOffset+5, drawPositionY +legYOffset, paint);
            }
            else if (legFacing == Facing.UPLEFT)
            {
                canvas.drawBitmap(walkUpLeftLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }
            else if (legFacing == Facing.UPRIGHT)
            {
                canvas.drawBitmap(walkUpRightLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }

            else if (legFacing == Facing.DOWNRIGHT)
            {
                canvas.drawBitmap(walkDownRightLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }

            else if (legFacing == Facing.DOWNLEFT)
            {
                canvas.drawBitmap(walkDownLeftLegs.getImage(0), drawPositionX +legXOffset, drawPositionY +legYOffset, paint);
            }


            if (bodyFacing == Facing.RIGHT)
            {
                if(riflePowerup){
                    canvas.drawBitmap(rifleWalkRight.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkRight.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }
            else if (bodyFacing == Facing.LEFT)
            {
                if (riflePowerup) {
                    canvas.drawBitmap(rifleWalkLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }
            else if (bodyFacing == Facing.UP)
            {
                if(riflePowerup){
                    canvas.drawBitmap(rifleWalkUp.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkUp.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }
            else if (bodyFacing == Facing.DOWN)
            {
                if(riflePowerup) {
                    canvas.drawBitmap(rifleWalkDown.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkDown.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }
            else if (bodyFacing == Facing.UPLEFT)
            {
                if (riflePowerup) {
                    canvas.drawBitmap(rifleWalkUpLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkUpLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }
            else if (bodyFacing == Facing.UPRIGHT)
            {
                if (riflePowerup) {
                    canvas.drawBitmap(rifleWalkUpRight.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkUpRight.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }

            else if (bodyFacing == Facing.DOWNRIGHT)
            {
                if (riflePowerup) {
                    canvas.drawBitmap(rifleWalkDownRight.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkDownRight.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }

            else if (bodyFacing == Facing.DOWNLEFT)
            {
                if (riflePowerup) {
                    canvas.drawBitmap(rifleWalkDownLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }else {
                    canvas.drawBitmap(pistolWalkDownLeft.getImage(0), drawPositionX, drawPositionY, paint);
                }
            }

        }

    }

    private void loadAnimations(Assets assets) //Shaun
    {
        //animations for the body with handgun
        pistolWalkRight =new Animation(1,assets.pistolRightWalk);
        pistolWalkLeft =new Animation(1,assets.pistolLeftWalk);
        pistolWalkDown =new Animation(1,assets.pistolDownWalk);
        pistolWalkUp =new Animation(1,assets.pistolUpWalk);
        pistolWalkUpRight =new Animation(1,assets.pistolUpRightWalk);
        pistolWalkUpLeft =new Animation(1,assets.pistolUpLeftWalk);
        pistolWalkDownRight =new Animation(1,assets.pistolDownRightWalk);
        pistolWalkDownLeft =new Animation(1,assets.pistolDownLeftWalk);

        //animations for the body with rifle

        rifleWalkRight =new Animation(1,assets.rifleRightWalk);
        rifleWalkLeft =new Animation(1,assets.rifleLeftWalk);
        rifleWalkDown =new Animation(1,assets.rifleDownWalk);
        rifleWalkUp =new Animation(1,assets.rifleUpWalk);
        rifleWalkUpRight =new Animation(1,assets.rifleUpRightWalk);
        rifleWalkUpLeft =new Animation(1,assets.rifleUpLeftWalk);
        rifleWalkDownRight =new Animation(1,assets.rifleDownRightWalk);
        rifleWalkDownLeft =new Animation(1,assets.rifleDownLeftWalk);

        //animations for the legs
        walkRightLegs =new Animation(1,assets.rightWalkLegs);
        walkLeftLegs =new Animation(1,assets.leftWalkLegs);
        walkDownLegs =new Animation(1,assets.downWalkLegs);
        walkUpLegs =new Animation(1,assets.upWalkLegs);
        walkUpRightLegs =new Animation(1,assets.upRightWalkLegs);
        walkUpLeftLegs =new Animation(1,assets.upLeftWalkLegs);
        walkDownRightLegs =new Animation(1,assets.downRightWalkLegs);
        walkDownLeftLegs =new Animation(1,assets.downLeftWalkLegs);
    }

    public Bitmap getFrame(){

        if (bodyFacing == Facing.RIGHT)
        {
            return pistolWalkRight.getCurrentFrame();
        }
        else if (bodyFacing == Facing.LEFT)
        {
            return pistolWalkLeft.getCurrentFrame();
        }
        else if (bodyFacing == Facing.UP)
        {
            return pistolWalkUp.getCurrentFrame();
        }
        else if (bodyFacing == Facing.DOWN)
        {
            return pistolWalkDown.getCurrentFrame();
        }
        else if (bodyFacing == Facing.UPLEFT)
        {
            return pistolWalkUpLeft.getCurrentFrame();
        }
        else if (bodyFacing == Facing.UPRIGHT)
        {
            return pistolWalkUpRight.getCurrentFrame();
        }

        else if (bodyFacing == Facing.DOWNRIGHT)
        {
            return pistolWalkDownRight.getCurrentFrame();
        }

        else if (bodyFacing == Facing.DOWNLEFT)
        {
            return pistolWalkDownLeft.getCurrentFrame();
        }
        else
        {
            return pistolWalkRight.getCurrentFrame();
        }
    }

    //Method written by Stuart
    @Override
    public void checkBounds() {
        if (drawPositionX + spriteBounds.width() >= this.screenX) {
            drawPositionX = this.screenX - spriteBounds.width();
            this.atRightEdge = true;
            this.atLeftEdge = false;
        } else if (drawPositionX <= 0) {
            drawPositionX = 0;
            this.atLeftEdge = true;
            this.atRightEdge = false;
        }else {
            this.atLeftEdge = false;
            this.atRightEdge = false;
        }
        if (drawPositionY + spriteBounds.height() >= this.screenY) {
            drawPositionY = this.screenY - spriteBounds.height();
            this.atBottomEdge = true;
            this.atTopEdge = false;
        } else if (getPositionY() <= 0) {
            drawPositionY = 0;
            this.atTopEdge = true;
            this.atBottomEdge = false;
        }else{
            this.atTopEdge = false;
            this.atBottomEdge = false;
        }

    }

    public void resetPosition(){
        this.positionX = (screenX/2);
        this.positionY = (screenY/2);
        drawPositionX = this.positionX;
        drawPositionY = this.positionY;
    }
    public int getLives(){return lives;}

    public int getDrawPositionX(){return drawPositionX;}

    public int getDrawPositionY(){return drawPositionY;}

    public void setLives(int lives){this.lives = lives;}

    public Rect getSpriteBounds(){ return spriteBounds;}

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        this.score += points;
    }
}
