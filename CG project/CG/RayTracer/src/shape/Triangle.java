package shape;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import tracer.Intersection;
import utils.RGBColor;

public class Triangle extends Shape {

	public Point v0;
	public Point v1;
	public Point v2;
	public Transformation transformation;
		

	public Triangle(Transformation transformation, Point v0, Point v1, Point v2) {
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.transformation = transformation;
	}
	
	public Triangle(Transformation transformation){
		this.transformation = transformation;
		this.v0 = new Point(1,0,0);
		this.v1 = new Point(0,1,0);
		this.v2 = new Point(0,0,1);
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		double a = v0.x - v1.x;
		double b = v0.x - v2.x;
		double c = ray.direction.x;
		double d = v0.x - ray.origin.x;
		
		double e = v0.y - v1.y;
		double f = v0.y - v2.y;
		double g = ray.direction.y;
		double h = v0.y - ray.origin.y;
		
		double i = v0.z - v1.z;
		double j = v0.z - v2.z;
		double k = ray.direction.z;
		double l = v0.z - ray.origin.z;
		
		double m = f*k - g*j;
		double n = h*k - g*l;
		double p = f*l - h*j;
		double q = g*i - e*k;
		double s = e*j - f*i;
		
		double inv_denom = 1.0/(a*m + b*q + c*s);
		
		double e1 = d*m - b*n - c*p;
		double beta = e1*inv_denom;
		
		System.out.println("Beta = " + beta);
		if(beta < 0) return null;
		
		double r = e*l - h*i;
		double e2 = a*n + d*q + c*r;
		double gamma = e2*inv_denom;
		
		System.out.println("Gamma = " + gamma);
		if(gamma<0) return null;
		
		if(beta + gamma > 1) return null;
		
		double e3 = a*p - b*r + d*s;
		double t = e3*inv_denom;
		
		//if(t< kEpsilon) return null;
		
		return new Intersection(ray, t, this);	
		
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
