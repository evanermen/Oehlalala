package shape;

import java.util.ArrayList;

import materials.Material;
import materials.Matte;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import world.World;

public class TriangleMesh extends Shape {
	
	
	public ArrayList<TriangleM> triangles = new ArrayList<TriangleM>();
	Intersection currentIntersection = null;
	
	public TriangleMesh(Transformation transformation, Material material){
		super(transformation, material);		
		for(TriangleM triangle: triangles){
			setTransformation(transformation);
			triangle.material = material;
		}
		//triangles.add(new TriangleM(new Point(-1,0,1), new Point(1, -0, 1), new Point(-1,0,-1), new Vector(0,1,0), new Vector(0,1,0), new Vector(0,1,0)));
		//triangles.add(new TriangleM(new Point(1,-0,1), new Point(1, 0, -1), new Point(-1, 0,-1), new Vector(0,1,0), new Vector(0,1,0), new Vector(0,1,0)));
	
		//triangles.add(new TriangleM());
		//triangles.add(new TriangleM(new Point(0,0,0), new Point(0, -10, 10), new Point(0,-10,-10), new Vector(1,0,0), new Vector(0,0,1), new Vector(0,1,0)));
		//triangles.add(new TriangleM(new Point(0,0,0), new Point(0, -10, -10), new Point(0, 10,-10), new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1)));
	}
	
	public TriangleMesh(){
		super(Transformation.createIdentity(), new Matte());
		for(TriangleM triangle: triangles){
			setTransformation(Transformation.createIdentity());
			triangle.material = new Matte();
		}
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		

		Double smallestT = Double.POSITIVE_INFINITY;
		Intersection rayIntersection = null;
		for (TriangleM triangle : triangles){
			Intersection intersection = triangle.intersect(ray);
			if (intersection != null && intersection.t < smallestT ) {
				smallestT = intersection.t;
				rayIntersection = intersection;				
			}
		}
		this.currentIntersection = rayIntersection;
		return rayIntersection;
	}

	@Override
	public RGBColor getColor(Point point) {
		return new RGBColor(0,1,1);
	}


	@Override
	public Vector getNormal(Point point) {
		//System.out.println("getting normal in traingleMesh");
		return currentIntersection.shape.getNormal(point);
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
		for(TriangleM triangle: triangles){
			triangle.transformation = transformation;			
		}
		
	}
	
	public void setMaterial(Material material) {
		this.material = material;
		for(TriangleM triangle: triangles){
			triangle.material = material;			
		}
		
	}
	
	public void setWorld(World world){
		super.setWorld(world);
		for(TriangleM triangle: triangles) {
			triangle.setWorld(world);
		}
	}



}
