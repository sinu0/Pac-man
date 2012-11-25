package com.android.game.pacman.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.android.game.pacman.utils.GameEnum;

public class Block extends SolidObject {

	private boolean selected = false;
	private int size;
	private Rect sourceRect;



	public Block(int x, int y, int size) {
		super(x * size, y * size);
		this.size = size;

	}

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		boundingRect.set(x, y, x + size, y + size);
		if (kind == GameEnum.PATH) {
			paint.setColor(Color.BLACK);
			if (!isTouched)
				paint.setStyle(Paint.Style.FILL);
			else {
				paint.setStyle(Paint.Style.STROKE);
				isTouched = false;
			}
			canvas.drawRect(boundingRect, paint);

		

		}

	}

	@Override
	public String toString() {

		if (kind == GameEnum.WALL)
			return "w";
		else
			return "p";
	}
}
