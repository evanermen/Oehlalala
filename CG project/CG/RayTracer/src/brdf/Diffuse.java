package brdf;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class Diffuse extends Brdf {
	double kd; //tussen 0 en 1
	RGBColor cd; //diffuse color
	
	
	public Diffuse(){
		this.kd = 0.65;
		this.cd = new RGBColor(0.5,0.5,0.5);
	}
	
	public Diffuse(RGBColor color){
		this.cd =  color;
		this.kd = 0.65;
		
	}
	
	public Diffuse(RGBColor cd, double kd){
		this.cd = cd;
		this.kd = kd;
	}
	
	
	
	public RGBColor f(Intersection intersection, Vector w0, Vector wi) {
		return cd.scale(kd*1/Math.PI);
	}

	public RGBColor rho(Intersection intersection, Vector w0){
		return cd.scale(kd);
	}
	
}
