package boundingBox;

import math.Point;
import math.Ray;
import math.Transformation;
import shape.Shape;
import utils.Intersection;
/**
 * A shape box is a bounding box directly around a shape. The box is Axis aligned for the UNTRANSFORMED shape!
 * @author eline vanermen
 *
 */
public class ShapeBox extends BoundingBox {

	Shape shape;
	
	public ShapeBox(Point min, Point max, Shape shape) {
		super(min, max);
		this.shape = shape;
	}
	
/*
	public boolean hit(Ray ray){
		Ray transformed = shape.transformation.transformInverse(ray);
		return super.hit(transformed);
	}
*/

	@Override
	public Intersection intersect(Ray ray) {
		return shape.intersect(ray);
	}
	//intersection method needs backtransformation of ray first!!! AH JA?? Transfomation zit in shape!!
	
}
