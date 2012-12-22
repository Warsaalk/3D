package util;

import java.util.ArrayList;

public class Colour {
	
	public float r;
	public float g;
	public float b;
	
	public Colour(){
	}
	
	public Colour(float r, float g, float b) {
		set(r,g,b);
	}
	
	public void set(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void set(Colour colour){
		this.r = colour.r;
		this.g = colour.g;
		this.b = colour.b;
	}
	
	public void add(Colour colour){
		r += colour.r;
		g += colour.g;
		b += colour.b;
	}
	
	public Colour mult(Colour colour){
		return new Colour(r*colour.r, g*colour.g, b*colour.b);
	}
	
	public Colour mult(float a){
		return new Colour(r*a, g*a, b*a);
	}
	
	public float[] get4Tuple(){
		return new float[]{r,g,b,1f};
	}
	
	public void average(ArrayList<Colour> colours){
		Colour col = new Colour();
		int len = colours.size();
		for( int i=0; i<len; i++){
			col.add(colours.get(i));
		}
		r = col.r / len;
		g = col.g / len;
		b = col.b / len;
	}
	
}
