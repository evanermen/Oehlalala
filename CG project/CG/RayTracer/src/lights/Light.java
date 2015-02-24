package lights;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public abstract class Light {
	public RGBColor color;
	
	public Light(){
		this.color = new RGBColor(255,255,255);
	}
	
	public Light(RGBColor color){
		this.color = color;
	}
	
	public abstract RGBColor getRadiance(Intersection intersection);
	
	public abstract Vector getDirection(Intersection intersection);
	
}
