package lights;

import java.util.List;

import materials.Emissive;
import math.Point;
import math.Ray;
import math.Vector;
import shape.Shape;
import utils.Intersection;

public class PointLight extends Light {

	public Point location;

	public PointLight(Emissive emissive, Point location){
		super(emissive);
		this.location = location;
	}

	public Vector getDirection(Intersection intersection) {
		return location.subtract(intersection.point).normalize(); //HIER ELINE DIT WAS NORMALIZED
	}

	public boolean inShadow(Ray shadowRay, Intersection intersection) {
		double max = location.subtract(shadowRay.origin).length();
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


}
