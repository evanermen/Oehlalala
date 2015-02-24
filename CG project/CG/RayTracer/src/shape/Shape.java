package shape;

import materials.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import utils.Intersection;
import utils.RGBColor;
import world.World;

/**
 * Interface which should be implemented by all {@link Shape}s.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public abstract class Shape {
	
	private Material material;
	
	private Transformation transformation;
	
	private World world;
	
	public Shape(Transformation transformation, Material material){
		if (transformation == null)
			throw new NullPointerException("the given transformation is null!");
		if (material == null)
			throw new NullPointerException("the given material is null!");
		this.transformation = transformation;
		this.material = material;
	}
	
	/**
	 * Returns whether the given {@link Ray} intersects this {@link Shape}.
	 * False when the given ray is null.
	 * 
	 * @param ray
	 *            the ray to intersect with.
	 * @return true when the given {@link Ray} intersects this {@link Shape}.
	 */
	public abstract Intersection intersect(Ray ray);
	
	public abstract RGBColor getColor(Point point);
	
	public abstract Transformation getTransformation();
	
	public abstract Vector getNormal(Point point);
	
	public abstract Material getMaterial();
	
	public World getWorld(){return world;}

	public void setWorld(World world) {
		// TODO Auto-generated method stub
		
	}
}
