package shape;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import tracer.Intersection;
import utils.RGBColor;

/**
 * Represents a finite, two-sided cone
 * @author eline vanermen
 *
 */
public class Hourglass extends Shape{
	public Transformation transformation;
	double angle;
	double height;
	
	
	public Hourglass(Transformation transformation, double angle, double height){
		if (transformation == null)
			throw new NullPointerException("the given origin is null!");
		if (height < 0)
			throw new IllegalArgumentException(
					"the given height cannot be smaller than zero!");
		if (angle < 0 || angle > 90)
			throw new IllegalArgumentException(
					"the given angle must be between 0 and 90 cannot be smaller than zero!");
		this.transformation = transformation;
		this.angle = angle;
		this.height = height;
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);
		Vector center = new Vector(0,0,1);
		
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		Vector z = new Vector(0,0,1);
		
		double a = direction.y*direction.y + direction.z*direction.z - direction.x*direction.x;
		double b = 2*rayOrigin.y*direction.y + 2*rayOrigin.z*direction.z - 2*rayOrigin.x*direction.x;
		double c = rayOrigin.y*rayOrigin.y + rayOrigin.z*rayOrigin.z - rayOrigin.x*rayOrigin.x;
		
		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return null;
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;
		
		if(t0>t1)
		return new Intersection(ray, t1, this);
		else return new Intersection(ray, t0, this);
		
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(0,0,255);
	}

	@Override
	public Transformation getTransformation() {
		return transformation;
	}

	@Override
	public Vector getNormal(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

}
