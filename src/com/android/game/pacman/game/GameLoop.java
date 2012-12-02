package com.android.game.pacman.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

	private SurfaceHolder surfaceHolder;
	private GameLogic gamePanel;
	private boolean running;
	private Canvas canvas;

	public GameLoop(SurfaceHolder surfaceHolder, GameLogic gamePanel) {

		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		long lastFpsTime = 0;
		// How many frames were in last FPS update
		int frameCounter = 0;

		Canvas canvas;
		int TICKS_PER_SECOND = 24;
		
		int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		int MAX_FRAMESKIP = 5;

		long next_game_tick = System.currentTimeMillis();
		int loops;
		float delay;
		while (running) {
			canvas = null;

			frameCounter++;

			 delay =  (System.currentTimeMillis()- lastFpsTime);
			
			lastFpsTime=System.currentTimeMillis();

			loops = 0;
			while (System.currentTimeMillis() > next_game_tick
					&& loops < MAX_FRAMESKIP) {
				
				canvas = this.surfaceHolder.lockCanvas();

				gamePanel.render(canvas);

				surfaceHolder.unlockCanvasAndPost(canvas);
				next_game_tick += SKIP_TICKS;
				loops++;
			}
			
			gamePanel.update(delay/1000);
		
		}

	}

	public void setRunning(Boolean looping) {
		running = looping;
	}

}
