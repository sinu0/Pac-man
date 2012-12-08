package com.android.game.pacman.game;

import com.adroid.game.pacman.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	GameLogic game;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenHeight = metrics.heightPixels;
		int screenWidth = metrics.widthPixels;
		game = new GameLogic(this,screenWidth,screenHeight);
		setContentView(game);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
//	@Override
//	protected void onPause() {
//		game.loop=null;
//		super.onPause();
//		
//	}
//	@Override
//	protected void onResume() {
//		game.loop = new GameLoop(game.getHolder(), game);
//		super.onResume();
//	}

}
