package utils;

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
		//System.out.println("making an intersection object: "+ ray + ", " + t + ", " + shape);
		//System.out.println("tranfsormation = " + shape.getTransformation());
		Ray transformed = shape.getTransformation().transformInverse(ray);
		//System.out.println("transformed ray = " + transformed);
		point = transformed.origin.add(transformed.direction.scale(t));	
		//System.out.println("at point " + point );
	}
	
	public RGBColor getColor(){
		return shape.getColor(point);
	}
	
	public Vector getNormal(){
		return shape.getNormal(point);
	}
}
