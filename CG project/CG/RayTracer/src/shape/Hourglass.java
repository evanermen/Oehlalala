package shape;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

/**
 * Represents a finite, two-sided cone
 * @author eline vanermen
 *
 */
public class Hourglass implements Shape{
	public Transformation transformation;
	double angle;
	double height;
	
	
	public Hourglass(Transformation transformation, double angle, double height){
		if (transformation == null)
			throw new NullPointerException("the given origin is null!");
		if (height < 0)
			throw new IllegalArgumentException(
					"the given height cannot be smaller than zero!");
		if (angle < 0 || angle > 2*Math.PI)
			throw new IllegalArgumentException(
					"the given angle must be between 0 and 2*Pi cannot be smaller than zero!");
		this.transformation = transformation;
		this.angle = angle;
		this.height = height;
	}
	
	@Override
	//http://mrl.nyu.edu/~dzorin/rend05/lecture2.pdf
	public boolean intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);
		Vector center = new Vector(0,0,1);
		
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		Vector z = new Vector(0,0,1);
		
		//double a = Math.cos(angle)* Math.cos(angle)  *  (direction.subtract(z.scale(direction.dot(z))).dot(direction.subtract(z.scale(direction.dot(z)))));
		//double b = 2 * Math.cos(angle) * Math.cos(angle)  *  direction.subtract(direction.scale(direction.dot(z));
		
		return false;
	}

}
