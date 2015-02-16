package world;

import java.util.ArrayList;
import java.util.List;

import lights.Light;
import math.Vector;
import utils.RGBColor;


import shape.Shape;

public class World {

	public RGBColor bg;
	//private Light ambient;
	public List<Shape> objects;
	public List<Light> lights;
	
	public World(){
		bg = new RGBColor();
		//ambient = New Ambient();
		objects = new ArrayList<Shape>();
		lights = new ArrayList<Light>();	
	}
	
	public World(RGBColor backgroundColor, ArrayList<Shape> objects, ArrayList<Light> lights ){
		this.bg = backgroundColor;
		this.objects = objects;
		this.lights = lights;
	}
	
	public void addObject(Shape shape){
		this.objects.add(shape);
	}
	
	public void addLight(Light light){
		this.lights.add(light);
	}
}
