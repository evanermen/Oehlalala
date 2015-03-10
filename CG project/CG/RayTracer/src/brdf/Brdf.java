package brdf;

import math.Vector;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public abstract class Brdf {


	public Texture t;
	public double k;

	public Brdf(Texture texture, double k){
		this.t = texture;
		this.k = k;
	}

	public abstract RGBColor f(Intersection intersection, Vector wi, Vector wo);

	public abstract RGBColor rho(Intersection intersection, Vector wo);

}
