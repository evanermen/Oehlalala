package shape;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

/**
 * Represents a three dimensional sphere.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public class Sphere extends Shape {
	
	public final double radius;

	/**
	 * Creates a new {@link Sphere} with the given radius and which is
	 * transformed by the given {@link Transformation}.
	 * 
	 * @param transformation
	 *            the transformation applied to this {@link Sphere}.
	 * @param radius
	 *            the radius of this {@link Sphere}..
	 * @throws NullPointerException
	 *             when the transformation is null.
	 * @throws IllegalArgumentException
	 *             when the radius is smaller than zero.
	 */
	public Sphere(Transformation transformation, Material material, double radius) {
		super(transformation, material);
		if (radius < 0)
			throw new IllegalArgumentException(
					"the given radius cannot be smaller than zero!");
		this.transformation = transformation;
		this.radius = radius;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.Shape#intersect(geometry3d.Ray3D)
	 */
	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);

		Vector o = transformed.origin.toVector3D();

		double a = transformed.direction.dot(transformed.direction);
		double b = 2.0 * (transformed.direction.dot(o));
		double c = o.dot(o) - radius * radius;

		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return null;
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;
		
		if(t0>t1){
			Point originPoint = transformed.origin.add(transformed.direction.scale(t1));	
			Point point = transformation.transform(originPoint);
			Vector normal = getNormal(originPoint);
			return new Intersection(ray, t1, this, point, normal);	
		}
		
		else {
			Point originPoint = transformed.origin.add(transformed.direction.scale(t0));	
			Point point = transformation.transform(originPoint);
			Vector normal = getNormal(originPoint);
			return new Intersection(ray, t0, this, point, normal);
		}
		//return t0 >= 0; //|| t1 >= 0;
	}
	
	public Vector getNormal(Point point){
		Vector transformedNormal = transformation.getNormalTransformationMatrix().transform(point.subtract(0,0,0));
		return transformedNormal.normalize();
	}
	
	
	public RGBColor getColor(Point point){
		return new RGBColor(1,1,0);
	}


	
}
