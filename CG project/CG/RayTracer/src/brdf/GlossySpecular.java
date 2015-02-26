package brdf;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class GlossySpecular extends Brdf {
	
	public double exp;

	public GlossySpecular(RGBColor color, double k, double exponent) {
		super(color, k);
		this.exp = exponent;
		// TODO Auto-generated constructor stub
	}

	@Override
	public RGBColor f(Intersection intersection, Vector wi, Vector w0) {
		RGBColor l = rho(intersection, w0);
		Vector v = intersection.normal.scale(intersection.normal.dot(wi)*2);
		double rdotwo = wi.scale(-1).add(v).dot(w0);
		if(rdotwo > 0.0)
			l = this.c.scale(this.k*Math.pow(rdotwo, this.exp));
		return l;
	}

	@Override
	public RGBColor rho(Intersection intersection, Vector wo) {
		return new RGBColor(0,0,0);
	}

}
