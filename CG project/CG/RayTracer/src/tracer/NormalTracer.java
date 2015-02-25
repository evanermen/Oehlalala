package tracer;

import gui.ImagePanel;
import utils.Intersection;
import utils.RGBColor;
import world.World;
import camera.Camera;

public class NormalTracer extends Tracer {

	public NormalTracer(World world, ImagePanel panel, Camera camera) {
		super(world, panel, camera);
	}

	@Override
	public void trace(int x, int y){
		// create a ray through the center of the pixel.
		RGBColor color = world.bg;
		Intersection rayIntersection = super.tryIntersection(x, y);
		
		if(rayIntersection != null){color = new RGBColor(rayIntersection.getNormal().normalize().abs());}
		panel.set(x, y, 20, (float)color.r, (float)color.g, (float)color.b);
	
	} 
}
