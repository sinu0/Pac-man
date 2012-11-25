package com.android.game.pacman.model;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.adroid.game.pacman.R;

public class SoundStuff {
	static SoundPool sp;
	static int pacamnWalk;
	public SoundStuff(Context context){
		sp=new SoundPool(3,AudioManager.STREAM_MUSIC,0);
		pacamnWalk = sp.load(context,R.raw.pacman_walk, 1);
		
		
	}
	public int getIdPacman(){
		return pacamnWalk;
	}
	
	

}
