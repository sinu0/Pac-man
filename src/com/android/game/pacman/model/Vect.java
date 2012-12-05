package com.android.game.pacman.model;

public class Vect {

	public double x;
	public double y;

	public Vect(double x, double y ) {
		this.x = x;
		this.y = y;
	}

	public Vect add(Vect another) {
		return new Vect(x+another.x,y+another.y);
		
	}
	public Vect mulitply(double speed){
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
	public void set(double x,double y){
		this.x=x;
		this.y=y;
	}
	static public  Vect normalize(Vect vect){
		return new Vect((double)(vect.x/vect.getLength()), (double)(vect.y/vect.getLength()));
	}
	static public double dot(Vect one,Vect two){
		return one.x*two.x+one.y*two.y;
	}
}
