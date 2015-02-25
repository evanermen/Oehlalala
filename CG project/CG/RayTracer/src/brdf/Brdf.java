package brdf;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public abstract class Brdf {


	public RGBColor c;
	public double k;

	public Brdf(RGBColor color, double k){
		this.c = color;
		this.k = k;
	}

	public abstract RGBColor f(Intersection intersection, Vector wi, Vector wo);

	public abstract RGBColor rho(Intersection intersection, Vector wo);

}
