package brdf;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class Lambertian extends Brdf {

	public Lambertian(RGBColor cd, double kd){
		super(cd, kd);
	}
	
	public Lambertian(RGBColor cd){
		super(cd, 0.5);
	}
	
	public Lambertian(){
		super(new RGBColor(1,1,1), 0.5);
	}
	
	
	public RGBColor f(Intersection intersection, Vector w0, Vector wi) {
		return c.scale(k/Math.PI);
	}

	public RGBColor rho(Intersection intersection, Vector w0){
		return c.scale(k);
	}
	
}
