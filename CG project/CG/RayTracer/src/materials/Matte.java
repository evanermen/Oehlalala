package materials;

import lights.Light;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import brdf.Diffuse;

public class Matte extends Material {

	
	public RGBColor color;
	public Diffuse diffuse; 
	
	
	public Matte(RGBColor color) {
		super(color);
	}

	
	
	@Override
	public RGBColor shade(Intersection intersection) {
		Vector w0 = intersection.ray.direction.scale(-1);
		for(Light light : intersection.getWorld().lights){
			Vector wi = light.getDirection(intersection);
			double ndotwi = intersection.getNormal().dot(wi);
			
			if(ndotwi > 0.0){
				Vector l1 = new Vector(light.getRadiance(intersection));
				RGBColor l = new RGBColor(diffuse.f(intersection, w0, wi).dot(l1).scale(ndotwi));
			}
			
		}
		return null;
	}

}
