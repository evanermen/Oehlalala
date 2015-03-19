package tracer;

import math.Ray;
import gui.ImagePanel;
import sampling.Sample;
import utils.Intersection;
import utils.RGBColor;
import world.World;
import boundingBox.BoundingBox;
import camera.Camera;

public class BBoxIntersectionTracer extends Tracer {

	int max;
	BoundingBox bigbox;

	public BBoxIntersectionTracer(World world, ImagePanel panel, Camera camera, int maxIntersections, BoundingBox bigbox) {
		super(world, panel, camera);
		max = maxIntersections;
		this.bigbox = bigbox;

	}

	@Override
	public void trace(int x, int y) {
		Ray ray = camera.generateRay(new Sample(x + 0.5, y + 0.5));
		Intersection rayIntersection = bigbox.intersect(ray);
		//if(rayIntersection != null && rayIntersection.shape instanceof Sphere) System.out.println("coordinates for sphere: " + x+ ", " + y);

		RGBColor color = world.bg;
		//if(rayIntersection != null){
		int count = ray.bboxcount;
		//color = new RGBColor(0,0, (1.0*count)/(max));
		
		double max3 = max/3.0;
		int q = (int) (count / max3);
		double r = count % max3;

		if(q == 0){ color = new RGBColor( 0, 0, r/max3);}
		else if(q == 1){ color = new RGBColor(0,r/max3, 1-r/max3);}
		else if(q == 2){ color = new RGBColor(r/max3, 1-r/max3 , 0 );}
		else{System.out.println("oei er is een probleem bij boxcount in tracer");}
		//}
		
//		if(q == 0){ color = new RGBColor( 0, r/max3, 1-r/max3);}
//		else if(q == 1){ color = new RGBColor(r/max3, 1-r/max3, 0);}
//		else if(q == 2){ color = new RGBColor(1,0 , 0 );}
//		else{System.out.println("oei er is een porbleem bij boxcount in tracer");}
//		
		
		
		
		panel.set(x, y, 255, (float)color.r, (float)color.g, (float)color.b);
	}

}
