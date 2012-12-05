package com.android.game.pacman.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.android.game.pacman.utils.GameEnum;

public class Block extends SolidObject {

	private boolean selected = false;
	private int size;
	static Paint paint = new Paint();

	public Block(int x, int y, int size) {
		super(x * size, y * size);
		this.size = size;

		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
	}

	public void draw(Canvas canvas) {

		if (kind == GameEnum.PATH) {
			boundingRect.set((float) x-size/2, (float) y-size/2, (float) x + size/2+size, (float) y
					+ size/2+size);
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
