package com.android.game.pacman.model;

import com.android.game.pacman.utils.GameEnum;

import android.R.integer;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

public class Collision {
	//nie uzywane
	static public Pair<GameEnum, Integer> wallDetectCollision(Rect one, Rect two) {
		double w = 0.5 * (one.width()-1 + two.width());
		double h = 0.5 * (one.height()-1 + two.height());
		double dx = one.exactCenterX() - two.exactCenterX();
		double dy = one.exactCenterY() - two.exactCenterY();
		if (Math.abs(dx) <= w && Math.abs(dy) <= h) {

			/* collision! */
			double wy = w * dy;
			double hx = h * dx;
			if (wy > hx)
				if (wy > -hx) {
					/* collision at the top */

					return new Pair<GameEnum, Integer>(GameEnum.UP,
							Integer.valueOf((int)(one.exactCenterY() - two.exactCenterY())-17));
				} else
					/* on the left */
					return new Pair<GameEnum, Integer>(GameEnum.RIGHT,
							Integer.valueOf((int)(two.exactCenterX() - one.exactCenterX())-17));

			else if (wy > -hx)
				/* on the right */
				return new Pair<GameEnum, Integer>(GameEnum.LEFT,
						Integer.valueOf((int)(two.exactCenterX() - one.exactCenterX())+17));

			else {
				/* at the bottom */
				return new Pair<GameEnum, Integer>(GameEnum.DOWN,
						Integer.valueOf((int)(one.exactCenterY() - two.exactCenterY())+17));
			}
		}
		return new Pair<GameEnum, Integer>(GameEnum.NORMAL, Integer.valueOf(0));

	}
	//nie uzwyane
	static public Pair<GameEnum, Double> pathDetectCollision(Rect one, Rect two,int size) {
		double w = 0.5 * (one.width()+size + two.width());
		double h = 0.5 * (one.height()+size + two.height());
		double dx = one.exactCenterX() - two.exactCenterX();
		double dy = one.exactCenterY() - two.exactCenterY();
		if (Math.abs(dx) <= w && Math.abs(dy) <= h) {

			/* collision! */
			double wy = w * dy;
			double hx = h * dx;
			//odleglodc miedzu srodkami
			double d= Math.sqrt(Math.pow(two.exactCenterX()-one.exactCenterX(), 2)+Math.pow(two.exactCenterY()-one.exactCenterY(), 2));
			if (wy > hx)
				if (wy > -hx) {
					/* collision at the top */

					return new Pair<GameEnum, Double>(GameEnum.UP,
							Double.valueOf(d));
				} else
					/* on the left */
					return new Pair<GameEnum, Double>(GameEnum.RIGHT,
							Double.valueOf(d));

			else if (wy > -hx)
				/* on the right */
				return new Pair<GameEnum, Double>(GameEnum.LEFT,
						Double.valueOf(d));

			else {
				/* at the bottom */
				return new Pair<GameEnum, Double>(GameEnum.DOWN,
						Double.valueOf(d));
			}
		}
		return new Pair<GameEnum, Double>(GameEnum.NORMAL, Double.valueOf(0));

	}
    public static boolean detectNormalCollision(Rect one,Rect two){
		return one.intersect(two);
	}
}
