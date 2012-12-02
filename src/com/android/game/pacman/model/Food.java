package com.android.game.pacman.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.android.game.pacman.utils.GameEnum;

public class Food extends SolidObject {

	private static Bitmap bitmap;

	public Food(int x, int y, Resources res) {
		super(x * 17, y * 17);
		kind = GameEnum.FOOD;
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(res,
					com.adroid.game.pacman.R.drawable.food);
		}

	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y, null);
		boundingRect = new RectF(x, y, x + bitmap.getWidth(),y + bitmap.getHeight());
	}

}
