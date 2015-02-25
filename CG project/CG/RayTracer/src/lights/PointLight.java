package lights;

import math.Point;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class PointLight extends Light {
	
	public Point location;
	
	public PointLight(RGBColor color, double ls, Point location){
		super(color, ls);
		this.location = location;
	}

	public Vector getDirection(Intersection intersection) {
		return location.subtract(intersection.point);
	}
	

}
