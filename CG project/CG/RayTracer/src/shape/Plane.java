package shape;

import java.util.ArrayList;
import java.util.Collections;

import boundingBox.BBoxCreator;
import boundingBox.ShapeBox;
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
			Point originPoint = transformed.origin.add(transformed.direction.scale(divident/divisor));	
			Point point = transformation.transform(originPoint);
			Vector transformedNormal = getNormal(originPoint, ray);
			Intersection intersection = new Intersection(ray, divident/divisor, this, point, transformedNormal);
			return intersection;
		}
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(1,1,0);
	}

	public Vector getNormal(Point point, Ray ray){
		Vector normal =  transformation.getNormalTransformationMatrix().transform(new Vector(0,0,1)).normalize();
		//if(normal.dot(ray.direction.normalize())<0) {System.out.println("inverting normal : " + normal.scale(-1)); return normal.scale(-1); }
		//else return normal;
		return normal;
	}

	@Override
	public Vector getNormal(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double shadowHit(Ray ray) {
		Ray transformed = transformation.transformInverse(ray).epsilonOffset();
		Vector normal = new Vector(0,0,1);

		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;

		double divisor = direction.dot(normal);
		double divident = -(rayOrigin.subtract(0, 0, 0)).dot(normal);

		if (direction.dot(normal)== 0 || divident/divisor <  0){return Double.POSITIVE_INFINITY;}
		else { 	
			return divident/divisor;
		}
	}

	@Override
	public void createBBox(BBoxCreator creator) {
		ArrayList<Double> xs = new ArrayList<Double>();
		ArrayList<Double> ys = new ArrayList<Double>();
		ArrayList<Double> zs = new ArrayList<Double>();
		//Double inf = Double.POSITIVE_INFINITY;
		Double inf = Double.MAX_VALUE;
		Point a = transformation.transform(new Point(inf, 0.01, inf));
		xs.add(a.x); ys.add(a.y); zs.add(a.z);
		Point b = transformation.transform(new Point(inf, 0.01, -inf));
		xs.add(b.x); ys.add(b.y); zs.add(b.z);
		Point c = transformation.transform(new Point(-inf, 0.01, inf));
		xs.add(c.x); ys.add(c.y); zs.add(c.z);
		Point d = transformation.transform(new Point(-inf, 0.01, -inf));
		xs.add(d.x); ys.add(d.y); zs.add(d.z);
		Point e = transformation.transform(new Point(inf, -0.01, inf));
		xs.add(a.x); ys.add(a.y); zs.add(a.z);
		Point f = transformation.transform(new Point(inf, -0.01, -inf));
		xs.add(b.x); ys.add(b.y); zs.add(b.z);
		Point g = transformation.transform(new Point(-inf, -0.01, inf));
		xs.add(c.x); ys.add(c.y); zs.add(c.z);
		Point h = transformation.transform(new Point(-inf, -0.01, -inf));
		xs.add(d.x); ys.add(d.y); zs.add(d.z);

		Point min = new Point(Collections.min(xs), Collections.min(ys), Collections.min(zs));
		Point max = new Point(Collections.max(xs), Collections.max(ys), Collections.max(zs));

		System.out.println("radius of plane is infinity" );
		System.out.println(min);
		System.out.println(max);
		
		ShapeBox bbox = new ShapeBox(min, max, this);
		creator.shapeboxes.add(bbox);

	}

}
