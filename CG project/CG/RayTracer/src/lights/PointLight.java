package lights;

import math.Point;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class PointLight extends Light {
	
	public RGBColor color;
	public double ls;
	public Point location;
	
	
	
	@Override
	public RGBColor getRadiance(Intersection intersection) {
		return color.scale(ls);
	}

	@Override
	public Vector getDirection(Intersection intersection) {
		return location.subtract(intersection.point);
	}
	

}
