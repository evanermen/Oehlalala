package lights;

import java.util.ArrayList;
import java.util.List;

import materials.Emissive;
import math.Point;
import math.Ray;
import math.Vector;
import samplers.Sampler;
import shape.Rectangle;
import shape.Shape;
import utils.Intersection;
import utils.RGBColor;

public class AreaLight extends Light {

	public Rectangle rect;
	public Vector normal;
	public Vector wi;
	Point samplePoint;
	Sampler sampler;

	public AreaLight(Emissive emissive, Rectangle rect){
		super(emissive);
		this.rect = rect;
	}

	@Override
	public Vector getDirection(Intersection intersection) {
		samplePoint = rect.sample().get(0);
		normal = rect.getNormal(samplePoint);
		wi = samplePoint.subtract(intersection.point);
		return wi.normalize();
	}

	public boolean inShadow(Ray shadowRay, Intersection intersection){
		//System.out.println("samplePoint is hetzelfde als de origin van de shadowRay: " + (samplePoint.x == shadowRay.origin.x && samplePoint.y == shadowRay.origin.y && samplePoint.z == shadowRay.origin.z));
		double max = samplePoint.subtract(shadowRay.origin).length();
		List<Shape> objects = intersection.getWorld().objects;
		for(Shape shape : objects){
			if(shape.material.stopsLight()){
				if(shape.shadowHit(shadowRay)> 0 && shape.shadowHit(shadowRay) < max){
					return true;
				}
			}	
		}
		return false;
	}
	
	public RGBColor L(Intersection intersection){
		double ndotd = normal.scale(-1).dot(wi);
		if(ndotd > 0.0){
			return emissive.getLe(intersection).maxToOne();
		}
		else return new RGBColor(0,0,0);

	}

	public double G(Intersection intersection){
		double ndotd = normal.scale(-1).dot(wi);
		double dsquared = intersection.point.subtract(samplePoint).lengthSquared();
		return (ndotd/dsquared);

	}

	public double pdf(Intersection intersection){
		return rect.pdf(intersection);

	}
}
