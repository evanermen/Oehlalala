package shape;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

/**
 * Interface which should be implemented by all {@link Shape}s.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public abstract class Shape {
	
	
	private Transformation transformation;
	
	/**
	 * Returns whether the given {@link Ray} intersects this {@link Shape}.
	 * False when the given ray is null.
	 * 
	 * @param ray
	 *            the ray to intersect with.
	 * @return true when the given {@link Ray} intersects this {@link Shape}.
	 */
	public abstract Intersection intersect(Ray ray);
	
	public abstract RGBColor getColor(Point point);
	
	public abstract Transformation getTransformation();
	
	public abstract Vector getNormal(Point point);
}
