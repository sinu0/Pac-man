package com.android.game.pacman.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.android.game.pacman.utils.GameEnum;

public class GameObject {
	protected Vect position;
	protected Bitmap bitmap;
	public Rect boundingRect = new Rect();
	protected boolean isTouched = false;
	protected GameEnum dirToChange;
	protected int speed;
	protected boolean rotate = false;
	protected double angle = 0;
	protected Vect direction;
	protected Vect target;
	protected boolean stop;
	protected int size;
	private Block[][] board;
	protected GameEnum nextDir;
	protected Rect sourceRect; // the rectangle to be drawn from the animation
	protected int spriteWidth; // the width of the sprite to calculate the cut
								// out
	protected int spriteHeight;
	protected Resources res;

	public void update() {

	}

	public GameObject(int size, int speed, Block[][] board) {
		this.speed = speed;
		this.size = size;
		this.board = board;
	}

	public void rotate(double angle) {
		rotate = true;
		this.angle = angle;
	}

	public void draw(Canvas canvas) {
		boundingRect.set(position.x, position.y, position.x + spriteWidth,
				position.y + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, boundingRect, null);
	}

	synchronized public void move() {

		if (target == null) {
			if (dirToChange == GameEnum.UP) {
				direction = new Vect(0, -1);
				rotate(270);
			}
			if (dirToChange == GameEnum.DOWN) {
				direction = new Vect(0, 1);
				rotate(90);
			}
			if (dirToChange == GameEnum.LEFT) {
				direction = new Vect(-1, 0);
				rotate(180);
			}
			if (dirToChange == GameEnum.RIGHT) {
				direction = new Vect(1, 0);
				rotate(0);
			}
			if (canMove(position, direction)) {
				stop = false;

				target = position.add(direction.mulitply(size));
			} else
				stop = true;
		}
		if (target != null) {
			if (moveTowards(target))
				target = null;
		}

	}

	private boolean moveTowards(Vect goal) {
		if (position.equals(goal))
			return true;
		Vect dir = Vect.normalize(goal.subtract(position)); // kierunek ruchu
		position = position.add(dir.mulitply(speed)); // nowa pozycja

		if (Math.abs(Vect.dot(dir, Vect.normalize(goal.subtract(position))) + 1) < speed) // jezeli
																							// minął
																							// cel
			position = goal;

		return position.equals(goal);
	}

	private boolean canMove(Vect position, Vect direction) {
		if (direction == null)
			return false;
		int nx = (int) (position.x / size) + (int) direction.x;
		int ny = (int) (position.y / size) + (int) direction.y;
		if (nx < 0 || ny < 0 || nx >= board.length || ny >= board[0].length)
			return false;
		return board[nx][ny].kind == GameEnum.PATH;

	}

	private boolean check(GameEnum dir) {
		Vect newdir = null;
		if (dir == GameEnum.UP)
			newdir = new Vect(0, -1);
		if (dir == GameEnum.DOWN)
			newdir = new Vect(0, 1);
		if (dir == GameEnum.LEFT)
			newdir = new Vect(-1, 0);
		if (dir == GameEnum.RIGHT)
			newdir = new Vect(1, 0);

		return canMove(position, newdir);
	}

	public void setDirection(GameEnum dir) {

		if (position.x % 17 == 0 && position.y % 17 == 0) {
			if (check(dir))
				dirToChange = dir;
		} else
			nextDir = dir;

	}

	public void canChangeDir() {
		if (position.x % 17 == 0 && position.y % 17 == 0) {
			if (check(nextDir)) {
				dirToChange = nextDir;
				nextDir = GameEnum.STOP;
			}
		}

	}
	public void setIsTouched(boolean state){
		isTouched=state;
	}
}
