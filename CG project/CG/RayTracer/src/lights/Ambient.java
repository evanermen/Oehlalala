package lights;

import materials.Emissive;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class Ambient extends Light{

	public Ambient(Emissive emissive){
		super(emissive);
	}
	
	public Ambient(){
		super(new Emissive(1, new RGBColor(1,1,1)));
	}
	//schoon ze
	@Override
	public Vector getDirection(Intersection intersection) {
		return null;
	}
	
	

}
