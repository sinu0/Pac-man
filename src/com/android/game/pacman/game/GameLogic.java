package com.android.game.pacman.game;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Vibrator;
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

public class GameLogic extends SurfaceView implements SurfaceHolder.Callback {

	private PacMan pacman;

	private LinkedList<Block> wall;
	private LinkedList<Block> path;
	private LinkedList<SolidObject> food;
	private LinkedList<Ghost> ghosts;

	private GameLoop loop;
	private Board boardGame;
	private SoundStuff ss;
	private Bitmap background;
	private int points = 0;
	private int eatInRow = 0; // mnoznik do zjedzonych duszkow

	private CountDownTimer task;
	private Timer t = new Timer();

	private Paint paint;

	private Vibrator v;

	static int BOARD_TILE_SIZE=17;
	static int BOARD_HEIGHT = 31;
	static int BOARD_WIDTH = 28;
	static int GHOST_EAT_POINT = 200;
	static int FOODUP_POINT = 150;
	static int FOOD_POINT = 50;

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
		background = Bitmap.createBitmap(tmpbackground, 0, 0, 256, 256, matrix,
				false);
		loop = new GameLoop(getHolder(), this);
		ss = new SoundStuff(context);
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
		
		//przez 20 sek po 15 zaczna mrugac duchy
		task = new CountDownTimer(20000, 1000) {
			private boolean first = false;
			private int ID;
			int times=0;
			@Override
			public void onTick(long millisUntilFinished) {
				if(!first){
				    ID=SoundStuff.sp.play(SoundStuff.ghostSiren, 1, 1, 1, -1, 1);
				    first=true;
				}
				
				if(times==15)
					for (Ghost ghost : ghosts) {
						ghost.setBlinking(true);
					}
				else
					times++;
			}

			@Override
			public void onFinish() {
				for (Ghost ghost : ghosts) {
					ghost.setEatable(false);
					ghost.setBlinking(false);
				}
					SoundStuff.sp.stop(ID);
					eatInRow = 0;
					first = false;
					
				
			}
			
		};
		
	}

	public void render(Canvas canvas) {
		// tlo
		paint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, 480, 1000, paint);
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
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void prepareLevel() {
		// TODO generate level
	}

	public void update(float time) {

		pacman.update(time, getWidth(), getHeight());
		for (Ghost ghost : ghosts) {
			ghost.update(time, getWidth(), getHeight());
		}
		otherCollision();
		handleCollsion();
		if (food.isEmpty()) {
			// do end game stuff
		}

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
					SoundStuff.sp.play(SoundStuff.pacmanEatGhost, 1, 1,1, 0, 1f);
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
					
					task.start();
					for (Ghost ghost : ghosts) {
						ghost.setEatable(true);

					}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//BOARD_TILE_SIZE=getWidth()/28;
		boardGame = new Board(BOARD_WIDTH, BOARD_HEIGHT, BOARD_TILE_SIZE,
				getResources());
		wall = boardGame.getWall();
		path = boardGame.getPath();
		food = boardGame.getbGame();
		pacman = new PacMan(getResources(), new Vect((BOARD_TILE_SIZE
				* BOARD_WIDTH / 2), BOARD_TILE_SIZE * 23), getContext(),
				boardGame.getBlock(), BOARD_TILE_SIZE, 100f);
		ghosts = new LinkedList<Ghost>();

		ghosts.add(new Ghost(17, 2, boardGame.getBlock(), getResources(),
				new Vect(12 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(17, 2, boardGame.getBlock(), getResources(),
				new Vect(13 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(17, 2, boardGame.getBlock(), getResources(),
				new Vect(14 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		ghosts.add(new Ghost(17, 2, boardGame.getBlock(), getResources(),
				new Vect(15 * BOARD_TILE_SIZE, 14 * BOARD_TILE_SIZE)));
		loop.setRunning(true);
		loop.start();
		int id=-1;
		do {
			id=SoundStuff.sp.play(SoundStuff.gameStart, 1, 1, 1, 0, 1);
		} while(id==0 );
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
