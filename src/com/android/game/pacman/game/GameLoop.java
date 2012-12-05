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
		long last = 0;

		Canvas canvas;

		double delay;
		while (running) {

			last = System.currentTimeMillis();
			delay = (last - lastFpsTime);

			lastFpsTime = last;
			canvas = this.surfaceHolder.lockCanvas();
			gamePanel.render(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);
			gamePanel.update(delay / 1000);
		}

	}

	public void setRunning(Boolean looping) {
		running = looping;
	}

}
