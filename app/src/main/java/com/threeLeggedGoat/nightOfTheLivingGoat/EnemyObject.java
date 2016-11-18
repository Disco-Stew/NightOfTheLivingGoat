package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

//Author Adam
public abstract class EnemyObject extends GameObject implements  Collidable {
    PlayerObject player;
    private boolean touchingPlayer;
    private int destinationX;
    private int destinationY;
    private int damage;
    private int walkSpeed;
    private float maxPrediction;

    public Rect spriteBounds;
    public Viewport viewport;
    private int rotation;
    public Assets assets;
    public Scaler scaler;

    public int points = 10;

    private Rect healthBarBackground = new Rect();
    private Paint backgroundPaint = new Paint();

    private Rect healthBarForeground = new Rect();
    public Paint foregroundPaint = new Paint();

    private int moveToPlayerCenterX;
    private int moveToPlayerCenterY;

    private int collisionBufferX;
    private int collisionBufferY;

    private final int BUFFER_WIDTH = 50;
    private final int HEALTH_BAR_BACKGROUND_SIDES = 20;
    private final int HEALTH_BAR_BACKGROUND_TOP = 15;
    private final int HEALTH_BAR_BACKGROUND_BOTTOM = 5;
    private final int HEALTH_BAR_FOREGROUND_SHRINK = 3;

    public Animation zombieWalk;

    public EnemyObject(Assets assets, PlayerObject player, int moveToPlayerCenterX, int moveToPlayerCenterY, int health, int damage, Viewport viewport, Scaler scaler) {
        this.player = player;
        this.walkSpeedPerSecond = this.player.getWalkSpeedPerSecond();
        this.walkSpeedDivider = this.player.walkSpeedDivider;
        this.maxHealth = health;
        this.currentHealth = health;
        this.damage = damage;
        this.assets = assets;
        this.viewport = viewport;
        this.scaler = scaler;
        forDestroy = false;

        loadAnimations();

        this.moveToPlayerCenterX = moveToPlayerCenterX;
        this.moveToPlayerCenterY = moveToPlayerCenterY;
        walkSpeed = (walkSpeedPerSecond / walkSpeedDivider);
        backgroundPaint.setARGB(255, 70, 60, 60);
        foregroundPaint.setARGB(255, 255, 0, 0);

        generateRandomStartingPosition((viewport.getCamX() + scaler.getDisplayX()/2), (viewport.getCamY() + scaler.getDisplayY()/2), (scaler.getDisplayX()/2 + BUFFER_WIDTH), (scaler.getDisplayY()/2 + BUFFER_WIDTH));
        spriteBounds = new Rect(positionX, positionY, positionX + zombieWalk.getImage(0).getWidth(), positionY +  zombieWalk.getImage(0).getHeight());

        this.viewport = viewport;
    }

    //Author Shaun
    abstract void loadAnimations();

    //Author Adam
    //Draws a health bar that stretches across the top of the enemy image
    //and changes as the enemy takes damage
    public void draw(Canvas canvas, Paint paint) {

        healthBarBackground.set((viewport.objectDrawX(this, player) - HEALTH_BAR_BACKGROUND_SIDES), (viewport.objectDrawY(this, player) - HEALTH_BAR_BACKGROUND_TOP),
                (viewport.objectDrawX(this, player) + zombieWalk.getImage(0).getWidth() + HEALTH_BAR_BACKGROUND_SIDES), (viewport.objectDrawY(this, player) - HEALTH_BAR_BACKGROUND_BOTTOM));


        healthBarForeground.set((healthBarBackground.left + HEALTH_BAR_FOREGROUND_SHRINK), (healthBarBackground.top + HEALTH_BAR_FOREGROUND_SHRINK),
                Math.round(((healthBarBackground.left + HEALTH_BAR_FOREGROUND_SHRINK)
                        + (((healthBarBackground.right - healthBarBackground.left) - (HEALTH_BAR_FOREGROUND_SHRINK * 2)) * (((float) currentHealth) / maxHealth)))), healthBarBackground.bottom - HEALTH_BAR_FOREGROUND_SHRINK);

        canvas.drawRect(healthBarBackground, backgroundPaint);
        canvas.drawRect(healthBarForeground, foregroundPaint);

        if (viewport.onScreen(this, player)) {
            zombieWalk.drawAnimation(canvas, viewport.objectDrawX(this, player), viewport.objectDrawY(this, player), rotation, paint);
        }
    }

