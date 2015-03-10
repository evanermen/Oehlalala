package materials;

import java.util.List;

import lights.Light;
import lights.PointLight;
import math.Ray;
import math.Vector;
import textures.Texture;
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



	public Phong(Texture color, double kd, double ka, double ks, double exponent){
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
		RGBColor l0 = ambientBrdf.rho(intersection, wo).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));
		List<Light> lights = intersection.getWorld().lights;
		for( Light light : lights){
			Vector wi = light.getDirection(intersection).normalize();
			double ndotwi = intersection.normal.dot(wi);
			if(ndotwi > 0.0){
				boolean inShadow = false;
				if(light.castShadows()){
					Ray shadowRay = new Ray(intersection.point, wi); 
					inShadow = ((PointLight)light).inShadow(shadowRay, intersection);
				}
				if(!inShadow){
					l0 = l0.add(((diffuseBrdf.f(intersection, wo, wi)).add(specularBrdf.f(intersection, wi, wo))).multiply(light.getRadiance(intersection).scale(ndotwi)));
				}
				//else System.out.println(intersection.shape + " is in shadow");
			}
		}
		return l0.maxToOne();
	}

}
