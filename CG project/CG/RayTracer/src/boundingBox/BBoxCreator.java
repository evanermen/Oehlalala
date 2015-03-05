package boundingBox;

import java.util.ArrayList;

import math.Point;
import shape.Shape;
import world.World;

public class BBoxCreator {
	
	public World world;
	public ArrayList<ShapeBox> shapeboxes = new ArrayList<ShapeBox>();
	
	
	
	
	public BBoxCreator(World world){
		this.world = world;
	}
	
	
	public BoundingBox createBBoxHierarchy(){
		createShapeBoxes();
		BoundingBox bigbox = createBigBox(shapeboxes);
		
		
		
		return null;  //grootste bbox
		
	}
	
	
	private void createShapeBoxes() {
		for(Shape shape : world.objects){
			shape.createBBox(this);		
		}
		
	}


	public BoundingBox splitBox(BoundingBox box){
		return null; //
	}
	
	public BoundingBox createBigBox(ArrayList<? extends BoundingBox> bboxes){
		double xmax = Double.NEGATIVE_INFINITY;
		double ymax = Double.NEGATIVE_INFINITY;
		double zmax = Double.NEGATIVE_INFINITY;
		double xmin = Double.POSITIVE_INFINITY;
		double ymin = Double.POSITIVE_INFINITY;
		double zmin = Double.POSITIVE_INFINITY;
		
		for(BoundingBox box : bboxes){
			if(box.getMin().x < xmin) xmin = box.getMin().x;
			if(box.getMin().y < xmin) xmin = box.getMin().x;
			if(box.getMin().z < xmin) xmin = box.getMin().x;
			
			if(box.getMax().x > xmax) xmax = box.getMax().x;
			if(box.getMax() > xmax) xmax = box.getMax().y;
			if(box.getMax() > xmax) xmax = box.getMax().z;
			
		}
		
		return new CompoundBox(new Point(xmin,ymin,zmin), new Point(xmax,ymax,zmax));
		
		/**
		Collections.sort(bboxes, new Comparator<ShapeBox>() {
	        @Override
	        public int compare(ShapeBox  box1, ShapeBox box2)
	        {
	        	if(box1.min.x > box2.min.x){ return 1;}
	    		else if(box1.min.x == box2.min.x){ return 0;}
	    		else {return -1;}
	        }
	    });
		double Xmin = shapeboxes.get(0).min.x;
		double Xmax = shapeboxes.get(shapeboxes.size()-1).min.x;
		*/
	}

}
