package textures;

import utils.Intersection;
import utils.RGBColor;

public abstract class Texture {
	
	public abstract RGBColor getColor(Intersection intersection);

}
