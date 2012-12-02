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
	// uzywane kied duszek jest do zjedzenia
	private int spriteEatHeight;
	private int spriteEatWidth;
	private Rect sourceEatRect;

	private Bitmap eyes;
	private Bitmap eatableBitmap;

	private boolean eatable = false;
	private boolean onlyEyes = false;
	private boolean blinking = false;

	private int farame = 0;
	private int framecount = 0;
	private float frameTicker;
	private float framePeriod = 0.5f;
	private boolean swap;

	private double time; // do mrugania;
	private int currentFrame;
	private int add;
	private float before;
	private long after;

	public Ghost(int size, float speed, Block[][] board, Resources res,
			Vect initPosition) {
		super(size, speed, board);

		bitmap = BitmapFactory.decodeResource(res,
				com.adroid.game.pacman.R.drawable.ghost);

		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth() / 4; // ilosc dostepnych klatek
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);

		eatableBitmap = BitmapFactory
				.decodeResource(res, R.drawable.ghost_eat2);
		spriteEatHeight = eatableBitmap.getHeight();
		spriteEatWidth = eatableBitmap.getWidth() / 2; // ilosc dostepnych
														// klatek
		sourceEatRect = new Rect(0, 0, spriteWidth, spriteHeight);

		eyes = BitmapFactory.decodeResource(res, R.drawable.ghost_eye);

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

	public void update(float gameTime, int canvasWidth, int canvasHeiht) {

		move(gameTime);
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
			boundingRect.set(position.x, position.y, position.x
					+ spriteEatWidth, position.y + spriteEatHeight);
			canvas.drawBitmap(eatableBitmap, sourceEatRect, boundingRect, null);
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

	private void calculateSourceRect(float gameTime) {

		if (!eatable) {
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
			frameTicker+= gameTime;
			time+=gameTime;
			if (frameTicker >   framePeriod) {
				frameTicker = 0;
				// increment the frame
				if (!swap)
					currentFrame++;
				else
					currentFrame--;
				if (currentFrame == 0 || currentFrame == 1) {
					swap = !swap;
					if (time > 1) {
						time = 0;
						framePeriod -= 0.08;
					}
				}

			}

		}
		// define the rectangle to cut out sprite
		sourceEatRect.left = currentFrame * spriteEatWidth;
		sourceEatRect.right = sourceEatRect.left + spriteEatWidth;
	}

	public void setBlinking(boolean blinking) {
		this.blinking = blinking;
		if (!blinking) {
			after=0;
			before=0;
			framePeriod=500;
		}
	}
}
