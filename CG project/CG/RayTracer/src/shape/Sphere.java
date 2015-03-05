package shape;

import java.util.ArrayList;
import java.util.Collections;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import boundingBox.BBoxCreator;
import boundingBox.ShapeBox;

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
		return new RGBColor(1,0,0);
	}

	@Override
	public double shadowHit(Ray ray) {
		
		Ray transformed = transformation.transformInverse(ray).epsilonOffset();

		Vector o = transformed.origin.toVector3D();

		double a = transformed.direction.dot(transformed.direction);
		double b = 2.0 * (transformed.direction.dot(o));
		double c = o.dot(o) - radius * radius;

		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return Double.POSITIVE_INFINITY;
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;
		
		if(t0>t1){
			return t1;	
		}
		
		else {
			return t0;
		}
		//return t0 >= 0; //|| t1 >= 0;
	}

	
	//first create bbox around original volume, then transform the bbox, then make a bbox around this bbox
	@Override
	public void createBBox(BBoxCreator creator) {
		ArrayList<Double> xs = new ArrayList<Double>();
		ArrayList<Double> ys = new ArrayList<Double>();
		ArrayList<Double> zs = new ArrayList<Double>();
		Point a = transformation.transform(new Point(-radius, -radius, -radius));
		xs.add(a.x); ys.add(a.y); zs.add(a.z);
		Point b = transformation.transform(new Point(radius, -radius, -radius));
		xs.add(b.x); ys.add(b.y); zs.add(b.z);
		Point c = transformation.transform(new Point(-radius, radius, -radius));
		xs.add(c.x); ys.add(c.y); zs.add(c.z);
		Point d = transformation.transform(new Point(-radius, -radius, radius));
		xs.add(d.x); ys.add(d.y); zs.add(d.z);
		Point e = transformation.transform(new Point(radius, radius, -radius));
		xs.add(e.x); ys.add(e.y); zs.add(e.z);
		Point f = transformation.transform(new Point(-radius, radius, radius));
		xs.add(f.x); ys.add(f.y); zs.add(f.z);
		Point g = transformation.transform(new Point(radius, -radius, radius));
		xs.add(g.x); ys.add(g.y); zs.add(g.z);
		Point h = transformation.transform(new Point(radius, radius, radius));
		xs.add(h.x); ys.add(h.y); zs.add(h.z);
		
		Point min = new Point(Collections.min(xs), Collections.min(ys), Collections.min(zs));
		Point max = new Point(Collections.max(xs), Collections.max(ys), Collections.max(zs));
		
		System.out.println("radius of sphere " + this.radius);
		System.out.println(min);
		System.out.println(max);
		ShapeBox bbox = new ShapeBox(min, max, this);
		creator.shapeboxes.add(bbox);
	}
		
	


	
}
