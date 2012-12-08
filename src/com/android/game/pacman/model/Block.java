package com.android.game.pacman.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.android.game.pacman.game.GameLogic;
import com.android.game.pacman.utils.GameEnum;

public class Block extends SolidObject {

	private boolean selected = false;
	static Paint paint = new Paint();

	public Block(int x, int y) {
		super(x * GameLogic.BOARD_TILE_SIZE, y * GameLogic.BOARD_TILE_SIZE);
	

		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
	}

	public void draw(Canvas canvas) {

		if (kind == GameEnum.PATH) {
			if (selected) {
				paint.setColor(Color.RED);
			}else
				paint.setColor(Color.BLACK);
				
			boundingRect.set((float) x - GameLogic.BOARD_TILE_SIZE / 2, (float) y - GameLogic.BOARD_TILE_SIZE / 2,
					(float) x + GameLogic.BOARD_TILE_SIZE / 2 + GameLogic.BOARD_TILE_SIZE, (float) y + GameLogic.BOARD_TILE_SIZE / 2 + GameLogic.BOARD_TILE_SIZE);
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
