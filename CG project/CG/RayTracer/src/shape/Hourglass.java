package shape;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

/**
 * Represents a finite, two-sided cone
 * @author eline vanermen
 *
 */
public class Hourglass extends Shape{
	public Transformation transformation;
	double angle;
	double height;
	public Material material;
	
	
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
		
		Intersection i0 = new Intersection(ray, t0, this);
		Intersection i1 = new Intersection(ray, t1, this);
		if(t0<t1 && Math.abs(i0.point.x)<height && t0>0) return i0;
		else if(t0<t1 && Math.abs(i1.point.x)<height && t1>0) return i1;
		else if(t1<t0 && Math.abs(i1.point.x)<height && t1>0) return i1;
		else if(t1<t0 && Math.abs(i0.point.x)<height && t0>0) return i0;
		else return null;
		

	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(255,255,0);
	}

	@Override
	public Transformation getTransformation() {
		return transformation;
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
	public Material getMaterial() {
		return material;
	}

}
