package com.android.game.pacman.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.android.game.pacman.game.GameLogic;
import com.android.game.pacman.utils.GameEnum;

public class Food extends SolidObject {

	private static Bitmap bitmap;

	public Food(int x, int y, Resources res) {
		super(x * GameLogic.BOARD_TILE_SIZE, y * GameLogic.BOARD_TILE_SIZE);
		kind = GameEnum.FOOD;
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(res,
					com.adroid.game.pacman.R.drawable.food);
		}

	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, (float)x, (float)y, null);
		boundingRect.set((float)x, (float)y, (float)x + bitmap.getWidth(),(float)y + bitmap.getHeight());
	}

}
