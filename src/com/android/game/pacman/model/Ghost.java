package com.android.game.pacman.model;

import java.util.LinkedList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.adroid.game.pacman.R;
import com.android.game.pacman.game.GameLogic;
import com.android.game.pacman.utils.GameEnum;

public class Ghost extends GameObject {
	private LinkedList<GameEnum> movement;
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

	private double frameTicker;
	private double framePeriod = 0.5f;
	private boolean swap;

	private double time; // do mrugania;
	private int currentFrame;

	public Ghost(double speed, Block[][] board, Resources res, Vect initPosition) {
		super(speed, board);

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
		stop = false;
	}

	public void doAi() {

	}

	public void update(double gameTime, int canvasWidth, int canvasHeiht) {

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

		destRect.set((float) position.x - GameLogic.BOARD_TILE_SIZE / 2,
				(float) position.y - GameLogic.BOARD_TILE_SIZE / 2,
				(float) position.x + spriteWidth / 2 + spriteWidth,
				(float) position.y + spriteHeight / 2 + spriteHeight);
		boundingRect.set((float) position.x, (float) position.y,
				(float) position.x + spriteWidth, (float) position.y
						+ spriteHeight);
		if (eatable) {
			boundingRect.set((float) position.x, (float) position.y,
					(float) position.x + spriteEatWidth, (float) position.y
							+ spriteEatHeight);
			destRect.set((float) position.x - GameLogic.BOARD_TILE_SIZE / 2,
					(float) position.y - GameLogic.BOARD_TILE_SIZE / 2,
					(float) position.x + spriteWidth / 2 + spriteWidth,
					(float) position.y + spriteHeight / 2 + spriteHeight);
			canvas.drawBitmap(eatableBitmap, sourceEatRect, destRect, null);
		} else if (onlyEyes)
			canvas.drawBitmap(eyes, (float) position.x, (float) position.y,
					null);
		else
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}

	public void setEatable(boolean eat) {
		eatable = eat;

	}

	public boolean isEatable() {
		return eatable;
	}

	private void calculateSourceRect(double gameTime) {

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
			frameTicker += gameTime;
			time += gameTime;
			if (frameTicker > framePeriod) {
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
			time = 0;
			currentFrame = 0;
			framePeriod = 500;
			eatable = false;
		}
	}
}
