package shape;

import materials.Matte;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import textures.ConstantColor;
import utils.Intersection;
import utils.RGBColor;
import boundingBox.BBoxCreator;
import boundingBox.ShapeBox;

public class TriangleM extends Shape {

	public Point v0;
	public Point v1;
	public Point v2;

	public Vector vn0;
	public Vector vn1;
	public Vector vn2;

	public Vector barys;
	
	public Vector vt0;
	public Vector vt1;
	public Vector vt2;



	public TriangleM(Point v0, Point v1, Point v2, Vector vn0, Vector vn1, Vector vn2) {
		super(Transformation.createIdentity(), new Matte(new ConstantColor(new RGBColor(1,1,1)), 0.8, 0.2));
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.vn0 = vn0;
		this.vn1 = vn1;
		this.vn2 = vn2;

	}

	
	public TriangleM(Point v0, Point v1, Point v2, Vector vn0, Vector vn1, Vector vn2, Vector vt0, Vector vt1, Vector vt2) {
		super(Transformation.createIdentity(), new Matte(new ConstantColor(new RGBColor(1,1,1)), 0.8 , 0.2));
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.vn0 = vn0;
		this.vn1 = vn1;
		this.vn2 = vn2;
		this.vt0 = vt0;
		this.vt1 = vt1;
		this.vt2 = vt2;

	}
	
	
	public TriangleM(){
		super(Transformation.createIdentity(), new Matte(new ConstantColor(new RGBColor(1,1,1)), 0.8,0.2));
		this.v0 = new Point(0,0,5);
		this.v1 = new Point(5,0,0);
		this.v2 = new Point(5,0,5);

		this.vn0 = new Vector(1,0,0);
		this.vn1 = new Vector(0,1,0);
		this.vn2 = new Vector(0,0,1);

	}

	@Override
	public Intersection intersect(Ray ray) {
		Ray transformed = ray.epsilonOffset();

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
		double l = v0.z - transformed.origin.z;   //ZAT HIER AL HEEL DE TIJD DE FOUT???? nee toch?  (er stond ray ipv transformed

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
		//Point point = transformation.transform(originPoint);
		Point point = originPoint;
		Vector normal = getNormal(originPoint);
		double u = vt0.x*barys.x + vt1.x*barys.y + vt2.x*barys.z; //ELINE !! HIER ZOU IETS FOUT KUNNEN ZIJN
		double v = vt0.y*barys.x + vt1.y*barys.y + vt2.y*barys.z; //ELINE !! HIER ZOU IETS FOUT KUNNEN ZIJN
	
		return new Intersection(ray, t, this, point, normal, barys, u, v);	
	}


	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(1,0.3,0);
	}


	@Override
	public Vector getNormal(Point point) {
		Vector normal = vn0.scale(barys.x).add(vn1.scale(barys.y)).add(vn2.scale(barys.z));
		//return transformation.getNormalTransformationMatrix().transform(normal).normalize();
		return normal.normalize();
	}

	@Override
	public double shadowHit(Ray ray) {
		//Ray transformed = transformation.transformInverse(ray).epsilonOffset();
		Ray transformed = ray;

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


		return t;	   //ELINE HIER??????? we denken t untransformed?
	}

	@Override
	public void createBBox(BBoxCreator creator) {
		/**Point v0t = this.transformation.transform(v0);
		Point v1t =  this.transformation.transform(v1);
		Point v2t =  this.transformation.transform(v2);*/
		
		double maxX = Math.max(Math.max(v0.x, v1.x), v2.x);
		double maxY = Math.max(Math.max(v0.y, v1.y), v2.y);
		double maxZ = Math.max(Math.max(v0.z, v1.z), v2.z);
		double minX = Math.min(Math.min(v0.x, v1.x), v2.x);
		double minY = Math.min(Math.min(v0.y, v1.y), v2.y);
		double minZ = Math.min(Math.min(v0.z, v1.z), v2.z);
		
		ShapeBox bbox = new ShapeBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ), this);
		creator.shapeboxes.add(bbox);
	}


	public void transform(Transformation transformation) {
		this.transformation = transformation;
		this.v0 = transformation.transform(v0);
		this.v1 = transformation.transform(v1);
		this.v2 = transformation.transform(v2);
		this.vn0 = transformation.getNormalTransformationMatrix().transform(vn0).normalize();
		this.vn1 = transformation.getNormalTransformationMatrix().transform(vn1).normalize();
		this.vn2 = transformation.getNormalTransformationMatrix().transform(vn2).normalize();
		
	}


}
