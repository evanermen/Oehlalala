package shape;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import world.World;

/**
 * 
 * @author eline vanermen
 * http://www.scratchapixel.com/old/lessons/3d-basic-lessons/lesson-7-intersecting-simple-shapes/ray-box-intersection/
 *
 */
public class Cube extends Shape {

	public Transformation transformation;
	public final double size;
	public Material material;
	
	public Cube(Transformation transformation, Material material, double size){
		super(transformation, material);
		this.size = size;
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		
		// lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
		// r.org is origin of ray
		
		Ray transformed = transformation.transformInverse(ray);
		Vector lb = new Vector(-size, -size, -size);
		Vector rt = new Vector(size, size, size);
		Point origin = transformed.origin;
		
		double t1 = (lb.x - origin.x)/ transformed.direction.x;
		double t2 = (rt.x - origin.x)/ transformed.direction.x;
		double t3 = (lb.y - origin.y)/ transformed.direction.y;
		double t4 = (rt.y - origin.y)/ transformed.direction.y;
		double t5 = (lb.z - origin.z)/ transformed.direction.z;
		double t6 = (rt.z - origin.z)/ transformed.direction.z;

		double tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		double tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behind us
		if (tmax < 0)
		{
		    double t = tmax;
		    return null;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
		    double t = tmax;
		    return null;
		}
		//System.out.println("THIS OBJECT = " + this);
		return new Intersection(ray, tmin, this);
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(0,255,255);
	}
	
	public Vector getNormal(Point point){
		
		//System.out.println("tranpoint = " + point.x + ", " + point.y + ", "+ point.z);
		Vector normal;
		
		double diffx = Math.abs(Math.abs(point.x) - size);
		double diffy = Math.abs(Math.abs(point.y) - size);
		double diffz = Math.abs(Math.abs(point.z) - size);
		if(diffx <= diffy){
			if (diffx<=diffz) normal = new Vector(point.x, 0, 0);
			else normal = new Vector(0, 0, point.z).normalize();			
		}
		else{
			if(diffy <= diffz) normal = new Vector(0, point.y, 0);
			else normal = new Vector(0, 0,point.z);	
		}
		
		/**if(normal.x == 0 && normal.y==0 &&normal.z==0) {
			System.out.println("point = " + point.x + ", " + point.y + ", "+ point.z);
			System.out.println("Normal = " + normal.x + ", " + normal.y + ", " + normal.z);
		}*/
		
		Vector transformedNormal = transformation.getNormalTransformationMatrix().transform(normal).normalize();
		//System.out.println("TransformedNormal = " + transformedNormal.x + ", " + transformedNormal.y + ", " + transformedNormal.z);

		/**if(transformedNormal.x == 0 && transformedNormal.y==0 &&transformedNormal.z==0) {
			System.out.println("point = " + point.x + ", " + point.y + ", "+ point.z);
			System.out.println("Normal = " + normal.x + ", " + normal.y + ", " + normal.z);
		}*/
		
		return transformedNormal;
		
	}
	



}
