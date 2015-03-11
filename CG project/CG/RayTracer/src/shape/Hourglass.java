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
 * Represents a finite, two-sided cone around the x-axis
 * @author eline vanermen
 *
 */
public class Hourglass extends Shape{
	
	double angle;
	double height;
	
	
	public Hourglass(Transformation transformation, Material material, double angle, double height){
		super(transformation, material);
		if (height < 0)
			throw new IllegalArgumentException(
					"the given height cannot be smaller than zero!");
		if (angle < 0 || angle > Math.PI/2)
			throw new IllegalArgumentException(
					"the given angle must be between 0 and Pi/2 cannot be smaller than zero!");
		this.angle = angle;
		this.height = height;
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);
				
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		
		double a = direction.y*direction.y + direction.z*direction.z - Math.tan(angle)*direction.x*direction.x;
		double b = 2*rayOrigin.y*direction.y + 2*rayOrigin.z*direction.z - Math.tan(angle)*2*rayOrigin.x*direction.x;
		double c = rayOrigin.y*rayOrigin.y + rayOrigin.z*rayOrigin.z - Math.tan(angle)*rayOrigin.x*rayOrigin.x;
		
		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return null;
		
		
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;
		
		
		Point originPoint0 = transformed.origin.add(transformed.direction.scale(t0));	
		Point point0 = transformation.transform(originPoint0);
		Vector normal0 = getNormal(originPoint0);
		Point originPoint1 = transformed.origin.add(transformed.direction.scale(t1));	
		Point point1 = transformation.transform(originPoint1);
		Vector normal1 = getNormal(originPoint1);
		
		Intersection i0 = new Intersection(ray, t0, this, point0, normal0);
		Intersection i1 = new Intersection(ray, t1, this, point1, normal1);
		if(t0<t1 && Math.abs(point0.x)<height && t0>0) return i0;
		else if(t0<t1 && Math.abs(point1.x)<height && t1>0) return i1;
		else if(t1<t0 && Math.abs(point1.x)<height && t1>0) return i1;
		else if(t1<t0 && Math.abs(point0.x)<height && t0>0) return i0;
		else return null;
		

	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(1,0,1);
	}

	
	//hier zit nog iets raar precies. normaal teruggetransformeerd?
	@Override
	public Vector getNormal(Point point) {
		double phi = Math.atan(point.y/point.z);
		//System.out.println("point = "+ point.x + ", " + point.y + ", " + point.z);
		//Vector normal =  new Vector(-Math.sin(angle), Math.cos(angle)*Math.sin(phi), Math.cos(angle)*Math.sin(phi));
		Vector normal = new Vector(2*point.x, -2*point.y, 2*point.z);
		return transformation.getNormalTransformationMatrix().transform(normal).normalize().add(1, 1, 1).scale(0.5);
		//return normal;
	}

	@Override
	public double shadowHit(Ray ray) {
		Ray transformed = transformation.transformInverse(ray).epsilonOffset();
		
		Vector direction = transformed.direction;
		Point rayOrigin = transformed.origin;
		
		double a = direction.y*direction.y + direction.z*direction.z - Math.tan(angle)*direction.x*direction.x;
		double b = 2*rayOrigin.y*direction.y + 2*rayOrigin.z*direction.z - Math.tan(angle)*2*rayOrigin.x*direction.x;
		double c = rayOrigin.y*rayOrigin.y + rayOrigin.z*rayOrigin.z - Math.tan(angle)*rayOrigin.x*rayOrigin.x;
		
		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return Double.POSITIVE_INFINITY;
		
		
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;
		
		
		Point originPoint0 = transformed.origin.add(transformed.direction.scale(t0));	
		Point point0 = transformation.transform(originPoint0);
		Point originPoint1 = transformed.origin.add(transformed.direction.scale(t1));	
		Point point1 = transformation.transform(originPoint1);
		
		if(t0<t1 && Math.abs(point0.x)<height && t0>0) return t0;
		else if(t0<t1 && Math.abs(point1.x)<height && t1>0) return t1;
		else if(t1<t0 && Math.abs(point1.x)<height && t1>0) return t1;
		else if(t1<t0 && Math.abs(point0.x)<height && t0>0) return t0;
		else return Double.POSITIVE_INFINITY;
	}

	@Override
	public void createBBox(BBoxCreator creator) {
		ArrayList<Double> xs = new ArrayList<Double>();
		ArrayList<Double> ys = new ArrayList<Double>();
		ArrayList<Double> zs = new ArrayList<Double>();
		double radius = Math.tan(angle)*height;
		Point a = transformation.transform(new Point(height, -radius, -radius));
		xs.add(a.x); ys.add(a.y); zs.add(a.z);
		Point b = transformation.transform(new Point(height, -radius, radius));
		xs.add(b.x); ys.add(b.y); zs.add(b.z);
		Point c = transformation.transform(new Point(height, radius, -radius));
		xs.add(c.x); ys.add(c.y); zs.add(c.z);
		Point d = transformation.transform(new Point(height, radius, radius));
		xs.add(d.x); ys.add(d.y); zs.add(d.z);
		Point e = transformation.transform(new Point(-height, -radius, -radius));
		xs.add(e.x); ys.add(e.y); zs.add(e.z);
		Point f = transformation.transform(new Point(-height, -radius, radius));
		xs.add(f.x); ys.add(f.y); zs.add(f.z);
		Point g = transformation.transform(new Point(-height, radius, -radius));
		xs.add(g.x); ys.add(g.y); zs.add(g.z);
		Point h = transformation.transform(new Point(-height, radius, radius));
		xs.add(h.x); ys.add(h.y); zs.add(h.z);
		
		Point min = new Point(Collections.min(xs), Collections.min(ys), Collections.min(zs));
		Point max = new Point(Collections.max(xs), Collections.max(ys), Collections.max(zs));
		
		ShapeBox bbox = new ShapeBox(min, max, this);
		creator.shapeboxes.add(bbox);
		
	}



}
