package com.android.game.pacman.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.android.game.pacman.utils.GameEnum;

public class PacMan extends GameObject {

	// rectangle

	private int frameNr; // number of frames in animation
	private int currentFrame = 0; // the current frame
	private double frameTicker; // the time of the last frame update
	private int framePeriod; // milliseconds between each frame (1000/fps)
	// the height of the sprite
	private Resources res;
	private float x1, x2, y1, y2, dx, dy;
	private boolean isPlaying = false;
	boolean swap = false;
	public PacMan(Resources res, Vect initPosition, Context context,
			Block[][] board, int size, int speed) {
		super(size, speed, board);
		dirToChange = GameEnum.STOP;
		this.res = res;
		position = initPosition;
		bitmap = BitmapFactory.decodeResource(res,
				com.adroid.game.pacman.R.drawable.pacmantaa);
		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth() / 3; // ilosc dostepnych klatek
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		frameNr = 6;
		framePeriod = 1000 / 6;

		SoundStuff.sp.play(SoundStuff.pacamnWalk, 1, 1, 1, -1, 1);
		nextDir = GameEnum.STOP;

		direction = new Vect(0, 1);

		this.size = size;
		stop = true;
	}

	@Override
	public void draw(Canvas canvas) {
		boundingRect.set(position.x, position.y, position.x + spriteWidth,
				position.y + spriteHeight);

		
			canvas.save();
			canvas.rotate((float) angle,
					(float) position.x + (spriteWidth / 2), (float) position.y
							+ (spriteHeight / 2));

			canvas.drawBitmap(bitmap, sourceRect, boundingRect, null);
			canvas.restore();

		
		

	}

	// the update method for ship
	public void update(long gameTime, int canvasWidth, int canvasHeiht) {

		move();
		calculateSourceRect(gameTime);
		//borders();
		handleSound();
		canChangeDir();

	}

	public void playSound(int kind) {

	}

	public void dir(MotionEvent event) {

		switch (event.getAction()) {
		case (MotionEvent.ACTION_DOWN):
			x1 = event.getX();
			y1 = event.getY();
			break;
		case (MotionEvent.ACTION_UP): {
			x2 = event.getX();
			y2 = event.getY();
			dx = x2 - x1;
			dy = y2 - y1;

			// Use dx and dy to determine the direction
			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {

					setDirection(GameEnum.RIGHT);
				} else {

					setDirection(GameEnum.LEFT);
				}
			} else {
				if (dy > 0) {

					setDirection(GameEnum.DOWN);
				} else {

					setDirection(GameEnum.UP);
				}
			}
		}
		}
	}

	public void handleSound() {
		if (stop) {
			SoundStuff.sp.pause(SoundStuff.pacamnWalk);
			isPlaying = false;
		} else if (!isPlaying) {
			SoundStuff.sp.resume(SoundStuff.pacamnWalk);
			isPlaying = true;
		}
	}

	public void borders() {
		if (position.x > 480 && position.y == 238) { // przejście przez sciane
														// nie bedzie dzialac
														// dla innych lvl!
			position.x = -size;
		}
		if (position.x < -size && position.y == 238) {
			position.x = 479;
		}
	}

	private void calculateSourceRect(long gameTime) {
		if (!stop) {
			if (gameTime > frameTicker + framePeriod) {
				frameTicker = gameTime;
				// increment the frame
				if (!swap)
					currentFrame++;
				else
					currentFrame--;
				if (currentFrame == 0 || currentFrame == 2) {
					swap = !swap;
				}

			}
		}

		// define the rectangle to cut out sprite
		sourceRect.left = currentFrame * spriteWidth;
		sourceRect.right = sourceRect.left + spriteWidth;
	}

	public void die() {
		stop=true;
		
		
	}

}
