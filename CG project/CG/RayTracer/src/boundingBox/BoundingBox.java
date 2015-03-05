package boundingBox;

import utils.Intersection;
import math.Point;
import math.Ray;
import math.Vector;

public abstract class BoundingBox {

	int level;  //for tracing
	Point min;
	Point max;

	public BoundingBox(Point min, Point max){
		this.min = min;
		this.max = max;
	}

	public int compare(BoundingBox  box2){
		if(this.min.x > box2.min.x){ return 1;}
		else if(this.min.x == box2.min.x){ return 0;}
		else {return -1;}

	}

	public abstract Intersection intersect(Ray ray);
	
	
	public boolean hit(Ray ray){
		
		Point lb = min;
		Point rt = max;
		Point origin = ray.origin;
		
		double t1 = (lb.x - origin.x)/ ray.direction.x;
		double t2 = (rt.x - origin.x)/ ray.direction.x;
		double t3 = (lb.y - origin.y)/ ray.direction.y;
		double t4 = (rt.y - origin.y)/ ray.direction.y;
		double t5 = (lb.z - origin.z)/ ray.direction.z;
		double t6 = (rt.z - origin.z)/ ray.direction.z;

		double tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		double tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behind us
		if (tmax < 0)
		{
		    return false;
		}

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
		    return false;
		}
		return true;
	}
}

//method hit
//method intersect

