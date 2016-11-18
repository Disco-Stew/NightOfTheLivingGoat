package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Assets //a class for loading and handling assets for various objects. Intended for use with Sprite Sheets to create animations
{

    private Matrix matrix;

    // Sprite sheets and sub image arrays go here
    private SpriteSheet playerSSheet;
    private SpriteSheet enemySSheet;

    Bitmap[] pistolRightWalk, pistolLeftWalk, pistolUpWalk, pistolDownWalk,
            pistolDownLeftWalk, pistolDownRightWalk, pistolUpLeftWalk, pistolUpRightWalk,

            rifleRightWalk, rifleLeftWalk, rifleUpWalk, rifleDownWalk, rifleDownLeftWalk,
            rifleDownRightWalk,rifleUpLeftWalk,rifleUpRightWalk,

    rightWalkLegs, leftWalkLegs, upWalkLegs, downWalkLegs, downLeftWalkLegs, downRightWalkLegs,
            upLeftWalkLegs, upRightWalkLegs;

    Bitmap[] basicEnemyWalk, sprintEnemyWalk, tankEnemyWalk,bossEnemyWalk;

    //standalone images
    Bitmap projectileRight, projectileLeft, projectileUp, projectileDown,
            projectileDownLeft, projectileDownRight, projectileUpLeft, projectileUpRight, projectileGeneric;

    Bitmap map;


    public Assets(Resources res)
    {
        pistolRightWalk = new Bitmap[20];
        pistolLeftWalk = new Bitmap[20];
        pistolUpWalk = new Bitmap[20];
        pistolDownWalk = new Bitmap[20];
        pistolDownLeftWalk = new Bitmap[20];
        pistolDownRightWalk = new Bitmap[20];
        pistolUpLeftWalk = new Bitmap[20];
        pistolUpRightWalk = new Bitmap[20];

        rifleRightWalk = new Bitmap[20];
        rifleLeftWalk = new Bitmap[20];
        rifleUpWalk = new Bitmap[20];
        rifleDownWalk = new Bitmap[20];
        rifleDownLeftWalk = new Bitmap[20];
        rifleDownRightWalk = new Bitmap[20];
        rifleUpLeftWalk = new Bitmap[20];
        rifleUpRightWalk = new Bitmap[20];

        rightWalkLegs = new Bitmap[20];
        leftWalkLegs = new Bitmap[20];
        upWalkLegs = new Bitmap[20];
        downWalkLegs = new Bitmap[20];
        downLeftWalkLegs = new Bitmap[20];
        downRightWalkLegs = new Bitmap[20];
        upLeftWalkLegs = new Bitmap[20];
        upRightWalkLegs = new Bitmap[20];

        basicEnemyWalk = new Bitmap[16];
        sprintEnemyWalk = new Bitmap[16];
        tankEnemyWalk = new Bitmap[16];
        bossEnemyWalk = new Bitmap[16];
        matrix = new Matrix();

        getResources(res);
    }

    private void getResources(Resources res) //loads sub images into a relevant array
    {
        getPlayerTextures(res);

        getEnemyTextures(res);

        getProjectileTextures(res);

        map = BitmapFactory.decodeResource(res,R.drawable.map);
    }

    private void getPlayerTextures(Resources res)
    {
        int walkWidth=110;
        int walkHeight=110;

        int feetWidth=86;
        int feetHeight=62;
        int count = 0; //used to ensure all animations are loaded
        playerSSheet = getSpriteSheet(res,R.drawable.playerspritesheet,1024,1024);

        //handgun animation frames
        for (int i=0; i<3;i++) //row
        {
            for (int j = 0; j < 7; j++) //column
            {
                if (count<20)
                {
                    //each frame is loaded once, then rotated and passed to the rest of the animations
                    pistolRightWalk[count] = playerSSheet.grabImage(j * walkWidth, i * walkHeight, walkWidth, walkHeight);
                    matrix.postRotate(45);
                    pistolDownRightWalk[count] = Bitmap.createBitmap(rotateBitmap(pistolRightWalk[count]), 23, 23, 110, 110);
                    matrix.postRotate(45);
                    pistolDownWalk[count] = rotateBitmap(pistolRightWalk[count]);
                    matrix.postRotate(45);
                    pistolDownLeftWalk[count] = Bitmap.createBitmap(rotateBitmap(pistolRightWalk[count]), 22, 22, 110, 110);
                    matrix.postRotate(45);
                    pistolLeftWalk[count] = rotateBitmap(pistolRightWalk[count]);
                    matrix.postRotate(45);
                    pistolUpLeftWalk[count] = Bitmap.createBitmap(rotateBitmap(pistolRightWalk[count]), 22, 22, 110, 110);
                    matrix.postRotate(45);
                    pistolUpWalk[count] = rotateBitmap(pistolRightWalk[count]);
                    matrix.postRotate(45);
                    pistolUpRightWalk[count] = Bitmap.createBitmap(rotateBitmap(pistolRightWalk[count]), 23, 23, 110, 110);
                    matrix.postRotate(45);
                    count++;
                }
            }
        }
        count=0;

        //rifle animation frames
        for (int i=3; i<6;i++) //row
        {
            for (int j = 0; j < 7; j++) //column
            {
                if (count<20)
                {
                    //each frame is loaded once, then rotated and passed to the rest of the animations
                    rifleRightWalk[count] = playerSSheet.grabImage(j * walkWidth, i * walkHeight, walkWidth, walkHeight);
                    matrix.postRotate(45);
                    rifleDownRightWalk[count] = Bitmap.createBitmap(rotateBitmap(rifleRightWalk[count]), 23, 23, 110, 110);
                    matrix.postRotate(45);
                    rifleDownWalk[count] = rotateBitmap(rifleRightWalk[count]);
                    matrix.postRotate(45);
                    rifleDownLeftWalk[count] = Bitmap.createBitmap(rotateBitmap(rifleRightWalk[count]), 22, 22, 110, 110);
                    matrix.postRotate(45);
                    rifleLeftWalk[count] = rotateBitmap(rifleRightWalk[count]);
                    matrix.postRotate(45);
                    rifleUpLeftWalk[count] = Bitmap.createBitmap(rotateBitmap(rifleRightWalk[count]), 22, 22, 110, 110);
                    matrix.postRotate(45);
                    rifleUpWalk[count] = rotateBitmap(rifleRightWalk[count]);
                    matrix.postRotate(45);
                    rifleUpRightWalk[count] = Bitmap.createBitmap(rotateBitmap(rifleRightWalk[count]), 23, 23, 110, 110);
                    matrix.postRotate(45);
                    count++;
                }
            }
        }

        count=0;

        //leg animation frames
        for (int i=0; i<2;i++) //row
        {
            for (int j = 0; j < 10; j++) //column
            {
                //each frame is loaded once, then rotated and passed to the rest of the animations
                rightWalkLegs[count] = playerSSheet.grabImage(j * feetWidth, i * feetHeight + 900, feetWidth, feetHeight);
                matrix.postRotate(45);
                downRightWalkLegs[count] = Bitmap.createBitmap(rotateBitmap(rightWalkLegs[count]), 10, 10, 80, 80);
                matrix.postRotate(45);
                downWalkLegs[count] = rotateBitmap(rightWalkLegs[count]);
                matrix.postRotate(45);
                downLeftWalkLegs[count] = Bitmap.createBitmap(rotateBitmap(rightWalkLegs[count]), 10, 10, 80, 80);
                matrix.postRotate(45);
                leftWalkLegs[count] = rotateBitmap(rightWalkLegs[count]);
                matrix.postRotate(45);
                upLeftWalkLegs[count] = Bitmap.createBitmap(rotateBitmap(rightWalkLegs[count]), 10, 10, 80, 80);
                matrix.postRotate(45);
                upWalkLegs[count] = rotateBitmap(rightWalkLegs[count]);
                matrix.postRotate(45);
                upRightWalkLegs[count] = Bitmap.createBitmap(rotateBitmap(rightWalkLegs[count]), 10, 10, 80, 80);
                matrix.postRotate(45);
                count++;
            }
        }
        playerSSheet.image.recycle();
    }

    private void getProjectileTextures(Resources res)
    {
        projectileRight = BitmapFactory.decodeResource(res, R.drawable.rocket);
        matrix.postRotate(45);
        projectileDownRight = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileDown = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileDownLeft = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileLeft = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileUpLeft = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileUp = rotateBitmap(projectileRight);
        matrix.postRotate(45);
        projectileUpRight = rotateBitmap(projectileRight);
        matrix.postRotate(45);

        projectileGeneric = BitmapFactory.decodeResource(res, R.drawable.genericprojectile);
    }

    private void getEnemyTextures(Resources res)
    {
        int count = 0;
        enemySSheet = getSpriteSheet(res,R.drawable.enemyspritesheet,1024,1792);

        int zombieWidth=128;
        int zombieHeight=128;

        int bossWidth=zombieWidth*2;
        int bossHeight=zombieHeight*2;

        for (int i=0;i<2;i++)//row
        {
            for (int j=0;j<8;j++)//column
            {
                if (count<16)
                {
                    basicEnemyWalk[count]=enemySSheet.grabImage(j*zombieWidth,i*zombieHeight,zombieWidth,zombieHeight);
                    count++;
                }
            }
        }

        count = 0;
        for (int i=2;i<4;i++)//row
        {
            for (int j=0;j<8;j++)//column
            {
                if (count<16)
                {
                    sprintEnemyWalk[count]=enemySSheet.grabImage(j*zombieWidth,i*zombieHeight,zombieWidth,zombieHeight);
                    count++;
                }
            }
        }

        count = 0;
        for (int i=4;i<6;i++)//row
        {
            for (int j=0;j<8;j++)//column
            {
                if (count<16)
                {
                    tankEnemyWalk[count]=enemySSheet.grabImage(j*zombieWidth,i*zombieHeight,zombieWidth,zombieHeight);
                    count++;
                }
            }
        }

        count = 0;
        for (int i=3;i<7;i++)//row
        {
            for (int j=0;j<4;j++)//column
            {
                if (count<16)
                {
                    bossEnemyWalk[count]=enemySSheet.grabImage(j*bossWidth,i*bossHeight,bossWidth,bossHeight);
                    count++;
                }
            }
        }

        enemySSheet.image.recycle();

    }

    //android disrupts the natural scale of images when loaded, so they must be rescaled back to their original sizes
    private SpriteSheet getSpriteSheet(Resources res, int id, int originalWidth, int originalHeight)
    {
        Bitmap spriteSheetImage;
        Bitmap spriteSheetImageScaled;
        try
        {
            spriteSheetImage=BitmapFactory.decodeResource(res, id);
            System.out.println("Unscaled Dimensions; " + spriteSheetImage.getWidth() + ", "+spriteSheetImage.getHeight());

            System.out.println("Sprite Sheet Dimensions; " + originalWidth + ", "+originalHeight);

            spriteSheetImageScaled=Bitmap.createScaledBitmap(spriteSheetImage, originalWidth, originalHeight, false);
        }

        catch(NullPointerException e)
        {
            spriteSheetImageScaled=null;
            e.printStackTrace();
        }

        return (new SpriteSheet(spriteSheetImageScaled));
    }

    public Bitmap rotateBitmap(Bitmap source) //rotates bitmaps using a matrix
    {
        return (Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true));
    }
}
