package com.android.game.pacman.game;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Vibrator;
import android.util.Log;
import android.util.Pair;
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

public class GameLogic extends SurfaceView implements SurfaceHolder.Callback {

	private PacMan pacman;
	private GameLoop loop;
	private ArrayList<Block> wall;
	private ArrayList<Block> path;
	private ArrayList<SolidObject> food;
	private Board boardGame;
	private SoundStuff ss;
	private Bitmap background;
	private Ghost ghost1;
	private Ghost ghost2;
	private Ghost ghost3;
	private Ghost ghost4;

	static int BOARD_TILE_SIZE = 17;
	static int BOARD_HEIGHT = 31;
	static int BOARD_WIDTH = 28;

	public GameLogic(Context context) {
		super(context);
		setFocusable(true);
		getHolder().addCallback(this);
		Bitmap tmpbackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.blue_gradien); 

		float scaleWidth = ((float) 480) / 256;
		float scaleHeight = ((float) 800) / 256;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		background = Bitmap.createBitmap(tmpbackground, 0, 0,
				256, 256, matrix,
				false);
		loop = new GameLoop(getHolder(), this);
		ss = new SoundStuff(context);

	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(background, 0f, 0f, null);

		for (Block obj : wall) {
			obj.draw(canvas);
		}
		for (Block obj : path) {
			obj.draw(canvas);
		}
		for (SolidObject obj : food) {
			obj.draw(canvas);
		}
		pacman.draw(canvas);
		ghost1.draw(canvas);
		ghost2.draw(canvas);
		ghost3.draw(canvas);
		ghost4.draw(canvas);
	}

	public void prepareLevel() {
		// TODO generate level
	}

	public void update() {

		otherCollision();
		handleCollsion();
		pacman.update(System.currentTimeMillis(), getWidth(), getHeight());
		ghost1.update(System.currentTimeMillis(), getWidth(), getHeight());
		ghost2.update(System.currentTimeMillis(), getWidth(), getHeight());
		ghost3.update(System.currentTimeMillis(), getWidth(), getHeight());
		ghost4.update(System.currentTimeMillis(), getWidth(), getHeight());

	}

	public void doAi(GameEnum deficultiy) {
		switch (deficultiy) {
		case EASY:
			// todo
			break;
		// todo
		case NORMAL:
			break;
		case HARD:
			// todo
			break;
		}

	}

	private void otherCollision() {
		for (SolidObject obj : food) {
			if (Collision.detectNormalCollision(pacman.getBoundingRect(),
					obj.getBoundingRect())) {
				obj.setIsTouched(true);
			}
		}
	}

	private void handleCollsion() {
		for (int i = 0; i < food.size(); i++) {
			if (food.get(i).getIsTouched()) {
				if (food.get(i).getKind() == GameEnum.FOODUP) {
					Vibrator v = (Vibrator) getContext().getSystemService(
							Context.VIBRATOR_SERVICE);
					v.vibrate(100);
				}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		boardGame = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_TILE_SIZE,
				getResources());
		wall = boardGame.getWall();
		path = boardGame.getPath();
		food = boardGame.getbGame();
		pacman = new PacMan(getResources(), new Vect((int) (BOARD_TILE_SIZE
				* BOARD_WIDTH / 2), BOARD_TILE_SIZE * 23), getContext(),
				boardGame.getBlock(), 17, 5);
		ghost1=new Ghost(17, 2, boardGame.getBlock(),getResources(),new Vect(12*BOARD_TILE_SIZE, 14*BOARD_TILE_SIZE));
		ghost2=new Ghost(17, 2, boardGame.getBlock(),getResources(),new Vect(13*BOARD_TILE_SIZE, 14*BOARD_TILE_SIZE));
		ghost3=new Ghost(17, 2, boardGame.getBlock(),getResources(),new Vect(14*BOARD_TILE_SIZE, 14*BOARD_TILE_SIZE));
		ghost4=new Ghost(17, 2, boardGame.getBlock(),getResources(),new Vect(15*BOARD_TILE_SIZE, 14*BOARD_TILE_SIZE));
		loop.setRunning(true);
		loop.start();

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

}
