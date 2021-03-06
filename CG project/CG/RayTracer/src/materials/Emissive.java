package materials;

import lights.Light;
import math.Vector;
import samplers.Sampler;
import textures.ConstantColor;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public class Emissive extends Material {

	public double ls;
	public RGBColor ce;


	public Emissive(Texture texture) {
		super(texture);
	}

	public Emissive(double ls, RGBColor ce){
		super(new ConstantColor(ce.scale(ls)));
		this.ls = ls;
		this.ce = ce;

	}


	@Override
	public RGBColor shade(Intersection intersection) {
		double temp = intersection.normal.scale(-1).dot(intersection.ray.direction);
		if(temp > 0.0){
			if(ce != null){
				return ce.scale(ls);
			}
			else{ 
				return texture.getColor(intersection);
			}
		}
		else{return new RGBColor(0.0,0.0,0.0);}
	}

	//stopt eigenlijk wel licht maar dan moet er ergens bij intersection ofzo iets zijn dat ge niet u eigen licht moogt stoppen ofzo
	public boolean stopsLight(){return false;}



	@Override   // er is geen schaduw op iets lichtgevends?
	public RGBColor areaShade(Intersection rayIntersection, Sampler sampler) {
		if(rayIntersection.normal.scale(-1).dot(rayIntersection.ray.direction) > 0.0){
			//return ce.scale(ls);
			return ce;
		}
		else return new RGBColor(0,0,0);
	}

	
	public RGBColor getLe(Intersection intersection){
		//return ce.scale(ls);  //u lampen worden grijs zo
		return ce;
	}

	@Override
	public RGBColor specificShade(Light light, Intersection intersection,
			Vector w0, Vector w1, double ndotwi) {
		System.out.println("You're really not supposed to get here. Yay for bad inheritance");
		return null;
	}

	@Override
	public RGBColor specificAreaShade(Light light, Intersection intersection,
			Vector w0, Vector wi, double ndotwi) {
		System.out.println("You're really not supposed to get here. Yay for bad inheritance");
		return null;
	}






}
