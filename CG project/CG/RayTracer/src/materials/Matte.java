package materials;

import lights.AreaLight;
import lights.Light;
import math.Vector;
import textures.ConstantColor;
import textures.Texture;
import utils.Intersection;
import utils.RGBColor;
import brdf.Lambertian;

public class Matte extends Material {

	public Matte(Texture texture, double kd, double ka) {
		super(texture, kd, ka);
		if (ka + kd > 1.0)
			throw new IllegalArgumentException(
					"kd and ka added together can't be more than 1.");

	}

	public Matte(Texture texture) {
		super(texture, 0.8, 0.2);
	}

	public Matte() {
		super(new ConstantColor(new RGBColor(1, 0, 0)), 0.8, 0.2);
	}

	@Override
	public RGBColor specificShade(Light light, Intersection intersection,
			Vector w0, Vector wi, double ndotwi) {
		RGBColor l1 = light.getRadiance(intersection);
		RGBColor l2 = diffuse.f(intersection, w0, wi).multiply(l1)
				.scale(ndotwi);
		return l2;
	}

	@Override
	public RGBColor specificAreaShade(Light light, Intersection intersection,
			Vector w0, Vector wi, double ndotwi) {
		RGBColor l1 = light.getRadiance(intersection);
		// / HIER moet die l2 niet eerst geadd worden en dan pas multiply en
		// shit? Nee
		RGBColor l2 = (diffuse.f(intersection, w0, wi)).multiply(l1)
				.scale(((AreaLight) light).G(intersection))
				.scale(ndotwi / ((AreaLight) light).pdf(intersection));
		return l2;
	}

}
