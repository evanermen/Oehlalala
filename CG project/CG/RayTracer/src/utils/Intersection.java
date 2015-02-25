package utils;

import materials.Material;
import math.Point;
import math.Ray;
import math.Vector;
import shape.Shape;
import world.World;

public class Intersection {
	
	public double t; //distance
	public Ray ray;
	public Shape shape;
	public Point point; //Niet getransformeerd! dus intersectiepunt met basisvolume rond oorsprong
	public Vector barys;

	public Intersection(Ray ray, double t, Shape shape ){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		Ray transformed = shape.getTransformation().transformInverse(ray);
		point = transformed.origin.add(transformed.direction.scale(t));	
		
	}
	
	
	//enkel voor triangles. Overerving anders?
	public Intersection(Ray ray, double t, Shape shape ,  Vector barys){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		Ray transformed = shape.getTransformation().transformInverse(ray);
		point = transformed.origin.add(transformed.direction.scale(t));	
		this.barys = barys;
		
	}
	
	
	public RGBColor getColor(){
		return shape.getColor(point);
	}
	
	public Vector getNormal(){
		return shape.getNormal(this);
	}
	
	public Material getMaterial(){
		return shape.getMaterial();
	}
	
	public World getWorld(){
		return shape.getWorld();
	}
}
