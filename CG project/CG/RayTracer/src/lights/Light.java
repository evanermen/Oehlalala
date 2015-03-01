package lights;



import java.util.List;

import math.Ray;
import math.Vector;
import shape.Shape;
import utils.Intersection;
import utils.RGBColor;

public abstract class Light {
	public RGBColor color;
	public double ls;
	
	public Light(){
		this.color = new RGBColor(1,1,1);
		//System.out.println("Color is set to " + color.r + ", " + color.g + ",  " + color.b);
	}
	
	public Light(RGBColor color, double ls){
		this.color = color;
		this.ls = ls;
	}
	
	public RGBColor getRadiance(Intersection intersection) {
		return color.scale(ls);
	}
	
	public abstract Vector getDirection(Intersection intersection);

	public boolean castShadows() {
		return true;
	}

	
}
