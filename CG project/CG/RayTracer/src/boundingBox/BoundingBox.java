package boundingBox;

import math.Point;

public class BoundingBox {

	int level;  //for tracing
	Point min;
	Point max;

	public BoundingBox(Point min, Point max){
		this.min = min;
		this.max = max;
	}

	public int compare(BoundingBox  box2){
		if(this.min.x > box2.min.x){ return 1;}
		else if(this.min.x == box2.min.x){ return 0;}
		else {return -1;}

	}
}

//method hit
//method intersect

