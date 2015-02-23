package tracer;

import java.util.List;

import camera.Camera;
import math.Point;
import math.Ray;
import gui.ImagePanel;
import sampling.Sample;
import shape.Shape;
import utils.Intersection;
import utils.RGBColor;
import world.World;

public class SimpleTracer extends Tracer {
	
	
	public SimpleTracer(World world, ImagePanel panel, Camera camera){
		super(world, panel, camera);
	}
	
	
	public void trace(int x, int y){
		// create a ray through the center of the pixel.
		System.out.println("world is " + world);
		RGBColor color = world.bg;
		Intersection rayIntersection = super.tryIntersection(x, y);
		if(rayIntersection != null)color = rayIntersection.getColor();
		panel.set(x, y, 255, (float)color.r, (float)color.g, (float)color.b);
	
	} 
}
