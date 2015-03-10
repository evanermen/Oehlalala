package materials;

import textures.Texture;
import utils.Intersection;
import utils.RGBColor;

public class LightMaterial extends Material {

	public LightMaterial(Texture texture) {
		super(texture);
	}

	@Override
	public RGBColor shade(Intersection intersection) {
		return super.texture.getColor(intersection);
	}
	
	public boolean stopsLight(){return false;}

}
