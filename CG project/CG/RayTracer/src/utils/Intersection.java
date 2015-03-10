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
	public Point point; //WEL getransformeerd! 
	public Vector normal;
	public Vector barys;
	public double u;
	public double v;

	public Intersection(Ray ray, double t, Shape shape, Point point, Vector normal ){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		this.point = point;
		this.normal = normal;
	}
	
	public Intersection(Ray ray, double t, Shape shape, Point point, Vector normal, Vector barys ){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		this.point = point;
		this.normal = normal;
		this.barys = barys;
	}
	
	public Intersection(Ray ray, double t, Shape shape, Point point, Vector normal, Vector barys, double u, double v ){
		this.ray = ray;
		this.t = t;
		this.shape = shape; 
		this.point = point;
		this.normal = normal;
		this.barys = barys;
		this.u = u;
		this.v = v;
	}
	
	
	public RGBColor getColor(){
		return shape.getColor(point);
	}
	
	public Vector getNormal(){
		return normal;
	}
	
	public Material getMaterial(){
		return shape.getMaterial();
	}
	
	public World getWorld(){
		return shape.getWorld();
	}
	
}