    //If the player is moving the enemy is moved towards were the player will be if it continues in the same direction
    //Otherwise the enemy is moved directly towards the player
    public void update() {
        destinationX = player.getPositionX() + moveToPlayerCenterX;
        destinationY = player.getPositionY() + moveToPlayerCenterY;

        //move the enemies destination co-ordinates by the maxPrediction value in the same direction the enemy is moving
        if (!touchingPlayer) {
            //retrieve (the distance between the enemy and the player)/2
            maxPrediction = calculateMaxPrediction();

            switch (player.direction){
                case MOVERIGHT:
                    destinationX += maxPrediction;
                    break;
                case MOVELEFT:
                    destinationX -= maxPrediction;
                    break;
                case MOVEUP:
                    destinationY -= maxPrediction;
                    break;
                case MOVEDOWN:
                    destinationY += maxPrediction;
                    break;
                case MOVEUPRIGHT:
                    destinationX += maxPrediction;
                    destinationY -= maxPrediction;
                    break;
                case MOVEDOWNRIGHT:
                    destinationX += maxPrediction;
                    destinationY += maxPrediction;
                    break;
                case MOVEUPLEFT:
                    destinationX -= maxPrediction;
                    destinationY -= maxPrediction;
                    break;
                case MOVEDOWNLEFT:
                    destinationX -= maxPrediction;
                    destinationY += maxPrediction;
            }
        }

        //Gets the X&Y distance between the enemy and it's destination
        float directionX = destinationX - getPositionX();
        float directionY = destinationY - getPositionY();

        //Gets the straight line difference between enemy and destination
        float hypotenuse =  (float) Math.hypot(directionX, directionY);

        //Normalise x & y differences
        directionX /= hypotenuse;
        directionY /= hypotenuse;

        calculateRotation();

        //Update enemy position in specified x & y directions by value of walk speed
        if(!touchingPlayer){
            walkSpeed = (walkSpeedPerSecond / walkSpeedDivider);
            updatePositionX(Math.round(directionX * walkSpeed));
            updatePositionY(Math.round(directionY * walkSpeed));
        }

        zombieWalk.runAnimation();

        collisionBufferX = zombieWalk.getImage(0).getWidth()/5;
        collisionBufferY = zombieWalk.getImage(0).getHeight()/5;

        spriteBounds.set(viewport.objectDrawX(this,player)+collisionBufferX, viewport.objectDrawY(this, player)+collisionBufferY,
                (viewport.objectDrawX(this,player) + zombieWalk.getImage(0).getWidth())-collisionBufferX, (viewport.objectDrawY(this,player) + zombieWalk.getImage(0).getHeight())-collisionBufferY);
    }

    //Author Adam
    public void damageEnemy(int damage) {
        this.currentHealth -= damage;
        if (currentHealth <=0) {
            this.setForDestroy();
        }
    }

    public Bitmap getFrame(){
        return zombieWalk.getCurrentFrame();
    }

