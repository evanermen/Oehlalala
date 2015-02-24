package materials;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import world.World;
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
		
		return null;
	}

}
