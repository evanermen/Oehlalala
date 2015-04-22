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

public class Square extends Shape {

	public Point corner;
	public Vector v1;
	public double v1lenght;
	public Vector v2;
	public double v2lenght;
	public Vector normal; 
	double kEpsilon = 0.0000001;
	
	
	
	public Square(Transformation transformation, Material material, Point corner, Vector v1, Vector v2) {
		super(transformation, material);
		this.corner = corner;
		this.v1 = v1;
		this.v1lenght = v1.lengthSquared();
		this.v2 = v2;
		this.v2lenght = v2.lengthSquared();
		this.normal = v1.cross(v2).normalize();
	}

	@Override
	public Intersection intersect(Ray ray) {
		
		Ray transformed = this.transformation.transformInverse(ray);
		double t = corner.subtract(ray.origin).dot(normal)/ray.direction.dot(normal);
		
		if(t <= kEpsilon) {return null;}
		
		Point p = ray.origin.add(ray.direction.scale(t));
		Vector d = p.subtract(corner);
		
		double ddotv1 = d.dot(v1); 
		if(ddotv1 < 0.0 || ddotv1 > v1lenght){ return null;}
		else{		
			double ddotv2 = d.dot(v2); 
			if(ddotv2 < 0.0 || ddotv2 > v1lenght){ return null;}
			else{
				Point originPoint = transformed.origin.add(transformed.direction.scale(t));
				Point point = transformation.transform(originPoint);
				Vector transformedNormal = transformation.getNormalTransformationMatrix().transform(normal);
				return new Intersection(ray, t, this, point, transformedNormal);
			}
		
		}
		
	}

	@Override
	public Vector getNormal(Point point) {
		return normal;
	}

	@Override   
	public RGBColor getColor(Point point) {
		return null;
	}

	@Override
	public void createBBox(BBoxCreator creator) {
		ArrayList<Double> xs = new ArrayList<Double>();
		ArrayList<Double> ys = new ArrayList<Double>();
		ArrayList<Double> zs = new ArrayList<Double>();
		Point a = transformation.transform(corner);
		xs.add(a.x); ys.add(a.y); zs.add(a.z);
		Point b = transformation.transform(corner.add(v1));
		xs.add(b.x); ys.add(b.y); zs.add(b.z);
		Point c = transformation.transform(corner.add(v2));
		xs.add(c.x); ys.add(c.y); zs.add(c.z);
		Point d = transformation.transform(corner.add(v1).add(v2));
		xs.add(d.x); ys.add(d.y); zs.add(d.z);
		
		
		Point min = new Point(Collections.min(xs), Collections.min(ys), Collections.min(zs));
		Point max = new Point(Collections.max(xs), Collections.max(ys), Collections.max(zs));
		
		ShapeBox bbox = new ShapeBox(min, max, this);
		creator.shapeboxes.add(bbox); 

	}

	@Override
	public double shadowHit(Ray ray) {
		Ray transformed = this.transformation.transformInverse(ray);
		double t = corner.subtract(ray.origin).dot(normal)/ray.direction.dot(normal);
		
		if(t <= kEpsilon) {return Double.POSITIVE_INFINITY;}
		
		Point p = ray.origin.add(ray.direction.scale(t));
		Vector d = p.subtract(corner);
		
		double ddotv1 = d.dot(v1); 
		if(ddotv1 < 0.0 || ddotv1 > v1lenght){ return Double.POSITIVE_INFINITY;}
		else{		
			double ddotv2 = d.dot(v2); 
			if(ddotv2 < 0.0 || ddotv2 > v1lenght){ return Double.POSITIVE_INFINITY;}
			else{
				
				return t;
			}
		
		}
	}

}
