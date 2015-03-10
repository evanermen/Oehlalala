package textures;

import utils.Intersection;
import utils.RGBColor;

public class ConstantColor extends Texture {

	RGBColor color;
	
	public ConstantColor(RGBColor color){
		this.color = color;
	}
	
	@Override
	public RGBColor getColor(Intersection intersection) {
		return color;
	}

}
