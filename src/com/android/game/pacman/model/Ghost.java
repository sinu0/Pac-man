package com.android.game.pacman.model;

import java.util.LinkedList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.adroid.game.pacman.R;
import com.android.game.pacman.utils.GameEnum;

public class Ghost extends GameObject {
	private LinkedList<GameEnum> movement;
	private int lvl;// table of actual lvl importanto to do ai
	private Rect sourceRect;

	private Bitmap eyes;
	private Bitmap eatableBitmap;

	private boolean eatable = false;
	private boolean onlyEyes = false;
	private boolean blinking = false;

	private int farame = 0;
	private int framecount = 0;
	private long frameTicker;
	private int framePeriod;
	private boolean swap;

	public Ghost(int size, int speed, Block[][] board, Resources res,
			Vect initPosition) {
		super(size, speed, board);
		bitmap = BitmapFactory.decodeResource(res,
				com.adroid.game.pacman.R.drawable.ghost);
		eatableBitmap = BitmapFactory.decodeResource(res, R.drawable.ghost_eat);
		eyes = BitmapFactory.decodeResource(res, R.drawable.ghost_eye);
		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth() / 4; // ilosc dostepnych klatek
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		dirToChange = GameEnum.STOP;
		this.res = res;
		position = initPosition;
		nextDir = GameEnum.STOP;
		direction = new Vect(0, 0);
		this.size = size;
		stop = false;
	}

	public void doAi() {

	}

	public void update(long gameTime, int canvasWidth, int canvasHeiht) {

		move();
		calculateSourceRect(gameTime);
		canChangeDir();
		if (eatable) {
			if (isTouched) {
				eatable = false;
				onlyEyes = true;
			}

		}
	}

	public void draw(Canvas canvas) {

		boundingRect.set(position.x, position.y, position.x + spriteWidth,
				position.y + spriteHeight);
		if (eatable) {
			canvas.drawBitmap(eatableBitmap, position.x, position.y, null);
		} else if (onlyEyes)
			canvas.drawBitmap(eyes, position.x, position.y, null);
		else
			canvas.drawBitmap(bitmap, sourceRect, boundingRect, null);
	}

	public void setEatable(boolean eat) {
		eatable = eat;

	}

	public boolean isEatable() {
		return eatable;
	}

	private void calculateSourceRect(long gameTime) {
		
		if (!eatable || !onlyEyes) {
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
		} else if (eatable && blinking) {
			//TODO change spite
		}
	}
}
