package boundingBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import math.Point;
import shape.Shape;
import world.World;

public class BBoxCreator {

	public World world;
	public ArrayList<ShapeBox> shapeboxes = new ArrayList<ShapeBox>();
	public BoundingBox bigbox;
	private int sortcount;




	public BBoxCreator(World world){
		this.world = world;
		this.sortcount = 0;
	}


	public BoundingBox createBBoxHierarchy(){
		createShapeBoxes();
		CompoundBox empty_bigbox = createBigBox(shapeboxes);
		bigbox = splitBox(empty_bigbox, shapeboxes);
		return bigbox;  //grootste bbox

	}


	private void createShapeBoxes() {
		for(Shape shape : world.objects){
			shape.createBBox(this);		
		}
	}


	private BoundingBox splitBox(CompoundBox box, ArrayList<ShapeBox> sboxes){
		//System.out.println("sboxes size = " + sboxes.size());
		if(sboxes.size()==1){return sboxes.get(0);}
		else{
			ArrayList<ShapeBox> sortedsboxes = sortList(sboxes, box.min, box.max);
			ArrayList<ShapeBox> list1 = new ArrayList<ShapeBox>(sortedsboxes.subList(0, sortedsboxes.size()/2));
			ArrayList<ShapeBox> list2 = new ArrayList<ShapeBox>(sortedsboxes.subList(sortedsboxes.size()/2, sortedsboxes.size()));

			CompoundBox box1 = createBigBox(list1);
			CompoundBox box2 = createBigBox(list2);

			box.boundingboxes.add(splitBox(box1, list1));
			box.boundingboxes.add(splitBox(box2, list2));

			return box; //
		}
	}



	private ArrayList<ShapeBox> sortList(ArrayList<ShapeBox> sboxes, Point min, Point max) {

		if(max.x-min.x >= max.y-min.y && max.x-min.x >= max.z-min.z){

			Collections.sort(sboxes, new Comparator<ShapeBox>() {
				@Override
				public int compare(ShapeBox  box1, ShapeBox box2)
				{
					if(box1.min.x > box2.min.x){ return 1;}
					else if(box1.min.x == box2.min.x){ return 0;}
					else {return -1;}
				}
			});
			
			sortcount ++;
			return sboxes;}
		
		
		else if(max.y-min.y >= max.z-min.z){

			Collections.sort(sboxes, new Comparator<ShapeBox>() {
				@Override
				public int compare(ShapeBox  box1, ShapeBox box2)
				{
					if(box1.min.y > box2.min.y){ return 1;}
					else if(box1.min.y == box2.min.y){ return 0;}
					else {return -1;}
				}
			});

			sortcount ++;
			return sboxes;}
		
		else if(max.z-min.z >= max.y-min.y){

			Collections.sort(sboxes, new Comparator<ShapeBox>() {
				@Override
				public int compare(ShapeBox  box1, ShapeBox box2)
				{
					if(box1.min.z > box2.min.z){ return 1;}
					else if(box1.min.z == box2.min.z){ return 0;}
					else {return -1;}
				}
			});

			sortcount ++;
			return sboxes;}
		
		else {throw new IllegalStateException("There is something wrong with yo splitting!");}
	}


	private CompoundBox createBigBox(ArrayList<? extends BoundingBox> bboxes){
		double xmax = Double.NEGATIVE_INFINITY;
		double ymax = Double.NEGATIVE_INFINITY;
		double zmax = Double.NEGATIVE_INFINITY;
		double xmin = Double.POSITIVE_INFINITY;
		double ymin = Double.POSITIVE_INFINITY;
		double zmin = Double.POSITIVE_INFINITY;

		for(BoundingBox box : bboxes){
			if(box.min.x < xmin) xmin = box.min.x;
			if(box.min.y < ymin) ymin = box.min.y;
			if(box.min.z < zmin) zmin = box.min.z;

			if(box.max.x > xmax) xmax = box.max.x;
			if(box.max.y > ymax) ymax = box.max.y;
			if(box.max.z > zmax) zmax = box.max.z;

		}

		//System.out.println("creatBigBox: " + xmin + ", " + ymin + ", " + zmin + "   ;    " + xmax + ", " + ymax + ", " + zmax + ", "  );
		return new CompoundBox(new Point(xmin,ymin,zmin), new Point(xmax,ymax,zmax));


	}

}
