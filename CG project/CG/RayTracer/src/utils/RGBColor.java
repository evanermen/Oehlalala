package utils;

import math.Vector;

public class RGBColor {
	public double r;
	public double g;
	public double b;


	public RGBColor(){
		r = 0;
		g = 0;
		b = 0;
	}
	
	public RGBColor(double red, double green, double blue){
		r = red;
		g = green;
		b = blue;
	}

	public RGBColor(Vector v) {
		r = v.x;
		g = v.y;
		b = v.z;
	}
	
	public RGBColor scale(double s){
		return new RGBColor(r*s, g*s, b*s);
	}
	
	
	public RGBColor add(RGBColor c){
		return new RGBColor(r+c.r, g+c.g, b+c.b);
	}
	
	public RGBColor multiply(RGBColor color){
		return new RGBColor(this.r*color.r, this.g*color.g, this.b*color.b);
	}
	
	public RGBColor showOutOfGamut(){
		if(this.r > 1 || this.g > 1 || this.b > 1){
			return new RGBColor(1,0,0);
		}
		else{return this;}
	}
	
	public RGBColor maxToOne(){
		double max = Math.max(r,Math.max(g, b));
		if(max>1.0) return new RGBColor(r/max, g/max, b/max);
		else return this;
	}
}

