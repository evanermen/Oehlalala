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
		
		Ray transformed = transformation.transformInverse(ray);
		double a = v0.x - v1.x;
		double b = v0.x - v2.x;
		double c = transformed.direction.x;
		double d = v0.x - ray.origin.x;
		
		double e = v0.y - v1.y;
		double f = v0.y - v2.y;
		double g = transformed.direction.y;
		double h = v0.y - ray.origin.y;
		
		double i = v0.z - v1.z;
		double j = v0.z - v2.z;
		double k = transformed.direction.z;
		double l = v0.z - transformed.origin.z;
		
		double m = f*k - g*j;
		double n = -h*k + g*l;  //tekenverandering
		double p = -f*l + h*j;  //tekenverandering
		double q = g*i - e*k;
		double s = e*j - f*i;
		
		double inv_denom = 1.0/(a*m + b*q + c*s);
		
		double e1 = d*m + b*n + c*p;
		double beta = e1*inv_denom;
		
		//System.out.println("Beta = " + beta);
		if(beta < 0) return null;
		
		double r = e*l - h*i;
		double e2 = a*n + d*q + c*r; //minteken voor a*n
		double gamma = e2*inv_denom;
		
		//System.out.println("Gamma = " + gamma);
		if(gamma<0) return null;
		
		if(beta + gamma > 1) return null;
		
		double e3 = a*p - b*r + d*s; //minteken voor a*p
		double t = e3*inv_denom;
		
		//if(t< kEpsilon) return null;
		
		return new Intersection(ray, t, this);	
		
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(getNormal(point));
	}

	@Override
	public Transformation getTransformation() {
		return transformation;
	}

	
	// ??? normaal aan juiste kant??
	@Override
	public Vector getNormal(Point point) {
		Vector divident = v1.subtract(v0).cross(v2.subtract(v0));
		double divisor = divident.length();
		return divident.scale(1/divisor);
	}

}
