package lights;



import materials.Emissive;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;

public abstract class Light {
	public Emissive emissive;
	
	public Light(){
		this.emissive = new Emissive(3, new RGBColor(1,1,1));
		//System.out.println("Color is set to " + color.r + ", " + color.g + ",  " + color.b);
	}
	
	public Light(Emissive emissive){
		this.emissive = emissive;
	}
	
	public RGBColor getRadiance(Intersection intersection) {
		return emissive.ce.scale(emissive.ls);
	}
	
	public abstract Vector getDirection(Intersection intersection);

	public boolean castShadows() {
		return true;
	}

	
}
