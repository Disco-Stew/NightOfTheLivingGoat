package com.threeLeggedGoat.nightOfTheLivingGoat;

import android.graphics.Bitmap;

public class SpriteSheet //allows loaded images to be recognised as SpriteSheets, with multiple images that can be accessed on it. Allows a more efficient graphics rendering
{

	Bitmap image;
	
	public SpriteSheet(Bitmap image)
	{
		this.image=image;
	}

	//allows sub-images of a sheet to be accessed, x and y referring to the co-ordinates of the first pixel and width and height the number of pixels
	//on the x and y axis to grab
	public Bitmap grabImage(int x, int y, int width, int height)
	{																		
		return Bitmap.createBitmap (image, x, y, width, height);
	}
	
}
