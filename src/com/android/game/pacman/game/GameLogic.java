package com.android.game.pacman.game;

import java.util.LinkedList;
import java.util.Timer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.adroid.game.pacman.R;
import com.android.game.pacman.model.Block;
import com.android.game.pacman.model.Board;
import com.android.game.pacman.model.Collision;
import com.android.game.pacman.model.Ghost;
import com.android.game.pacman.model.PacMan;
import com.android.game.pacman.model.SolidObject;
import com.android.game.pacman.model.SoundStuff;
import com.android.game.pacman.model.Vect;
import com.android.game.pacman.utils.GameEnum;

public class GameLogic extends SurfaceView implements SurfaceHolder.Callback,
		SensorEventListener {

	private PacMan pacman;

	private LinkedList<Block> wall;
	private LinkedList<Block> path;
	private LinkedList<SolidObject> food;
	private LinkedList<Ghost> ghosts;

	public GameLoop loop;
	private Board boardGame;
	private SoundStuff ss;
	private CountDownTimer task;
	private SensorManager sensorManager;
	private Vibrator v;

	private int points = 0;
	private int eatInRow = 0; // mnoznik do zjedzonych duszkow
	private boolean accOn;
	private boolean start = false;

	private final static Paint paint = new Paint();
	public static int BOARD_TILE_SIZE;
	public static int BOARD_HEIGHT;
	public static int BOARD_WIDTH;
	public final static int GHOST_EAT_POINT = 200;
	public final static int FOODUP_POINT = 150;
	public final static int FOOD_POINT = 50;
	public static int WIDTH;
	public static int HEIGHT;

	public GameLogic(Context context, int w, int h) {
		super(context);
		setFocusable(true);
		getHolder().addCallback(this);

		WIDTH = w;
		HEIGHT = h;

		loop = new GameLoop(getHolder(), this);
		ss = new SoundStuff(context);
		paint.setStyle(Paint.Style.FILL);
		// przez 10 sek po 5 zaczna mrugac duchy
		task = new CountDownTimer(10000, 1000) {
			private boolean first = false;
			private int ID;
			int times = 0;

			@Override
			public void onTick(long millisUntilFinished) {

				if (!first) {
					ID = SoundStuff.sp.play(SoundStuff.ghostSiren, 1, 1, 1, -1,
							1);

					for (Ghost ghost : ghosts) {
						ghost.setEatable(true);
					}
					first = true;
				}

				if (times == 5)
					for (Ghost ghost : ghosts) {
						ghost.setBlinking(true);
					}
				else
					times++;
			}

			@Override
			public void onFinish() {
				for (Ghost ghost : ghosts) {
					ghost.reset();
				}
				SoundStuff.sp.stop(ID);
				eatInRow = 0;
				first = false;
				times = 0;

			}

		};

	}

	public void render(Canvas canvas) {
		// tlo
		paint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, WIDTH, HEIGHT, paint);
		// napis z punktami
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Points: " + points, 10, getHeight() - 40, paint);

		for (Block obj : path) {
			obj.draw(canvas);
		}
		for (SolidObject obj : food) {
			obj.draw(canvas);
		}
		pacman.draw(canvas);
		for (Ghost ghost : ghosts) {
			ghost.draw(canvas);
		}

	}

	public void loadNextLvl() {
		task.cancel();
		pacman.stop();
		boardGame = new Board(getResources(), R.raw.lvl);

		wall = boardGame.getWall();
		path = boardGame.getPath();
		food = boardGame.getbGame();
		pacman = new PacMan(getResources(), new Vect((BOARD_TILE_SIZE
				* BOARD_WIDTH / 2), BOARD_TILE_SIZE * 23), getContext(),
				boardGame.getBlock(), 100f, 3);
		ghosts = new LinkedList<Ghost>();

		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				11 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				13 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				15 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				17 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		int id = -1;
		do {
			id = SoundStuff.sp.play(SoundStuff.gameStart, 1, 1, 1, 0, 1);
		} while (id == 0);
	}

	public void update(double time) {

		pacman.update(time, getWidth(), getHeight());
		for (Ghost ghost : ghosts) {
			ghost.update(time, getWidth(), getHeight());
		}
		otherCollision();
		handleCollsion();
		if (food.isEmpty()) {
			loadNextLvl();
		}

	}

	public void doAi(int deficultiy) {
		switch (deficultiy) {
		case GameEnum.EASY:
			// todo
			break;
		// todo
		case GameEnum.NORMAL:
			break;
		case GameEnum.HARD:
			// todo
			break;
		}

	}

	private void otherCollision() {
		for (SolidObject obj : food) {
			if (Collision.detectNormalCollision(pacman.boundingRect,
					obj.boundingRect)) {
				obj.setIsTouched(true);

			}
		}
		for (Ghost ghost : ghosts) {
			if (Collision.detectNormalCollision(pacman.boundingRect,
					ghost.boundingRect)) {
				if (ghost.isEatable()) {
					ghost.setIsTouched(true);
					eatInRow++;
					points += GHOST_EAT_POINT * eatInRow;
					SoundStuff.sp.play(SoundStuff.pacmanEatGhost, 1, 1, 1, 0,
							1f);
					v.vibrate(100);

				} else
					pacman.die();
			}
		}

	}

	private void handleCollsion() {
		for (int i = 0; i < food.size(); i++) {
			if (food.get(i).getIsTouched()) {
				if (food.get(i).kind == GameEnum.FOODUP) {
					points += FOODUP_POINT;
					v.vibrate(100);
					task.onFinish();
					task.start();

				} else
					points += FOOD_POINT;

				food.remove(i);

			}

		}
	}

	@Override
	public boolean onTouchEvent(android.view.MotionEvent event) {

		pacman.dir(event);

		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		WIDTH = width;
		HEIGHT = height;
		BOARD_TILE_SIZE = (int) (WIDTH / BOARD_WIDTH);

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		boardGame = new Board(getResources(), R.raw.lvl0);

		wall = boardGame.getWall();
		path = boardGame.getPath();
		food = boardGame.getbGame();
		pacman = new PacMan(getResources(), new Vect((BOARD_TILE_SIZE
				* BOARD_WIDTH / 2), BOARD_TILE_SIZE * 23), getContext(),
				boardGame.getBlock(), 100f, 3);
		ghosts = new LinkedList<Ghost>();

		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				11 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				13 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				15 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(2, boardGame.getBlock(), getResources(), new Vect(
				17 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		int id = -1;
		do {
			id = SoundStuff.sp.play(SoundStuff.gameStart, 1, 1, 1, 0, 1);
		} while (id == 0);
		sensorManager = (SensorManager) getContext().getSystemService(
				getContext().SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
		v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
		loop.setRunning(true);
		loop.start();
		start = true;

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				loop.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && start) {

			pacman.dirAcc(event);
		}

	}

	public boolean isAccOn() {
		return accOn;
	}

	public void setAccOn(boolean accOn) {
		this.accOn = accOn;
	}

	public void stop() {

	}

}
