package utils;

import materials.Material;
import math.Point;
import math.Ray;
import math.Vector;
import shape.Shape;

public class Intersection {
	
	public double t; //distance
	public Ray ray;
	public Shape shape;
	public Point point;
	

	public Intersection(Ray ray, double t, Shape shape ){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		Ray transformed = shape.getTransformation().transformInverse(ray);
		point = transformed.origin.add(transformed.direction.scale(t));	
	}
	
	public RGBColor getColor(){
		return shape.getColor(point);
	}
	
	public Vector getNormal(){
		return shape.getNormal(point);
	}
	
	public Material getMaterial(){
		return shape.getMaterial();
	}
}
