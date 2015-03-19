package tracer;

import java.util.List;

import math.Ray;
import gui.ImagePanel;
import sampling.Sample;
import shape.Shape;
import utils.Intersection;
import utils.RGBColor;
import world.World;
import boundingBox.BoundingBox;
import camera.Camera;


public class BBoxTracer extends Tracer {

	BoundingBox bigbox;
	int bboxcountermax = 0;

	public BBoxTracer(World world, ImagePanel panel, Camera camera, BoundingBox bigbox) {
		super(world, panel, camera);
		this.bigbox = bigbox;
	}

	@Override
	public void trace(int x, int y) {
		// create a ray through the center of the pixel.
		Ray ray = camera.generateRay(new Sample(x + 0.5, y + 0.5));

		RGBColor color = world.bg;
		if(bigbox.hit(ray) != -1){
			Intersection rayIntersection = bigbox.intersect(ray);

			if(rayIntersection != null)color = rayIntersection.shape.material.shade(rayIntersection);
			if(color != null){
				//System.out.println("color is " + (float)color.r + " " + (float)color.g +" " +(float)color.b);
				RGBColor color2 = color.maxToOne();
			}
		}
		panel.set(x, y, 200, (float)color.r, (float)color.g, (float)color.b);
		if(ray.bboxcount > bboxcountermax){
			bboxcountermax = ray.bboxcount;
			System.out.println("maxboxcount= " + ray.bboxcount);
		}
	}

}
