package brdf;

import math.Vector;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public class Lambertian extends Brdf {

	public Lambertian(Texture cd, double kd){
		super(cd, kd);
	}
	
	public Lambertian(Texture cd){
		super(cd, 0.5);
	}
		
	
	public RGBColor f(Intersection intersection, Vector w0, Vector wi) {
		return t.getColor(intersection).scale(k/Math.PI);
	}

	public RGBColor rho(Intersection intersection, Vector w0){
		RGBColor col = t.getColor(intersection);
		return t.getColor(intersection).scale(k);
	}
	
	
	
}
