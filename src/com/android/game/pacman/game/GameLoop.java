package com.android.game.pacman.game;

import android.graphics.Canvas;
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
		Canvas canvas;
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					gamePanel.update();
					gamePanel.render(canvas);
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void setRunning(Boolean looping) {
		running = looping;
	}

}
