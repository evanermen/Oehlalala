package boundingBox;

import math.Point;
import math.Transformation;
import shape.Shape;
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
	
	public Point getMin(){
		return this.shape.transformation.transform(min);
	}
	
	public Point getMax(){
		return this.shape.transformation.transform(max);
	}
	
	
	//intersection method needs backtransformation of ray first!!! Transfomation zit in shape!!
	
}
