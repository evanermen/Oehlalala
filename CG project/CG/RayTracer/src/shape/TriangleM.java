package shape;

import materials.Material;
import materials.Matte;
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

	public Vector barys;



	public TriangleM(Point v0, Point v1, Point v2, Vector vn0, Vector vn1, Vector vn2) {
		super(Transformation.createIdentity(), new Matte(new RGBColor(255,255,255)));
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.vn0 = vn0;
		this.vn1 = vn1;
		this.vn2 = vn2;

	}

	public TriangleM(){
		super(Transformation.createIdentity(), new Matte(new RGBColor(255,255,255)));
		this.v0 = new Point(0,0,5);
		this.v1 = new Point(5,0,0);
		this.v2 = new Point(5,0,5);

		this.vn0 = new Vector(1,0,0);
		this.vn1 = new Vector(0,1,0);
		this.vn2 = new Vector(0,0,1);

	}

	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = transformation.transformInverse(ray);

		double a = v0.x - v1.x;
		double b = v0.x - v2.x;
		double c = transformed.direction.x;
		double d = v0.x - transformed.origin.x;

		double e = v0.y - v1.y;
		double f = v0.y - v2.y;
		double g = transformed.direction.y;
		double h = v0.y - transformed.origin.y;

		double i = v0.z - v1.z;
		double j = v0.z - v2.z;
		double k = transformed.direction.z;
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
		if(beta <= 0) return null;

		double r = e*l - h*i;
		double e2 = -a*n + d*q + c*r; //minteken voor a*n
		double gamma = e2*inv_denom;

		//System.out.println("Gamma = " + gamma);
		if(gamma<=0) return null;

		if(beta + gamma >= 1) return null;

		double e3 = -a*p - b*r + d*s; //minteken voor a*p
		double t = e3*inv_denom;

		//if(t< kEpsilon) return null;
		double alpha = 1 - beta - gamma;
		this.barys = new Vector(alpha, beta, gamma);

		Point originPoint = transformed.origin.add(transformed.direction.scale(t));	
		Point point = transformation.transform(originPoint);
		Vector normal = getNormal(originPoint);

		return new Intersection(ray, t, this, point, normal, barys);	
	}


	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(1,0.3,0);
	}


	@Override
	public Vector getNormal(Point point) {

		//System.out.println("Baries = "+ barys);
		Vector normal = vn0.scale(barys.x).add(vn1.scale(barys.y)).add(vn2.scale(barys.z));
		//System.out.println("Normal = " + normal.x + ", " + normal.y + ", " + normal.z);
		return transformation.getNormalTransformationMatrix().transform(normal).normalize();
	}

	@Override
	public double shadowHit(Ray ray) {
		Ray transformed = transformation.transformInverse(ray).epsilonOffset();

		double a = v0.x - v1.x;
		double b = v0.x - v2.x;
		double c = transformed.direction.x;
		double d = v0.x - transformed.origin.x;

		double e = v0.y - v1.y;
		double f = v0.y - v2.y;
		double g = transformed.direction.y;
		double h = v0.y - transformed.origin.y;

		double i = v0.z - v1.z;
		double j = v0.z - v2.z;
		double k = transformed.direction.z;
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
		if(beta <= 0) return Double.POSITIVE_INFINITY;

		double r = e*l - h*i;
		double e2 = -a*n + d*q + c*r; //minteken voor a*n
		double gamma = e2*inv_denom;

		//System.out.println("Gamma = " + gamma);
		if(gamma<=0) return Double.POSITIVE_INFINITY;

		if(beta + gamma >= 1) return Double.POSITIVE_INFINITY;

		double e3 = -a*p - b*r + d*s; //minteken voor a*p
		double t = e3*inv_denom;

		//if(t< kEpsilon) return null;
		double alpha = 1 - beta - gamma;
		this.barys = new Vector(alpha, beta, gamma);


		return t;	
	}


}
