package materials;

import samplers.Sampler;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public abstract class Material {

	public Texture texture;
	
	public Material(Texture texture){
		this.texture = texture;
	}
	

	
	public boolean stopsLight(){ return true;}
	
	public abstract RGBColor shade(Intersection intersection );



	public abstract RGBColor areaShade(Intersection rayIntersection, Sampler sampler);
	//Haal deel van de functie naar hier svp!!
	
}
