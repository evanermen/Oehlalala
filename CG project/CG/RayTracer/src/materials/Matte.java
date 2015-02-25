package materials;

import lights.Light;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import brdf.Diffuse;

public class Matte extends Material {

	public Diffuse diffuse; 
	public Diffuse ambient;
	
	
	public Matte(RGBColor color, double kd, double ka) {
		super(color);
		this.diffuse = new Diffuse(color, kd);
		this.ambient = new Diffuse(color, ka);
	}
	
	public Matte(RGBColor color){
		super(color);
		this.diffuse = new Diffuse(color);
		this.ambient = new Diffuse(color);
	}
	
	public Matte(){
		this.diffuse = new Diffuse();
		this.ambient = new Diffuse();
	}

	
	
	@Override
	public RGBColor shade(Intersection intersection) {
		
		Vector w0 = intersection.ray.direction.scale(-1);
		RGBColor l = ambient.rho(intersection, w0).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));
		for(Light light : intersection.getWorld().lights){
			Vector wi = light.getDirection(intersection);
			//System.out.println("direction light = " + wi);
			Vector n = intersection.getNormal();
			double ndotwi = n.dot(wi); 
			//System.out.println("ndotwi = " + ndotwi);
			if(ndotwi > 0.0){
				RGBColor l1 = light.getRadiance(intersection);
				//System.out.println("color l1 = " + l1.r);
				l = l.add((diffuse.f(intersection, w0, wi)).multiply(l1).scale(ndotwi));
				//System.out.println("color l = " + l.r);
			}
			
		}
		//System.out.println("l is "+ l.r + "maxtoone: " + l.maxToOne().r);
		return l.maxToOne();
	}

}
