package materials;

import utils.Intersection;
import utils.RGBColor;

public abstract class Material {

	public RGBColor color;
	
	public Material(RGBColor color){
		
		this.color = color;
	}
	
	
	public abstract RGBColor shade(Intersection intersection );
	
}
