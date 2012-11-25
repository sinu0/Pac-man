package com.android.game.pacman.model;

import java.util.LinkedList;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.android.game.pacman.utils.GameEnum;

public class Ghost extends GameObject {
	private LinkedList<GameEnum> movement;
	private int lvl;// table of actual lvl importanto to do ai
	private Rect sourceRect;
	private boolean eatable=false;
	public Ghost(int size, int speed, Block[][] board, Resources res,Vect initPosition) {
		super(size, speed, board);
		bitmap = BitmapFactory.decodeResource(res,
				com.adroid.game.pacman.R.drawable.ghost);
		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth() / 4; // ilosc dostepnych klatek
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		dirToChange = GameEnum.STOP;
		this.res = res;
		position = initPosition;
		nextDir = GameEnum.STOP;
		direction = new Vect(0, 0);
		this.size = size;
		stop=false;
	}

	public void doAi() {

	}

	public void update(long gameTime, int canvasWidth, int canvasHeiht) {

		move();
		calculateSourceRect();
		canChangeDir();

	}

	@Override
	public void draw(Canvas canvas) {
		Rect destRect = new Rect(position.x, position.y, position.x
				+ spriteWidth, position.y + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		boundingRect = destRect;
	}

	private void calculateSourceRect() {

		if (angle == 270) {// gora
			sourceRect.left = 1 * spriteWidth;
			sourceRect.right = sourceRect.left + spriteWidth;

		}
		if (angle == 90) {// dol
			sourceRect.left = 3 * spriteWidth;
			sourceRect.right = sourceRect.left + spriteWidth;

		}
		if (angle == 180) {// prawo
			sourceRect.left = 0 * spriteWidth;
			sourceRect.right = sourceRect.left + spriteWidth;

		}
		if (angle == 0) {// lewo
			sourceRect.left = 2 * spriteWidth;
			sourceRect.right = sourceRect.left + spriteWidth;

		}
	}
}
