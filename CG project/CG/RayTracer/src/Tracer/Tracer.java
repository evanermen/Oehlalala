package tracer;

import gui.ImagePanel;

import java.util.List;

import camera.Camera;
import math.Ray;
import sampling.Sample;
import shape.Shape;
import utils.Intersection;
import utils.RGBColor;
import world.World;

public abstract class Tracer {
	
	World world;
	ImagePanel panel;
	Camera camera;
	
	
	public Tracer(World world, ImagePanel panel, Camera camera){
		this.world = world;
		this.panel = panel;
		this.camera = camera;
		}
	
	public abstract void trace(int x, int y);
	
	
	public Intersection tryIntersection(int x, int y){
		// create a ray through the center of the pixel.
		Ray ray = camera.generateRay(new Sample(x + 0.5, y + 0.5));
		boolean hit = false;
		List<Shape> shapes = world.objects;
		Double smallestT = Double.POSITIVE_INFINITY;
		Intersection rayIntersection = null;
		for (Shape shape : shapes){
			Intersection intersection = shape.intersect(ray);
			if (intersection != null && intersection.t < smallestT ) {
				smallestT = intersection.t;
				rayIntersection = intersection;				
			}
		}
		return rayIntersection;
	}
}
