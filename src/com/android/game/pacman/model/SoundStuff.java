package com.android.game.pacman.model;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.adroid.game.pacman.R;

public class SoundStuff {
	public static SoundPool sp;
	public static int pacamnWalk;
	public static int ghostSiren;
	public static int gameStart;
	public static int gameOver;
	public static int pacmanCherry;
	public static int pacmanEatGhost;

	public SoundStuff(Context context) {
	
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		pacamnWalk = sp.load(context, R.raw.pacman_walk, 1);
		ghostSiren = sp.load(context, R.raw.pacman_siren, 1);
		gameStart = sp.load(context, R.raw.pacman_start, 1);
		gameOver = sp.load(context, R.raw.pacmandie, 1);
		pacmanCherry = sp.load(context, R.raw.pacman_cherry, 1);
		pacmanEatGhost = sp.load(context, R.raw.pacman_ghost_eating, 1);

	}

}
