package materials;

import lights.Light;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import brdf.Lambertian;

public class Matte extends Material {

	public Lambertian diffuse; 
	public Lambertian ambient;
	
	
	public Matte(RGBColor color, double kd, double ka) {
		super(color);
		if(ka + kd > 1.0)
			throw new IllegalArgumentException("kd and ka added together can't be more than 1.");
		this.diffuse = new Lambertian(color, kd);
		this.ambient = new Lambertian(color, ka);
	}
	
	public Matte(RGBColor color){
		super(color);
		this.diffuse = new Lambertian(color);
		this.ambient = new Lambertian(color);
	}
	
	public Matte(){
		this.diffuse = new Lambertian();
		this.ambient = new Lambertian();
	}

	
	
	
	//HIER ELINE de shading van plane verloopt niet goed. check normaal en slides lecture 4 slide 29
	@Override
	public RGBColor shade(Intersection intersection) {
		
		Vector w0 = intersection.ray.direction.scale(-1).normalize();
		//System.out.println("ambient = " + ambient);
		//System.out.println("intersection.getWorld() = " + intersection);
		RGBColor l = ambient.rho(intersection, w0).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));
		for(Light light : intersection.getWorld().lights){
			Vector wi = light.getDirection(intersection).normalize();
			//System.out.println("direction light = " + wi);
			Vector n = intersection.getNormal();
			Vector normal = n;
			if(wi.dot(n)> Math.PI) {normal = n.scale(-1);}
			double ndotwi = normal.dot(wi); 
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
