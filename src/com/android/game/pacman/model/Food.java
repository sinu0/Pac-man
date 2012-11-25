package com.android.game.pacman.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.android.game.pacman.utils.GameEnum;

public class Food extends SolidObject {
	


	public Food(int x,int y,Resources res,GameEnum kind){
		super(x*17, y*17);		
		if(kind ==GameEnum.FOOD)
		bitmap = BitmapFactory.decodeResource(res,
				com.adroid.game.pacman.R.drawable.food);
		else
			bitmap = BitmapFactory.decodeResource(res,
					com.adroid.game.pacman.R.drawable.foodup);
		this.kind=kind;
	}

}