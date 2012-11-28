package com.android.game.pacman.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.android.game.pacman.utils.GameEnum;

public class SolidObject {
	protected int x;
	protected int y;
	static Bitmap bitmap;
	public Rect boundingRect = new Rect();
	protected boolean isTouched = false;
	public GameEnum kind;
	protected boolean rotate = false;
	protected double angle = 0;
	public SolidObject(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y, null);
		boundingRect = new Rect(x, y, x + bitmap.getWidth(),y + bitmap.getHeight());
	}

	
	public void setIsTouched(boolean b) {
		isTouched = b;
	}
	
	public boolean getIsTouched() {
		return isTouched;
	}



	public void rotate(double angle) {
		rotate = true;
		this.angle = angle;
	}
	

}
