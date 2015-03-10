package materials;

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
	
}
