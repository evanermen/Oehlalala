package shape;

import math.Matrix;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class TriangleM extends Shape {

	public Point v0;
	public Point v1;
	public Point v2;
	
	public Vector vn0;
	public Vector vn1;
	public Vector vn2;

	
	public TriangleM(Point v0, Point v1, Point v2, Vector vn0, Vector vn1, Vector vn2) {
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.vn0 = vn0;
		this.vn1 = vn1;
		this.vn2 = vn2;
		
	}
	
	public TriangleM(){
		this.v0 = new Point(0,0,0);
		this.v1 = new Point(0,10,10);
		this.v2 = new Point(0,10,-10);
		
		this.vn0 = new Vector(1,0,0);
		this.vn1 = new Vector(0,1,0);
		this.vn2 = new Vector(0,0,1);
		
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
		return Transformation.createIdentity();
	}

	@Override
	public Vector getNormal(Point point) {
		Vector barys = findBarys(point);
		Vector normal = vn0.scale(barys.x).add(vn1.scale(barys.y)).add(vn2.scale(barys.z));
		//System.out.println("Normal = " + normal.x + ", " + normal.y + ", " + normal.z);
		return normal.abs().normalize();
	}
	
	public Vector findBarys(Point point){
		double AreaT = findArea(v0,v1,v2);
		double Area01 = findArea(v0, v1, point);
		double Area12 = findArea(point, v1, v2);
		double Area20 = findArea(v0, point, v2);
		
		double b0 = Area12/AreaT;
		double b1 = Area20/AreaT;
		double b2 = Area01/AreaT;
		
		return new Vector(b0, b1, b2);
		
	}
	
	 public double findArea(Point a, Point b, Point c)
	    { 
		 	double sideA = a.subtract(b).length();
		 	System.out.println("sideA = " + sideA);
		 	double sideB = b.subtract(c).length();
		 	System.out.println("sideB = " + sideB);
		 	double sideC = c.subtract(a).length();
		 	System.out.println("sideC = " + sideC);
		 	System.out.println("sum = " + (sideA + sideB + sideC) );
	        double s = 0.5*(sideA + sideB + sideC);
	        System.out.println("s = " + s);
	        double area = Math.sqrt(s*(s-sideA)*(s-sideB)*(s-sideC));
	        System.out.println("Area = " + area);
	        return area;
	        
	    }
}
