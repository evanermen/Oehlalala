package materials;

import utils.Intersection;
import utils.RGBColor;

public abstract class Material {

	public RGBColor color;
	
	public Material(RGBColor color){
		
		this.color = color;
	}
	
	public Material(){
		this.color = new RGBColor(0,1,1);
	}
	
	
	public boolean stopsLight(){ return true;}
	
	public abstract RGBColor shade(Intersection intersection );
	
}
