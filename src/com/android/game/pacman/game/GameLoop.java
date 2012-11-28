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
        //How many frames were in last FPS update
        int frameCounter = 0;

		Canvas canvas;
		while (running) {
			canvas = null;
	
			frameCounter++;

	        int delay = (int)(System.currentTimeMillis() - lastFpsTime);
	        //If last FPS was calculated more than 1 second ago
	        if (delay > 1000) {
	            //Calculate FPS
	            double FPS = (((double)frameCounter)/delay)*1000; //delay is in milliseconds, that's why *1000
	            frameCounter = 0; //Reset frame counter
	            lastFpsTime = System.currentTimeMillis(); //Reset fps timer

	            Log.v("FPS: ",String.valueOf(FPS));
	        }
	        gamePanel.update();
			
				canvas = this.surfaceHolder.lockCanvas();
				
					
					gamePanel.render(canvas);
				
		
					surfaceHolder.unlockCanvasAndPost(canvas);
				
		}
		
	}

	public void setRunning(Boolean looping) {
		running = looping;
	}

}
