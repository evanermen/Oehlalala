package boundingBox;

import java.util.ArrayList;

import math.Point;

public class CompoundBox extends BoundingBox {


	ArrayList<BoundingBox> boundingboxes = new ArrayList<BoundingBox>();
	
	public CompoundBox(Point min, Point max) {
		super(min, max);
		// TODO Auto-generated constructor stub
	}

	
	
	//intersection return whichever intersection is the smallest of all boundingboxes
	
}
