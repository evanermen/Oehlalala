package boundingBox;

import java.util.ArrayList;

import math.Point;
import math.Ray;
import utils.Intersection;
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

	@Override
	public Intersection intersect(Ray ray) {
		Intersection intersection = null;
		double t = Double.POSITIVE_INFINITY;
		if(hit(ray)){
			for(BoundingBox box : boundingboxes){
				Intersection temp_intersection = box.intersect(ray);
				if(temp_intersection != null && temp_intersection.t < t){
					intersection = temp_intersection;
					t = temp_intersection.t;
				}
			}
		}
		return intersection;
	}

	
	
	//intersection return whichever intersection is the smallest of all boundingboxes
	
}
