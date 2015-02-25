package shape;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

/**
 * Represents an infinite plane.
 * @author eline vanermen
 *
 */
public class Plane extends Shape {


	/**
	 * Creates a new Plane (the x-y-plane), transformed by the given Transformation
	 * @param transformation
	 * @throws NullPointerException
	 *             when the transformation is null.
	 *             
	 */
	public Plane(Transformation transformation, Material material){
		super(transformation, material);
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);
		Vector normal = new Vector(0,0,1);
		
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		
		double divisor = direction.dot(normal);
		double divident = -(rayOrigin.subtract(0, 0, 0)).dot(normal);
		
		if (direction.dot(normal)== 0 || divident/divisor <  0){return null;}
		else { 
				Intersection intersection = new Intersection(ray, divident/divisor, this);
				return intersection;
		}
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(255,255,0);
	}

	public Vector getNormal(Point point){
		return transformation.getNormalTransformationMatrix().transform(new Vector(0,0,1)).normalize();
	}


}
