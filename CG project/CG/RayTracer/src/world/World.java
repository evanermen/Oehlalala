package world;

import java.util.ArrayList;
import java.util.List;

import lights.Light;
import math.Vector;
import shape.Shape;
import camera.Camera;

public class World {

	private Vector backgroundColor;
	//private Light ambient;
	private List<Shape> objects;
	private List<Light> lights;
	
	public World(){
		backgroundColor = new Vector();
		//ambient = New Ambient();
		objects = new ArrayList<Shape>();
		lights = new ArrayList<Light>();
		
		
		
		
		
	}
}
