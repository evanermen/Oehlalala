package tracer;

import gui.ImagePanel;

import java.util.ArrayList;

import math.Ray;
import math.Vector;
import samplers.Jittered;
import samplers.Random;
import samplers.Sampler;
import utils.Intersection;
import utils.RGBColor;
import world.World;
import boundingBox.BoundingBox;
import camera.Camera;
import camera.PerspectiveCamera;

public class AreaLighting extends Tracer {


	//krijg ergens de shadwosampler tot in de arealight shade methode
	
	
	BoundingBox bigbox;
	int bboxcountermax = 0;
	Sampler raySampler;
	Sampler shadowSampler;

	public AreaLighting(World world, ImagePanel panel, Camera camera, BoundingBox bigbox) {
		super(world, panel, camera);
		this.bigbox = bigbox;
		this.raySampler = new Jittered(1);
		this.shadowSampler = new Random(1);
	}

	public AreaLighting(World world, ImagePanel panel, Camera camera, BoundingBox bigbox, Sampler raySampler, Sampler shadowSampler) {
		super(world, panel, camera);
		this.raySampler = raySampler;
		this.shadowSampler = shadowSampler;
		this.bigbox = bigbox;
	}



	@Override
	public void trace(int x, int y){
		ArrayList<Ray> rays = raySampler.getRays(x,y,camera);
		
		RGBColor color = world.bg;
		Vector colorsum = new Vector(0,0,0);
		for(Ray ray: rays){
			if(bigbox.hit(ray) != -1){
				Intersection rayIntersection = bigbox.intersect(ray);
				if(rayIntersection != null){
					Vector c = new Vector(rayIntersection.shape.material.areaShade(rayIntersection));
					colorsum = colorsum.add(c);
				}
			}
			if(colorsum.x != 0 || colorsum.y != 0 || colorsum.z != 0 ){
				color = new RGBColor(colorsum.scale(1.0/raySampler.nbSamples));
			}
			
			panel.set(x, y, 200, (float)color.r, (float)color.g, (float)color.b);
			if(ray.bboxcount > bboxcountermax){
				bboxcountermax = ray.bboxcount;
				System.out.println("maxboxcount= " + ray.bboxcount);
			}
		}
	}
}
