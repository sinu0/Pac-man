package com.android.game.pacman.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.android.game.pacman.utils.GameEnum;

public class FoodUp extends SolidObject{
	
	public FoodUp(int x,int y,Resources res){
		super(x*17, y*17);		
		kind =GameEnum.FOODUP;
		if(bitmap==null){
			bitmap = BitmapFactory.decodeResource(res,
					com.adroid.game.pacman.R.drawable.foodup);
		}
		
		
		
	}

}
