package materials;

import utils.Intersection;
import utils.RGBColor;

public class LightMaterial extends Material {

	public LightMaterial(RGBColor color) {
		super(color);
	}

	@Override
	public RGBColor shade(Intersection intersection) {
		return super.color;
	}
	
	public boolean stopsLight(){return false;}

}
