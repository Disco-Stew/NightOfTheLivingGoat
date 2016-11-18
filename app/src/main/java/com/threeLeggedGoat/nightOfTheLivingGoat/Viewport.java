package com.threeLeggedGoat.nightOfTheLivingGoat;

public class Viewport {
    private float camX, camY;
    private Scaler scaler;

    int mapWidth,mapHeight;

    public Viewport(Scaler scaler, int width, int height){
        this.scaler = scaler;
        this.mapWidth=width;
        this.mapHeight=height;
    }

    //calculates the x value of the viewport
    public int viewportX(float playerPositionX){
        camX = playerPositionX - (scaler.getDisplayX()/2);

        //ensures viewport doesn't go past map boundaries
        if(camX<0){
            camX = 0;
        }
        else if(camX>(mapWidth-scaler.getDisplayX())){
            camX = mapWidth-scaler.getDisplayX();
        }

        //return negative value as the map is being drawn in the negative area
        return -Math.round(camX);
    }

    //calculates the y value of the viewport
    public int viewportY(float playerPositionY){
        camY = playerPositionY - (scaler.getDisplayY()/2);

        //ensures viewport doesn't go past map boundaries
        if(camY<0){
            camY=0;
        }
        else if(camY>mapHeight-scaler.getDisplayY()){
            camY = mapHeight-scaler.getDisplayY();
        }

        //return negative value as the map is being drawn in the negative area
        return -Math.round(camY);
    }

    //checks whether the player is in one of the 4 corners
    public boolean inCorner(float playerX,float playerY){

        //the 4 extremes the viewport can be at
        boolean x = (-viewportX(playerX)==0);
        boolean y = (-viewportY(playerY)==0);
        if (x&&y){
            return true;
        } else if ((-viewportX(playerX)==0)&&(-viewportY(playerY)==(mapHeight-scaler.getDisplayY()))){
            return true;
        } else if ((-viewportX(playerX)==(mapWidth-scaler.getDisplayX()))&&(-viewportY(playerY)==0)){
            return true;
        } else if ((-viewportX(playerX)==(mapWidth-scaler.getDisplayX()))&&(-viewportY(playerY)==(mapHeight-scaler.getDisplayY()))){
            return true;
        }else{
            return false;
        }
    }

    //checks whether the player is on the left/right edge of screen
    public boolean edgeX(float playerX,float playerY){
        if((-viewportX(playerX)==0)&&(-viewportY(playerY)>0)&&(-viewportY(playerY)<(mapHeight-scaler.getDisplayY()))){
            return true;
        }else if((-viewportX(playerX)==(mapWidth-scaler.getDisplayX()))&&(-viewportY(playerY)>0)&&(-viewportY(playerY)<(mapHeight-scaler.getDisplayY()))){
            return true;
        }
        else{
            return false;
        }
    }

    //checks whether the player is on the top/bottom edge of screen
    public boolean edgeY(float playerX,float playerY){
        if((-viewportY(playerY)==0)&&(-viewportX(playerX)>0)&&(-viewportX(playerX)<(mapWidth-scaler.getDisplayX()))){
            return true;
        }else if((-viewportY(playerY)==(mapHeight-scaler.getDisplayY()))&&(-viewportX(playerX)>0)&&(-viewportX(playerX)<(mapWidth-scaler.getDisplayX()))){
            return true;
        }
        else{
            return false;
        }
    }

    //returns the x value at which object should be drawn
    public int objectDrawX(GameObject object,PlayerObject player){
        return(object.getPositionX()+viewportX(player.getPositionX()));
    }

    //returns the y value at which object should be drawn
    public int objectDrawY(GameObject object,PlayerObject player){
        return(object.getPositionY()+viewportY(player.getPositionY()));
    }

    //checks if object should be drawn
    public boolean onScreen(GameObject object,PlayerObject player){
        if((object.getPositionX()>=-viewportX(player.getPositionX()))&&(object.getPositionX()<(-viewportX(player.getPositionX())+scaler.getDisplayX()))
                &&(object.getPositionY()>=-viewportY(player.getPositionY()))&&(object.getPositionY()<(-viewportY(player.getPositionY())+scaler.getDisplayY()))){
            return true;
        }else {
            return false;
        }
    }

    public float getCamX() {
        return camX;
    }

    public float getCamY() {
        return camY;
    }
}
