package lights;

import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public class Ambient extends Light{

	public Ambient(RGBColor color, double ls){
		super(color, ls);
	}
	
	public Ambient(){
		super(new RGBColor(1,1,1),3);
	}
	//schoon ze
	@Override
	public Vector getDirection(Intersection intersection) {
		return null;
	}
	
	

}
