package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Animation //class for allowing animations to be set up
{

	private int speed; //determines speed at which animations are run
	private int frames; //holds number of frames to be animated
	
	private int index=0; //ticks until it reaches speed, then loads the next frame
	private int count=1; //holds which frame is currently being accessed in the images array below
	private int currentImg; //holds current Image to be/being rendered

	private Bitmap[] images; //holds frames to be rendered

	
	
	public Animation(int speed,Bitmap... args) //allows different speeds and different sized animations/different number of frames
	{
		this.speed=speed;
		images=new Bitmap[args.length];
		images=args;
		frames=args.length;
		currentImg=0;
	}
	
	public void runAnimation() //creates a delay that allows each frame to be rendered with enough time to be visible/prevents overlapping frames
	{
		index++;
		
		if (index>speed)
		{
			index=0;
			nextFrame();
		}
	}

	public Bitmap getCurrentFrame(){
		return images[currentImg];
	}
	private void nextFrame() // loads new frame to be rendered into currentImg
	{
		currentImg=count;
		count++;
		if (count>=frames)count=0;
	}
	
	public void drawAnimation(Canvas canvas,int x, int y, Paint paint) //draws currentImg
	{
		canvas.drawBitmap(images[currentImg], x, y, paint);
	}

	public void drawAnimation(Canvas canvas,int x, int y, int rotation, Paint paint) //draws currentImg
	{
		canvas.save();
		canvas.rotate(rotation, x + (images[currentImg].getWidth() / 2), y + (images[currentImg].getHeight() / 2));
		canvas.drawBitmap(images[currentImg], x, y, paint);
		canvas.restore();
	}

	public Bitmap getImage(int index)
	{
		return images[index];
	}

	public void resetAnimation()//not used but exists for completeness, reusability and extensibility
	{
		count=0;
		index=0;
	}

}
