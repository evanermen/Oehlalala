package boundingBox;

import java.util.ArrayList;

import math.Point;
/**
 * A coumpound box is a bounding box around other bounding boxes - either shape or compound boxes. The boxes are globally axis aligned.
 * 
 * @author eline vanermen
 *
 */
public class CompoundBox extends BoundingBox {


	ArrayList<BoundingBox> boundingboxes = new ArrayList<BoundingBox>();
	
	public CompoundBox(Point min, Point max) {
		super(min, max);
		// TODO Auto-generated constructor stub
	}

	
	
	//intersection return whichever intersection is the smallest of all boundingboxes
	
}
