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
		//System.out.println("world is " + world);
		RGBColor color = world.bg;
		Intersection rayIntersection = super.tryIntersection(x, y);
		if(rayIntersection != null)color = rayIntersection.shape.material.shade(rayIntersection);
		if(color != null){
			System.out.println("color is " + (float)color.r + " " + (float)color.g +" " +(float)color.b);
			panel.set(x, y, 200, (float)color.r, (float)color.g, (float)color.b);}
		else{panel.set(x, y, 200 , (int)world.bg.r,(int)world.bg.g, (int)world.bg.b );}
	
		} 
}
