package tracer;

import gui.ImagePanel;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import world.World;
import camera.Camera;

public class DepthTracer extends Tracer {

	public DepthTracer(World world, ImagePanel panel, Camera camera) {
		super(world, panel, camera);
	}

	@Override
	public void trace(int x, int y) {
		RGBColor color = world.bg;
		Intersection rayIntersection = super.tryIntersection(x, y);
		if(rayIntersection != null)color = new RGBColor(new Vector(1,1,0).scale(1/rayIntersection.t*5));
		panel.set(x, y, 255, (float)color.r, (float)color.g, (float)color.b);

	}

}
