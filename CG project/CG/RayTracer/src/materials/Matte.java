package materials;

import java.util.ArrayList;

import lights.AreaLight;
import lights.Light;
import lights.PointLight;
import math.Point;
import math.Ray;
import math.Vector;
import samplers.Sampler;
import textures.ConstantColor;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;
import brdf.Lambertian;

public class Matte extends Material {

	public Lambertian diffuse; 
	public Lambertian ambient;



	public Matte(Texture texture, double kd, double ka) {
		super(texture);
		if(ka + kd > 1.0)
			throw new IllegalArgumentException("kd and ka added together can't be more than 1.");
		this.diffuse = new Lambertian(texture, kd);
		this.ambient = new Lambertian(texture, ka);
	}

	public Matte(Texture texture){
		super(texture);
		this.diffuse = new Lambertian(texture);
		this.ambient = new Lambertian(texture);
	}

	public Matte(){
		super(new ConstantColor(new RGBColor(1,1,1)));
		Texture texture = new ConstantColor(new RGBColor(1,1,1));
		this.diffuse = new Lambertian(texture);
		this.ambient = new Lambertian(texture);
	}





	//HIER ELINE de shading van plane verloopt niet goed. check normaal en slides lecture 4 slide 29
	@Override
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
					RGBColor l1 = light.getRadiance(intersection);
					l = l.add((diffuse.f(intersection, w0, wi)).multiply(l1).scale(ndotwi));
				}
				//else System.out.println(intersection.shape + " is in shadow");
			}

		}
		RGBColor m = l.maxToOne();
		return l.maxToOne();
	}

	@Override
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
				Vector normal = intersection.getNormal().normalize();//TODO normalize nodig?
				if(wi.dot(n)> Math.PI) {normal = n.scale(-1);}
				double ndotwi = normal.dot(wi); 
				if(ndotwi > 0.0){
					boolean inShadow = false;
					if(light.castShadows()){
						Ray shadowRay = new Ray(intersection.point, wi);
						inShadow = ((AreaLight)light).inShadow(shadowRay, intersection);			
					}
					if(!inShadow){
						RGBColor l1 = light.getRadiance(intersection);
						RGBColor l2 = (diffuse.f(intersection, w0, wi)).multiply(l1).scale(((AreaLight)light).G(intersection)).scale(ndotwi/((AreaLight)light).pdf(intersection));
						if(l2.r>0 && l2.g>0 && l2.b>0){
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