    // Author Shaun
    private void calculateRotation() //calculates the rotation the enemies must take to face the player
    {
        // (x1,y1) = enemy position
        int x1=positionX;
        int y1=positionY;
        // (x2,y2) = player position
        int x2=player.positionX;
        int y2=player.positionY;
        float hypotenuse =  (float) Math.hypot(x2-x1, y2-y1);

        double theta; //theta is the mathematical term for an angle

        /*space is divided into quadrants centred around x1,y1 i.e. the enemy position.
        * Based on the trigonometric functions for finding the angle of a right angled triangle, the quadrants are used to narrow the relative location
        * of the target (in this case the player) so that while the equation will return an angle (theta) that is less than 90, it can be offset to allow an accurate rotation
        * For example, to calculate the angle if the player is in the top left quadrant relative to the enemy 180 must be added to theta, since that quadrant begins 180 degrees
        * after 0, which in Android faces to the right relative to the screen canvas.
        */
        if (x2 == x1) //Ensures a smooth transition between top and bottom quadrants
        {
            if (y2 < y1)
            {
                rotation=270;
            }
            else if (y2 > y1)
            {
                rotation=90;
            }
        }

        else if (y2 == y1)//Ensures a smooth transition between right and left quadrants
        {
            if (x2 > x1)
            {
                rotation=0;
            }
            else if (x2 < x1)
            {
                rotation=180;
            }
        }

        else if (x2 > x1) //right hand quadrants
        {
            if (y2 < y1) //top right quadrant
            {
                theta = Math.toDegrees(Math.asin((y1 - y2) / hypotenuse));
                rotation = (int) (360 - theta);
            }
            else if (y2 > y1)//bottom right quadrant
            {
                theta = Math.toDegrees(Math.asin((y2 - y1) / hypotenuse));
                rotation = (int) (theta);
            }
        }
        else if (x2 < x1) //left hand quadrants
        {
            if (y2 < y1)//top left quadrant
            {
                theta = Math.toDegrees(Math.asin((y1 - y2) / hypotenuse));
                rotation = (int) (180 + theta);
            }
            else if (y2 > y1)//bottom left quadrant
            {
                theta = Math.toDegrees(Math.asin((y2 - y1) / hypotenuse));
                rotation = (int) (180 - theta);
            }
        }

    }

    //Author Adam
    public int getDamage(){return damage;}

    //Author Adam
    public void setTouchingPlayer(boolean touchingPlayer){
        this.touchingPlayer = touchingPlayer;
    }

    //retrieves (the straight line distance between the enemy and the player)/2
    //Author Adam
    private float calculateMaxPrediction(){
        return (float) (Math.sqrt(Math.pow(destinationX - this.getPositionX(),2) + Math.pow(destinationY - this.getPositionY(),2)))/2;
    }

    @Override
    public void checkBounds() {}


    //Author Adam
    public Rect getSpriteBounds(){ return spriteBounds;}

    //Author Adam
    @Override
    public void setForDestroy(){
        this.forDestroy = true;
        player.updateScore(points);
    }

    //Positions the enemy at a random point along a perimeter of a rectangle surrounding the viewport
    //this rectangle is larger than the viewport by the values BUFFER_X and BUFFER_Y
    //Author Adam
    private void generateRandomStartingPosition(float screenCenterX, float screenCenterY, float bufferX, float bufferY){
        Random random = new Random();

        //Randomly positions enemy outside the screen
        switch (random.nextInt(4)) {
            case 0:
                //Moves enemy outside one side of the screen
                this.positionX = (int) (screenCenterX + bufferX);

                //Positions enemy at random point on that side
                randomisePositionY(random);
                break;
            case 1:
                this.positionX = (int) (screenCenterX - bufferX);
                randomisePositionY(random);
                break;
            case 2:
                this.positionY = (int) (screenCenterY + bufferY);
                randomisePositionX(random);
                break;
            case 3:
                this.positionY = (int) (screenCenterY - bufferY);
                randomisePositionX(random);
                break;
        }
    }

    //Positions an enemy at a random point along a horizontal line of (viewport.width + (BUFFER_X*2)) length
    //Author Adam
    private void randomisePositionX(Random random) {
        this.positionX += random.nextInt(scaler.getDisplayX() +(BUFFER_WIDTH*2)+1);
    }

    //Positions an enemy at a random point along a vertical line of (viewport.height + (BUFFER_Y*2)) length
    //Author Adam
    private void randomisePositionY(Random random) {
        this.positionY += random.nextInt(scaler.getDisplayY() +(BUFFER_WIDTH*2)+1);
    }

    //Author Adam
    public boolean isForDestroy() {return forDestroy;}

    //Author Adam
    public int getCenterX(){
        return positionX +  zombieWalk.getImage(0).getWidth()/2;
    }

    //Author Adam
    public int getCenterY(){
        return positionY +  zombieWalk.getImage(0).getHeight()/2;
    }

    //Author Adam
    public int getDestinationX(){
        return destinationX;
    }

    //Author Adam
    public int getDestinationY(){
        return destinationY;
    }
}
