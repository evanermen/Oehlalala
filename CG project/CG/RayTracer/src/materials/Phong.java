package materials;

import java.util.List;

import lights.Light;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import brdf.GlossySpecular;
import brdf.Lambertian;

public class Phong extends Material {

	private double kd;
	private double ka;
	private double exponent;
	private Lambertian ambientBrdf;
	private Lambertian diffuseBrdf;
	private GlossySpecular specularBrdf;



	public Phong(RGBColor color, double kd, double ka, double ks, double exponent){
		super(color);
		this.kd = kd;
		this.ka = ka;
		this.exponent = exponent;
		this.ambientBrdf = new Lambertian(color, ka);
		this.diffuseBrdf = new Lambertian(color, kd);
		this.specularBrdf = new GlossySpecular(color, ks, exponent);
	}
	
	

	@Override
	public RGBColor shade(Intersection intersection) {
		Vector wo = intersection.ray.direction.scale(-1).normalize();
		RGBColor lamb = ambientBrdf.rho(intersection, wo).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));
		List<Light> lights = intersection.getWorld().lights;
		RGBColor l0 = new RGBColor(0,0,0);
		for( Light light : lights){
			Vector wi = light.getDirection(intersection).normalize();
			double ndotwi = intersection.normal.dot(wi);
			System.out.println(ndotwi);
			if(ndotwi > 0.0){
				
				l0 = l0.add((diffuseBrdf.f(intersection, wo, wi)).add(specularBrdf.f(intersection, wi, wo))).multiply(light.getRadiance(intersection).scale(ndotwi));
			
				//System.out.println("l =" + new Vector(l));
			
			}
			
		}
		return l0.add(lamb);
	}

}
