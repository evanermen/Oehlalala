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
}

