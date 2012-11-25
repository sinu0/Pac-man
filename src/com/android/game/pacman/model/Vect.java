package com.android.game.pacman.model;

public class Vect {

	public int x;
	public int y;

	public Vect(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vect add(Vect another) {
		return new Vect(x + another.x, y + another.y);
	}
	public Vect mulitply(int speed){
		x=x*speed;
		y=y*speed;
		return this;
	}
	public boolean equals(Vect vect){
		return x==vect.x && y==vect.y;
	}
	public double getLength(){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public Vect subtract(Vect vect){
		return new Vect(x-vect.x,y-vect.y);
	}
	static public  Vect normalize(Vect vect){
		return new Vect((int)(vect.x/vect.getLength()), (int)(vect.y/vect.getLength()));
	}
	static public double dot(Vect one,Vect two){
		return one.x*two.x+one.y*two.y;
	}
}
