package shape;

import math.Ray;
import math.Transformation;

public class Torus implements Shape{
	Transformation transformation;
	double bigRadius;
	double smallRadius;
	
	public Torus(Transformation transformation, double bigRadius, double smallRadius){
		if (transformation == null)
			throw new NullPointerException("the given origin is null!");
		if (bigRadius < 0)
			throw new IllegalArgumentException(
					"the given bigRadius cannot be smaller than zero!");
		if (smallRadius < 0)
			throw new IllegalArgumentException(
					"the given smallRadius must be between 0 and 2*Pi cannot be smaller than zero!");
		this.transformation = transformation;
		this.bigRadius = bigRadius;
		this.smallRadius = smallRadius;
	

}

	@Override
	public boolean intersect(Ray ray) {
		// TODO Auto-generated method stub
		return false;
	}
}