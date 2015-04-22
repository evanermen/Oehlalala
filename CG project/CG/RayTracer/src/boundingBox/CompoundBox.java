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
		double t = Double.MAX_VALUE;
		//double tempt = hit(ray);
		BoundingBox bbox1 = boundingboxes.get(0);
		BoundingBox bbox2 = boundingboxes.get(1);

		if(bbox2 == null){ System.out.println("oei bbox2 is null");}
		
		//double tempt = hit(ray);
		double t1 = bbox1.hit(ray);
		double t2 = bbox2.hit(ray);

		if(t2<t1){
			double tempt = t2;
			BoundingBox tempbox = bbox2;
			t2 = t1;
			t1 = tempt;
			bbox2 = bbox1;
			bbox1 = tempbox;
		}

		if(t1 != -1 && t1 <= ray.t){
			Intersection temp_intersection = bbox1.intersect(ray);
			if(temp_intersection != null ){ //&& temp_intersection.t < ray.t
				t = temp_intersection.t;
				intersection = temp_intersection;
			}
		}

		if(t2 != -1 && t2 <= ray.t){
			Intersection temp_intersection = bbox2.intersect(ray);
			if(temp_intersection != null && temp_intersection.t < t){ //&& temp_intersection.t < ray.t
				intersection = temp_intersection;
			}
		}

		/**
		if(tempt != -1 && tempt < ray.t){
			for(BoundingBox box : boundingboxes){
				Intersection temp_intersection = box.intersect(ray);
				if(temp_intersection != null && temp_intersection.t < t){
					intersection = temp_intersection;
					t = temp_intersection.t;
					ray.t = intersection.t;
				}
			}
		}

*/
		if(intersection != null){ ray.t = intersection.t;}
		return intersection;
	}

	//intersection return whichever intersection is the smallest of all boundingboxes

}
