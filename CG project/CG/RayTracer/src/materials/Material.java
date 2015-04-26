package materials;

import java.util.ArrayList;

import brdf.Lambertian;
import lights.AreaLight;
import lights.Light;
import lights.PointLight;
import math.Point;
import math.Ray;
import math.Vector;
import samplers.Sampler;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public abstract class Material {

	public Texture texture;
	public double kd;
	public double ka;
	public Lambertian diffuse; 
	public Lambertian ambient;

	
	public Material(Texture texture){
		this.texture = texture;
	}
	
	public Material(Texture texture, double kd, double ka){
		this.texture = texture;
		this.kd = kd;
		this.ka = ka;
		this.diffuse = new Lambertian(texture, kd);
		this.ambient = new Lambertian(texture, ka);
	}
	

	public abstract RGBColor specificShade(Light light,Intersection intersection, Vector w0, Vector w1, double ndotwi);
	
	public abstract RGBColor specificAreaShade(Light light, Intersection intersection,
			Vector w0, Vector wi, double ndotwi);
	
	
	public boolean stopsLight(){ return true;}
	
	public RGBColor shade(Intersection intersection) {

		Vector w0 = intersection.ray.direction.scale(-1).normalize();
		RGBColor l = ambient.rho(intersection, w0).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));
		for(Light light : intersection.getWorld().lights){
			Vector wi = light.getDirection(intersection).normalize();
			Vector n = intersection.getNormal();
			Vector normal = intersection.getNormal().normalize();//TODO normalize nodig?
			if(wi.dot(n)> Math.PI) {normal = n.scale(-1);}
			double ndotwi = normal.dot(wi); 
			if(ndotwi > 0.0){
				boolean inShadow = false;
				if(light.castShadows()){
					Ray shadowRay = new Ray(intersection.point, wi);
					inShadow = ((PointLight)light).inShadow(shadowRay, intersection);			
				}
				if(!inShadow){
					//deesverschilt
					RGBColor l1 = specificShade(light, intersection, w0, wi, ndotwi);
					//RGBColor l1 = light.getRadiance(intersection);
					l = l.add(l1);
				}
				//else System.out.println(intersection.shape + " is in shadow");
			}

		}
		//RGBColor m = l.maxToOne();
		return l.maxToOne();
	}


	public RGBColor areaShade(Intersection intersection, Sampler sampler) {
		Vector w0 = intersection.ray.direction.scale(-1).normalize();
		RGBColor color = ambient.rho(intersection, w0).multiply(intersection.getWorld().ambientLight.getRadiance(intersection));	
		for(Light light : intersection.getWorld().lights){
			//hier komen de samples. LET OP SET EERST SAMPLE POINT en dan getdirection aanroepen
			ArrayList<Point> samplePoints = ((AreaLight)light).rect.sample(sampler);
			for(Point sample : samplePoints){
				RGBColor l = new RGBColor(0,0,0);
				((AreaLight)light).samplePoint = sample;
				Vector wi = light.getDirection(intersection).normalize();
				Vector n = intersection.getNormal();
				if(wi.dot(n)> Math.PI) {n = n.scale(-1);}
				double ndotwi = n.dot(wi); 
				if(ndotwi > 0.0){
					boolean inShadow = false;
					if(light.castShadows()){
						Ray shadowRay = new Ray(intersection.point, wi);
						inShadow = ((AreaLight)light).inShadow(shadowRay, intersection);			
					}
					if(!inShadow){
						RGBColor l2 = specificShade(light, intersection, w0, wi, ndotwi );
						if(l2.r>=0 && l2.g>=0 && l2.b>=0){
							l = l.add(l2);
						}
						
						//System.out.println("L is = " + l.r + " "+ l.g + " "+ l.b);

					}
					//else System.out.println(intersection.shape + " is in shadow");
				}

				color = color.add(l.scale(1.0/sampler.nbSamples)); //maxToOnebij?
				//System.out.println("Color is = " + color.r + " "+ color.g + " "+ color.b);
			}
		}

		return color.maxToOne();
	}

	
}
