package com.android.game.pacman.model;

public class Vect {

	public float x;
	public float y;

	public Vect(float x, float y ) {
		this.x = x;
		this.y = y;
	}

	public Vect add(Vect another) {
		return new Vect(x + another.x, y + another.y);
	}
	public Vect mulitply(float speed){
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
		return new Vect((float)(vect.x/vect.getLength()), (float)(vect.y/vect.getLength()));
	}
	static public double dot(Vect one,Vect two){
		return one.x*two.x+one.y*two.y;
	}
}
