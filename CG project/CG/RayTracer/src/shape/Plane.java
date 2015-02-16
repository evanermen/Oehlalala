package shape;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.RGBColor;

/**
 * Represents an infinite plane.
 * @author eline vanermen
 *
 */
public class Plane implements Shape {

	private Transformation transformation;

	/**
	 * Creates a new Plane (the x-y-plane), transformed by the given Transformation
	 * @param transformation
	 * @throws NullPointerException
	 *             when the transformation is null.
	 *             
	 */
	public Plane(Transformation transformation){
		if (transformation == null)
			throw new NullPointerException("the given origin is null!");
		this.transformation = transformation;
	}
	
	@Override
	public Point intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);
		Vector normal = new Vector(0,0,1);
		
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		
		double divisor = direction.dot(normal);
		double divident = -(rayOrigin.subtract(0, 0, 0)).dot(normal);
		
		if (direction.dot(normal)== 0 || divident/divisor <  0){return null;}
		else { 
				Point intersection = transformed.origin.add(transformed.direction.scale(divident/divisor));
				return intersection;
		}
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(255,0,0);
	}

}
