package boundingBox;

import math.Point;
import shape.Shape;

public class ShapeBox extends BoundingBox {

	Shape shape;
	
	public ShapeBox(Point min, Point max, Shape shape) {
		super(min, max);
		this.shape = shape;
	}
	
	
}
