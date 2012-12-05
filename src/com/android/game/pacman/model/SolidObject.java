package com.android.game.pacman.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.android.game.pacman.utils.GameEnum;

public class SolidObject {
	protected double x;
	protected double y;
	static Bitmap bitmap;
	public RectF boundingRect = new RectF();
	protected boolean isTouched = false;
	public int kind;
	protected boolean rotate = false;
	protected double angle = 0;
	public SolidObject(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, (float)x, (float)y, null);
		boundingRect.set((float)x, (float)y, (float)x + bitmap.getWidth(),(float)y + bitmap.getHeight());
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
