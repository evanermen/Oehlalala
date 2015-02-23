package shape;

import java.util.ArrayList;
import java.util.List;

import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class TriangleMesh extends Shape {
	
	public ArrayList<TriangleM> triangles = new ArrayList<TriangleM>();
	Intersection currentIntersection = null;
	
	public TriangleMesh(){
		//triangles.add(new TriangleM(new Point(-1,0,1), new Point(1, -0, 1), new Point(-1,0,-1), new Vector(0,1,0), new Vector(0,1,0), new Vector(0,1,0)));
		//triangles.add(new TriangleM(new Point(1,-0,1), new Point(1, 0, -1), new Point(-1, 0,-1), new Vector(0,1,0), new Vector(0,1,0), new Vector(0,1,0)));
	
		//triangles.add(new TriangleM());
		//triangles.add(new TriangleM(new Point(0,0,0), new Point(0, -10, 10), new Point(0,-10,-10), new Vector(1,0,0), new Vector(0,0,1), new Vector(0,1,0)));
		//triangles.add(new TriangleM(new Point(0,0,0), new Point(0, -10, -10), new Point(0, 10,-10), new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1)));
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		//System.out.println("looking for intersections in trianglemesh");
		Double smallestT = Double.POSITIVE_INFINITY;
		Intersection rayIntersection = null;
		for (TriangleM triangle : triangles){
			Intersection intersection = triangle.intersect(ray);
			if (intersection != null && intersection.t < smallestT ) {
				smallestT = intersection.t;
				rayIntersection = intersection;				
			}
		}
		this.currentIntersection = rayIntersection;
		return rayIntersection;
	}

	@Override
	public RGBColor getColor(Point point) {
		return currentIntersection.shape.getColor(point);
	}

	@Override
	public Transformation getTransformation() {
		return currentIntersection.shape.getTransformation();
	}

	@Override
	public Vector getNormal(Point point) {
		return currentIntersection.shape.getNormal(point);
	}

}
