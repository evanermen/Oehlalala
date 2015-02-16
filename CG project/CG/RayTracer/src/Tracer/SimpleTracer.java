package Tracer;

import java.util.List;

import camera.Camera;
import math.Point;
import math.Ray;
import gui.ImagePanel;
import sampling.Sample;
import shape.Shape;
import utils.RGBColor;
import world.World;

public class SimpleTracer extends Tracer {
	//Private/ public??
	World world;
	ImagePanel panel;
	Camera camera;
	
	public SimpleTracer(World world, ImagePanel panel, Camera camera){
		this.world = world;
		this.panel = panel;
		this.camera = camera;
	}
	
	public void trace(int x, int y){
		
		// create a ray through the center of the pixel.
		Ray ray = camera.generateRay(new Sample(x + 0.5, y + 0.5));
		RGBColor color = world.bg;
		boolean hit = false;
		List<Shape> shapes = world.objects;
		for (Shape shape : shapes){
			Point intersection = shape.intersect(ray);
			if (intersection != null) {
				hit = true;
				color = shape.getColor(intersection);
				break;
			}
		}
		System.out.println(color.r);
		panel.set(x, y, 255, (float)color.r, (float)color.g, (float)color.b);
	
	}

}
